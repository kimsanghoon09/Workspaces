package com.sh.app.product.view;

import java.util.Scanner;

import com.sh.app.product.controller.ProductController;

public class ProductMenu {
	private ProductController productController = new ProductController();
	private Scanner sc = new Scanner(System.in);
	
	public void mainMenu() {
		String menu = "\n"
					+ "======================\n"
					+ " 상품 메뉴\n"
					+ "======================\n"
					+ "1. 어른이날 상품 \n"
					+ "2. 어린이날 상품\n"
					+ "3. 심심한날 상품\n"
					+ "4. 우울한날 상품\n"
					+ "0. 이전메뉴로 돌아가기\n"
					+ "======================\n"
					+ "선택 : ";
		while(true) {
			System.out.print(menu);
			String choice = sc.next();
			
			switch(choice) {
			case "1" : break;
			case "2" : break;
			case "3" : break;
			case "4" : break;
			case "0" : return;
			default : System.out.println("> 잘못 입력하셨습니다😓");
			}
		}
		
	}
	
	
}
