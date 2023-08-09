package ncs.test5;

import java.util.Scanner;

public class TestScore {

	public static void main(String[] args) {
		TestScore testScore = new TestScore();
//		testScore.test1();
		testScore.test2();
	}
	
	public void test2() {
		Scanner sc = new Scanner(System.in);
		double[][] stdtArr = new double[3][5];
		String result = "";
		
		for (int i = 0; i < stdtArr.length; i++) {
			
			for (int j = 0; j < stdtArr[i].length; j++) {
				switch(j) {
				case 0: 
				case 1: 
				case 2: 
					System.out.print("과목" + (j + 1) + " 점수 입력 : ");
					stdtArr[i][j] = sc.nextDouble();
					break;
				case 3: 
					//총점
					stdtArr[i][j] = stdtArr[i][0] + stdtArr[i][1] + stdtArr[i][2];
					break;
				case 4: 
					//평균
					stdtArr[i][j] = stdtArr[i][3] / 3;
					// 한행당 한번 처리구문
					String formatStr = String.format("%d %10.1f %10.1f %10.1f %10.1f %10.1f%n",
							 i,
							 stdtArr[i][0],
							 stdtArr[i][1],
							 stdtArr[i][2],
							 stdtArr[i][3],
							 stdtArr[i][4]);
					result += formatStr;
					break;
				}
			}
		}
		
		System.out.printf("%s %10s %10s %10s %10s %10s%n","index","과목1","과목2","과목3","총점","평균");
		System.out.println("------------------------------------------------------------------------------------------------------");
		System.out.println(result);
	}
	
	public void test1() {
		Scanner sc = new Scanner(System.in);
		double[][] stdtArr = new double[3][5];
		String result = "";
		
		for (int i = 0; i < stdtArr.length; i++) {
			System.out.println("----- " + (i + 1) + "번째 학생 입력 ----");
			
			System.out.print("과목1 점수 입력 : ");
			stdtArr[i][0] = sc.nextDouble();
			System.out.print("과목2 점수 입력 : ");
			stdtArr[i][1]  = sc.nextDouble();
			System.out.print("과목3 점수 입력 : ");
			stdtArr[i][2] = sc.nextDouble();
			
			//총점
			stdtArr[i][3] = stdtArr[i][0] + stdtArr[i][1] + stdtArr[i][2];
			//평균
			stdtArr[i][4] = stdtArr[i][3] / 3; // 과목수
			
			String formatStr = String.format("%d %10.1f %10.1f %10.1f %10.1f %10.1f%n",
									 i,
									 stdtArr[i][0],
									 stdtArr[i][1],
									 stdtArr[i][2],
									 stdtArr[i][3],
									 stdtArr[i][4]);
			System.out.println(formatStr);
			
			result += formatStr;
		}
		
		System.out.printf("%s %10s %10s %10s %10s %10s%n","index","과목1","과목2","과목3","총점","평균");
		System.out.println("------------------------------------------------------------------------------------------------------");
		System.out.println(result);
	}

}
