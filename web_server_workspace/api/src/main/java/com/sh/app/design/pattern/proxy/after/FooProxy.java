package com.sh.app.design.pattern.proxy.after;

public class FooProxy implements Foo {
	Foo target;
	Aspect aspect;
	
	public FooProxy(Foo target, Aspect aspect) {
		this.target = target;
		this.aspect = aspect;
	}
	
	@Override
	public String getName() {
		// target 실행전
		aspect.before();
		String returnValue = target.getName();
		// target 실행후
		aspect.after();
		return returnValue;
	}

}
