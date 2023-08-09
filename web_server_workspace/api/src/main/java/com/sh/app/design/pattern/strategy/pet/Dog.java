package com.sh.app.design.pattern.strategy.pet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dog implements Pet{
	private String name;
}
