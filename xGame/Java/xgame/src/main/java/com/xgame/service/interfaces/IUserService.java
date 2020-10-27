package com.xgame.service.interfaces;

import java.util.List;

import org.springframework.web.server.ResponseStatusException;

import com.xgame.common.viewmodels.ProfileViewModel;
import com.xgame.common.viewmodels.UserViewModel;
import com.xgame.service.models.UserCredentials;

public interface IUserService {
	UserViewModel registerNewUser(UserCredentials credentials);

	List<UserViewModel> search(String param);

	UserViewModel login(UserCredentials credentials) throws ResponseStatusException;

	Boolean deactivateUser(Integer id);

	Boolean deactivateUser(String email);

	ProfileViewModel getProfile(Integer id);

	ProfileViewModel getProfile(String email);
}
