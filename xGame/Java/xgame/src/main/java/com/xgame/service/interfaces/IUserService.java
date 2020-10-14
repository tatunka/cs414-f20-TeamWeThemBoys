package com.xgame.service.interfaces;

import java.util.List;

import com.xgame.common.viewmodels.ProfileViewModel;
import com.xgame.common.viewmodels.UserViewModel;

public interface IUserService {
	UserViewModel createNewUser(String name, String email, String password);
	List<UserViewModel> search(String param);
	Boolean deactivateUser(Integer id);
	Boolean deactivateUser(String email);
	ProfileViewModel getProfile(Integer id);
	ProfileViewModel getProfile(String email);
}
