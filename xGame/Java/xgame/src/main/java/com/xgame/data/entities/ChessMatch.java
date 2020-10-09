package com.xgame.data.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class ChessMatch {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column( nullable=false, columnDefinition="INT DEFAULT 0")
	private Integer turnCount;
	@Column(nullable=false)
	private String chessBoard;
	@Column(columnDefinition="DATETIME DEFAULT NOW()", nullable=false)
	private Date createDateTime;
	private Date startDateTime;
	private Date endDateTime;
	
	//navigation props
	@ManyToOne(optional=false)
	@JoinColumn(name = "whitePlayerUserId", referencedColumnName = "id")
	private User whitePlayer;
	@ManyToOne(optional=false)
	@JoinColumn(name = "blackPlayerUserId", referencedColumnName = "id")
	private User blackPlayer;
	@ManyToOne(optional=false)
	@JoinColumn(name = "winningPlayerUserId", referencedColumnName = "id")
	private User winningPlayer;
	@OneToOne
	@JoinColumn(name = "matchStatusId", referencedColumnName = "id", nullable=false)
	private MatchStatus matchStatus;
	
	//constructors
	protected ChessMatch() {}
	
	public ChessMatch(String chessBoard) {
		this.chessBoard = chessBoard;
	}
	
	//getters and setters
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Date getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}
	public Date getStartDateTime() {
		return startDateTime;
	}
	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}
	public Date getEndDateTime() {
		return endDateTime;
	}
	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}
	public User getWhitePlayer() {
		return this.whitePlayer;
	}
	public void setWhitePlayer(User whitePlayer) {
		this.whitePlayer = whitePlayer;
	}
	public User getBlackPlayer() {
		return this.whitePlayer;
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
}
