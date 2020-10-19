package engine;

import java.util.ArrayList;

public class Knight extends ChessPiece{
	
	public Knight(ChessBoard board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return this.color == Color.BLACK ? "\u265E" : "\u2658";
	}

	@Override
	public ArrayList<String> legalMoves() {
		ArrayList<String> moves = new ArrayList<String>();
		ChessPiece adjPiece;
		String move;
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
		try {
			for (int[] o : offsets) {
				if(column + o[0] >= 0 && column + o[0] <= 7 && row + o[1] >= 0 && row + o[1] <= 7) {
			        move = toPosition(column + o[0], row + o[1]);
					adjPiece = board.getPiece(move);
					if(adjPiece == null || (adjPiece != null && adjPiece.getColor() != this.color)) {
						moves.add(move);
					}
				}
		    }
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}

		return moves;
	}
}
