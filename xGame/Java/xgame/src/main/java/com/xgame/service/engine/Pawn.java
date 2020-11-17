package com.xgame.service.engine;

import java.util.ArrayList;

import com.xgame.service.engine.ChessPiece.Color;

public class Pawn extends ChessPiece {
	
	public Pawn(ChessBoard board, Color color) {
		super(board, color);
	}
	
	public Pawn() {
		super();
	}

	@Override
	public String toString() {
		return this.color == Color.BLACK ? "\u265F" : "\u2659";
	}
	
	//adjusts the pawn move list to only list threatening moves
		public ArrayList<String> findThreateningPawnMoves(char c, char i) throws IllegalPositionException{
			ArrayList<String> moves = legalMoves();
			char letter = c;
			char number = i;
			if(this.color == Color.BLACK) {
				if(letter < 'h' && number > '1') {
					moves.remove(("" + (++letter) + (--number)));
				}
				if(letter < 'h') {
					number = i;
					letter = c;
					moves.add("" + ++letter + number);
				}
				if(number > '1') {
					number = i;
					letter = c;
					moves.add("" + letter + --number);
				}
			}
			if(this.color == Color.WHITE) {
				if(letter > 'a' && number < '8') {
					moves.remove(("" + (--letter) + (++number)));
				}
				if(letter > 'a') {
					number = i;
					letter = c;
					moves.add("" + --letter + number);
				}
				if(number < '8') {
					number = i;
					letter = c;
					moves.add("" + letter + ++number);
				}
			}
			return moves;
		}

	@Override
	public ArrayList<String> legalMoves() throws IllegalPositionException {
		ArrayList<String> moves = new ArrayList<String>();
		int dx = color == Color.WHITE ? 1 : -1;

		if((color == Color.WHITE && row == 7) || (color == Color.BLACK && row == 0)) {
			return moves;
		}
		
		String ahead = toPosition(column - dx, row + dx);
		try {
			
		    ChessPiece adjPiece = board.getPiece(ahead);
		    if (adjPiece == null) {
		        moves.add(ahead);
		    }
		    String aheadUp = toPosition(column, row + dx);
		    adjPiece = board.getPiece(aheadUp);
		    if (aheadUp != null && adjPiece != null && adjPiece.getColor() != color) {
		        moves.add(aheadUp);
		    }
		    String aheadOver = toPosition(column - dx, row);
		    adjPiece = board.getPiece(aheadOver);
		    if (aheadOver != null && adjPiece != null && adjPiece.getColor() != color) {
		        moves.add(aheadOver);
		    }
		} catch (IllegalPositionException e) {
			throw(e);
		}
		return moves;
	}
}
