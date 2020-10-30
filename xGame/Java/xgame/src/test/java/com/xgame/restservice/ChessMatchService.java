package com.xgame.restservice;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.xgame.common.enums.MatchStatus;
import com.xgame.data.IChessMatchRepository;
import com.xgame.data.IMessageRepository;
import com.xgame.data.IUserRepository;
import com.xgame.data.entities.ChessMatch;
import com.xgame.data.entities.Message;
import com.xgame.data.entities.User;
import com.xgame.service.interfaces.IChessMatchService;

@SpringBootTest
class ChessMatchService {
	
	@Autowired
	private IChessMatchService service;
	@Autowired
	private IChessMatchRepository matchRepo;
	@Autowired
	private IUserRepository userRepo;
	@Autowired
	private IMessageRepository messageRepo;

	@Test
	void createMatch() {
		var matchCount = matchRepo.count();

		var player1 = userRepo.save(new User("junit1", "junit1@email.com", "junit1password"));
		var player2 = userRepo.save(new User("junit2", "junit2@email.com", "junit2password"));
		
		var newMatch = service.createMatch(player1.getId(), player2.getId());
		
		try {
			assertNotNull(newMatch);
			assertEquals(matchCount + 1, matchRepo.count());
			assertEquals(newMatch.getWhiteId(), player1.getId());
			assertEquals(newMatch.getBlackId(), player2.getId());
			assertEquals(newMatch.getWhiteEmail(), player1.getEmail());
			assertEquals(newMatch.getBlackEmail(), player2.getEmail());
			assertEquals(newMatch.getTurnCount(), 0);
		}
		catch(Exception e) {
			fail();
		}
		finally {
			//cleanup
			matchRepo.deleteById(newMatch.getId());
			userRepo.delete(player1);
			userRepo.delete(player2);
		}
		
	}
	
	@Test
	void acceptInvite() {
		
		var player1 = userRepo.save(new User("junit1", "junit1@email.com", "junit1password"));
		var player2 = userRepo.save(new User("junit2", "junit2@email.com", "junit2password"));
		List<Message> messages = new ArrayList<Message>();
		
		var chessMatch = new ChessMatch("This is a test board");
		chessMatch.setWhitePlayer(player1);
		chessMatch.setBlackPlayer(player2);
		chessMatch.setTurnCount(0);
		chessMatch.setMatchStatus(MatchStatus.PENDING);
		
		var match = matchRepo.saveAndFlush(chessMatch);
		try {
			//test match acceptance
			var success = service.acceptInvite(match.getId());
			assertTrue(success);
			
			messages = messageRepo.findByUserId(player1.getId());
			assertEquals(messages.size(), 1);
			assertEquals(messages.get(0).getContents(), player2.getNickname() + " has accepted your invitation to a match!");
			
			//match = matchRepo.getOne(match.getId());
			//assertEquals(match.getMatchStatus(), MatchStatus.INPROGRESS);
			//assertTrue(match.getStartTimestamp() != null);
		}
		catch(Exception e) {
			fail();
		}
		finally {
			//cleanup
			messageRepo.deleteAll(messages);
			matchRepo.delete(match);
			userRepo.delete(player1);
			userRepo.delete(player2);
		}
	}
}
