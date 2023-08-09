package com.sh.app.emp.entity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Emp {
	@NonNull
	private String empId;
	@NonNull
	private String empName;
	@NonNull
	private String empNo;
	private String email;
	private String phone;
	private String deptCode;
	@NonNull
	private String jobCode;
	@NonNull
	private String salLevel;
	private Integer salary;
	private Double bonus; // double타입인 경우 컬럼값이 null이면 0으로 처리. Double타입인 경우 null 처리.
	private String managerId;
	private LocalDate hireDate;
	private LocalDate quitDate;
	private boolean quitYn; // Y=true, N=false | 1=true, 0=false
}
