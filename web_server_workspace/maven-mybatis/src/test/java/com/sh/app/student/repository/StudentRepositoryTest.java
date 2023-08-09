package com.sh.app.student.repository;

import static com.sh.app.common.SqlSessionUtils.getSqlSession;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sh.app.student.entity.Student;

class StudentRepositoryTest {
	private SqlSession session;
	private StudentRepository studentRepository;

	@BeforeEach
	void setUp() throws Exception {
		this.session = getSqlSession();
		this.studentRepository = new StudentRepositoryImpl();
	}

	@AfterEach
	void tearDown() throws Exception {
		this.session.close();
	}

	@Test
	void test() {
		// 테스트 
		List<Student> students = studentRepository.findAll(session);
	}

}
