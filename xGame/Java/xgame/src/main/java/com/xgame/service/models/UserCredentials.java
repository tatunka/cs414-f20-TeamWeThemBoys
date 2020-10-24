package com.xgame.service.models;

public class UserCredentials {
	public String nickname;
	public String email;
	public String password;

	public UserCredentials(String nickname, String email, String password) {
		this.nickname = nickname;
		this.email = email;
		this.password = password;
	}
}
