package com.xgame.service.engine;

import java.util.ArrayList;

public class Bishop extends ChessPiece{
	
	public Bishop(ChessBoard board, Color color) {
		super(board, color);
	}
	
	public Bishop() {
		super();
	}

	@Override
	public String toString() {
		return this.color == Color.BLACK ? "\u265D" : "\u2657";
	}

	@Override
	public ArrayList<String> legalMoves() throws IllegalPositionException {
		return legalMoves(true);
	}
	
	@Override
	public ArrayList<String> legalMoves(boolean checkTest) throws IllegalPositionException {
		ArrayList<String> moves = new ArrayList<String>();
		ChessPiece adjPiece;
		String move;
		try {

		    for (int i = column + 1, j = row + 1; i <= 7 && j <= 7; i++, j++) {
			move = toPosition(i, j);
		        adjPiece = board.getPiece(move);
		        if (adjPiece == null) {
		        	if(checkTest) {
			        	if(isSafe(move)) {
			        		moves.add(move);
			        	}
		        	}else {
		        		moves.add(move);
		        	}
		        } else if (adjPiece.getColor() != this.getColor()) {
		        	if(checkTest) {
			        	if(isSafe(move)) {
			        		moves.add(move);
			        	}
		        	}else {
		        		moves.add(move);
		        	}
		            break;
		        } else {
		            break;
		        }
		    }

		    for (int i = column - 1, j = row + 1; i >= 0 && j <= 7; i--, j++) {
			move = toPosition(i, j);
		        adjPiece = board.getPiece(move);
		        if (adjPiece == null) {
		        	if(checkTest) {
			        	if(isSafe(move)) {
			        		moves.add(move);
			        	}
		        	}else {
		        		moves.add(move);
		        	}
		        } else if (adjPiece.getColor() != this.getColor()) {
		        	if(checkTest) {
			        	if(isSafe(move)) {
			        		moves.add(move);
			        	}
		        	}else {
		        		moves.add(move);
		        	}
		            break;
		        } else {
		            break;
		        }
		    }

		    for (int i = column + 1, j = row - 1; i <= 7 && j >= 0; i++, j--) {
			move = toPosition(i, j);
		        adjPiece = board.getPiece(move);
		        if (adjPiece == null) {
		        	if(checkTest) {
			        	if(isSafe(move)) {
			        		moves.add(move);
			        	}
		        	}else {
		        		moves.add(move);
		        	}
		        } else if (adjPiece.getColor() != this.getColor()) {
		        	if(checkTest) {
			        	if(isSafe(move)) {
			        		moves.add(move);
			        	}
		        	}else {
		        		moves.add(move);
		        	}
		            break;
		        } else {
		            break;
		        }
		    }

		    for (int i = column - 1, j = row - 1; i >= 0 && j >= 0; i--, j--) {
			move = toPosition(i, j);
		        adjPiece = board.getPiece(move);
		        if (adjPiece == null) {
		        	if(checkTest) {
			        	if(isSafe(move)) {
			        		moves.add(move);
			        	}
		        	}else {
		        		moves.add(move);
		        	}
		        } else if (adjPiece.getColor() != this.getColor()) {
		        	if(checkTest) {
			        	if(isSafe(move)) {
			        		moves.add(move);
			        	}
		        	}else {
		        		moves.add(move);
		        	}
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
}
