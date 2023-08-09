package com.sh.app.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sh.app.common.controller.AbstractController;
import com.sh.app.emp.controller.EmpSearchController1;
import com.sh.app.emp.controller.EmpSearchController2;
import com.sh.app.emp.controller.EmpSearchController3;
import com.sh.app.emp.repository.EmpRepositoryImpl;
import com.sh.app.emp.service.EmpService;
import com.sh.app.emp.service.EmpServiceImpl;
import com.sh.app.student.controller.StudentCreateController;
import com.sh.app.student.controller.StudentCreateController2;
import com.sh.app.student.controller.StudentDeleteController;
import com.sh.app.student.controller.StudentDetailController;
import com.sh.app.student.controller.StudentListController;
import com.sh.app.student.controller.StudentUpdateController;
import com.sh.app.student.repository.StudentRepositoryImpl;
import com.sh.app.student.service.StudentService;
import com.sh.app.student.service.StudentServiceImpl;

/**
 * Servlet implementation class FirstServlet
 */
@WebServlet("*.do")
public class FirstServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Map<String, AbstractController> commandMap; // url:String = controller:AbstractController 맵
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FirstServlet() {
        this.commandMap = new HashMap<>();
        
        // url과 controller객체 매핑
        StudentService studentService = new StudentServiceImpl(new StudentRepositoryImpl());
        EmpService empService = new EmpServiceImpl(new EmpRepositoryImpl());
        
        commandMap.put("/student/studentList.do", new StudentListController(studentService));
        commandMap.put("/student/studentDetail.do", new StudentDetailController(studentService));
        commandMap.put("/student/studentCreate.do", new StudentCreateController(studentService));
        commandMap.put("/student/studentCreate2.do", new StudentCreateController2(studentService));
        commandMap.put("/student/studentUpdate.do", new StudentUpdateController(studentService));
        commandMap.put("/student/studentDelete.do", new StudentDeleteController(studentService));
        
        commandMap.put("/emp/search1.do", new EmpSearchController1(empService));
        commandMap.put("/emp/search2.do", new EmpSearchController2(empService));
        commandMap.put("/emp/search3.do", new EmpSearchController3(empService));
 
        System.out.println(commandMap);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 요청 url 가져오기
		String url = request.getRequestURI(); // /maven-mybatis/student/studentList.do
		url = url.replace(request.getContextPath(), "");
		
		String method = request.getMethod();
		System.out.println(method + " " + url + " 요청!");
		
		// 2. controller 가져오기
		AbstractController controller = commandMap.get(url);
		if(controller == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "그런 url 없습니다.");
			return;
		}
		
		// 3. doGet/doPost 호출
		String viewName = null;
		// 다형성
		// - 정적바인딩 실행되는 타입의 메소드 호출
		// - 동적바인딩 상속/다형성이 적용된 경우에 한해, 실제객체(자식객체)의 오버라이드된 메소드 호출
		switch(method) {
		case "GET" : viewName = controller.doGet(request, response); break;
		case "POST" : viewName = controller.doPost(request, response); break;
		}
		
		System.out.println(viewName);
		
		// 4. 응답처리 - forward | redirect | 아무 작업 없음(controller 직접 응답을 작성한 경우)
		if(viewName == null) {
			// json, file 응답하는 경우
		}
		else if(viewName.startsWith("redirect:")) {
			// redirect 하는 경우 "redirect:/student/studentList.do"
			String location = request.getContextPath() + viewName.replace("redirect:", "");
			response.sendRedirect(location);
		}
		else {
			// forward 하는 경우 "student/studentList"
			String prefix = "/WEB-INF/views/";
			String suffix = ".jsp";
			String view = prefix + viewName + suffix;
			request.getRequestDispatcher(view).forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
