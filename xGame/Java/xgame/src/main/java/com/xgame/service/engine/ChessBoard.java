package com.xgame.service.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.xgame.common.enums.MatchOutcome;

import com.xgame.service.engine.ChessPiece.Color;

public class ChessBoard {
	private ChessPiece[][] board;
	private MatchOutcome status = null;
	private Color winningColor = null;

	public ChessBoard(){
		this.board = new ChessPiece[8][8]; 
	}

	public ChessPiece[][] getBoard() {
		return this.board;
	}
	
	public MatchOutcome getOutcome() {
		return status;
	}
	
	public Color getWinningColor() {
		return winningColor;
	}

	/**
	 * Starts a match by placing all the pieces in their designated starting
	 * positions.
	 */
	public void initialize() {
		//Instantiate game pieces, put on board
		placePiece(new King(this, Color.WHITE), "h1");
		placePiece(new King(this, Color.BLACK), "a8");
		
		placePiece(new Queen(this, Color.WHITE), "g2");
		placePiece(new Queen(this, Color.BLACK), "b7");
		
		placePiece(new Bishop(this, Color.WHITE), "h2");
		placePiece(new Bishop(this, Color.WHITE), "f1");
		placePiece(new Bishop(this, Color.BLACK), "c8");
		placePiece(new Bishop(this, Color.BLACK), "a7");
		
		placePiece(new Knight(this, Color.WHITE), "h3");
		placePiece(new Knight(this, Color.WHITE), "g1");
		placePiece(new Knight(this, Color.BLACK), "b8");
		placePiece(new Knight(this, Color.BLACK), "a6");
		
		placePiece(new Rook(this, Color.WHITE), "e1");
		placePiece(new Rook(this, Color.WHITE), "h4");
		placePiece(new Rook(this, Color.BLACK), "a5");
		placePiece(new Rook(this, Color.BLACK), "d8");
		
		placePiece(new Pawn(this, Color.WHITE), "e4");
		placePiece(new Pawn(this, Color.WHITE), "f2");
		placePiece(new Pawn(this, Color.WHITE), "g3");
		
		placePiece(new Pawn(this, Color.BLACK), "b6");
		placePiece(new Pawn(this, Color.BLACK), "c7");
		placePiece(new Pawn(this, Color.BLACK), "d5");
		
		for(char c = 'a',  i = '4'; c <= 'e' && i <= '8'; c++, i++) {
			placePiece(new Pawn(this, Color.BLACK), ""+c+i);
		}
		placePiece(new Pawn(this, Color.WHITE), "f3");
		
		for(char c = 'd',  i = '1'; c <= 'h' && i <= '5'; c++, i++) {
			placePiece(new Pawn(this, Color.WHITE), ""+c+i);
		}
		
	}
	
	/**
	 * Resumes a match by taking an existing list of pieces and 
	 * assigning it to the ChessBoard.
	 * @param pieces
	 */
	public void resume(ChessPiece[][] pieces) {
		this.board = pieces;

		for(var p1 : pieces) {
			for(var piece : p1) {
				if(piece != null) {
					piece.setBoard(this);
				}
			}
		}
		
		setGameStatus();
	}

	//public methods
	
	/**
	 * Get a piece at the given position of the board.
	 * @param position - String position
	 * @return - ChessPiece at position
	 * @throws IllegalPositionException
	 */
	public ChessPiece getPiece(String position) throws IllegalPositionException{
		var p = position.toCharArray();
		if(p.length == 2 && p[0] >= 97 && p[0] <= 104 && p[1] >= 49 && p[1] <= 56) {
			return board[p[1] - 49][p[0] - 97];
		}
		
		throw new IllegalPositionException(position);
	}
	
	/**
	 * Places a piece at a new position on the board
	 * @param piece - Piece to move
	 * @param position - new position
	 * @return - whether the placement succeeded or not
	 */
	public boolean placePiece(ChessPiece piece, String position) {
		if(piece == null) { return false; }
		try {
			var pieceAtPosition = getPiece(position);
			if(pieceAtPosition == null || pieceAtPosition.getColor() != piece.getColor()) {
				board[Integer.parseInt(position.substring(1)) - 1][(int)position.charAt(0) - 97] = piece;
				piece.setPosition(position);
			}
			return true;
		} catch(IllegalPositionException e) {
			return false;
		} 
	}
	
