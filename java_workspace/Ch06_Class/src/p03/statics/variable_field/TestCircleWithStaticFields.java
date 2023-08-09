package p03.statics.variable_field;

// JVM이 main method 실행 방법 : class이름.main() => TestCircleWithStaticFields.main()
public class TestCircleWithStaticFields {
	int a = 10;
	static int b = 20;

	public TestCircleWithStaticFields() {
		System.out.println("main default constructor");
	}
	
	// static method
	public static void main(String[] args) {
		System.out.println("new로 인스턴스 생성 전 최초 Circle 인스턴스 갯수 : " 
					+ Circle.getNumberOfInstances());
		Circle c1 = new Circle();
		System.out.println("c1 인스턴스 생성 후 Circle 인스턴스 갯수 : " 
				+ Circle.getNumberOfInstances());
		
		Circle c2 = new Circle(5);
		System.out.println("c2 인스턴스 생성 후 Circle 인스턴스 갯수 : " 
				+ Circle.getNumberOfInstances());
		System.out.println("c2 인스턴스 생성 후 Circle 인스턴스 갯수 : " 
				+ Circle.numberOfInstances);
		Circle.numberOfInstances = 10;
		System.out.println("c2 인스턴스 생성 후 Circle 인스턴스 갯수 : " 
				+ c2.numberOfInstances);
		
		TestCircleWithStaticFields test = new TestCircleWithStaticFields();
		test.a = 20;
		test.printMain();
		b = 30;
		staticPrintMain();
		System.out.println("a : " + test.a);
	}

	public void printMain() {
		System.out.println("a : " + a);
		System.out.println("TestCircleWithStaticFields class instance method 호출");
	}
	
	public static void staticPrintMain() {
		System.out.println("TestCircleWithStaticFields class static method 호출");
	}
}
