package com.sh.app.student.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.sh.app.student.entity.Student;

public interface StudentRepository {

	List<Student> findAll(SqlSession session);

	Student findById(SqlSession session, int id);

	int insertStudent(SqlSession session, Student student);

	int updateStudent(SqlSession session, Student student);

	int deleteStudent(SqlSession session, int id);

	int getTotalCount(SqlSession session);

	List<Student> findPage(SqlSession session, Map<String, Object> params);

}
