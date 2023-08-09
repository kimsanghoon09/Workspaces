package ncs.test6;

import java.util.Scanner;

public class ExceptionTest {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		Calculator calculator = new Calculator();
		double result = 0;

		System.out.print("정수 입력 : ");

		try {
			int data = sc.nextInt();
			result = calculator.getSum(data);
			System.out.println("결과값 : " + result);
		} catch (InvalidException e) {
			System.err.println(e.getMessage());
		}

	}

}
