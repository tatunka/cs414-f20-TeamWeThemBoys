package com.xgame.service.engine;

import java.util.ArrayList;

public class Rook extends ChessPiece {
	
	private ChessPieceType type = ChessPieceType.ROOK;

	public Rook(ChessBoard board, Color color) {
		super(board, color);

	}

	@Override
	public String toString() {
		return this.color == Color.BLACK ? "\u265C" : "\u2656";
	}

	@Override
	public ArrayList<String> legalMoves() throws IllegalPositionException {
		ArrayList<String> moves = new ArrayList<String>();
		ChessPiece adjPiece;
		String move;
		try {
			//all possible moves in the up
			for (int i = row + 1; i <= 7; i++) {
				move = toPosition(column, i);
		        adjPiece = board.getPiece(move);
		        if (adjPiece == null) {
		            moves.add(move);
		        } else if (adjPiece.getColor() != this.getColor()) {
		            moves.add(move);
		            break;
		        } else {
		            break;
		        }
		    }
		    //all possible moves in the down
			for (int i = row - 1; i >= 0; i--) {
				move = toPosition(column, i);
		        adjPiece = board.getPiece(move);
		        if (adjPiece == null) {
		            moves.add(move);
		        } else if (adjPiece.getColor() != this.getColor()) {
		            moves.add(move);
		            break;
		        } else {
		            break;
		        }
		    }
		    //all possible moves to the right
			for (int i = column + 1; i <= 7 ; i++) {
				move = toPosition(i, row);
		        adjPiece = board.getPiece(move);
		        if (adjPiece == null) {
		            moves.add(move);
		        } else if (adjPiece.getColor() != this.getColor()) {
		            moves.add(move);
		            break;
		        } else {
		            break;
		        }
		    }
		    //all possible moves to the left
			for (int i = column - 1; i >= 0 ; i--) {
				move = toPosition(i, row);
		        adjPiece = board.getPiece(move);
		        if (adjPiece == null) {
		            moves.add(move);
		        } else if (adjPiece.getColor() != this.getColor()) {
		            moves.add(move);
		            break;
		        } else {
		            break;
		        }
		    }
		} catch (IllegalPositionException e) {
			throw(e);
		}
		
		return moves;
	}

	public ChessPieceType getType() {
		return type;
	}

}
