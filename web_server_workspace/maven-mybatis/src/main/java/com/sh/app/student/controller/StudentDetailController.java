package com.sh.app.student.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sh.app.common.controller.AbstractController;
import com.sh.app.student.entity.Student;
import com.sh.app.student.service.StudentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StudentDetailController extends AbstractController {

	private final StudentService studentService;
	
	@Override
	public String doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 사용자입력값 처리
		int id = Integer.parseInt(request.getParameter("id"));
		// 2. 업무로직
		Student student = studentService.findById(id);
		request.setAttribute("student", student);
		
		return "student/studentDetail";
	}
}
