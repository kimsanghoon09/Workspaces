package com.sh.app.design.pattern.strategy.mvc;

public class Main {

	public static void main(String[] args) {
		ProductController foodProductController = new ProductController(new FoodProductServiceImpl());
		ProductController elecProductController = new ProductController(new ElecProductServiceImpl());

	}

}
