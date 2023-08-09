package com.sh.app.tv;

public class LgTv implements Tv {

	public LgTv() {
		System.out.println("Lg Tv 객체 생성!!");
	}
	
	public void powerOn() {
		System.out.println("LG Tv 전원을 켰습니다.");
	}

	public void powerOff() {
		System.out.println("LG Tv 전원을 껐습니다.");
	}

	public void channelTo(int no) {
		System.out.println("Lg Tv 채널을 " + no + "로 변경합니다~");
	}

}
