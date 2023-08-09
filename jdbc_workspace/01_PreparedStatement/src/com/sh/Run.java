package com.sh;

import com.sh.member.view.MemberMenu;

public class Run {

	public static void main(String[] args) {
//		MemberMenu menu = new MemberMenu();
//		menu.mainMenu();
		
		new MemberMenu().mainMenu();
		
		System.out.println("----- 프로그램 종료 -----");
	}

}
