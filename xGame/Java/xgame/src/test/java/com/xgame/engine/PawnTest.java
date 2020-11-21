package com.xgame.engine;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import com.xgame.service.engine.ChessBoard;
import com.xgame.service.engine.ChessPiece.Color;
import com.xgame.service.engine.IllegalPositionException;
import com.xgame.service.engine.King;
import com.xgame.service.engine.Knight;
import com.xgame.service.engine.Pawn;
import com.xgame.service.engine.Rook;

class PawnTest {

	private ChessBoard board = new ChessBoard();
	private Pawn wPawn = new Pawn(board, Color.WHITE);
	private Pawn bPawn = new Pawn(board, Color.BLACK);
	
	private Boolean listEqualRegardlessOrder(List<String> correctList, ArrayList<String> givenList) {
		return (givenList.size() == correctList.size() && correctList.containsAll(givenList) && 
				givenList.containsAll(correctList));
	}
	
	@BeforeEach
	void setup() {
		board = new ChessBoard();
		wPawn = new Pawn(board, Color.WHITE);
		bPawn = new Pawn(board, Color.BLACK);
	}
	
	
	@Test
	void getColor() {
		assertEquals(wPawn.getColor(), Color.WHITE, "White Pawn has wrong Color");
		assertEquals(bPawn.getColor(), Color.BLACK, "Black Pawn has wrong Color");
	}
	
	@Test
	void toStringTest() {
		assertEquals(wPawn.toString(), "\u2659", "White Pawn is wrong character");
		assertEquals(bPawn.toString(), "\u265F", "Black Pawn is wrong character");
	}
	
	//make sure good moves on empty board
	@Test
	void legalMovesTest() {
		try {
			board.placePiece(bPawn, "e5");
			assertTrue(listEqualRegardlessOrder(Arrays.asList("f4"), bPawn.legalMoves()));
			board.placeNull("e5");
			
			//cannot move further
			board.placePiece(bPawn, "h1");
			assertTrue(bPawn.legalMoves().isEmpty());
			board.placeNull("h1");
			
			board.placePiece(wPawn, "e4");
			assertTrue(listEqualRegardlessOrder(Arrays.asList("d5"), (wPawn.legalMoves())));
			board.placeNull("e4");
			
			//cannot move further
			board.placePiece(wPawn, "a8");
			assertTrue(wPawn.legalMoves().isEmpty());
			
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void threateningMovesTest() {
		try {
			board.placePiece(bPawn, "e4");
			
			assertTrue(listEqualRegardlessOrder(Arrays.asList("f4", "e3"), bPawn.findThreateningPawnMoves('e','4')));
			
			board.placePiece(wPawn, "e4");
			
			assertTrue(listEqualRegardlessOrder(Arrays.asList("d4", "e5"), wPawn.findThreateningPawnMoves('e','4')));
			
			board.placePiece(bPawn, "h1");
			
			assertTrue(bPawn.findThreateningPawnMoves('h','1').isEmpty());
			
			board.placePiece(wPawn, "a8");
			
			assertTrue(wPawn.findThreateningPawnMoves('a','8').isEmpty());
			
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
			assertTrue(listEqualRegardlessOrder(Arrays.asList("e6", "d6", "e7"), board.getPiece("d7").legalMoves()));
			
			board.placePiece(new Knight(board, Color.BLACK), "d2");
			board.placePiece(new Knight(board, Color.BLACK), "e3");
			assertTrue(listEqualRegardlessOrder(Arrays.asList("d3", "d2", "e3"), board.getPiece("e2").legalMoves()));
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void checkBehaviorTest() {
		try {
			board.placePiece(new King(board, Color.BLACK), "c5");
			board.placePiece(new Rook(board, Color.WHITE), "f5");
			board.placePiece(bPawn, "c6");
			assertTrue(listEqualRegardlessOrder(Arrays.asList("d5"), bPawn.legalMoves()));
			board.placePiece(bPawn, "d3");
			assertTrue(board.isInCheck(Color.BLACK));
			assertTrue(bPawn.legalMoves().isEmpty());
			
			//make sure pawns cannot move to leave king in check
			board.initialize();
			board.placePiece(new Rook(board, Color.WHITE), "d8");
			board.placeNull("c8");
			board.placeNull("c7");
			board.placeNull("b8");
			board.placePiece(bPawn, "b8");
			assertTrue(bPawn.legalMoves().isEmpty());
			board.placePiece(new Rook(board, Color.WHITE), "c8");
			assertTrue(bPawn.legalMoves().equals(Arrays.asList("c8")));
			
			
		}catch (IllegalPositionException e) {
			e.printStackTrace();
		}
	}
	
}
