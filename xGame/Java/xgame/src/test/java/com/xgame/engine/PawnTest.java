package com.xgame.engine;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.xgame.service.engine.ChessBoard;
import com.xgame.service.engine.ChessPiece.Color;
import com.xgame.service.engine.IllegalPositionException;
import com.xgame.service.engine.Knight;
import com.xgame.service.engine.Pawn;

class PawnTest {

	private ChessBoard board = new ChessBoard();
	private Pawn wPawn = new Pawn(board, Color.WHITE);
	private Pawn bPawn = new Pawn(board, Color.BLACK);
	
	@Test
	void getColor() {
		assertEquals(wPawn.getColor(), Color.WHITE, "White Pawn has wrong Color");
		assertEquals(bPawn.getColor(), Color.BLACK, "Black Pawn has wrong Color");
	}
	
	@Test
	void toStringTest() {
		assertEquals(wPawn.toString(), "\u2659", "White Pawn is wrong charatcer");
		assertEquals(bPawn.toString(), "\u265F", "Black Pawn is wrong charatcer");
	}
	
	//make sure good moves on empty board
	@Test
	void legalMovesTest() {
		try {
			bPawn.setPosition("e4");
			
			assertTrue(bPawn.legalMoves().containsAll(
				Arrays.asList("f3")));
			//cannot move further
			bPawn.setPosition("h1");
			assertTrue(bPawn.legalMoves().isEmpty());
			
			wPawn.setPosition("e4");
			assertTrue(wPawn.legalMoves().containsAll(
				Arrays.asList("d5")));
			//cannot mvoe further
			wPawn.setPosition("a8");
			assertTrue(wPawn.legalMoves().isEmpty());
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void legalAttackTest() {
		try {
			board.initialize();
			board.placePiece(new Knight(board, Color.WHITE), "d6");
			board.placePiece(new Knight(board, Color.WHITE), "e7");
			assertTrue(board.getPiece("d7").legalMoves().containsAll(
				Arrays.asList("e6", "d6", "e7")));
			
			board.placePiece(new Knight(board, Color.BLACK), "d2");
			board.placePiece(new Knight(board, Color.BLACK), "e3");
			assertTrue(board.getPiece("e2").legalMoves().containsAll(
					Arrays.asList("d3", "d2", "e3")));
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
	}
	
}
