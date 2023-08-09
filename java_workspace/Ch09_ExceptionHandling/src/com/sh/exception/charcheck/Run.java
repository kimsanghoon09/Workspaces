package com.sh.exception.charcheck;

import java.util.Scanner;

public class Run {

	public static void main(String[] args) {
		new Run().test();
	}

	public void test() {
		Scanner sc = new Scanner(System.in);
		System.out.print("문자열을 입력하세요 : ");
		try {
			String s = sc.nextLine();

			CharacterProcess cp = new CharacterProcess();
			System.out.println(cp.countAlpha(s));

		} catch (CharCheckException e) {
			// 에러메세지만 출력!
			System.err.println(e.getMessage());
		}

	}

}
