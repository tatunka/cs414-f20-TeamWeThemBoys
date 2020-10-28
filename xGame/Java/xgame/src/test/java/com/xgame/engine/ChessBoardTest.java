package com.xgame.engine;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.xgame.service.engine.ChessBoard;
import com.xgame.service.engine.ChessPiece.Color;
import com.xgame.service.engine.IllegalMoveException;
import com.xgame.service.engine.IllegalPositionException;
import com.xgame.service.engine.Pawn;
import com.xgame.service.engine.Rook;

class ChessBoardTest {
    
	ChessBoard board = new ChessBoard();
    
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
			wPawn.setPosition("b8");
			assertTrue(board.promotePawn(wPawn, "knight"));
			assertEquals(board.getPiece("b8").toString(), "\u2658");
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
	}
	
}