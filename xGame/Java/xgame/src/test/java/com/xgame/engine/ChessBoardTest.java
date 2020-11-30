package com.xgame.engine;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.xgame.service.engine.Bishop;
import com.xgame.service.engine.ChessBoard;
import com.xgame.service.engine.ChessPiece.Color;
import com.xgame.service.engine.IllegalMoveException;
import com.xgame.service.engine.IllegalPositionException;
import com.xgame.service.engine.King;
import com.xgame.service.engine.Knight;
import com.xgame.service.engine.Pawn;
import com.xgame.service.engine.Queen;
import com.xgame.service.engine.Rook;

class ChessBoardTest {
    
	ChessBoard board = new ChessBoard();
	
	@BeforeEach
	public void setUp() {
		board = new ChessBoard();
	}
	
    
	@Test
	void constructorTest() {
		assertNotNull(board);
	}
	
	@Test
	void getPieceWorks() {
		board.initialize();
		try {
			assertTrue(board.getPiece("e1").toString() == "\u2656");
			assertTrue(board.getPiece("a6").toString() == "\u265E");
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void getPieceEmptyString() {
		Exception exception = assertThrows(IllegalPositionException.class, () -> {
	        board.getPiece("");
	    });
	 
	    String expectedMessage = "is an illegal position";
	    String actualMessage = exception.toString();
	 
	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void getPieceBadArgs() {
		//bad rows
		assertThrows(IllegalPositionException.class, () -> {board.getPiece("h-1"); });
		assertThrows(IllegalPositionException.class, () -> {board.getPiece("h9"); });
		//bad columns
		assertThrows(IllegalPositionException.class, () -> {board.getPiece("x3"); });
		assertThrows(IllegalPositionException.class, () -> {board.getPiece("A3"); });
		//bad both
		assertThrows(IllegalPositionException.class, () -> {board.getPiece("3x"); });
		//nonsense characters
		assertThrows(IllegalPositionException.class, () -> {board.getPiece("b~"); });
		assertThrows(IllegalPositionException.class, () -> {board.getPiece("?4"); });
		assertThrows(IllegalPositionException.class, () -> {board.getPiece("!@"); });
	}
	
	
	
	@Test
	void initializePawnsTest() {
		board.initialize();
		String[] wPawnsPos = {"e4", "f2", "g3", "d1", "e2", "f3", "g4", "h5"};
		String[] bPawnsPos = {"b6", "c7", "d5", "a4", "b5", "c6", "d7", "e8"};
		try {
			for(int i = 0; i <= 7; i++) {
				assertTrue(board.getPiece(wPawnsPos[i]).toString() == "\u2659");
				assertTrue(board.getPiece(bPawnsPos[i]).toString() == "\u265F");
			}
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
			
	}
	
	@Test
	void initializeTest() {
		board.initialize();
		String[] bRowPos = {"a5", "a6", "a7", "a8", "b8", "c8", "d8", "b7"};
		String[] wRowPos = {"h4", "h3", "h2", "h1", "g1", "f1", "e1", "g2"};
		String[] bRow = {"\u265C", "\u265E", "\u265D", "\u265A", "\u265E", "\u265D", "\u265C", "\u265B"};
		String[] wRow = {"\u2656", "\u2658", "\u2657", "\u2654", "\u2658", "\u2657", "\u2656", "\u2655"};
		try {
			for(int i = 0; i <= 7; i++) {
				assertTrue(board.getPiece(wRowPos[i]).toString() == wRow[i]);
				assertTrue(board.getPiece(bRowPos[i]).toString() == bRow[i]);
			}
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
	}

	
	
	@Test
	void placePieceTest() {
		//good args
		assertEquals(board.placePiece(new Rook(board, Color.BLACK), "d4"), true);
		//null piece
		Rook bRook = null;
		assertEquals(board.placePiece(bRook, "d4"), false);
		//bad position
		assertEquals(board.placePiece(new Rook(board, Color.BLACK), "34"), false);
		assertEquals(board.placePiece(new Rook(board, Color.BLACK), ""), false);
	}
	
	@Test
	void badMovesTest(){
		//do not place any pieces
		assertThrows(IllegalMoveException.class, () -> { board.move("e1", "e2"); });
		//place all pieces, move something not there
		board.initialize();
		assertThrows(IllegalMoveException.class, () -> { board.move("e3", "e4"); });
		//piece is there, make a bad move
		//Pawn has moves, but not this
		assertThrows(IllegalMoveException.class, () -> { board.move("b2", "c3"); });
	}
	
	@Test
	void goodMovesTest() {
		board.initialize();
		try {
			board.move("e2", "d3");
			assertNull(board.getPiece("e2"));
			assertEquals(board.getPiece("d3").toString(), "\u2659");
			//create dummy rook to attack
			board.placePiece(new Rook(board, Color.WHITE), "c4");
			board.move("c4", "c1");
			board.move("c1", "a1");
			assertNull(board.getPiece("c1"));
			assertEquals(board.getPiece("a1").toString(), "\u2656");
			
		} catch (IllegalMoveException e) {
			e.printStackTrace();
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void promotePawnTest() {
		Pawn wPawn = new Pawn(board, Color.WHITE);
		try {
			board.placePiece(wPawn, "b8");
			assertFalse(board.promotePawn("b8", King.class));
			assertFalse(board.promotePawn("b8", Pawn.class));
			assertTrue(board.promotePawn("b8", Knight.class));
			assertEquals(board.getPiece("b8").toString(), "\u2658");
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void isThreatenedTest() {
		try {
			//test empty board
			assertFalse(board.isThreatened("a1", Color.BLACK));
			
			//test threat on an empty square
			board.placePiece(new Rook(board, Color.BLACK), "a8");
			assertTrue(board.isThreatened("a1", Color.BLACK));
			assertFalse(board.isThreatened("a1", Color.WHITE));
			
			//test threat on a filled square
			board.placePiece(new Rook(board,Color.WHITE), "a1");
			assertTrue(board.isThreatened("a1", Color.BLACK));
			assertFalse(board.isThreatened("a1", Color.WHITE));
			
			//test black pawn adjustment
			board.placePiece(new Pawn(board, Color.BLACK), "d3");
			assertFalse(board.isThreatened("e2", Color.BLACK));
			assertTrue(board.isThreatened("d2", Color.BLACK));
			assertTrue(board.isThreatened("e3", Color.BLACK));
			
			//test white pawn adjustment
			board.placePiece(new Pawn(board, Color.WHITE), "d3");
			assertFalse(board.isThreatened("c4", Color.WHITE));
			assertTrue(board.isThreatened("d4", Color.WHITE));
			assertTrue(board.isThreatened("c3", Color.WHITE));
		}catch (IllegalPositionException e) {
			System.out.println(e);
		}
		
	}
	
	@Test
	void isInCheckTest() {
		try {
			//test initial
			board.initialize();
			assertFalse(board.isInCheck(Color.WHITE));
			assertFalse(board.isInCheck(Color.BLACK));
			
			//test black in check
			board.placePiece(new Rook(board, Color.WHITE), "a7");
			assertTrue(board.isInCheck(Color.BLACK));
			assertFalse(board.isInCheck(Color.WHITE));
			
			//test both in check
			board.placePiece(new Rook(board, Color.BLACK), "h2");
			assertTrue(board.isInCheck(Color.BLACK));
			assertTrue(board.isInCheck(Color.WHITE));
			
			//test white in check
			board.initialize();
			board.placePiece(new Rook(board, Color.BLACK), "h2");
			assertFalse(board.isInCheck(Color.BLACK));
			assertTrue(board.isInCheck(Color.WHITE));
		}catch (IllegalPositionException e) {
			System.out.println(e);
		}
	}
	
	@Test
	void isInCheckGivenMoveTest() {
		board.initialize();
		try {
			//test that board state is preserved
			boolean value = board.isInCheckGivenMove("d5", "d4", Color.BLACK);
			ChessBoard compareBoard = new ChessBoard();
			compareBoard.initialize();
			assertTrue(board.toString().equals(compareBoard.toString()));
			assertFalse(value);
			
			//test move that leads to check
			board.placePiece(new Rook(board, Color.WHITE), "c8");
			value = board.isInCheckGivenMove("b8", "c6", Color.BLACK);
			assertTrue(value);
			
		}catch (IllegalPositionException e) {
			System.out.println(e);
		}
	}
	
	@Test
	void isInCheckMateTest() {
		try {
			//test minimum pieces
			board.placePiece(new King(board, Color.BLACK), "a8");
			board.placePiece(new Queen(board, Color.WHITE), "b7");
			assertTrue(board.isInCheck(Color.BLACK));
			assertFalse(board.isInCheckMate(Color.BLACK));
			board.placePiece(new Bishop(board, Color.WHITE), "c6");
			assertTrue(board.isInCheckMate(Color.BLACK));
			
			//test full board
			board.initialize();
			board.placePiece(new Queen(board, Color.WHITE), "b7");
			board.placePiece(new Bishop(board, Color.WHITE), "c6");
			assertTrue(board.isInCheck(Color.BLACK));
			assertFalse(board.isInCheckMate(Color.BLACK));
			board.placeNull("c7");
			board.placeNull("b6");
			board.placeNull("c8");
			board.placeNull("b8");
			assertTrue(board.isInCheckMate(Color.BLACK));
		}catch (IllegalPositionException e) {
			System.out.println(e);
		}
	}
}
