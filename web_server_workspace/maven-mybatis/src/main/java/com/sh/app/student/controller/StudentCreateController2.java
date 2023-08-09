package com.sh.app.student.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sh.app.common.controller.AbstractController;
import com.sh.app.common.gson.LocalDateTimeAdapter;
import com.sh.app.student.entity.Student;
import com.sh.app.student.service.StudentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StudentCreateController2 extends AbstractController {

	private final StudentService studentService;
	
	@Override
	public String doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1. 사용자입력값처리
		String name = request.getParameter("name");
		String tel = request.getParameter("tel");
		Student student = Student.builder()
				.name(name)
				.tel(tel)
				.build();
		System.out.println(student);
		
		// 2. 업무로직
		int result = studentService.insertStudent(student);
		
		// 생성된 id로 조회
		student = studentService.findById(student.getId());
		
		// 3. 응답처리 (json)
		response.setContentType("application/json; charset=utf-8");
		Map<String, Object> resultMap = Map.of(
			"result", "success",
			"student", student
		);
		Gson gson = new GsonBuilder()
				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
				.create();
		gson.toJson(resultMap, response.getWriter());
		return null;
	}
	
}
