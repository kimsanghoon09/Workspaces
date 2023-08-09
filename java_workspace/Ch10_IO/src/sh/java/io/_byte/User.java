package sh.java.io._byte;

import java.io.Serializable;

/**
 * Serializable인터페이스는 몸통코드가 없는 마커인터페이스이다. (규격만 제공)
 *
 */
public class User implements Serializable {
	private String username;
	private String password;
	private boolean married; 
	
	public User() {}

	public User(String username, String password, boolean married) {
		super();
		this.username = username;
		this.password = password;
		this.married = married;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * boolean형의 getter는 is로 시작한다.
	 * @return
	 */
	public boolean isMarried() {
		return this.married;
	}
	public void setMarried(boolean married) {
		this.married = married;
	}
	
	
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", married=" + married + "]";
	}
	
}
