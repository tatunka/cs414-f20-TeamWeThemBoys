package com.xgame.restservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.xgame.common.viewmodels.UserViewModel;
import com.xgame.service.interfaces.IUserService;
import com.xgame.service.models.UserCredentials;

@RestController
public class UserController {

	@Autowired
	IUserService userService;

	@GetMapping("/user/search")
	public List<UserViewModel> search(@RequestParam(value = "param", defaultValue = "NO_SEARCH_TEXT") String param) {

		return userService.search(param);
	}

	@PostMapping("/user")
	public UserViewModel registerNewUser(@RequestBody UserCredentials credentials) {
		try {
			return userService.registerNewUser(credentials);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"There is already a registered user with the given username, email, or password");
		}
	}

	@GetMapping("/user")
	public UserViewModel login(@RequestBody UserCredentials credentials) {
		return userService.login(credentials);
	}
}
