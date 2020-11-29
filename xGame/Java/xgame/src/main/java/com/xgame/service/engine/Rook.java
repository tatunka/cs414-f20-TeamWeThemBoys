package com.xgame.service.engine;

import java.util.ArrayList;

public class Rook extends ChessPiece {

	public Rook(ChessBoard board, Color color) {
		super(board, color);
	}
	
	public Rook() {
		super();
	}

	@Override
	public String toString() {
		return this.color == Color.BLACK ? "\u265C" : "\u2656";
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
		//all possible moves in the up
		for (int i = row + 1; i <= 7; i++) {
			move = toPositionString(column, i);
	        adjPiece = board.getPiece(move);
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
	    //all possible moves in the down
		for (int i = row - 1; i >= 0; i--) {
			move = toPositionString(column, i);
	        adjPiece = board.getPiece(move);
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
	    //all possible moves to the right
		for (int i = column + 1; i <= 7 ; i++) {
			move = toPositionString(i, row);
	        adjPiece = board.getPiece(move);
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
	    //all possible moves to the left
		for (int i = column - 1; i >= 0 ; i--) {
			move = toPositionString(i, row);
	        adjPiece = board.getPiece(move);
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
