package com.xgame.restservice;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xgame.common.enums.MatchOutcome;
import com.xgame.common.enums.MatchStatus;
import com.xgame.common.viewmodels.MatchViewModel;
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
		User player1 = null;
		User player2 = null;
		MatchViewModel newMatch = null;
		
		try {
			player1 = userRepo.save(new User("junit1", "junit1@email.com", "junit1password"));
			player2 = userRepo.save(new User("junit2", "junit2@email.com", "junit2password"));
			
			newMatch = service.createMatch(player1.getId(), player2.getId());
			
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
			if(newMatch != null) {
				matchRepo.deleteById(newMatch.getId());
			}
			if(player1 != null) {
				userRepo.delete(player1);
			}
			if(player2 != null) {
				userRepo.delete(player2);
			}
		}
		
	}
	
	@Test
	void acceptInvite() {
		User player1 = null;
		User player2 = null;
		MatchViewModel match = null;
		
		try {
			player1 = userRepo.save(new User("junit1", "junit1@email.com", "junit1password"));
			player2 = userRepo.save(new User("junit2", "junit2@email.com", "junit2password"));
			
			match = service.createMatch(player1.getId(), player2.getId());
			
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
			
			var messages = messageRepo.findByUserIdAndReadTimestampIsNull(player1.getId());
			assertEquals(messages.size(), 1);
			assertEquals(messages.get(0).getContents(), player2.getNickname() + " has accepted your invitation to a match!");
		}
		catch(Exception e) {
			fail();
		}
		finally {
			//cleanup
			if(match != null) {
				matchRepo.deleteById(match.getId());
			}
			if(player1 != null) {
				var messages = messageRepo.findByUserIdAndReadTimestampIsNull(player1.getId());
				messageRepo.deleteAll(messages);
				userRepo.delete(player1);
			}
			if(player2 != null) {
				userRepo.delete(player2);
			}
		}
	}
	
	@Test 
	void getMatchById(){
		var matchCount = matchRepo.count();
		
		User player1 = null;
		User player2 = null;
		MatchViewModel testMatchFromRepo = null;
		
		try {
			player1 = userRepo.save(new User("junit1", "junit1@email.com", "junit1password"));
			player2 = userRepo.save(new User("junit2", "junit2@email.com", "junit2password"));
			
			var testMatch = service.createMatch(player1.getId(), player2.getId());
			testMatchFromRepo = service.getMatch(testMatch.getId());
			
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
			if(testMatchFromRepo != null) {
				matchRepo.deleteById(testMatchFromRepo.getId());
			}
			if(player1 != null) {
				userRepo.delete(player1);
			}
			if(player2 != null) {
				userRepo.delete(player2);
			}
		}
	}
	
	@Test
	void cannotGetMatchById() {
		assertThrows(ResponseStatusException.class, () -> {
			//No match with Id "-1" should exist
			service.getMatch(-1); //
		});
	}
	
	@Test
	void draw() {
		User player1 = null;
		User player2 = null;
		MatchViewModel match = null;
		
		try {
			player1 = userRepo.save(new User("junit1", "junit1@email.com", "junit1password"));
			player2 = userRepo.save(new User("junit2", "junit2@email.com", "junit2password"));
			
			//start match
			match = service.createMatch(player1.getId(), player2.getId());
			service.acceptInvite(match.getId());
			
			//suggest draw
			var suggestDraw = service.suggestDraw(match.getId(), player1.getId());
			assertNull(suggestDraw);
			var acceptDraw = service.suggestDraw(match.getId(), player2.getId());
			assertEquals(acceptDraw, MatchOutcome.DRAW);
			
			var updatedMatch = service.getMatch(match.getId());
			assertEquals(updatedMatch.getStatus(), MatchStatus.COMPLETED);			
		
			var messages1 = messageRepo.findByUserIdAndReadTimestampIsNull(player1.getId());
			var messages2 = messageRepo.findByUserIdAndReadTimestampIsNull(player2.getId());
			assertEquals(messages1.size(), 2);
			assertEquals(messages2.size(), 2);
		}
		catch(Exception e) {
			fail();
		}
		finally {
			//cleanup
			if(match != null) {
				matchRepo.deleteById(match.getId());
			}
			if(player1 != null) {
				var messages = messageRepo.findByUserIdAndReadTimestampIsNull(player1.getId());
				messageRepo.deleteAll(messages);
				userRepo.delete(player1);
			}
			if(player2 != null) {
				var messages2 = messageRepo.findByUserIdAndReadTimestampIsNull(player2.getId());
				messageRepo.deleteAll(messages2);
				userRepo.delete(player2);
			}
		}
	}
	
	@Test
	void denyDraw() {
		User player1 = null;
		User player2 = null;
		MatchViewModel match = null;
		
		try {
			player1 = userRepo.save(new User("junit1", "junit1@email.com", "junit1password"));
			player2 = userRepo.save(new User("junit2", "junit2@email.com", "junit2password"));
			
			//start match
			match = service.createMatch(player1.getId(), player2.getId());
			service.acceptInvite(match.getId());
			
			//suggest draw
			var suggestDraw = service.suggestDraw(match.getId(), player1.getId());
			assertNull(suggestDraw);
			
			//deny draw
			var acceptDraw = service.denyDraw(match.getId(), player2.getId());
			assertEquals(acceptDraw, MatchStatus.INPROGRESS);
			
			var updatedMatch = service.getMatch(match.getId());
			assertEquals(updatedMatch.getStatus(), MatchStatus.INPROGRESS);			
		
			var messages1 = messageRepo.findByUserIdAndReadTimestampIsNull(player1.getId());
			var messages2 = messageRepo.findByUserIdAndReadTimestampIsNull(player2.getId());
			assertEquals(messages1.size(), 2);
			assertEquals(messages2.size(), 2);
		}
		catch(Exception e) {
			fail();
		}
		finally {
			//cleanup
			if(match != null) {
				matchRepo.deleteById(match.getId());
			}
			if(player1 != null) {
				var messages = messageRepo.findByUserIdAndReadTimestampIsNull(player1.getId());
				messageRepo.deleteAll(messages);
				userRepo.delete(player1);
			}
			if(player2 != null) {
				var messages2 = messageRepo.findByUserIdAndReadTimestampIsNull(player2.getId());
				messageRepo.deleteAll(messages2);
				userRepo.delete(player2);
			}
		}
	}
}
