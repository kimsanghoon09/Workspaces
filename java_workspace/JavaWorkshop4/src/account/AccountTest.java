package account;

/**
 * class 명과 method 명은 변경 하지 않는다 위에 선언한 클래스 변수와 클래스 함수만을 이용한다//toString().....
 * getXXX/setXXX는 필요 시 만들어서 사용한다
 */
public class AccountTest {

	public static void main(String[] args) {
		Account acc;
		// account 객체 생성
		acc = new Account("441-0290-1203", 500000, 0.073);
		// account 기본 정보 출력
		System.out.printf("계좌정보: %s 현재잔액: %d%n", acc.getAccount(), acc.getBalance());
		// account 에 20000원 입금
		acc.deposit(20000);
		// account 변경 정보 출력
		System.out.printf("계좌정보: %s 현재잔액: %d%n", acc.getAccount(), acc.getBalance());
		// 이자 출력 – 현재 잔고를 기준으로 고객에게 줄 이자 금액을 출력 한다
		System.out.println("이자: " + acc.calculateInterest());
	}

}
