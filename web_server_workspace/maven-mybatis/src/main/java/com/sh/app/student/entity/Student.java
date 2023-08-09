package com.sh.app.student.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
//@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
	private int id;
	private String name;
	private String tel;
	private LocalDateTime createdAt;
	
//	public static Builder builder() {
//		return new Builder();
//	}
//	
//	public static class Builder {
//		private int id;
//		private String name;
//		private String tel;
//		private LocalDateTime createdAt;
//		
//		public Builder id(int id) {
//			this.id = id;
//			return this;
//		}
//		public Builder name(String name) {
//			this.name = name;
//			return this;
//		}
//		public Builder tel(String tel) {
//			this.tel = tel;
//			return this;
//		}
//		public Builder createdAt(LocalDateTime createdAt) {
//			this.createdAt = createdAt;
//			return this;
//		}
//		public Student build() {
//			return new Student(this.id, this.name, this.tel, this.createdAt);
//		}
//	}
}
