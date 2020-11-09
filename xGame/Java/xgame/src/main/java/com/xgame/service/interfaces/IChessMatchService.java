package com.xgame.service.interfaces;

import com.xgame.common.viewmodels.MatchViewModel;

public interface IChessMatchService {
	MatchViewModel createMatch(int whiteId, int blackId);
	MatchViewModel acceptInvite(int matchId);
	MatchViewModel getMatch(int id);
	MatchViewModel updateMatch(MatchViewModel matchState);
	MatchViewModel EndMatch(MatchViewModel matchState, int winnerId);
}