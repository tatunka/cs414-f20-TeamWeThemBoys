package com.xgame.service.interfaces;

import com.xgame.common.viewmodels.ProfileViewModel;
import com.xgame.data.entities.User;

public interface IUserService {
	User createNewUser(String name, String email, String password);
	Boolean deactivateUser(Integer id);
	Boolean deactivateUser(String email);
	ProfileViewModel getProfile(Integer id);
	ProfileViewModel getProfile(String email);
}
