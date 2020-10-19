package engine;

import java.util.ArrayList;

public class Pawn extends ChessPiece{

	public Pawn(ChessBoard board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return this.color == Color.BLACK ? "\u265F" : "\u2659";
	}

	@Override
	public ArrayList<String> legalMoves() {
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
			e.printStackTrace();
		}
		return moves;
	}

}
