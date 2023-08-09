package com.sh.app.design.pattern.strategy.mvc;

import java.util.List;

public class FoodProductServiceImpl implements ProductService {

	@Override
	public List<? extends Product> findAll() {
		List<FoodProduct> foodProducts = null;
		return foodProducts;
	}

}
