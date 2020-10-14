package com.xgame.restservice;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.xgame.data.ChessMatchRepository;
import com.xgame.data.UserRepository;
import com.xgame.service.interfaces.IChessMatchService;

@SpringBootTest
class ChessMatchService {
	
	@Autowired
	private IChessMatchService service;
	@Autowired
	private ChessMatchRepository matchRepo;
	@Autowired
	private UserRepository userRepo;

	@Test
	void createMatch() {
		var matchCount = matchRepo.count();
		var userCount = userRepo.count();
		
		assertTrue(userCount >= 2, "There are not enough Users to create a match.");
		
		var users = userRepo.findAll();
		var user1 = users.get(0);
		var user2 = users.get(1);
		
		var newMatch = service.createMatch(user1.getId(), user2.getId());
		
		assertNotNull(newMatch);
		assertEquals(matchCount + 1, matchRepo.count());
		assertEquals(newMatch.getWhiteId(), user1.getId());
		assertEquals(newMatch.getBlackId(), user2.getId());
		assertEquals(newMatch.getWhiteEmail(), user1.getEmail());
		assertEquals(newMatch.getBlackEmail(), user2.getEmail());
		assertEquals(newMatch.getTurnCount(), 0);
		
		//cleanup
		matchRepo.deleteById(newMatch.getMatchId());
	}

}
