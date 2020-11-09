package com.xgame.service.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.xgame.common.viewmodels.MatchViewModel;
import com.xgame.service.engine.IllegalMoveException;
import com.xgame.service.engine.IllegalPositionException;

public interface IGameService {
	MatchViewModel move(int matchId, String fromPosition, String toPosition) throws JsonMappingException, JsonProcessingException, IllegalMoveException, IllegalPositionException;
	String getBoard(int matchId) throws JsonMappingException, JsonProcessingException;
}
