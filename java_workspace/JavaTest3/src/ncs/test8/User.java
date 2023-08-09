package ncs.test8;

import java.util.Objects;

public class User {

	private String id;
	private String password;
	private String name;
	private int age;
	private char gender;
	private String phone;

	public User(String id, String password, String name, int age, char gender, String phone) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.phone = phone;
	}

	public User() {
	}

	/**
	 * 복사생성자
	 * @param user
	 */
	public User(User user) {
		this.id = user.id;
		this.password = user.password;
		this.name = user.name;
		this.age = user.age;
		this.gender = user.gender;
		this.phone = user.phone;
	}

	@Override
	public int hashCode() {
		return Objects.hash(age, gender, id, name, password, phone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return age == other.age && gender == other.gender && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && Objects.equals(password, other.password)
				&& Objects.equals(phone, other.phone);
	}

	/**
	 * 현재객체의 필드값을 가지고 새로운 User객체 생성
	 * 
	 * Override 부모메소드의 시그니쳐(접근제한자, 리턴타입, 메소드명, 매개변수선언부, 예외선언부)와 똑같이 선언해야한다.
	 * 
	 * Override 예외 (호출부에서 더욱 쉽게 사용하기 위해)
	 * 1. 접근제한자 더 개방할수 있다. protected --> public
	 * 2. 부모메소드에 선언된 예외선언을 제거 할수 있다.  throws CloneNotSupportedException --> 제거
	 * 3. 반환타입을 Object의 자식타입으로 선언가능 (공변변환타입)
	 */
	@Override
	public User clone() {
		// return new User(this.id, this.password, this.name, this.age, this.gender, this.phone);
		return new User(this); // 복사생성자
	}
	

	@Override
	public String toString() {
		return id + " " + password + " " + name + " " + age + " " + gender + " " + phone;
	}

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
