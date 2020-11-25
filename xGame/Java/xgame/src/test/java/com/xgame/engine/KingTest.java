package com.xgame.engine;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.xgame.service.engine.ChessBoard;
import com.xgame.service.engine.ChessPiece.Color;
import com.xgame.service.engine.IllegalPositionException;
import com.xgame.service.engine.King;

class KingTest {

	private ChessBoard board = new ChessBoard();
	private King wKing = new King(board, Color.WHITE);
	private King bKing= new King(board, Color.BLACK);
	
	private Boolean listEqualRegardlessOrder(List<String> correctList, ArrayList<String> givenList) {
		return (givenList.size() == correctList.size() && correctList.containsAll(givenList) && 
				givenList.containsAll(correctList));
	}
	
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
		board.initialize();
		board.placeNull("a8");

		try {
			board.placePiece(bKing, "e5");
			List<String> correctMoves = Arrays.asList("d6", "e6", "f6", "f5", "e4");
			ArrayList<String> givenMoves = bKing.legalMoves();
			assertTrue(listEqualRegardlessOrder(correctMoves, givenMoves));
			board.placeNull("e5");
			
			//moving out of corner
			board.placePiece(bKing, "a1");
			correctMoves = Arrays.asList("a2", "b2", "b1");
			givenMoves = bKing.legalMoves();
			assertTrue(listEqualRegardlessOrder(correctMoves, givenMoves));
			board.placeNull("a1");
			
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
	}
}





