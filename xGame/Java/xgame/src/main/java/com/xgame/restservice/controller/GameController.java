package com.xgame.restservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xgame.common.viewmodels.MatchViewModel;
import com.xgame.service.engine.IllegalMoveException;
import com.xgame.service.engine.IllegalPositionException;
import com.xgame.service.interfaces.IGameService;

@CrossOrigin
@RestController
public class GameController {

	@Autowired
	private IGameService gameService;
	
	
	@PatchMapping("/game/move")
	public MatchViewModel move(
			@RequestParam(value = "matchId") int matchId, 
			@RequestParam(value = "fromPosition") String fromPosition, 
			@RequestParam(value = "toPosition") String toPosition) {
		
		try {
			return gameService.move(matchId, fromPosition, toPosition);
		}
		catch (JsonProcessingException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error processing board.", e);
		}
		catch (IllegalPositionException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot move piece at illegal position " + fromPosition + ".", e);
		} 
		catch (IllegalMoveException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot make move " + fromPosition + " to " + toPosition + ". Move is illegal.", e);
		}
	}
	
	@GetMapping("/game")
	public String getBoard(@RequestParam(value = "matchId") int matchId) {
		try {
			return gameService.getBoard(matchId);
		} catch (JsonProcessingException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error processing board.", e);
		}
	}
	
	@GetMapping("/game/legalMoves")
	public List<String> getLegalMoves(@RequestParam(value = "matchId") int matchId,
			@RequestParam(value = "piecePosition") String piecePosition) {
		try {
			return gameService.getLegalMoves(matchId, piecePosition);
		} 
		catch (JsonProcessingException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error processing board.", e);
		}
	}
	
	@PatchMapping("/game/promote")
	public MatchViewModel promotePawn(@RequestParam(value = "matchId") int matchId, 
			@RequestParam(value = "position") String position,
			@RequestParam(value = "piece") String piece) {
		return gameService.promotePawn(matchId, position, piece);
	}
}
