package ncs.test1;

import java.util.Scanner;

public class Test1 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("A, B, C, D, E별 평가점수를 입력하세요. 예)10 20 40 50 80 \n >>");
		String[] s = sc.nextLine().split(" ");

//		System.out.println(s.length);
		// 1.개수검사
		if (s.length != 5) {
			System.out.println("다시 입력하세요.");
			return;
		}

		// 2.범위검사
		double[] d = new double[s.length];
		for (int i = 0; i < d.length; i++) {
			d[i] = Double.parseDouble(s[i]);

//			if(!(d[i]>=10 && d[i]<=100)){
			if (d[i] < 10 || d[i] > 100) {
				System.out.println("다시 입력하세요.");
				return;
			}
		}

		// 3.평가점수 계산
		double result = (d[0] + d[1]) / 2 * 0.6 + (d[2] + d[3]) / 2 * 0.2 + d[4] * 0.2;
		String grade = "";
		// byte short char int String enum 타입의 값만 처리가능!
		switch ((int) (result / 10)) {
		case 10:
		case 9:
			grade = "Gold Class";
			break;
		case 8:
			grade = "Silver Class";
			break;
		case 7:
			grade = "Bronze Class";
			break;
		default:
			grade = "Normal Class";
			break;
		}

		System.out.println("평가점수 : " + result + "점");
		System.out.println("Class : " + grade);

	}

}
