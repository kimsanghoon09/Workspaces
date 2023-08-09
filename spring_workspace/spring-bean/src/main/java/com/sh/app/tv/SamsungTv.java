package com.sh.app.tv;

public class SamsungTv implements Tv {

	public SamsungTv() {
		System.out.println("삼성 Tv 객체 생성!");
	}
	
	public void powerOn() {
		System.out.println("삼성 Tv 전원을 켰습니다.");
	}

	public void powerOff() {
		System.out.println("삼성 Tv 전원을 껐습니다.");
	}
	
	public void channelTo(int no) {
		System.out.println("삼성 Tv 채널을 " + no + "로 변경합니다~");
	}

}
