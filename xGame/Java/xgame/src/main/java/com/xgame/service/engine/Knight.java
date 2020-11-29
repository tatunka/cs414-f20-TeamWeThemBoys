package com.xgame.service.engine;

import java.util.ArrayList;

public class Knight extends ChessPiece {
	
	public Knight(ChessBoard board, Color color) {
		super(board, color);
	}
	
	public Knight() {
		super();
	};

	@Override
	public String toString() {
		return this.color == Color.BLACK ? "\u265E" : "\u2658";
	}

	@Override
	public ArrayList<String> legalMoves() throws IllegalPositionException {
		return legalMoves(true);
	}
	
	@Override
	public ArrayList<String> legalMoves(boolean checkTest) throws IllegalPositionException {
		ArrayList<String> moves = new ArrayList<String>();
		int[][] offsets = {
		        {-2, 1},
		        {-2, -1},
		        {1, 2},
		        {1, -2},
		        {2, 1},
		        {2, -1},
		        {-1, -2},
		        {-1, 2}
		    };
		for (int[] o : offsets) {
			if(column + o[0] >= 0 && column + o[0] <= 7 && row + o[1] >= 0 && row + o[1] <= 7) {
		        var move = toPositionString(column + o[0], row + o[1]);
				var adjPiece = board.getPiece(move);
				
				if((adjPiece == null || adjPiece.getColor() != this.color) && (!checkTest || (checkTest && isSafe(move)))) {
						moves.add(move);
				}
			}
		}

		return moves;
	}
}
