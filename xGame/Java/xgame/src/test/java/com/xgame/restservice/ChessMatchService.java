package com.xgame.restservice;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

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
import com.xgame.data.entities.Message;
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
		
		var match = service.createMatch(player1.getId(), player2.getId());
		
		try {
			
			var chessBoard = new ChessBoard();
			chessBoard.initialize();
			var mapper = new ObjectMapper();
			mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			var json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(chessBoard);
			
			//test match acceptance
			var inprogMatch = service.acceptInvite(match.getId());
			assertEquals(inprogMatch.getWhiteId(), player1.getId());
			assertEquals(inprogMatch.getWhiteEmail(), player1.getEmail());
			assertEquals(inprogMatch.getBlackId(), player2.getId());
			assertEquals(inprogMatch.getBlackEmail(), player2.getEmail());
			assertEquals(inprogMatch.getChessBoard(), json);
			assertEquals(inprogMatch.getTurnCount(), 1);
			
			messages = messageRepo.findByUserId(player1.getId());
			assertEquals(messages.size(), 1);
			assertEquals(messages.get(0).getContents(), player2.getNickname() + " has accepted your invitation to a match!");
		}
		catch(Exception e) {
			fail();
		}
		finally {
			//cleanup
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
			assertEquals(testMatchFromRepo.getWhiteId(), player1.getId());
			assertEquals(testMatchFromRepo.getBlackId(), player2.getId());
			assertEquals(testMatchFromRepo.getWhiteEmail(), player1.getEmail());
			assertEquals(testMatchFromRepo.getBlackEmail(), player2.getEmail());
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
