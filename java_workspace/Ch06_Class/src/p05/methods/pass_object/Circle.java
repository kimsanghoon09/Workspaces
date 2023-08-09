package p05.methods.pass_object;

public class Circle {
	private double radius;		// instance field
	private static int numberOfInstances = 0;	// new로 생성된 instance 갯수 관리용 field
	
	public Circle() {
		this.radius = 1;
		numberOfInstances++;
	}

	public Circle(double radius) {
		super();
		this.radius = radius;
		numberOfInstances++;
	}

	// static method
	public static int getNumberOfInstances() {
//		helloPrint();
		return numberOfInstances;
	}

	// getter method
	public double getRadius() {
		return radius;
	}

	// setter method
	public void setRadius(double radius) {
		if (radius >= 0) {
			this.radius = radius;
		} else {
			this.radius = 0;
		}
	}

	// instance method
	// 원의 면적
	public double getArea() {
		return this.radius*this.radius*Math.PI;
	}
	
	// instance method
	// 원의 둘레
	public double getPerimeter() {
		return 2*Math.PI*this.radius;
	}
	
	public void helloPrint() {
		printPrivate();
		staticHelloPrint();
		System.out.println("Hello, Instance Print");
	}
	
	public static void staticHelloPrint() {
//		this.radius = 20;
		Circle c = new Circle();
		c.radius = 20;
		System.out.println("Hello, static Print");
	}
	
	// instance method
	private void printPrivate() {
		System.out.println("hello, private");
	}
}







