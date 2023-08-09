package p02.method.overloading;

public class CalculatorEx {

	public static void main(String[] args) {
		Calculator calc = new Calculator();
		double result=0;
		
		result = calc.areaRectangle(10);
		System.out.println("정사각형 넓이 : " + result);
		System.out.println(result);

		result = calc.areaRectangle(10, 20);
		System.out.println("직사각형 넓이 : " + result);
		
	}

}
