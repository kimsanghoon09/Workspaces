package com.sh.app.emp.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.sh.app.emp.dto.EmpDto;
import com.sh.app.emp.entity.Emp;

public interface EmpRepository {

	List<Emp> findAll(SqlSession session);

	List<Emp> search1(SqlSession session, Map<String, Object> filters);

	List<EmpDto> search2(SqlSession session, Map<String, Object> filters);

	List<EmpDto> search3(SqlSession session, Map<String, Object> filters);

}
