package account;

public class Account {
	private String account;
	private int balance;
	private double interestRate;

	public Account() {
		super();
	}

	public Account(String account, int balance, double interestRate) {
		super();
		this.account = account;
		this.balance = balance;
		this.interestRate = interestRate;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	
	public double calculateInterest() {
		return balance>0 ? balance * interestRate : 0;
	}
	
	public void deposit(int money) {
		if(money < 0) {
			System.out.println("입력오류: 0이상 금액을 입력하시기 바랍니다.");
//			return;//조기리턴시키거나 else처리
		}else {
			balance += money;
		}
	}
	
	public void withdraw(int money) {
		if(money < 0) {
			System.out.println("입력오류: 0이상 금액을 입력하시기 바랍니다.");
		}else {
			balance -= money;
		}
	}
	
	public void accountInfo() {
		System.out.printf("계좌정보: %s 현재잔액: %d 이자율: %.1f%%", account, balance,interestRate*100);
	}
}
