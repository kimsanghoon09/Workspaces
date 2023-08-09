package com.sh.app.emp.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.sh.app.emp.dto.EmpDto;
import com.sh.app.emp.entity.Emp;

public class EmpRepositoryImpl implements EmpRepository {

	@Override
	public List<Emp> findAll(SqlSession session) {
		return session.selectList("emp.findAll");
	}
	
	@Override
	public List<Emp> search1(SqlSession session, Map<String, Object> filters) {
		return session.selectList("emp.search1", filters);
	}
	
	@Override
	public List<EmpDto> search2(SqlSession session, Map<String, Object> filters) {
		return session.selectList("emp.search2", filters);
	}
	@Override
	public List<EmpDto> search3(SqlSession session, Map<String, Object> filters) {
		return session.selectList("emp.search3", filters);
	}
	
}
