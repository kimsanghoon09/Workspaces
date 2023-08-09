package com.sh.app.emp.service;

import static com.sh.app.common.SqlSessionUtils.getSqlSession;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.sh.app.emp.dto.EmpDto;
import com.sh.app.emp.entity.Emp;
import com.sh.app.emp.repository.EmpRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmpServiceImpl implements EmpService {
	private final EmpRepository empRepository;
	
	@Override
	public List<Emp> findAll() {
		SqlSession session = getSqlSession();
		List<Emp> emps = empRepository.findAll(session);
		session.close();
		return emps;
	}
	
	@Override
	public List<Emp> search1(Map<String, Object> filters) {
		SqlSession session = getSqlSession();
		List<Emp> emps = empRepository.search1(session, filters);
		session.close();
		return emps;
	}
	@Override
	public List<EmpDto> search2(Map<String, Object> filters) {
		SqlSession session = getSqlSession();
		List<EmpDto> emps = empRepository.search2(session, filters);
		session.close();
		return emps;
	}
	
	@Override
	public List<EmpDto> search3(Map<String, Object> filters) {
		SqlSession session = getSqlSession();
		List<EmpDto> emps = empRepository.search3(session, filters);
		session.close();
		return emps;
	}
}
