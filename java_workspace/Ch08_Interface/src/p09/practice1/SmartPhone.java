package p09.practice1;

// SmartPhone
// 1. 부모인 PDA class를 상속받음
// 2. 부모 Interface인 MP3Interface, MobilePhoneInterface 상속 받음
public class SmartPhone extends PDA implements MP3Interface, MobilePhoneInterface {

	@Override
	public void sendCall() {
		System.out.println("따르릉 따르릉~~");
	}

	@Override
	public void receiveCall() {
		System.out.println("전화 왔어요.");
	}

	@Override
	public void sendSMS() {
		System.out.println("문자 보냅니다.");
	}

	@Override
	public void receiveSMS() {
		System.out.println("문자 받습니다.");
	}

	@Override
	public void play() {
		System.out.println("음악 연주합니다.");
	}

	@Override
	public void stop() {
		System.out.println("음악 연주중단 합니다.");
	}

	public void schedule() {
		System.out.println("일정 관리 합니다.");
	}
}





