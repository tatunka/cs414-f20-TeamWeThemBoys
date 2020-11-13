package com.xgame.restservice;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xgame.data.IChessMatchRepository;
import com.xgame.data.IMessageRepository;
import com.xgame.data.IUserRepository;
import com.xgame.data.entities.User;
import com.xgame.service.engine.ChessBoard;
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
			assertEquals(newMatch.getWhitePlayerId(), player1.getId());
			assertEquals(newMatch.getBlackPlayerId(), player2.getId());
			assertEquals(newMatch.getWhitePlayerNickname(), player1.getNickname());
			assertEquals(newMatch.getBlackPlayerNickname(), player2.getNickname());
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
		
		var match = service.createMatch(player1.getId(), player2.getId());
		
		try {
			
			var chessBoard = new ChessBoard();
			chessBoard.initialize();
			var mapper = new ObjectMapper();
			mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			var json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(chessBoard.getBoard());
			
			//test match acceptance
			var inprogMatch = service.acceptInvite(match.getId());
			assertEquals(inprogMatch.getWhitePlayerId(), player1.getId());
			assertEquals(inprogMatch.getWhitePlayerNickname(), player1.getNickname());
			assertEquals(inprogMatch.getBlackPlayerId(), player2.getId());
			assertEquals(inprogMatch.getBlackPlayerNickname(), player2.getNickname());
			assertEquals(inprogMatch.getChessBoard(), json);
			assertEquals(inprogMatch.getTurnCount(), 1);
			
			var messages = messageRepo.findByUserId(player1.getId());
			assertEquals(messages.size(), 1);
			assertEquals(messages.get(0).getContents(), player2.getNickname() + " has accepted your invitation to a match!");
		}
		catch(Exception e) {
			fail();
		}
		finally {
			//cleanup
			var messages = messageRepo.findByUserId(player1.getId());
			messageRepo.deleteAll(messages);
			matchRepo.deleteById(match.getId());
			userRepo.delete(player1);
			userRepo.delete(player2);
		}
	}
	
	@Test 
	void getMatchById(){
		var matchCount = matchRepo.count();
		
		var player1 = userRepo.save(new User("junit1", "junit1@email.com", "junit1password"));
		var player2 = userRepo.save(new User("junit2", "junit2@email.com", "junit2password"));
		
		var testMatch = service.createMatch(player1.getId(), player2.getId());
		var testMatchFromRepo = service.getMatch(testMatch.getId());
		
		try {
			assertNotNull(testMatchFromRepo);
			assertEquals(matchCount + 1, matchRepo.count());
			assertEquals(testMatchFromRepo.getWhitePlayerId(), player1.getId());
			assertEquals(testMatchFromRepo.getBlackPlayerId(), player2.getId());
			assertEquals(testMatchFromRepo.getWhitePlayerNickname(), player1.getNickname());
			assertEquals(testMatchFromRepo.getBlackPlayerNickname(), player2.getNickname());
			assertEquals(testMatchFromRepo.getTurnCount(), 0);
		}
		catch(Exception e) {
			fail();
		}
		finally {
			//cleanup
			matchRepo.deleteById(testMatchFromRepo.getId());
			userRepo.delete(player1);
			userRepo.delete(player2);
		}
	}
	
	@Test
	void cannotGetMatchById() {
		assertThrows(ResponseStatusException.class, () -> {
			//No match with Id "-1" should exist
			service.getMatch(-1); //
		});
	}
}
