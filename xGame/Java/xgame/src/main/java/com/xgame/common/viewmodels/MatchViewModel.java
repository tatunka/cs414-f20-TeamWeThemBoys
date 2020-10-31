package com.xgame.common.viewmodels;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.xgame.data.entities.ChessMatch;

public class MatchViewModel {
	private int id;
	private int whiteId;
	private int blackId;
	private String whiteEmail;
	private String blackEmail;
	private int turnCount;
	@JsonRawValue
	private String chessBoard;
	
	public MatchViewModel(ChessMatch match) {
		var whitePlayer = match.getWhitePlayer();
		var blackPlayer = match.getBlackPlayer();
		
		if(whitePlayer != null && blackPlayer != null) {
			this.id = match.getId();
			this.whiteId = whitePlayer.getId();
			this.blackId = blackPlayer.getId();
			this.whiteEmail = whitePlayer.getEmail();
			this.blackEmail = blackPlayer.getEmail();
			this.turnCount = match.getTurnCount();
			this.chessBoard = match.getChessBoard();
		}
	}
	
	//getters and setters
	public int getId() {
		return id;
	}
	public void setId(int matchId) {
		this.id = matchId;
	}
	public int getWhiteId() {
		return whiteId;
	}
	public void setWhiteId(int whiteId) {
		this.whiteId = whiteId;
	}
	public int getBlackId() {
		return blackId;
	}
	public void setBlackId(int blackId) {
		this.blackId = blackId;
	}
	public String getWhiteEmail() {
		return whiteEmail;
	}
	public void setWhiteEmail(String whiteEmail) {
		this.whiteEmail = whiteEmail;
	}
	public String getBlackEmail() {
		return blackEmail;
	}
	public void setBlackEmail(String blackEmail) {
		this.blackEmail = blackEmail;
	}
	public int getTurnCount() {
		return turnCount;
	}
	public void setTurnCount(int turnCount) {
		this.turnCount = turnCount;
	}
	public String getChessBoard() {
		return chessBoard;
	}
	public void setChessBoard(String chessBoard) {
		this.chessBoard = chessBoard;
	}
}
