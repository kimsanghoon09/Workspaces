package p05.access_modifier_protected1;

// protected 정의
// 1. default access modifier + A를 부모로 한 자식 class에서만 사용 가능
// 2. 부모 자식 관계가 아닌 경우에는 사용 불가
public class A {
	protected String field;
	int a;
	
	protected A() {
		
	}

	protected void method() {
		System.out.println("A class method");
	}
}
