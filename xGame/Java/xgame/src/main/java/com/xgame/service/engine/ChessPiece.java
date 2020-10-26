package com.xgame.service.engine;

import java.util.ArrayList;

public abstract class ChessPiece {
	
	public enum Color {WHITE, BLACK};
	
	protected ChessBoard board;

	protected int row;

	protected int column;

	protected Color color;
	
	public ChessPiece(ChessBoard board, Color color) {
		this.board = board;
		this.color = color;
	}
	
	public Color getColor() {
		return this.color;
	}

	public String getPosition() {
		return "" + String.valueOf((char)(column + 97)) + (row+1);
	}
	
	public void setPosition(String position) throws IllegalPositionException{
		if(position.length() != 2) {
			throw new IllegalPositionException(position);
		}
		int inputColumn = ((int)position.charAt(0)) - 97;
		int inputRow;
		try{
			inputRow = Integer.parseInt(position.substring(1)) - 1;
		} catch(NumberFormatException e) {
			throw new IllegalPositionException("Cannot format position");
		}
		
		if ((inputColumn < 0 || inputColumn > 7) || 
			(inputRow < 0 || inputRow > 7)){
			throw new IllegalPositionException(position);
		} else {
			column = inputColumn;
			row = inputRow;
		}
	}
	
	protected String toPosition(int col, int row) {
		return "" + String.valueOf((char)(col + 97)) + (row + 1);
	}
	
	abstract public String toString();
	
	abstract public ArrayList<String> legalMoves() throws IllegalPositionException;
	
}
