package p01.basic2;

public class SamsungPhone implements Phone {

	@Override
	public void sendCall() {
		System.out.println("Samsung Phone - 띠르릉");
	}

	@Override
	public void receiveCall() {
		this.flush();
		System.out.println("Samsung Phone - 전화왔습니다.");
	}

	public void flush() {
		System.out.println("Samsung Phone - 전화기에 불이 켜졌습니다.");
	}
}
