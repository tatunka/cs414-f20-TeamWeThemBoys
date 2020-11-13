package com.xgame.restservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.xgame.common.enums.MatchStatus;
import com.xgame.common.enums.MessageType;
import com.xgame.data.IChessMatchRepository;
import com.xgame.data.IMessageRepository;
import com.xgame.data.IUserRepository;
import com.xgame.data.entities.ChessMatch;
import com.xgame.data.entities.User;
import com.xgame.service.interfaces.IMessageService;

@SpringBootTest
public class MessageService {
	
	@Autowired
	private IMessageService messageService;
	@Autowired
	private IUserRepository userRepo;
	@Autowired
	private IChessMatchRepository matchRepo;
	@Autowired
	private IMessageRepository messageRepo;

	@Test
	void getInvites() {
		var player1 = userRepo.save(new User("junit1", "junit1@email.com", "junit1password"));
		var player2 = userRepo.save(new User("junit2", "junit2@email.com", "junit2password"));
		
		var chessMatch = new ChessMatch("This is a test board");
		chessMatch.setWhitePlayer(player1);
		chessMatch.setBlackPlayer(player2);
		chessMatch.setTurnCount(0);
		chessMatch.setMatchStatus(MatchStatus.PENDING);
		
		var match = matchRepo.save(chessMatch);
		
		try {
				
			//test invites
			var invites = messageService.getInvites(player2.getId());
			assertEquals(invites.size(), 1);
			
			var invite = invites.get(0);
			assertEquals(invite.getContent(), "junit1");
			assertEquals(invite.getId(), match.getId());
		}
		catch(Exception e) {
			fail();
		}
		finally {
			//cleanup
			matchRepo.delete(match);
			userRepo.delete(player1);
			userRepo.delete(player2);
		}
	}
	
	@Test
	void getMessages() {
		var player1 = userRepo.save(new User("junit1", "junit1@email.com", "junit1password"));
		var player2 = userRepo.save(new User("junit2", "junit2@email.com", "junit2password"));
		
		var chessMatch = new ChessMatch("This is a test board");
		chessMatch.setWhitePlayer(player1);
		chessMatch.setBlackPlayer(player2);
		chessMatch.setTurnCount(0);
		chessMatch.setMatchStatus(MatchStatus.PENDING);
		
		var match = matchRepo.save(chessMatch);
		var message1 = messageService.send(player1.getId(), "This is a message!");
		var message2 = messageService.send(player2.getId(), "this is another message!");
		
		try {
			var messages1 = messageService.getAll(player1.getId());
			assertEquals(messages1.size(), 1);
			assertEquals(messages1.get(0).getContent(), message1.getContent());
			assertEquals(messages1.get(0).getId(), message1.getId());
			assertEquals(messages1.get(0).getType(), MessageType.MESSAGE);
			
			var messages2 = messageService.getAll(player2.getId());
			assertEquals(messages2.size(), 2);
			assertEquals(messages2.get(0).getId(), match.getId());
			assertEquals(messages2.get(0).getType(), MessageType.INVITATION);
			assertEquals(messages2.get(0).getContent(), match.getWhitePlayer().getNickname());
			assertEquals(messages2.get(1).getId(), message2.getId());
			assertEquals(messages2.get(1).getType(), MessageType.MESSAGE);
			assertEquals(messages2.get(1).getContent(), message2.getContent());
		}
		catch(Exception e) {
			fail();
		}
		finally {
			matchRepo.delete(match);
			messageRepo.deleteAll(messageRepo.findByUserId(player1.getId()));
			messageRepo.deleteAll(messageRepo.findByUserId(player2.getId()));
			userRepo.delete(player1);
			userRepo.delete(player2);
		}
	}
	
	@Test
	void readMessage() {
		
		User user = null;
		
		try {
			user = userRepo.save(new User("junit1", "junit1@email.com", "junit1password"));

			var message1 = messageService.send(user.getId(), "Test message 1");
			var message2 = messageService.send(user.getId(), "Test message 2");
			var message3 = messageService.send(user.getId(), "Test message 3");
			var message4 = messageService.send(user.getId(), "Test message 4");
			var message5 = messageService.send(user.getId(), "Test message 5");
			var message6 = messageService.send(user.getId(), "Test message 6");
			
			var messages = messageService.getAll(user.getId());
			assertEquals(messages.size(), 6);
			
			messageService.read(message1.getId());
			messageService.read(message2.getId());
			messageService.read(message3.getId());
			
			var messages1 = messageService.getAll(user.getId());
			assertEquals(messages1.size(), 3);
			List<Integer> messages1Ids = messages1.stream().map(m -> m.getId()).collect(Collectors.toList());
			messages1Ids.contains(message4.getId());
			messages1Ids.contains(message5.getId());
			messages1Ids.contains(message6.getId());
			
			messageService.read(message4.getId());
			messageService.read(message5.getId());
			messageService.read(message6.getId());
			
			var messages2 = messageService.getAll(user.getId());
			assertEquals(messages2.size(), 0);
		}
		catch(Exception e) {
			fail(e);
		}
		finally {
			messageRepo.deleteAll(messageRepo.findByUserId(user.getId()));
			userRepo.delete(user);
		}
	}
}
