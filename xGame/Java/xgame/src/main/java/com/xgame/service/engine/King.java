package com.xgame.service.engine;

import java.util.ArrayList;

public class King extends ChessPiece{

	public King(ChessBoard board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return this.color == Color.BLACK ? "\u265A" : "\u2654";
	}

	@Override
	public ArrayList<String> legalMoves() throws IllegalPositionException {
		//loop through possible moves, only add is opposing ppiece cannot move there next turn
		ArrayList<String> moves = new ArrayList<String>();
		ChessPiece adjPiece;
		String move;
		int[][] offsets = {
	        {1, 0},
	        {0, 1},
	        {-1, 0},
	        {0, -1},
	        {1, 1},
	        {-1, 1},
	        {-1, -1},
	        {1, -1}
	    };
		try {
			for (int[] o : offsets) {
				if(column + o[0] >= 0 && column + o[0] <= 7 && row + o[1] >= 0 && row + o[1] <= 7) {
			        move = toPosition(column + o[0], row + o[1]);
					adjPiece = board.getPiece(move);
					if(adjPiece == null || (adjPiece != null && adjPiece.getColor() != this.color)){
						moves.add(move);
					}
				}
		    }
		} catch (IllegalPositionException e) {
			throw(e);
		}

		return moves;
		
	}

}
