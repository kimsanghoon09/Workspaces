package com.sh.app.design.pattern.proxy.after;

public class FooImpl implements Foo {
	public String getName() {
		System.out.println("FooImpl#getName");
		return "Hong Gildong";
	}
}
