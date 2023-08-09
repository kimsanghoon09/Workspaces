package com.sh.app.design.pattern.proxy.after;

public class FooMain {

	/**
	 * JoinPoint : FooImpl
	 * Aspect : AspectImpl (advice를 가진 클래스)
	 * Proxy : FooProxy (aop에 의해서 동적생성됨)
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Foo foo = new FooProxy(new FooImpl(), new AspectImpl());
		System.out.println(foo.getName());
	}

}
