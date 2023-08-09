package com.sh.app.design.pattern.builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	private long code;
	private String username;
	private String password;
	private LocalDate birthday;
	private String address;
	private boolean married;
	private LocalDateTime createdAt;
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder {
		private long code;
		private String username;
		private String password;
		private LocalDate birthday;
		private String address;
		private boolean married;
		private LocalDateTime createdAt;
		
		public Builder code(long code) {
			this.code = code;
			return this; // Builder객체 반환해서 method chaining 가능 
		}
		public Builder username(String username) {
			this.username = username;
			return this;
		}
		public Builder password(String password) {
			this.password = password;
			return this;
		}
		public Builder birthday(LocalDate birthday) {
			this.birthday = birthday;
			return this;
		}
		public Builder address(String address) {
			this.address = address;
			return this;
		}
		public Builder married(boolean married) {
			this.married = married;
			return this;
		}
		public Builder createdAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
			return this;
		}
		public User build() {
			return new User(this.code, this.username, this.password, this.birthday, this.address, this.married, this.createdAt);
		}
		
	}
	
	
}
