package com.xgame.common.viewmodels;

import java.sql.Timestamp;

import com.xgame.common.enums.MatchOutcome;
import com.xgame.data.entities.ChessMatch;
import com.xgame.data.entities.User;
import com.xgame.service.engine.ChessPiece.Color;

public class MatchHistoryViewModel {
	
	private int id;
	private Timestamp startTime;
	private Timestamp endTime;
	private String opponentNickname;
	private Color color;
	private MatchOutcome outcome;
	private Integer moveCount;
	private String winningPlayer = null;
	
	public MatchHistoryViewModel(ChessMatch match, User user) {
		MatchViewModel matchView = new MatchViewModel(match);
		this.id = match.getId();
		this.startTime = match.getStartTimestamp();
		this.endTime = match.getLastTurnTimestamp();
		this.color = matchView.getBlackPlayerId() == user.getId() ? Color.BLACK : Color.WHITE;
		this.opponentNickname = this.color == Color.BLACK ? matchView.getWhitePlayerNickname() : matchView.getBlackPlayerNickname();
		this.outcome = match.getMatchOutcome();
		if(this.outcome != MatchOutcome.DRAW) this.winningPlayer = match.getWinningPlayer().getNickname();
		this.moveCount = match.getTurnCount();
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public String getOpponentNickname() {
		return opponentNickname;
	}

	public Color getColor() {
		return color;
	}

	public MatchOutcome getOutcome() {
		return outcome;
	}

	public Integer getMoveCount() {
		return moveCount;
	}

	public String getWinningPlayer() {
		return winningPlayer;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
