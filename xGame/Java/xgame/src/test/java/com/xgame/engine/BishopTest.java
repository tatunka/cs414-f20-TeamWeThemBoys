package engine;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import engine.Bishop;
import engine.ChessBoard;
import engine.IllegalPositionException;
import engine.ChessPiece.Color;

class BishopTest {
	
	private ChessBoard board = new ChessBoard();
	private Bishop wBishop = new Bishop(board, Color.WHITE);
	private Bishop bBishop = new Bishop(board, Color.BLACK);
	
	@Test
	void getColorTest() {
		assertEquals(wBishop.getColor(), Color.WHITE, "White Bishop has wrong Color");
		assertEquals(bBishop.getColor(), Color.BLACK, "Black Bishop has wrong Color");
	}

	@Test
	void toStringTest() {
		assertEquals(wBishop.toString(), "\u2657", "White Bishop is wrong character");
		assertEquals(bBishop.toString(), "\u265D", "Black Bishop is wrong character");
	}
	
	//make sure good moves on empty board
	@Test
	void legalMovesTest() {
		try {
			bBishop.setPosition("e4");
			assertTrue(bBishop.legalMoves().containsAll(
				Arrays.asList("b1", "c2", "d3", "f5", "g6", "h7", 
							  "h1", "g2", "f3", "d5", "c6", "b7", "a8")));
  			//moving out of corner
			bBishop.setPosition("a1");
			assertTrue(bBishop.legalMoves().containsAll(
					Arrays.asList("b2", "c3", "d4", "e5", "f6", "g7", "h8")));
			//do not move out of bounds
			bBishop.setPosition("b2");
			assertTrue(bBishop.legalMoves().containsAll(
					Arrays.asList("a1", "c3", "d4", "e5", "f6", "g7", "h8", 
								  "c3", "a3")));
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
	}
	@Test
	void illegalMovesTest() {
		board.initialize();
		try {
			assertTrue(board.getPiece("c8").legalMoves().isEmpty(), "Should not have initial moves");
			//can move up to attack, cannot move back into same color
			bBishop.setPosition("d3");
			assertTrue(bBishop.legalMoves().containsAll(
					Arrays.asList("c4", "c2", "b1", "e4", "e2")));
			//can only attack
			bBishop.setPosition("g1");
			assertTrue(bBishop.legalMoves().containsAll(
					Arrays.asList("f2", "h2")));
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
	}

}
