package com.xgame.engine;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.xgame.service.engine.ChessBoard;
import com.xgame.service.engine.ChessPiece.Color;
import com.xgame.service.engine.IllegalPositionException;
import com.xgame.service.engine.Knight;

class KnightTest {

	private ChessBoard board = new ChessBoard();
	private Knight wKnight = new Knight(board, Color.WHITE);
	private Knight bKnight = new Knight(board, Color.BLACK);

	@Test
	void getColorTest() {
		assertEquals(wKnight.getColor(), Color.WHITE, "White Knight has wrong Color");
		assertEquals(bKnight.getColor(), Color.BLACK, "Black Knight has wrong Color");
	}
	
	@Test
	void toStringTest() {
		assertEquals(wKnight.toString(), "\u2658", "White Knight is wrong character");
		assertEquals(bKnight.toString(), "\u265E", "Black Knight is wrong character");
	}
	
	@Test
	void legalMovesTest() {
		try {
			bKnight.setPosition("e4");
			assertTrue(bKnight.legalMoves().containsAll(
				Arrays.asList("d6", "f6", "g5", "g3", "f2", "d2", "c3",
							  "c5")));
			//moving out of corner
			bKnight.setPosition("a1");
			assertTrue(bKnight.legalMoves().containsAll(
				Arrays.asList("c2", "b3")));
			//do not move out of bounds
			bKnight.setPosition("b2");
			assertTrue(bKnight.legalMoves().containsAll(
					Arrays.asList("a4", "c4", "d3", "d1")));
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
			bKnight.setPosition("e5");
			assertTrue(bKnight.legalMoves().containsAll(
					Arrays.asList("f7", "g6", "g4", "d3", "f3", "c4")));
			//can only attack
			bKnight.setPosition("g1");
			assertTrue(bKnight.legalMoves().containsAll(
					Arrays.asList("h3", "f3", "e2")));
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
	}

}
