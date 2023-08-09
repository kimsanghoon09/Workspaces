package call.by;

import java.util.Arrays;

/**
 * 메소드호출시 매개인자의 타입에 따른 구분
 * call by value 값복사 기본형
 * call by reference 주소값복사 참조형
 *
 */
public class CallByStudy {
	
	public static void main(String[] args) {
		CallByStudy study = new CallByStudy();
		
		int a = 10;
		a = study.test1(a); // test1연산결과를 반환
		System.out.println(a);
		
		int[] brr = {1, 2, 3};
		study.test2(brr);
		System.out.println(Arrays.toString(brr));
		
		// String은 참조형이지만 call by value처럼 작동한다.
		// 문자열저장소를 이용해서 불변immutable하게 관리하기 때문.
		String str = "안녕";
		str = study.test3(str);
		System.out.println(str);
	}
	
	private String test3(String str) {
		str += "ㅋㅋㅋㅋ";
		return str;
	}

	/**
	 * call by reference
	 *  - 객체주소값을 복사하고, heap영역의 객체를 공유한다.
	 * @param brr
	 */
	private void test2(int[] brr) {
		for(int i = 0; i < brr.length; i++)
			brr[i] *= 100;
	}

	/**
	 * call by value
	 * @param a
	 */
	private int test1(int a) {
		a *= 100;
		return a;
	}

}
