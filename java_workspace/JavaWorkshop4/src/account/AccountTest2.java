package account;

public class AccountTest2 {

	public static void main(String[] args) {
//		1) 5개의 Account 형 객체 배열 선언 
//		2) for문을 이용하여 Account 객체를 배열에 생성
//		- 계좌번호: 221-0101-211X(X 부분은 1부터 5까지의 정수가 셋팅 된다)
//		- 잔액 및 이자율은 모두 100000원, 4.5% 이다.
		Account[] accs = new Account[5];
		for (int i = 0; i < accs.length; i++) {
			// String account, int balance, double interestRate
			accs[i] = new Account("221-0101-211" + (i + 1), 100000, 0.045);
		}
//		3) Account class에 Account의 모든 정보를 출력 할 수 있는 accountInfo()를 만든다.
//		4) for문을 이용하여 생성된 배열의 모든 정보를 출력 한다.(출력 시 accountInfo()함수 이용)
		for (Account acc : accs) {
			acc.accountInfo();
			System.out.println();
		}
		System.out.println();
//		5) for문을 이용하여 모든 Account 객체의 이자율을 3.7% 변경 하고 이자를 화면에 출력 한다.
		for (int i = 0; i < accs.length; i++) {
			accs[i].setInterestRate(0.037);
			accs[i].accountInfo();
			System.out.printf(" 이자: %.0f원%n", accs[i].calculateInterest());
		}
	}

}
