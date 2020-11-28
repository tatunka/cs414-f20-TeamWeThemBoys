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

	    for (int i = column + 1, j = row + 1; i <= 7 && j <= 7; i++, j++) {
	    	var move = toPositionString(i, j);
	        var adjPiece = board.getPiece(move);
	        if (adjPiece == null || adjPiece.getColor() != this.color) {
        		if (!checkTest || checkTest && isSafe(move)) {
		        	moves.add(move);
		        }
        		if(adjPiece != null) {
        			break;
        		}
	        }
	        else {
	        	break;
	        }
	    }

	    for (int i = column - 1, j = row + 1; i >= 0 && j <= 7; i--, j++) {
	    	var move = toPositionString(i, j);
	        var adjPiece = board.getPiece(move);
	        if (adjPiece == null || adjPiece.getColor() != this.color) {
        		if (!checkTest || checkTest && isSafe(move)) {
		        	moves.add(move);
		        }
        		if(adjPiece != null) {
        			break;
        		}
	        }
	        else {
	        	break;
	        }
	    }

	    for (int i = column + 1, j = row - 1; i <= 7 && j >= 0; i++, j--) {
	    	var move = toPositionString(i, j);
	        var adjPiece = board.getPiece(move);
	        if (adjPiece == null || adjPiece.getColor() != this.color) {
        		if (!checkTest || checkTest && isSafe(move)) {
		        	moves.add(move);
		        }
        		if(adjPiece != null) {
        			break;
        		}
	        }
	        else {
	        	break;
	        }
	    }

	    for (int i = column - 1, j = row - 1; i >= 0 && j >= 0; i--, j--) {
	    	var move = toPositionString(i, j);
	        var adjPiece = board.getPiece(move);
	        if (adjPiece == null || adjPiece.getColor() != this.color) {
        		if (!checkTest || checkTest && isSafe(move)) {
		        	moves.add(move);
		        }
        		if(adjPiece != null) {
        			break;
        		}
	        }
	        else {
	        	break;
	        }
	    }
		return moves;
	}
}
