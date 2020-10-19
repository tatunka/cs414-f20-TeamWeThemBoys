package engine;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import engine.ChessBoard;
import engine.IllegalPositionException;
import engine.Rook;
import engine.ChessPiece.Color;

class RookTest {

	private ChessBoard board = new ChessBoard();
	private Rook wRook = new Rook(board, Color.WHITE);
	private Rook bRook = new Rook(board, Color.BLACK);
	
	@Test
	void getColor() {
		assertEquals(wRook.getColor(), Color.WHITE, "White Rook has wrong Color");
		assertEquals(bRook.getColor(), Color.BLACK, "Black Rook has wrong Color");
	}
	
	@Test
	void toStringTest() {
		assertEquals(wRook.toString(), "\u2656", "White Rook is wrong charatcer");
		assertEquals(bRook.toString(), "\u265C", "Black Rook is wrong charatcer");
	}
	
	@Test
	void setGoodPosition() {
		try {
			bRook.setPosition("a1");
			assertEquals(bRook.column, 0);
			assertEquals(bRook.row, 0);
			//change position
			bRook.setPosition("d6");
			assertEquals(bRook.column, 3);
			assertEquals(bRook.row, 5);
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void setBadPosition() {
		//position contains illegal characters
		assertThrows(IllegalPositionException.class, () ->{ bRook.setPosition("^6"); });
		assertThrows(IllegalPositionException.class, () ->{ bRook.setPosition("a#"); });
		assertThrows(IllegalPositionException.class, () ->{ bRook.setPosition("~~"); });
		assertThrows(IllegalPositionException.class, () ->{ bRook.setPosition(":^)"); });
		//position outside the board
		//bad columns
		assertThrows(IllegalPositionException.class, () ->{ bRook.setPosition("A4"); });
		assertThrows(IllegalPositionException.class, () ->{ bRook.setPosition("z4"); });
		//bad row
		assertThrows(IllegalPositionException.class, () ->{ bRook.setPosition("b0"); });
		assertThrows(IllegalPositionException.class, () ->{ bRook.setPosition("b9"); });
		//bad both
		assertThrows(IllegalPositionException.class, () ->{ bRook.setPosition("A0"); });
		assertThrows(IllegalPositionException.class, () ->{ bRook.setPosition("z0"); });
		assertThrows(IllegalPositionException.class, () ->{ bRook.setPosition("A9"); });
		assertThrows(IllegalPositionException.class, () ->{ bRook.setPosition("z9"); });
	}
	
	
	//make sure good moves on empty board
	@Test
	void legalMovesTest() {
		try {
			bRook.setPosition("e4");
			assertTrue(bRook.legalMoves().containsAll(
				Arrays.asList("e1", "e2", "e3", "e5", "e6", "e7", "e8", 
							  "a4", "b4", "c4", "d4", "f4", "g4", "h4")));
			//moving out of corner
			bRook.setPosition("a1");
			assertTrue(bRook.legalMoves().containsAll(
					Arrays.asList("a2", "a3", "a4", "a5", "a6", "a7", "a8", 
								  "b1", "c1", "d1", "f1", "g1", "h1")));
			//do not move out of bounds
			bRook.setPosition("b2");
			assertTrue(bRook.legalMoves().containsAll(
					Arrays.asList("b1", "b3", "b4", "b5", "b6", "b7", "b8", 
								  "a2", "c2", "d2", "e2", "f2", "g2", "h2")));
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void illegalMovesTest() {
		board.initialize();
		try {
			assertTrue(board.getPiece("a8").legalMoves().isEmpty(), "Should not have initial moves");
			//can move up to attack, cannot move back into same color
			bRook.setPosition("a3");
			assertTrue(bRook.legalMoves().containsAll(
					Arrays.asList("a1", "a2", "b3", "c3", "d3", "e3", "f3")));
			//can only attack
			bRook.setPosition("g1");
			assertTrue(bRook.legalMoves().containsAll(
					Arrays.asList("h1", "g2", "f1")));
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
	}
}
