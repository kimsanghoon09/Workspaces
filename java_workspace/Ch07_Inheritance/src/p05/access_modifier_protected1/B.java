package p05.access_modifier_protected1;

public class B {
	
	public void method() {
		A a = new A();
		a.field = "new_value";
		a.method();
	}

}
