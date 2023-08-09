package com.sh.app.design.pattern.proxy.after;

public class AspectImpl implements Aspect {

	@Override
	public void before() {
		System.out.println("ㅋㅋㅋ");
	}

	@Override
	public void after() {
		System.out.println("ㅎㅎㅎ");
	}

}
