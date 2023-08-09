package com.sh.app.design.pattern.strategy.pet;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Snake implements Pet {
	private String name;

	@Override
	public String getName() {
		return this.name;
	}

}
