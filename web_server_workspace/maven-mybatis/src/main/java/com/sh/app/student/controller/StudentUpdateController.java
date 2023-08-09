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
public class StudentUpdateController extends AbstractController {
	private final StudentService studentService;
	
	@Override
	public String doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1. 사용자입력값 처리
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String tel = request.getParameter("tel");
//		Student student = new Student(id, name, tel, null);
		Student student = Student.builder()
				.id(id)
				.name(name)
				.tel(tel)
				.build();
		System.out.println(student);
		
		// 2. 업무로직 요청
		int result = studentService.updateStudent(student);
		
		
		return "redirect:/student/studentDetail.do?id=" + id;
	}
}
