package com.sh.web.menu;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/menuOrder.do")
public class MenuOrderServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		//1.전송값에 한글이 있을 경우 인코딩처리해야함.
		req.setCharacterEncoding("utf-8");
		
		//2.전송값 꺼내서 변수에 기록하기.
		String mainMenu = req.getParameter("mainMenu");
		String sideMenu = req.getParameter("sideMenu");
		String drinkMenu = req.getParameter("drinkMenu");
		System.out.println("mainMenu = " + mainMenu);
		System.out.println("sideMenu = " + sideMenu); 
		System.out.println("drinkMenu = " + drinkMenu);
		
		//3.비지니스 로직 - 결제금액
		int price = 0;
		switch (mainMenu) {
		case "한우버거": price += 5000;break;
		case "밥버거": price += 4500;break;
		case "치즈버거": price += 4000;break;
		}
		switch (sideMenu) {
		case "감자튀김": price += 1500;break;
		case "어니언링": price += 1700;break;
		}
		switch (drinkMenu) {
		case "콜라": 
		case "사이다": price += 1000;break;
		case "커피": price += 1500;break;
		case "밀크쉐이크": price += 2500;break;
		}
		
		
		//4.실행결과 처리 및 view단 처리위임
		req.setAttribute("price", price);
		
		//RequestDispatcher(인터페이스)에 view단 jsp 경로 지정
		RequestDispatcher reqDispatcher = getServletContext().getRequestDispatcher("/menu/menuEnd.jsp");
		reqDispatcher.forward(req, resp);
		
	}
	
}
