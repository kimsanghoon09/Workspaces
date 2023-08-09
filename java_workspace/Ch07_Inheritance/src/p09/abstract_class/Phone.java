package p09.abstract_class;

// abstract class (추상 class)
// 1. abstract class로 선언되어 있으면 new로 인스턴스 만들지 못함
// 2. 반드시 abstract class를 부모로 한 자식 class만 new로 인스턴스 만들 수 있음
// 3. 만든 목적 : 
//   - abstract class내의 field, method들을 많은 class에서 반드시 선언되기 원할 때
// 4. 생성자가 존재
//   - 이유 => 자식 class을 인스턴스를 만들기 전에 부모 class도 인스턴스가 존재해야 해서
//            자식 class를 인스턴스 만들 때 내부적으로 사용됨
public abstract class Phone {
	public String owner;
	
	public Phone() {
		
	}

	public Phone(String owner) {
		super();
		this.owner = owner;
	}
	
	public void turnOn() {
		System.out.println("폰 전원을 켭니다.");
	}

	public void turnOff() {
		System.out.println("폰 전원을 끕니다.");
	}
}
