package com.sh.app.emp.dto;

import com.sh.app.emp.entity.Emp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class EmpDto extends Emp {
	private String gender;
}
