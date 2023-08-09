package com.sh.app.student.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sh.app.common.controller.AbstractController;
import com.sh.app.student.entity.Student;
import com.sh.app.student.service.StudentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StudentListController extends AbstractController {
	
	private final StudentService studentService;

//	public StudentListController(StudentService studentService) {
//		this.studentService = studentService;
//	}
	
	@Override
	public String doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 사용자입력값 처리
		int page = 1;
		try {
			page = Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException ignore) {}
		int limit = 10;
		Map<String, Object> params = Map.of(
			"page", page,
			"limit", limit
		);
		System.out.println(params);
		// 2. 업무로직
//		List<Student> students = studentService.findAll();
		List<Student> students = studentService.findPage(params);
		
		// 마지막페이지 구하기
		int totalCount = studentService.getTotalCount();
		int lastPage = (int) Math.ceil((double) totalCount / limit);
		System.out.println(lastPage);
		request.setAttribute("lastPage", lastPage);
		request.setAttribute("students", students);
		
		return "student/studentList"; // /WEB-INF/views/student/studentList.jsp
	}
}
