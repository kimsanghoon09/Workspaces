package ncs.test8;

public class UsetTest {

	public static void main(String[] args) {
		
		User[] users = new User[3];
		// User 객체를 3개 생성하여 배열에 넣는다. 
		users[0] = new User("user01", "pass01", "김철수", 32, 'M', "010-1234-5678");
		users[1] = new User("user02", "pass02", "이영희", 25, 'F', "010-5555-7777");
		users[2] = new User("user03", "pass03", "황진이", 20, 'F', "010-9874-5632");

		// 배열에 있는 객체 정보를 모두 toString() 으로 출력 한다. – for 문을 이용 할 것
		System.out.println("users------------------------------");
		for (int i = 0; i < users.length; i++) {
//			System.out.println(users[i].toString());
			System.out.println(users[i]); // toString은 객체가 문자열로 표현되어야 할때 자동으로 호출
		}

		User[] copyUsers = new User[users.length]; // users배열과 동일한 크기
		// users가 참조하는 객체들의 복사본을 만든다. - for 문을 이용 할 것	
		for (int i = 0; i < copyUsers.length; i++) {
			copyUsers[i] = users[i].clone();
		}

		//copyUsers 의 객체 정보를 모두 출력한다 – for each 문을 이용할 것
		System.out.println("copyUsers------------------------------");
		for (User u : copyUsers) {
			System.out.println(u.toString());
		}

		//users 와 copyUsers 의 각 index 별 객체의 값들이 일치하는지 확인 출력한다.
		System.out.println("비교결과---------------------------------");
		System.out.println(users[0].equals(copyUsers[0]) + ", " + (users[0] == copyUsers[0]));
		System.out.println(users[1].equals(copyUsers[1]) + ", " + (users[1] == copyUsers[1]));
		System.out.println(users[2].equals(copyUsers[2]) + ", " + (users[2] == copyUsers[2]));

	}

}
