package com.xgame.service.engine;

import java.util.ArrayList;

public class King extends ChessPiece{

	public King(ChessBoard board, Color color) {
		super(board, color);
	}
	
	public King() {
		super();
	}

	@Override
	public String toString() {
		return this.color == Color.BLACK ? "\u265A" : "\u2654";
	}

	@Override
	public ArrayList<String> legalMoves() throws IllegalPositionException {
		//loop through possible moves, only add is opposing piece cannot move there next turn
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

		for (int[] o : offsets) {
			if(column + o[0] >= 0 && column + o[0] <= 7 && row + o[1] >= 0 && row + o[1] <= 7) {
		        move = toPositionString(column + o[0], row + o[1]);
				adjPiece = board.getPiece(move);
				if((adjPiece == null || (adjPiece != null && adjPiece.getColor() != this.color)) && !board.isInCheckGivenMove(getPosition(), move, getColor())){
					moves.add(move);
				}
			}
	    }

		return moves;
	}
	
	@Override
	public ArrayList<String> legalMoves(boolean checkTest) throws IllegalPositionException {
		return legalMoves();
	}
}
