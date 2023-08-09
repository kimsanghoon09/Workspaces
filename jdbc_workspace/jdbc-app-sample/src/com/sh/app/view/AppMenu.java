package com.sh.app.view;

import java.util.Scanner;

import com.sh.app.member.model.dto.Member;
import com.sh.app.member.view.MemberMenu;
import com.sh.app.product.view.ProductMenu;

public class AppMenu {
	public static Member loginMember = null; // 로그인한 사용자를 관리한 static변수
	
	private Scanner sc = new Scanner(System.in);
	private MemberMenu memberMenu = new MemberMenu();
	private ProductMenu productMenu = new ProductMenu();

	public void initMenu() {
		
		while(true) {
			if(loginMember == null) {
				String menu = "\n"
						+ "======================\n"
						+ " 세상에서 가장 앱스러운 앱\n"
						+ "======================\n"
						+ "1. 회원가입\n"
						+ "2. 로그인\n"
						+ "0. 프로그램 종료\n"
						+ "======================\n"
						+ "선택 : ";
				System.out.print(menu);
				String choice = sc.next();
				
				switch(choice) {
				case "1" : 
					memberMenu.registerMember();  
					break;
				case "2" : 
					loginMember = memberMenu.login();
					break;
				case "0" : return;
				default  : System.out.println("> 잘못 입력하셨습니다😓");
				}
			}
			else 
				loginMenu();
			}
		}

	private void loginMenu() {
		String menu = "\n"
					+ "======================\n"
					+ " 세상에서 가장 앱스러운 앱\n"
					+ "======================\n"
					+ "1. 상품조회\n"
					+ "2. 장바구니확인\n"
					+ "3. 결제\n"
					+ "0. 로그아웃\n"
					+ "======================\n"
					+ "선택 : ";
		while (true) {
			System.out.print(menu);
			String choice = sc.next();
			
			switch(choice) {
			case "1" : 
				productMenu.mainMenu();
				break;
			case "2" : break;
			case "3" : break;
			case "0" :
				System.out.println("> 다음에 만나요 😊");
				loginMember = null; 
				return;
			default : System.out.println("> 잘못 입력하셨습니다😓");
			}
		}
	}


}
