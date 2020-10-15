package com.xgame.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xgame.common.viewmodels.ProfileViewModel;
import com.xgame.common.viewmodels.UserViewModel;
import com.xgame.data.UserRepository;
import com.xgame.service.interfaces.IUserService;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserRepository userRepo;

	@Override
	public UserViewModel createNewUser(String name, String email, String password) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<UserViewModel> search(String param) {
		var users = userRepo.findByNicknameLikeAndIsDeletedFalseOrEmailAndIsDeletedFalse("%" + param + "%", param);
		
		return users.stream()
				.map(u -> new UserViewModel(u))
				.collect(Collectors.toList());
	}

	@Override
	public Boolean deactivateUser(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deactivateUser(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProfileViewModel getProfile(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProfileViewModel getProfile(String email) {
		// TODO Auto-generated method stub
		return null;
	}
}