	/**
	 * Moves a ChessPiece from on position to another, if the move is valid.
	 * @param fromPosition - starting position of ChessPiece
	 * @param toPosition - ending position of ChessPiece
	 * @throws IllegalMoveException
	 * @throws IllegalPositionException
	 */
	public void move(String fromPosition, String toPosition) throws IllegalMoveException, IllegalPositionException{
		var piece = getPiece(fromPosition);
		if(piece == null) { throw new IllegalMoveException("No piece at that position"); };
		var legalMoves = piece.legalMoves();
		if(legalMoves.contains(toPosition)) {
			var success = placePiece(piece, toPosition);
			
			if(success) {
				var oldPosition = fromPosition.toCharArray();
				board[oldPosition[1] - 49][oldPosition[0] - 97] = null;
				setGameStatus();
			}
		}
		else {
			throw new IllegalMoveException("Illegal Move");
		}
	}
	
	/**
	 * Checks if a certain move will lead to that color being in check
	 * @param fromLocation
	 * @param toLocation
	 * @param pieceColor
	 * @return
	 * @throws IllegalPositionException
	 */
	public boolean isInCheckGivenMove(String fromLocation, String toLocation, Color pieceColor) throws IllegalPositionException {
		ChessPiece movingPiece = getPiece(fromLocation);
		ChessPiece destinationPiece = getPiece(toLocation);
		boolean isSafe = false;
		
		//move the piece
		board[Integer.parseInt(fromLocation.substring(1)) - 1][(int)fromLocation.charAt(0) - 97] = null;
		placeNull(fromLocation);
		placePiece(movingPiece, toLocation);

		//check if the piece color is in check
		isSafe = isInCheck(pieceColor);
		
		//revert the move
		if(destinationPiece == null) {
			placeNull(toLocation);
		}else {
			placePiece(destinationPiece, toLocation);
		}
		placePiece(movingPiece, fromLocation);
		return isSafe;
	}
	
	/**
	 * Promotes a Pawn that has reached the end of the board
	 * @param pawn - the Pawn to promote
	 * @param promotionPiece - class of the new 
	 * @return
	 * @throws IllegalPositionException 
	 */
	public boolean promotePawn(String pawnPosition, Class<?> promotionPiece) throws IllegalPositionException {
		String[] wRank8 = {"a5", "a6", "a7", "a8", "b8", "c8", "d8"};
		String[] bRank8 = {"h4", "h3", "h2", "h1", "g1", "f1", "e1"};
		List<String> wRank8List = Arrays.asList(wRank8);
		List<String> bRank8List = Arrays.asList(bRank8);
		var pawn = getPiece(pawnPosition);
		var color = pawn.getColor();
		
		String position = pawn.getPosition();
		if(pawn instanceof Pawn &&
				(color == Color.WHITE && wRank8List.contains(position) ||
				color == Color.BLACK && bRank8List.contains(position))) {
			
			if(ChessPiece.class.isAssignableFrom(promotionPiece) && 
					promotionPiece != King.class && promotionPiece != Pawn.class) {
				try {
					this.placeNull(position);
					var piece = (ChessPiece) promotionPiece
							.getDeclaredConstructor(ChessBoard.class, Color.class)
							.newInstance(this, pawn.getColor());
					this.placePiece(piece, position);
					return true;
				}
				catch (Exception e) {
					return false;
				}
			}
		} 
		return false;
	}	

