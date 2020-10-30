package com.xgame.restservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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
		var message1 = messageService.sendMessage(player1.getId(), "This is a message!");
		var message2 = messageService.sendMessage(player2.getId(), "this is another message!");
		
		try {
			var messages1 = messageService.getMessages(player1.getId());
			assertEquals(messages1.size(), 1);
			assertEquals(messages1.get(0).getContent(), message1.getContent());
			assertEquals(messages1.get(0).getId(), message1.getId());
			assertEquals(messages1.get(0).getType(), MessageType.MESSAGE);
			
			var messages2 = messageService.getMessages(player2.getId());
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
			messageRepo.deleteById(message1.getId());
			messageRepo.deleteById(message2.getId());
			userRepo.delete(player1);
			userRepo.delete(player2);
		}
		
	}
}
