package p09.abstract_class;

public class TestPhone {

	public static void main(String[] args) {
		// Cannot instantiate the type Phone
//		Phone phone = new Phone();
		SmartPhone sp = new SmartPhone("홍길동");
		sp.turnOn();
		sp.internetSearch();
		sp.turnOff();
	}

}
