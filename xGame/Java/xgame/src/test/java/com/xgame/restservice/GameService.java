package com.xgame.restservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xgame.data.IChessMatchRepository;
import com.xgame.data.IMessageRepository;
import com.xgame.data.IUserRepository;
import com.xgame.service.engine.ChessBoard;
import com.xgame.service.engine.ChessPiece;
import com.xgame.service.engine.ChessPiece.Color;
import com.xgame.service.engine.IllegalMoveException;
import com.xgame.service.engine.IllegalPositionException;
import com.xgame.service.engine.Knight;
import com.xgame.service.engine.Pawn;
import com.xgame.service.interfaces.IChessMatchService;
import com.xgame.service.interfaces.IGameService;
import com.xgame.service.interfaces.IUserService;
import com.xgame.service.models.UserCredentials;

@SpringBootTest
public class GameService {

	@Autowired
	private IGameService gameService;
	@Autowired
	private IChessMatchService matchService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IMessageRepository messageRepo;
	@Autowired 
	private IUserRepository userRepo;
	@Autowired
	private IChessMatchRepository matchRepo;
	
	
	@Test
	void moveTest() throws JsonMappingException, JsonProcessingException, IllegalMoveException, IllegalPositionException {
		var credentials1 = new UserCredentials("user1", "user1@email.com", "user1Password");
		var credentials2 = new UserCredentials("user2", "user2@email.com", "user2Password");
		var user1 = userService.registerNewUser(credentials1);
		var user2 = userService.registerNewUser(credentials2);
		
		var pendingMatch = matchService.createMatch(user1.getId(), user2.getId());
		var match = matchService.acceptInvite(pendingMatch.getId());
		
		try {
			//move white pawn
			var move1 = gameService.move(match.getId(), "d1", "c2");
			//move black knight
			var move2 = gameService.move(match.getId(), "a6", "b4");
			//move white knight
			var move3 = gameService.move(match.getId(), "h3", "g5");
			//move black knight
			var move4 = gameService.move(match.getId(), "b4", "c2");
			//move white knight
			var move5 = gameService.move(match.getId(), "g5", "e6");
			
			//check for errors
			//wrong turn
			Assertions.assertThrows(Exception.class, () -> gameService.move(match.getId(), "e1", "d1"));
			//illegal move
			Assertions.assertThrows(Exception.class, () -> gameService.move(match.getId(), "a5", "b5"));
			//illegal position
			Assertions.assertThrows(Exception.class, () -> gameService.move(match.getId(), "x", "q"));
			//no piece
			Assertions.assertThrows(Exception.class, () -> gameService.move(match.getId(), "a1", "a2"));
			
			//move black bishop
			var move6 = gameService.move(match.getId(), "a4", "b3");
			//move white knight
			var move7 = gameService.move(match.getId(), "e6", "c7");
			//move black knight
			Assertions.assertThrows(IllegalMoveException.class, () -> gameService.move(match.getId(), "c2", "a1"));
			
			var mapper = new ObjectMapper();
			mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			var board = mapper.readValue(move7.getChessBoard(), ChessPiece[][].class);
			var chessBoard = new ChessBoard();
			chessBoard.resume(board);
			
			var blackPawn = chessBoard.getPiece("b3");
			assertEquals(blackPawn.getColor(), Color.BLACK);
			assertEquals(blackPawn.getClass(), Pawn.class);
			
			var whiteKnight = chessBoard.getPiece("c7");
			assertEquals(whiteKnight.getColor(), Color.WHITE);
			assertEquals(whiteKnight.getClass(), Knight.class);
			
			var emptyBlackKnight = chessBoard.getPiece("a6");
			assertNull(emptyBlackKnight);
			var emptyBlackPawn2 = chessBoard.getPiece("a4");
			assertNull(emptyBlackPawn2);
			var emptyWhitePawn = chessBoard.getPiece("d1");
			assertNull(emptyWhitePawn);
			var emptyWhiteKnight = chessBoard.getPiece("h3");
			assertNull(emptyWhiteKnight);
			
			//try to make a move on a completed match
			Assertions.assertThrows(Exception.class, () -> gameService.move(match.getId(), "e1", "d1"));
		}
		catch (Exception e) {
			fail(e);
		}
		finally {
			var messages1 = messageRepo.findByUserIdAndReadTimestampIsNull(user1.getId());
			var messages2 = messageRepo.findByUserIdAndReadTimestampIsNull(user2.getId());
			messageRepo.deleteAll(messages1);
			messageRepo.deleteAll(messages2);
			
			matchRepo.deleteById(match.getId());
			
			userRepo.deleteById(user1.getId());
			userRepo.deleteById(user2.getId());
		}
	}	

