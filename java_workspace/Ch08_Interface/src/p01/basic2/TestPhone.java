package p01.basic2;

public class TestPhone {

	public static void main(String[] args) {
		Phone phone;
		phone = new SamsungPhone();
//		phone = new LGPhone();
//		phone = new ApplePhone();	// promotion
		
		phone.sendCall();			// override => polymoephism
		phone.receiveCall();
//		phone.flush();

		SamsungPhone sp = (SamsungPhone) phone;	// casting
		sp.flush();
	}

}
