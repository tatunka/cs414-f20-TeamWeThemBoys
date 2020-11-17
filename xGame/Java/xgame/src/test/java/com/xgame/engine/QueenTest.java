package com.xgame.engine;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.xgame.service.engine.ChessBoard;
import com.xgame.service.engine.ChessPiece.Color;
import com.xgame.service.engine.IllegalPositionException;
import com.xgame.service.engine.King;
import com.xgame.service.engine.Queen;
import com.xgame.service.engine.Rook;

class QueenTest {

	private ChessBoard board = new ChessBoard();
	private Queen wQueen = new Queen(board, Color.WHITE);
	private Queen bQueen = new Queen(board, Color.BLACK);
	
	private Boolean listEqualRegardlessOrder(List<String> correctList, ArrayList<String> givenList) {
		return (givenList.size() == correctList.size() && correctList.containsAll(givenList) && 
				givenList.containsAll(correctList));
	}
	
	@BeforeEach
	void setup() {
		board = new ChessBoard();
		wQueen = new Queen(board, Color.WHITE);
		bQueen = new Queen(board, Color.BLACK);
	}

	@Test
	void getColorTest() {
		assertEquals(wQueen.getColor(), Color.WHITE, "White Queen has wrong Color");
		assertEquals(bQueen.getColor(), Color.BLACK, "Black Queen has wrong Color");
	}
	
	@Test
	void toStringTest() {
		assertEquals(wQueen.toString(), "\u2655", "White Queen is wrong character");
		assertEquals(bQueen.toString(), "\u265B", "Black Queen is wrong character");
	}
	
	@Test
	void legalMovesTest() {
		try {
			bQueen.setPosition("e4");
			assertTrue(bQueen.legalMoves().containsAll(
				Arrays.asList("e1", "e2", "e3", "e5", "e6", "e7", "e8", 
						  	  "a4", "b4", "c4", "d4", "f4", "g4", "h4", 
						  	  "b1", "c2", "d3", "f5", "g6", "h7", "h1",
							  "g2", "f3", "d5", "c6", "b7", "a8")));
//			moving out of corner
			bQueen.setPosition("a1");
			assertTrue(bQueen.legalMoves().containsAll(
				Arrays.asList("b2", "c3", "d4", "e5", "f6", "g7", "h8",
							  "a2", "a3", "a4", "a5", "a6", "a7", "a8", 
							  "b1", "c1", "d1", "f1", "g1", "h1")));
			
			//do not move out of bounds
			bQueen.setPosition("b2");
			assertTrue(bQueen.legalMoves().containsAll(
				Arrays.asList("b1", "b3", "b4", "b5", "b6", "b7", "b8", 
							  "a2", "c2", "d2", "e2", "f2", "g2", "h2",
							  "a1", "c3", "d4", "e5", "f6", "g7", "h8", 
							  "c3", "a3")));
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
			bQueen.setPosition("e5");
			assertTrue(bQueen.legalMoves().containsAll(
				Arrays.asList("e4", "f4", "g3", "f5", "g5", "h5", "f6", 
							  "g7", "h8", "e6", "e7", "d4", "c3", "b2", 
							  "a1")));
			//can only attack
			bQueen.setPosition("g2");
			assertTrue(bQueen.legalMoves().containsAll(
				Arrays.asList("f1", "g1", "h1", "h2", "h3", "g3", "f3", 
							  "f2")));
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void checkBehaviorTest() {
		try {
			//test that there are no valid moves while in check
			board.placePiece(new King(board, Color.BLACK), "e3");
			board.placePiece(bQueen, "g1");
			board.placePiece(new Rook(board, Color.WHITE), "e7");
			assertTrue(board.isInCheck(Color.BLACK));
			assertTrue(bQueen.legalMoves().isEmpty());
			
			//test that only the move to get out of check exists
			board.placePiece(bQueen, "g4");
			assertTrue(listEqualRegardlessOrder(Arrays.asList("e4","e6"), bQueen.legalMoves()));
			
			//test can't move to result in same color check
			board.placePiece(bQueen, "e4");
			assertTrue(listEqualRegardlessOrder(Arrays.asList("e5","e6","e7"), bQueen.legalMoves()));
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
	}
}
