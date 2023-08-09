package com.sh.app.student.service;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.sh.app.student.entity.Student;
import com.sh.app.student.repository.StudentRepositoryImpl;

class StudentServicePagingTest {
	private StudentService studentService;
	
	@BeforeEach
	void setUp() throws Exception {
		this.studentService = new StudentServiceImpl(new StudentRepositoryImpl());
	}

	@DisplayName("전체 학생수 조회")
	@Test
	void testGetTotalCount() {
		// given
		
		// when
		int totalCount = studentService.getTotalCount();
		
		// then
		List<Student> students = studentService.findAll();
		assertThat(totalCount).isEqualTo(students.size());
	}
	
	@DisplayName("학생 페이징 조회")
	@ParameterizedTest
	@ValueSource(ints = {1,2,3,4,5,6,7,8,9,10})
	void testFindPage(int page) throws Exception {
		// when
		final int limit = 10;
//		List<Student> students = studentService.findPage(page, limit);
		
		Map<String, Object> params = new HashMap<>();
		params.put("page", page);
		params.put("limit", limit);
		List<Student> students = studentService.findPage(params);
		System.out.println(students);
		// then
		assertThat(students).size().isLessThanOrEqualTo(limit);
	}

}






