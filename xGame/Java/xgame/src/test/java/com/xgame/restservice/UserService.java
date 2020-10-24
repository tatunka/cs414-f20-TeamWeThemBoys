package com.xgame.restservice;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.xgame.data.UserRepository;
import com.xgame.data.entities.User;
import com.xgame.service.interfaces.IUserService;

@SpringBootTest
class UserService {

	@Autowired
	IUserService userService;
	@Autowired
	UserRepository userRepo;

	@Test
	void search() {
		var user1 = new User("JUnit1", "junit1@email.com", "junit1password");
		var user2 = new User("JUnit2", "junit2@email.com", "junit2password");
		var user3 = new User("JUnit3", "junit3@email.com", "junit3password");
		var userEntity1 = userRepo.save(user1);
		var userEntity2 = userRepo.save(user2);
		var userEntity3 = userRepo.save(user3);

		var fuzzySearch = userService.search("JUnit");
		var exactNicknameSearch = userService.search("JUnit2");
		var emailSearch = userService.search("junit3@email.com");

		var fuzzySearchUserIds = fuzzySearch.stream().map(f -> f.getId()).collect(Collectors.toList());
		var nicknameIds = exactNicknameSearch.stream().map(f -> f.getId()).collect(Collectors.toList());
		var emailIds = emailSearch.stream().map(f -> f.getId()).collect(Collectors.toList());

		assertTrue(fuzzySearch.size() >= 3);
		assertTrue(exactNicknameSearch.size() >= 1);
		assertTrue(emailSearch.size() == 1);

		assertTrue(fuzzySearchUserIds.contains(userEntity1.getId()));
		assertTrue(fuzzySearchUserIds.contains(userEntity2.getId()));
		assertTrue(fuzzySearchUserIds.contains(userEntity3.getId()));
		assertTrue(nicknameIds.contains(userEntity2.getId()));
		assertTrue(emailIds.contains(userEntity3.getId()));

		// cleanup
		userRepo.delete(userEntity1);
		userRepo.delete(userEntity2);
		userRepo.delete(userEntity3);
	}

	@Test
	void registerNewUser_validCredentials_addsUserToDatabase() {
//		var testNickname = "testNickname1";
//		var testEmail = "testEmail1";
//		var testPassword = "testPassword1";
//
//		var credentials = new UserCredentials(testNickname, testEmail, testPassword);
//		userService.registerNewUser(credentials);
//
//		var userInDb = userRepo.findByEmailAndPasswordHashAndIsDeletedFalse(testEmail, testPassword);
//		var userInDbView = new UserViewModel(userInDb);
//
//		assertTrue(userInDbView.getNickname().equals(testNickname));
//		assertTrue(userInDbView.getEmail().equals(testEmail));
//
//		userRepo.delete(userInDb);
	}

}
