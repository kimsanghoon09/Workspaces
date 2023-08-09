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
public class StudentCreateController extends AbstractController {
	private final StudentService studentService;
	
	/**
	 * 학생등록 폼 요청
	 */
	@Override
	public String doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		return "student/studentCreate";
	}
	
	/**
	 * db 학생정보 등록 요청
	 */
	@Override
	public String doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1. 사용자입력값
		String name = request.getParameter("name");
		String tel = request.getParameter("tel");
		Student student = Student.builder()
					.name(name)
					.tel(tel)
					.build();
		System.out.println(student);
		
		// 2. 업무로직
		int result = studentService.insertStudent(student);
		
		return "redirect:/student/studentList.do";
	}
}
