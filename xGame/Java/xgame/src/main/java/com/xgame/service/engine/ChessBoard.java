package com.xgame.service.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.xgame.service.engine.ChessPiece.Color;

public class ChessBoard {
	private ChessPiece[][] board;

	public ChessBoard(){
		this.board = new ChessPiece[8][8]; 
	}
	
	public ChessPiece[][] getBoard() {
		return this.board;
	}

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
	
	public void resume(ChessPiece[][] pieces) {
		this.board = pieces;
		
		for(var p1 : pieces) {
			for(var piece : p1) {
				if(piece != null) {
					piece.setBoard(this);
				}
			}
		}
	}
	
	public ChessPiece getPiece(String position) throws IllegalPositionException{
		if(position.length() != 2) {
			throw new IllegalPositionException(position);
		}
		int inputColumn = ((int)position.charAt(0)) - 97;
		int inputRow;
		try {
			inputRow = Integer.parseInt(position.substring(1)) - 1;
		} catch (NumberFormatException e) {
			throw new IllegalPositionException("Cannot Parse " + position);
		}
		if ((inputColumn < 0 || inputColumn > 7) || 
			(inputRow < 0 || inputRow > 7)){
			throw new IllegalPositionException(position);
		} else {
			return board[inputRow][inputColumn];
		}
	}
	
	public boolean placePiece(ChessPiece piece, String position) {
		if(position.length() != 2 || piece == null) {
			return false;
		}
		try {
			ChessPiece old_piece = getPiece(position);
			if(old_piece == null || (old_piece != null && old_piece.getColor() != piece.getColor()) ) {
				board[Integer.parseInt(position.substring(1)) - 1][(int)position.charAt(0) - 97] = piece;
				piece.setPosition(position);
			}
			return true;
		} catch(IllegalPositionException e) {
			return false;
		} 
	}
	
	public void move(String fromPosition, String toPosition) throws IllegalMoveException, IllegalPositionException{
		try {
			ChessPiece toMove = getPiece(fromPosition);
			if (toMove == null) {
				throw new IllegalMoveException("There is no piece there");
			}
			ArrayList<String> legalMoves = toMove.legalMoves();
			if (legalMoves.isEmpty() || !legalMoves.contains(toPosition)){
				throw new IllegalMoveException("This piece cannot move there");
			} else {
				if(getPiece(toPosition) != null) {
					int toRow = Integer.parseInt(toPosition.substring(1)) - 1;
					int toColumn = (int)toPosition.charAt(0) - 97;
					board[toRow][toColumn] = null;
				}
				
				int fromRow = Integer.parseInt(fromPosition.substring(1)) - 1;
				int fromColumn = (int)fromPosition.charAt(0) - 97;
				placePiece(toMove, toPosition);
				board[fromRow][fromColumn] = null;
			}
		} catch( IllegalPositionException e) {
			throw(e);
		}
		
	}
	
	public boolean promotePawn(Pawn pawnToPromote, String promoteTo) {
		String[] wRank8 = {"a5", "a6", "a7", "a8", "b8", "c8", "d8"};
		String[] bRank8 = {"h4", "h3", "h2", "h1", "g1", "f1", "e1"};
		
		List<String> wRank8List = Arrays.asList(wRank8);
		List<String> bRank8List = Arrays.asList(bRank8);
		
		String position = pawnToPromote.getPosition();
		if((pawnToPromote.getColor() == Color.WHITE && wRank8List.contains(position)) ||
		   (pawnToPromote.getColor() == Color.BLACK && bRank8List.contains(position))) {
			int row = Integer.parseInt(position.substring(1)) - 1;
			int col = (int)position.charAt(0) - 97;
			
			board[row][col] = null;
			switch(promoteTo) {
				case "bishop":
					board[row][col] = new Bishop(this, pawnToPromote.getColor());
					return true;
				case "rook":
					board[row][col] = new Rook(this, pawnToPromote.getColor());
					return true;
				case "knight":
					board[row][col] = new Knight(this, pawnToPromote.getColor());
					return true;
				case "queen":
					board[row][col] = new Queen(this, pawnToPromote.getColor());
					return true;
				default: 
					return false;
			}
		} 
		return false;
	}
	

	public boolean isThreatened(String position, Color threatColor) {
		try {
			for(char c = 'a'; c <= 'h'; c++) {
				for(char i = '1'; i <= '8'; i++) {
					String location = ""+c+i;
					if(getPiece(location) != null && getPiece(location).getColor() == threatColor) {
						ArrayList<String> moves = getPiece(location).legalMoves();
						if(moves.contains(position)) {
							
							return true;
						}
					}
				}
			}
		}catch(IllegalPositionException e) {
			System.out.println(e);
		}
		return false;
	}
	
	public boolean isInCheck(Color playerColor) {
		Color threatColor = (playerColor == Color.WHITE) ? Color.BLACK : Color.WHITE;
		String kingName = (playerColor == Color.BLACK) ? "\u265A" : "\u2654";
		for(char c = 'a'; c <= 'h'; c++) {
			for(char i = '1'; i <= '8'; i++) {
				String location = ""+c+i;
				try {
					if(getPiece(location) != null && getPiece(location).toString() == kingName) {
						return(isThreatened(location, threatColor));
					}
				}catch(IllegalPositionException e) {
					System.out.println(e);
				}
				
			}
		}
		return false;

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
}
