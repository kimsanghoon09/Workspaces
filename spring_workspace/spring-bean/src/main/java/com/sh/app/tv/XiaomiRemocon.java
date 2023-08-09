package com.sh.app.tv;

public class XiaomiRemocon implements RemoteControl {

	// XiomiRemocon은 Tv객체에 의존
	private Tv tv;
	
	public XiaomiRemocon(Tv tv) {
		System.out.println("XiaomiRemocon 객체 생성 : " + tv);
		this.tv = tv;
	}
	
	public void channelTo(int no) {
		this.tv.channelTo(no);
	}
	
	public void setTv(Tv tv) {
		this.tv = tv;
	}

}
