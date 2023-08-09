package workshop4;

public class Test04 {
	public static void main(String[] args) {
		int inx = Integer.parseInt(args[0]);
		Calc calc = new Calc();
		System.out.println("결과: " + calc.calculate(inx));
	}
}

class Calc {
	public int calculate(int data) {
		int sum = 0;
		System.out.print("짝수: ");
		for (int inx = 1; inx <= data; inx++) {
			if (inx % 2 == 0) {
				sum += inx;
				System.out.print(inx + " ");
			}
		}
		System.out.println(" ");
		return sum;
	}
}