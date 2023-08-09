package ncs.test6;

import java.util.Scanner;

public class CalcTest {

	public static void main(String[] args) {
		Calculate c= new Calculate();
		
		Scanner sc = new Scanner(System.in);
		System.out.print("정수1을 입력하세요 : "); 
		int a = sc.nextInt();
		System.out.print("정수2을 입력하세요 : ");
		int b = sc.nextInt();
		
		System.out.println("합:"+c.sum(a, b));
		System.out.println("차:"+c.subtract(a, b));
		System.out.println("곱:"+c.multiply(a, b));
		System.out.println("나누기:"+c.divide(a, b));
		
	}

}
