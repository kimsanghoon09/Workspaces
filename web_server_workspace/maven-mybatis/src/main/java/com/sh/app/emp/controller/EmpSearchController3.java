package com.sh.app.emp.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sh.app.common.controller.AbstractController;
import com.sh.app.emp.dto.EmpDto;
import com.sh.app.emp.service.EmpService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmpSearchController3 extends AbstractController {
	private final EmpService empService;
	
	@Override
	public String doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 사용자입력값 처리
		// mybatis.foreach태그의 collection속성에는 반복접근 가능한 객체면 모두 가능.
		// T[], List<T>, Set<T>, Map<K, V> 모두 가능
		String[] _jobCodes = request.getParameterValues("jobCode");
		List<String> jobCodes = null;
		if(_jobCodes != null) 
			jobCodes = Arrays.asList(_jobCodes);
		Map<String, Object> filters = new HashMap<>();
		filters.put("jobCodes", jobCodes);
		System.out.println(jobCodes);
		// 2. 업무로직
		List<EmpDto> emps = empService.search3(filters);
		System.out.println(emps);
		request.setAttribute("emps", emps);
		return "emp/search3";
	}

}