	@Test
	void getLegalMovesTest() throws JsonMappingException, JsonProcessingException, IllegalPositionException {
		var credentials1 = new UserCredentials("user1", "user1@email.com", "user1Password");
		var credentials2 = new UserCredentials("user2", "user2@email.com", "user2Password");
		var user1 = userService.registerNewUser(credentials1);
		var user2 = userService.registerNewUser(credentials2);

		var pendingMatch = matchService.createMatch(user1.getId(), user2.getId());
		var match = matchService.acceptInvite(pendingMatch.getId());

		try {
			// white pawn
			assertTrue(
					gameService.getLegalMoves(match.getId(), "d1").equals(new ArrayList<String>(Arrays.asList("c2"))));
			// white knight
			assertTrue(gameService.getLegalMoves(match.getId(), "h3")
					.equals(new ArrayList<String>(Arrays.asList("f4", "g5"))));
			// black castle
			assertTrue(gameService.getLegalMoves(match.getId(), "a5").equals(new ArrayList<String>()));

			// illegal position
			Assertions.assertThrows(Exception.class, () -> gameService.getLegalMoves(match.getId(), "x"));

		} catch (Exception e) {
			fail();
		} finally {
			var messages1 = messageRepo.findByUserIdAndReadTimestampIsNull(user1.getId());
			var messages2 = messageRepo.findByUserIdAndReadTimestampIsNull(user2.getId());
			messageRepo.deleteAll(messages1);
			messageRepo.deleteAll(messages2);

			matchRepo.deleteById(match.getId());

			userRepo.deleteById(user1.getId());
			userRepo.deleteById(user2.getId());
		}
	}
	
	@Test
	void PromotePawn() throws JsonProcessingException{
		var credentials1 = new UserCredentials("user1", "user1@email.com", "user1Password");
		var credentials2 = new UserCredentials("user2", "user2@email.com", "user2Password");
		var user1 = userService.registerNewUser(credentials1);
		var user2 = userService.registerNewUser(credentials2);

		var pendingMatch = matchService.createMatch(user1.getId(), user2.getId());
		var match = matchService.acceptInvite(pendingMatch.getId());
		try {
			var chessBoard = new ChessBoard();
			chessBoard.placePiece(new Pawn(chessBoard, Color.WHITE), "b8");
			
			var mapper = new ObjectMapper();
			mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			var json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(chessBoard.getBoard());
			match.setChessBoard(json);
			var match1 = matchService.updateMatch(match);
			var initialBoard = mapper.readValue(match1.getChessBoard(), ChessPiece[][].class);
			var pawn = initialBoard[7][1];
			assertEquals(pawn.getClass(), Class.forName("com.xgame.service.engine.Pawn"));
			assertEquals(pawn.getColor(), Color.WHITE);
			
			var match2 = gameService.promotePawn(match.getId(), "b8", "Knight");
			var board = mapper.readValue(match2.getChessBoard(), ChessPiece[][].class);
			
			var knight = board[7][1];
			assertEquals(knight.getClass(), Class.forName("com.xgame.service.engine.Knight"));
			assertEquals(knight.getColor(), Color.WHITE);
		}
		catch(Exception e) {
			fail(e);
		}
		finally {
			var messages1 = messageRepo.findByUserIdAndReadTimestampIsNull(user1.getId());
			var messages2 = messageRepo.findByUserIdAndReadTimestampIsNull(user2.getId());
			messageRepo.deleteAll(messages1);
			messageRepo.deleteAll(messages2);

			matchRepo.deleteById(match.getId());

			userRepo.deleteById(user1.getId());
			userRepo.deleteById(user2.getId());
		}
	}
}
