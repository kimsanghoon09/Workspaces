package com.sh.app.student.service;

import java.util.List;
import java.util.Map;

import com.sh.app.student.entity.Student;

public interface StudentService {
	List<Student> findAll();

	Student findById(int id);

	int insertStudent(Student student);

	int updateStudent(Student student);

	int deleteStudent(int id);

	int getTotalCount();

	List<Student> findPage(Map<String, Object> params); 
}
