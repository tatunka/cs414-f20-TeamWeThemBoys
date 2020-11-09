package com.xgame.engine;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.xgame.service.engine.ChessBoard;
import com.xgame.service.engine.ChessPiece.Color;
import com.xgame.service.engine.IllegalPositionException;
import com.xgame.service.engine.King;

class KingTest {

	private ChessBoard board = new ChessBoard();
	private King wKing = new King(board, Color.WHITE);
	private King bKing= new King(board, Color.BLACK);
	
	@Test
	void getColor() {
		assertEquals(wKing.getColor(), Color.WHITE, "White King has wrong Color");
		assertEquals(bKing.getColor(), Color.BLACK, "Black King has wrong Color");
	}
	
	@Test
	void toStringTest() {
		assertEquals(wKing.toString(), "\u2654", "White King is wrong charatcer");
		assertEquals(bKing.toString(), "\u265A", "Black King is wrong charatcer");
	}
	
	//make sure good moves on empty board
	@Test
	void legalMovesTest() {
		try {
			bKing.setPosition("e4");
			assertTrue(bKing.legalMoves().containsAll(
				Arrays.asList("d3", "d4", "d5", "e5", "e3", "f4", "f5", "f3")));
			//moving out of corner
			bKing.setPosition("a1");
			assertTrue(bKing.legalMoves().containsAll(
					Arrays.asList("a2", "b1", "b2")));
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void illegalMovesTest() {
		board.initialize();
		try {
			assertTrue(board.getPiece("b7").legalMoves().isEmpty(), "Should not have initial moves");
			//can move up, cannot move back into same color
			bKing.setPosition("e5");
			assertTrue(bKing.legalMoves().containsAll(
					Arrays.asList("e4", "d6", "e6", "f6", "f5")));
			//can only attack
			bKing.setPosition("g2");
			assertTrue(bKing.legalMoves().containsAll(
					Arrays.asList("g3", "h1")));
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
	}
}