	/**
	 * Prints current board as a string.
	 */
	public String toString() {
		String chess="";
		String upperLeft = "\u250C";
		String upperRight = "\u2510";
		String horizontalLine = "\u2500";
		String horizontal3 = horizontalLine + "\u3000" + horizontalLine;
		String verticalLine = "\u2502";
		String upperT = "\u252C";
		String bottomLeft = "\u2514";
		String bottomRight = "\u2518";
		String bottomT = "\u2534";
		String plus = "\u253C";
		String leftT = "\u251C";
		String rightT = "\u2524";
		String topLine = upperLeft;

		for (int i = 0; i<7; i++) {
			topLine += horizontal3 + upperT;
		}

		topLine += horizontal3 + upperRight;
		String bottomLine = bottomLeft;

		for (int i = 0; i<7; i++) {
			bottomLine += horizontal3 + bottomT;
		}

		bottomLine += horizontal3 + bottomRight;
		chess+=topLine + "\n";

		for (int row = 7; row >=0; row--) {
			String midLine = "";

			for (int col = 0; col < 8; col++) {
				if(board[row][col]==null) {
					midLine += verticalLine + " \u3000 ";

				}
				else {
					midLine += verticalLine + " "+board[row][col]+" ";
				}
			}

			midLine += verticalLine;
			String midLine2 = leftT;

			for (int i = 0; i<7; i++) {
				midLine2 += horizontal3 + plus;
			}

			midLine2 += horizontal3 + rightT;
			chess+=midLine+ "\n";

			if(row>=1) chess += midLine2 + "\n";
		}
		chess+=bottomLine;
		return chess;
	}
	
	//private methods
	/**
	 * Removes the ChessPiece at the indicated position
	 * @param position - Position of ChessPiece to remove
	 * @throws IllegalPositionException
	 */
	public void placeNull(String position) throws IllegalPositionException {
		if(position.length() != 2 && position.charAt(0) >= 'a' && position.charAt(0) <= 'h' && position.charAt(1) >= '1' && position.charAt(1) <= '8') {
			throw new IllegalPositionException("");
		}
		
		board[Integer.parseInt(position.substring(1)) - 1][(int)position.charAt(0) - 97] = null;
	}

	/**
	 * Checks if a given square is threatened by the other colors pieces 
	 * @param position - Position to check
	 * @param threatColor - Color to check for threats
	 * @return
	 * @throws IllegalPositionException
	 */
	public boolean isThreatened(String position, Color threatColor) throws IllegalPositionException{
		for(char c = 'a'; c <= 'h'; c++) {
			for(char i = '1'; i <= '8'; i++) {
				String location = ""+c+i;
				if(getPiece(location) != null && getPiece(location).getColor() == threatColor 
						&& getPiece(location).getClass() != King.class) {
					ArrayList<String> moves = getPiece(location).legalMoves(false);
					if(getPiece(location).getClass() == Pawn.class) {
						moves = ((Pawn)getPiece(location)).findThreateningPawnMoves(c, i);
					}
					if(moves.contains(position)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Checks if the King of a color's square is threatened
	 * @param playerColor - Color to consider for check
	 * @return
	 * @throws IllegalPositionException
	 */
	public boolean isInCheck(Color playerColor) throws IllegalPositionException{
		Color threatColor = (playerColor == Color.WHITE) ? Color.BLACK : Color.WHITE;
		String kingName = (playerColor == Color.WHITE) ? "\u2654" : "\u265A";
		for(char c = 'a'; c <= 'h'; c++) {
			for(char i = '1'; i <= '8'; i++) {
				String location = ""+c+i;
				if(getPiece(location) != null && getPiece(location).toString() == kingName) {
					return(isThreatened(location, threatColor));
				}				
			}
		}
		return false;
	}
	
	/**
	 * Checks if the color is in checkmate
	 * @param playerColor - Color to check for checkmate
	 * @return
	 */
	public boolean isInCheckMate(Color playerColor) {
		try {
			if(isInCheck(playerColor)) {
				for(char c = 'a'; c <= 'h'; c++) {
					for(char i = '1'; i <= '8'; i++) {
						String location = ""+c+i;
						if(getPiece(location) != null && getPiece(location).getColor() == playerColor) {
							if(getPiece(location).legalMoves().size() > 0) {
								//if any piece has legal moves, then there is no checkmate
								return false;
							}
						}				
					}
				}
				return true;
			}
		}
		catch (IllegalPositionException e) {
			return false;
		}
		return false;
	}
	
	/**
	 * Sets the game status based on the state of the board.
	 */
	public void setGameStatus() {
		if(isInCheckMate(Color.BLACK)) {
			winningColor = Color.BLACK;
			status = MatchOutcome.VICTORY;
		}
		if(isInCheckMate(Color.WHITE)) {
			winningColor = Color.WHITE;
			status = MatchOutcome.VICTORY;
		}
	}

}
