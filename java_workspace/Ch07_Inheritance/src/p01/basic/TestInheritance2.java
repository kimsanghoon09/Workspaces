package p01.basic;

// 1. 상속(Inheritance)
// - 자식 class는 부모의 field, method 모두 사용 가능 
//   (부모 위의 모든 조상에 속한 field, method들도 모두 사용 가능)
// - 모든 부모에 대하여 heap memory에 인스턴스를 생성함
//   => 가장 위에 있는 조상의 constructor를 먼저 호출하고, 맨마지막으로 본인 class의 constructor를 
// 2. Java는 상속할 때 부모 class가 1개만 상속 가능
//   (참고) C++ 은 여러개의 부모 class를 상속 가능
// 3. 모든 class들은 Object class를 부모(조상) class로 상속받고 있음
//   => Object class인 경우는 extends를 사용하지 않더라도 힙메모리에 인스턴스로 존재함
//   => Object class는 Java의 모든 library class들에도 조상 class로 됨
public class TestInheritance2 {

	public static void main(String[] args) {
		Student s = new Student();
		s.name = "홍길동학생";
		s.age = 30;
		s.study();
		s.speak();
		s.eat();
		s.sleep();
		s.walk();
		
		System.out.println();
		
		StudentWorker sw = new StudentWorker();
		sw.name = "김학생워커";
		sw.work();
		sw.study();
		sw.speak();
		sw.eat();
		sw.sleep();
		sw.walk();
		
	}

}
