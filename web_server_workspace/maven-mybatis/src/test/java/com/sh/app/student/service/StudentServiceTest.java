package com.sh.app.student.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.sh.app.student.entity.Student;
import com.sh.app.student.repository.StudentRepositoryImpl;

@TestMethodOrder(OrderAnnotation.class)
class StudentServiceTest {
	private StudentService studentService;
	
	@BeforeEach
	void setUp() throws Exception {
		this.studentService = new StudentServiceImpl(new StudentRepositoryImpl());
	}

	@DisplayName("학생 전체 조회")
	@Test
	void test() {
		List<Student> students = studentService.findAll();
		assertThat(students).isNotNull().isNotEmpty();
		
		// 행별 테스트 (jdk8 lambda)
		assertThat(students).allSatisfy((student) -> {
			assertThat(student.getId()).isNotEqualTo(0);
			assertThat(student.getName()).isNotNull();
			assertThat(student.getTel()).isNotNull();
			assertThat(student.getCreatedAt()).isNotNull();
		});
	}
	
	@DisplayName("존재하는 학생 1명 조회")
	@Test
	void testStudentIsNotNull() throws Exception {
		int id = 3;
		Student student = studentService.findById(id);
		assertThat(student).isNotNull();
		assertThat(student.getId()).isNotNull().isGreaterThan(0);
		assertThat(student.getName()).isNotNull();
		assertThat(student.getTel()).isNotNull();
		assertThat(student.getCreatedAt()).isNotNull();
	}
	
	@DisplayName("존재하지 않는 학생 1명 조회")
	@Test
	void testStudentIsNull() throws Exception {
		int id = Integer.MAX_VALUE;
		Student student = studentService.findById(id);
		assertThat(student).isNull();
	}
	
	@Order(1)
	@DisplayName("학생 등록")
	@Test
	void testInsertStudent() throws Exception {
		Student student = new Student(0, "유관순", "01099998888", null);
		int result = studentService.insertStudent(student);
		assertThat(result).isGreaterThan(0);
		int id = student.getId();
		assertThat(id).isGreaterThan(0); // 발급된 pk를 student객체 set되었는지 테스트
		
		student = studentService.findById(id);
		assertThat(student).isNotNull();
	}
	
	@Order(2)
	@DisplayName("학생 수정")
	@ParameterizedTest
	@MethodSource("studentProvider")
	void testUpdateStudent(Student student) throws Exception {
		// given 주어진상황
		assertThat(student).isNotNull();
		
		// when 수정
		int id = student.getId();
		String newName = "유관순관순";
		String newTel = "01812341234"; 
		student.setName(newName);
		student.setTel(newTel);
		int result = studentService.updateStudent(student);
		
		// then 검증
		assertThat(result).isGreaterThan(0);
		
		student = studentService.findById(id);
		assertThat(student).isNotNull();
		assertThat(student.getName()).isEqualTo(newName);
		assertThat(student.getTel()).isEqualTo(newTel);
	}
	
	@Order(3)
	@DisplayName("학생 삭제")
	@ParameterizedTest
	@MethodSource("studentProvider")
	void testDeletStudent(Student student) throws Exception {
		// given 테스트할 데이터 세팅
		assertThat(student).isNotNull();
		int id = student.getId();
		// when
		int result = studentService.deleteStudent(id);
		// then
		assertThat(result).isGreaterThan(0);
		Student other = studentService.findById(id);
		assertThat(other).isNull();
	}
	
	public static Stream<Arguments> studentProvider() {
		StudentService studentService = new StudentServiceImpl(new StudentRepositoryImpl());
		List<Student> students = studentService.findAll();
		return Stream.of(Arguments.arguments(students.get(0)));
	}

}






