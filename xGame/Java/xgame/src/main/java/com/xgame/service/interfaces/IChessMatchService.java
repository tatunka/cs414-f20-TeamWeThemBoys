package com.xgame.service.interfaces;

import com.xgame.common.enums.MatchOutcome;
import com.xgame.common.viewmodels.MatchViewModel;

public interface IChessMatchService {
	MatchViewModel createMatch(int whiteId, int blackId);
	MatchViewModel acceptInvite(int matchId);
	void rejectInvite(int matchId, int playerId);
	MatchViewModel getMatch(int id);
	MatchViewModel updateMatch(MatchViewModel matchState);
	MatchViewModel EndMatch(MatchViewModel matchState, int winnerId);
	MatchOutcome suggestDraw(int matchId, int playerId);
	void denyDraw(int matchId, int playerId);
}