package com.sh.uml.association.composition;

/**
 * Composition 합성관계
 * - 생명주기가 의존적인 경우.
 * - 속이 찬 다이아몬드로 표시
 * - Hotel클래스가 Room클래스의 생명주기를 결정한다.
 * - Hotel을 삭제하면, Room도 삭제된다.
 *
 */
public class CompositionMain {

	public static void main(String[] args) {
		Hotel sinla = new Hotel("신라호텔", 5);
		System.out.println(sinla);
		
		sinla = null; // 호텔을 삭제하면, 내부 방객체도 함께 제거됨.
	}

}
