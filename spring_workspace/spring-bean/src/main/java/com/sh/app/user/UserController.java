package com.sh.app.user;

public class UserController {
	private UserService userService; 
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	public void insertUser() {
		this.userService.insertUser();
	}
}
