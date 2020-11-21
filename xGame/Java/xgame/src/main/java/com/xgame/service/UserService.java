package com.xgame.service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.google.common.hash.Hashing;
import com.xgame.common.enums.MatchStatus;
import com.xgame.common.viewmodels.MatchHistoryViewModel;
import com.xgame.common.viewmodels.ProfileViewModel;
import com.xgame.common.viewmodels.UserViewModel;
import com.xgame.data.IChessMatchRepository;
import com.xgame.data.IUserRepository;
import com.xgame.data.entities.User;
import com.xgame.service.interfaces.IUserService;
import com.xgame.service.models.UserCredentials;

@Service
public class UserService implements IUserService {

	@Autowired
	private IUserRepository userRepo;
	@Autowired
	private IChessMatchRepository matchRepo;
	
	private final String salt = "boys-them-we-team";

	@Override
	public UserViewModel registerNewUser(UserCredentials credentials) {
		var hashedPassword = Hashing.sha256().hashString(credentials.password + salt, StandardCharsets.UTF_8)
				.toString();
		var user = new User(credentials.nickname, credentials.email, hashedPassword);
		userRepo.save(user);

		return new UserViewModel(user);
	}

	@Override
	public List<UserViewModel> search(String param) {
		var users = userRepo.findByNicknameLikeAndIsDeletedFalseOrEmailAndIsDeletedFalse("%" + param + "%", param);

		return users.stream().map(u -> new UserViewModel(u)).collect(Collectors.toList());
	}

	@Override
	public UserViewModel login(UserCredentials credentials) throws ResponseStatusException {
		var hashedPassword = Hashing.sha256().hashString(credentials.password + salt, StandardCharsets.UTF_8)
				.toString();
		var user = userRepo.findByEmailAndPasswordHashAndIsDeletedFalse(credentials.email, hashedPassword);

		if (!user.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No account found for given email and password.");
		}

		return new UserViewModel(user.get());

	}

	@Override
	public void deactivateUser(Integer id) {
		var userOpt = userRepo.findById(id);
		
		if(!userOpt.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No account found for given id.");
		}

		var user = userOpt.get();
		user.setIsDeleted(true);
		userRepo.save(user);
	}

	@Override
	public ProfileViewModel getProfile(Integer id) {
		var u = userRepo.findById(id);
		
		if (!u.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No account found for given email and password.");
		}
		
		var user = u.get();
		
		var matches = matchRepo.findByBlackPlayerIdAndMatchStatus(user.getId(), MatchStatus.COMPLETED);
		matches.addAll(matchRepo.findByWhitePlayerIdAndMatchStatus(user.getId(), MatchStatus.COMPLETED));
		
		List<MatchHistoryViewModel> viewModels = matches
				.stream()
				.map(i -> new MatchHistoryViewModel(i, user))
				.collect(Collectors.toList());
		
		System.out.println("views: " + viewModels);
		
		return new ProfileViewModel(new UserViewModel(user).getNickname(), viewModels);
	}
}
