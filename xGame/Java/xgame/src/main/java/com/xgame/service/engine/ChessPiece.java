package com.xgame.service.engine;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "type")
@JsonSubTypes({
	@Type(value = King.class, name = "KING"),
	@Type(value = Queen.class, name = "QUEEN"),
	@Type(value = Knight.class, name = "KNIGHT"),
	@Type(value = Bishop.class, name = "BISHOP"),
	@Type(value = Rook.class, name = "ROOK"),
	@Type(value = Pawn.class, name = "PAWN")
})

public abstract class ChessPiece {
	
	public enum Color {WHITE, BLACK};
	@JsonIgnore
	protected ChessBoard board;
	protected int row;
	protected int column;
	protected Color color;

	//constructors
	public ChessPiece(ChessBoard board, Color color) {
		this.board = board;
		this.color = color;
	}
	
	public ChessPiece() {}
	
	//getters
	public Color getColor() {
		return this.color;
	}
	
	//setters
	public void setBoard(ChessBoard board) {
		this.board = board;
	}

	//methods
	/**
	 * Gets position of string of piece
	 * @return - position string
	 */
	public String getPosition() {
		return "" + String.valueOf((char)(column + 97)) + (row+1);
	}
	
	/**
	 * Sets position of piece
	 * @param position - position string
	 * @throws IllegalPositionException
	 */
	public void setPosition(String position) throws IllegalPositionException{
		if(position.length() != 2) {
			throw new IllegalPositionException(position);
		}
		int inputColumn = ((int)position.charAt(0)) - 97;
		int inputRow;
		try{
			inputRow = Integer.parseInt(position.substring(1)) - 1;
		} 
		catch(NumberFormatException e) {
			throw new IllegalPositionException("Cannot format position");
		}
		
		if ((inputColumn < 0 || inputColumn > 7) || 
			(inputRow < 0 || inputRow > 7)){
			throw new IllegalPositionException(position);
		} 
		else {
			column = inputColumn;
			row = inputRow;
		}
	}
	
	/**
	 * Converts column and row pair to position string
	 * @param col
	 * @param row
	 * @return - position string
	 */
	protected String toPositionString(int col, int row) {
		return "" + String.valueOf((char)(col + 97)) + (row + 1);
	}
	
	/**
	 * Checks that a ChessPiece's move doesn't leave its king in check
	 * @param toPosition - position to check for safety
	 * @return - true if the move is safe, false otherwise
	 * @throws IllegalPositionException
	 */
	protected Boolean isSafe(String toPosition) throws IllegalPositionException {
		return !board.isInCheckGivenMove(getPosition(), toPosition, getColor());
	}
	
	//abstract methods
	/**
	 * Returns the string representation of a piece
	 */
	abstract public String toString();
	
	/**
	 * Returns a list of the piece's legal moves
	 * @return - A list of position strings
	 * @throws IllegalPositionException
	 */
	abstract public ArrayList<String> legalMoves() throws IllegalPositionException;
	
	/**
	 * Returns a list of the piece's legal moves
	 * @param checkTest - Flag indicating whether to check if a move will leave the King in check
	 * @return - A list of position strings
	 * @throws IllegalPositionException
	 */
	abstract public ArrayList<String> legalMoves(boolean checkTest) throws IllegalPositionException;
}
