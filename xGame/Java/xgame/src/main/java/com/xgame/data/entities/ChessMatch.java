package com.xgame.data.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.xgame.common.enums.MatchOutcome;
import com.xgame.common.enums.MatchStatus;

@Entity
public class ChessMatch {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(nullable=false, columnDefinition="INT DEFAULT 0")
	private Integer turnCount;
	@Lob
	@Column(nullable=false)
	private String chessBoard;
	@Column(nullable=false, columnDefinition="BIT DEFAULT 0")
	private Boolean IsDrawSuggestedByWhite = false;
	@Column(nullable=false, columnDefinition="BIT DEFAULT 0")
	private Boolean IsDrawSuggestedByBlack = false;
	@CreationTimestamp
	private Timestamp creationTimestamp;
	private Timestamp startTimestamp;
	@UpdateTimestamp
	private Timestamp lastTurnTimestamp;
	
	//navigation props
	@ManyToOne(optional=false)
	@JoinColumn(name = "whitePlayerUserId", referencedColumnName = "id")
	private User whitePlayer;
	@ManyToOne(optional=false)
	@JoinColumn(name = "blackPlayerUserId", referencedColumnName = "id")
	private User blackPlayer;
	@ManyToOne(optional=true)
	@JoinColumn(name = "winningPlayerUserId", referencedColumnName = "id", nullable=true)
	private User winningPlayer;
	@Enumerated(EnumType.STRING)
	private MatchStatus matchStatus;
	@Enumerated(EnumType.STRING)
	private MatchOutcome matchOutcome;
	
	//constructors
	protected ChessMatch() {}
	
	public ChessMatch(String chessBoard) {
		this.chessBoard = chessBoard;
	}
	
	//getters and setters
	public Integer getId() {
		return id;
	}
	public Integer getTurnCount() {
		return turnCount;
	}
	public void setTurnCount(Integer turnCount) {
		this.turnCount = turnCount;
	}
	public String getChessBoard() {
		return chessBoard;
	}
	public void setChessBoard(String chessBoard) {
		this.chessBoard = chessBoard;
	}
	public Boolean getIsDrawSuggestedByWhite() {
		return IsDrawSuggestedByWhite;
	}
	public void setIsDrawSuggestedByWhite(Boolean isDrawSuggestedByWhite) {
		IsDrawSuggestedByWhite = isDrawSuggestedByWhite;
	}
	public Boolean getIsDrawSuggestedByBlack() {
		return IsDrawSuggestedByBlack;
	}
	public void setIsDrawSuggestedByBlack(Boolean isDrawSuggestedByBlack) {
		IsDrawSuggestedByBlack = isDrawSuggestedByBlack;
	}
	public Timestamp getCreationTimestamp() {
		return creationTimestamp;
	}
	public Timestamp getStartTimestamp() {
		return startTimestamp;
	}
	public void setStartTimestamp(Timestamp startTimestamp) {
		this.startTimestamp = startTimestamp;
	}
	public Timestamp getLastTurnTimestamp() {
		return lastTurnTimestamp;
	}
	public User getWhitePlayer() {
		return this.whitePlayer;
	}
	public void setWhitePlayer(User whitePlayer) {
		this.whitePlayer = whitePlayer;
	}
	public User getBlackPlayer() {
		return this.blackPlayer;
	}
	public void setBlackPlayer(User blackPlayer) {
		this.blackPlayer = blackPlayer;
	}
	public User getWinningPlayer() {
		return this.winningPlayer;
	}
	public void setWinningPlayer(User winningPlayer) {
		this.winningPlayer = winningPlayer;
	}
	public MatchStatus getMatchStatus() {
		return matchStatus;
	}
	public void setMatchStatus(MatchStatus matchStatus) {
		this.matchStatus = matchStatus;
	}
	public MatchOutcome getMatchOutcome() {
		return matchOutcome;
	}
	public void setMatchOutcome(MatchOutcome matchOutcome) {
		this.matchOutcome = matchOutcome;
	}
}
