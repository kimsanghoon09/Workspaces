package com.sh.app.student.service;

import static com.sh.app.common.SqlSessionUtils.getSqlSession;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.sh.app.student.entity.Student;
import com.sh.app.student.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
	private final StudentRepository studentRepository;
	
	@Override
	public List<Student> findAll() {
		SqlSession session = getSqlSession();
		List<Student> students = studentRepository.findAll(session);
		session.close();
		return students;
	}


	@Override
	public Student findById(int id) {
		SqlSession session = getSqlSession();
		Student student = studentRepository.findById(session, id);
		session.close();
		return student;
	}
	
	@Override
	public int insertStudent(Student student) {
		SqlSession session = getSqlSession();
		int result = 0;
		try {
			// dml요청
			result = studentRepository.insertStudent(session, student);
			session.commit();
		} catch (Exception e) {
			session.rollback();
			throw e;
		} finally {
			session.close();
		}
		return result;
	}
	
	@Override
	public int updateStudent(Student student) {
		SqlSession session = getSqlSession();
		int result = 0;
		try {
			result = studentRepository.updateStudent(session, student);
			session.commit();
		} catch (Exception e) {
			session.rollback();
			throw e;
		} finally {
			session.close();
		}
		return result;
	}
	
	@Override
	public int deleteStudent(int id) {
		SqlSession session = getSqlSession();
		int result = 0;
		try {
			result = studentRepository.deleteStudent(session, id);
			session.commit();
		} catch (Exception e) {
			session.rollback();
			throw e;
		} finally {
			session.close();
		}
		return result;
	}
	
	@Override
	public int getTotalCount() {
		SqlSession session = getSqlSession();
		int totalCount = studentRepository.getTotalCount(session);
		session.close();
		return totalCount;
	}
	
	@Override
	public List<Student> findPage(Map<String, Object> params) {
		SqlSession session= getSqlSession();
		List<Student> students = studentRepository.findPage(session, params);
		session.close();
		return students;
	}
	
}
