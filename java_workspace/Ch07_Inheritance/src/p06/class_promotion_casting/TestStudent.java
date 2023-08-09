package p06.class_promotion_casting;

public class TestStudent {

	public static void main(String[] args) {
		int a = 1;
		double d;
		
		d = a; // promotion(승격) : 메모리 크기가 작은 방에서 큰 방으로 옮길 때는 자바에서 허용
		a = (int) d;	// casting(강제형변환) : 메모리 크기가 큰 것을 작은 방으로 옮기는 것
		
		Person p;
		Student s = new Student("홍길동");
		s.printPersonInformation();
		s.printStudentInformation();

		// promotion => 자식 클래스의 인스턴스를 부모 class로 형변환 한 것임 
		// 부모 인스턴스인 p에 자식 인스턴스인 s의 주소를 넣은 것임
		// => 힙메모리에 생성된 부모 인스턴스만 사용하겠다는 의미임
		// => 부모 인스턴스의 field와 method만 사용
		// 
		p = s;
		System.out.println(p.name);
		p.printPersonInformation();
//		p.printStudentInformation();
		
		Student s2;
		s2 = (Student) p;	// casting (강제형변환) : 부모클래스 인스턴스를 자식 인스턴스로 변환
		s2.printStudentInformation();
		
		// 부모만 있는 인스턴스를 자식 인스턴스로 강제 형변환은 가능하지만, 자식 인스턴스 메소드 호출하면 에러 발생
		Person p2 = new Person("김길동");
		Student s3 = (Student) p2;
		s3.printStudentInformation();
		
	}

}





