package com.sh.app.emp.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.sh.app.emp.dto.EmpDto;
import com.sh.app.emp.entity.Emp;
import com.sh.app.emp.repository.EmpRepositoryImpl;

class EmpServiceTest {
	private EmpService empService;

	@BeforeEach
	void setUp() throws Exception {
		this.empService = new EmpServiceImpl(new EmpRepositoryImpl());
	}

	@DisplayName("empService 객체 생성")
	@Test
	void test() {
		assertThat(empService).isNotNull();
	}
	
	@DisplayName("전체 사원 조회")
	@Test
	void testFindAll() throws Exception {
		// when
		List<Emp> emps = empService.findAll();
		System.out.println(emps);
		// then
		assertThat(emps).isNotNull().isNotEmpty();
		assertThat(emps).allSatisfy((emp) -> {
			assertThat(emp.getEmpId()).isNotNull();
			assertThat(emp.getEmpName()).isNotNull();
			assertThat(emp.getJobCode()).isNotNull();
			assertThat(emp.getSalLevel()).isNotNull();
		});
	}
	
	@DisplayName("사원 검색 - 사원명 | 이메일 | 전화번호")
	@ParameterizedTest
	@CsvSource({
		"emp_name, 동",
		"emp_name, 이",
		"email, a",
		"phone, 010"
	})
	void testFindBySearchTypeAndSearchKeyword(String searchType, String searchKeyword) throws Exception {
		// given
		Map<String, Object> filters = Map.of(
			"searchType", searchType,
			"searchKeyword", searchKeyword
		);
		// when
		List<Emp> emps = empService.search1(filters);
		// select * from emp where emp_name like '%동%'
		// select * from emp where email like '%a%'
		// select * from emp where phone like '%010%'
		
		// then
		assertThat(emps).isNotNull().isNotEmpty();
		assertThat(emps).allSatisfy((emp) -> {
			switch(searchType) {
			case "emp_name": assertThat(emp.getEmpName()).contains(searchKeyword); break;
			case "email": assertThat(emp.getEmail()).contains(searchKeyword); break;
			case "phone": assertThat(emp.getPhone()).contains(searchKeyword); break;
			}
		});
	}
	
	@DisplayName("사원검색시 searchType, searchKeyword가 null 또는 ''인 경우")
	@ParameterizedTest
	@CsvSource(value = {
		"null, null",
		"'', 동",
		"emp_name, ''",
		"'',''"
	}, nullValues = "null")
	void testSearchTypeIsNullOrSearchKeywordIsNull(String searchType, String searchKeyword) throws Exception {
		// given
		Map<String, Object> filters = new HashMap<>();
		filters.put("searchType", searchType);
		filters.put("searchKeyword", searchKeyword);
		// when
		List<Emp> emps = empService.search1(filters);
		// then
		List<Emp> allEmps = empService.findAll();
		assertThat(emps).size().isEqualTo(allEmps.size());
	}
	
	@DisplayName("사원 성별 조회")
	@ParameterizedTest
	@ValueSource(strings={"남", "여"})
	void testFindByGender(String gender) throws Exception {
		// given
		Map<String, Object> filters = new HashMap<>();
		filters.put("gender", gender);
		// when
		List<EmpDto> emps = empService.search2(filters);
		// then
		assertThat(emps).allSatisfy((emp) -> {
			assertThat(emp.getGender()).isEqualTo(gender);
		});
	}
	
	@DisplayName("사원 성별 조회 - gender값 없음")
	@ParameterizedTest
	@NullSource
	void testFindByGender2(String gender) throws Exception {
		// given
		Map<String, Object> filters = new HashMap<>();
		filters.put("gender", gender);
		// when
		List<EmpDto> emps = empService.search2(filters);
		// then
		List<Emp> allEmps = empService.findAll();
		assertThat(emps).size().isEqualTo(allEmps.size());
	}

	@DisplayName("급여로 사원조회")
	@ParameterizedTest
	@CsvSource({
		"3000000, ge",
		"2500000, le",
		"5000000, le",
		"5000000, ge",
	})
	void testFindBySalary(Integer salary, String salaryCompare) throws Exception {
		// given
		Map<String, Object> filters = Map.of(
			"salary", salary,
			"salaryCompare", salaryCompare
		);
		// when
		List<EmpDto> emps = empService.search2(filters);
		// then
		assertThat(emps).allSatisfy((emp) -> {
			switch(salaryCompare) {
			case "ge" : assertThat(emp.getSalary()).isGreaterThanOrEqualTo(salary); break;
			case "le" : assertThat(emp.getSalary()).isLessThanOrEqualTo(salary);break;
			}
			
		});
	}
	
	
	@DisplayName("성별값이 없거나 급여값이 없는 경우 사원조회")
	@ParameterizedTest
	@CsvSource(value = {
		"null, 2_000_000, ge",
		"남, null, null",
		"여, 3_000_000, le",
		"남, 5_000_000, ge"
	}, nullValues = "null")
	void testGenderIsNullOrSalaryIsNull(String gender, Integer salary, String salaryCompare) throws Exception {
		// given
		Map<String, Object> filters = new HashMap<>();
		filters.put("gender", gender);
		filters.put("salary", salary);
		filters.put("salaryCompare", salaryCompare);
		// when
		List<EmpDto> emps = empService.search2(filters);
		// then
		assertThat(emps).allSatisfy((emp) -> {
			if(gender != null) {
				assertThat(emp.getGender()).isEqualTo(gender);
			}
			if(salary != null && salaryCompare != null) {
				switch(salaryCompare) {
				case "ge" : assertThat(emp.getSalary()).isGreaterThanOrEqualTo(salary); break; 
				case "le" : assertThat(emp.getSalary()).isLessThanOrEqualTo(salary); break;
				}
			}
		});
	}
	@DisplayName("복수개의 직급코드로 사원조회")
	@ParameterizedTest
	@MethodSource("generateJobCodes")
	void testFindByJobCodes(List<String> jobCodes) throws Exception {
		// given
		Map<String, Object> filters = Map.of("jobCodes", jobCodes);
		// when
		List<EmpDto> emps = empService.search3(filters);
		// then
		assertThat(emps).allSatisfy((emp) -> {
			assertThat(jobCodes).contains(emp.getJobCode());
		});
	}
	static Stream<Arguments> generateJobCodes(){
		return Stream.of(
			Arguments.of(Arrays.asList("J1", "J2", "J3")),
			Arguments.of(Arrays.asList("J5", "J6")),
			Arguments.of(Arrays.asList("J3"))
		);
	}
	
	
	@DisplayName("직급코드가 null인 경우 사원 전체조회")
	@ParameterizedTest
	@NullSource
	void testJobCodesIsNull(List<String> jobCodes) throws Exception {
		// given
		Map<String, Object> filters = new HashMap<>();
		filters.put("jobCodes", jobCodes);
		// when
		List<EmpDto> emps = empService.search3(filters);
		// then
		List<Emp> allEmps = empService.findAll();
		assertThat(emps).size().isEqualTo(allEmps.size());
	}

}




