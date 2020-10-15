package com.xgame.restservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xgame.common.viewmodels.UserViewModel;
import com.xgame.service.interfaces.IUserService;

@RestController
public class UserController {
	
	@Autowired
	IUserService userService;
	
	@GetMapping("/user/search")
	public List<UserViewModel> search(
			@RequestParam(value = "param", defaultValue="NO_SEARCH_TEXT") String param) {
		
		return userService.search(param);
	}
}
