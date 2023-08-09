package com.sh.app.emp.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sh.app.common.controller.AbstractController;
import com.sh.app.emp.entity.Emp;
import com.sh.app.emp.service.EmpService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmpSearchController1 extends AbstractController {
	private final EmpService empService;
	
	@Override
	public String doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 사용자입력값 처리
		String searchType = request.getParameter("searchType");
		String searchKeyword = request.getParameter("searchKeyword");
		Map<String, Object> filters = new HashMap<>();
		filters.put("searchType", searchType);
		filters.put("searchKeyword", searchKeyword);
		System.out.println(filters);

		// 2. 업무로직 
		List<Emp> emps = empService.search1(filters);
		System.out.println(emps);
		request.setAttribute("emps", emps);
		return "emp/search1";
	}
}
