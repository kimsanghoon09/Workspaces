package com.sh.app.design.pattern.strategy.mvc;

import java.util.List;

public interface ProductService {
	List<? extends Product> findAll();
}
