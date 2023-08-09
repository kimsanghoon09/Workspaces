package com.sh.app.design.pattern.strategy.mvc;

import java.util.List;

public class ElecProductServiceImpl implements ProductService {

	@Override
	public List<? extends Product> findAll() {
		List<ElecProduct> elecProducts = null;
		return elecProducts;
	}
}
