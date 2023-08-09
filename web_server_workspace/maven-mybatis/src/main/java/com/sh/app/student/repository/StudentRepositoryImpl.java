package com.sh.app.student.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import com.sh.app.student.entity.Student;

public class StudentRepositoryImpl implements StudentRepository {

	@Override
	public List<Student> findAll(SqlSession session) {
		// namespace.id
		return session.selectList("student.findAll");
	}

	@Override
	public Student findById(SqlSession session, int id) {
		return session.selectOne("student.findById", id);
	}
	
	@Override
	public int insertStudent(SqlSession session, Student student) {
		return session.insert("student.insertStudent", student);
	}
	
	@Override
	public int updateStudent(SqlSession session, Student student) {
		return session.update("student.updateStudent", student);
	}
	
	@Override
	public int deleteStudent(SqlSession session, int id) {
		return session.delete("student.deleteStudent", id);
	}
	
	@Override
	public int getTotalCount(SqlSession session) {
		return session.selectOne("student.getTotalCount");
	}
	
	@Override
	public List<Student> findPage(SqlSession session, Map<String, Object> params) {
		int page = (int) params.get("page");
		int limit = (int) params.get("limit");
		
		int offset = (page - 1) * limit; //  1page - 0, 2page - 10, 3page - 20, ...
		RowBounds rowBounds = new RowBounds(offset, limit);
		return session.selectList("student.findAll", null, rowBounds);
	}

}
