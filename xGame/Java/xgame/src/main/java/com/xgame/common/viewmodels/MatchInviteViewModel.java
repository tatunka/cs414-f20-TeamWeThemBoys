package com.xgame.common.viewmodels;

import java.sql.Timestamp;

public class MatchInviteViewModel {

	private int matchId;
	private String whitePlayerNickname;
	private Timestamp matchCreatedTimestamp;
	
	//constructor
	public MatchInviteViewModel(int matchId, String nickname, Timestamp matchCreatedTimestamp) {
		this.matchId = matchId;
		this.whitePlayerNickname = nickname;
		this.matchCreatedTimestamp = matchCreatedTimestamp;
	}

	//getters
	public int getMatchId() {
		return matchId;
	}
	public String getWhitePlayerNickname() {
		return whitePlayerNickname;
	}
	public Timestamp getMatchCreatedTimestamp() {
		return matchCreatedTimestamp;
	}
}
