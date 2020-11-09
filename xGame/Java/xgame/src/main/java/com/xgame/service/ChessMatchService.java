package com.xgame.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xgame.common.enums.MatchStatus;
import com.xgame.common.viewmodels.MatchViewModel;
import com.xgame.data.IChessMatchRepository;
import com.xgame.data.IMessageRepository;
import com.xgame.data.IUserRepository;
import com.xgame.data.entities.ChessMatch;
import com.xgame.data.entities.Message;
import com.xgame.service.engine.ChessBoard;
import com.xgame.service.interfaces.IChessMatchService;

@Service
public class ChessMatchService implements IChessMatchService {

	@Autowired
	private IChessMatchRepository matchRepo;
	@Autowired
	private IUserRepository userRepo;
	@Autowired
	private IMessageRepository messageRepo;
	
	@Override
	public MatchViewModel createMatch(int whiteId, int blackId) {
		var whitePlayer = userRepo.findById(whiteId);
		var blackPlayer = userRepo.findById(blackId);
		var match = new ChessMatch("This is a chessboard");
		
		if(whitePlayer.isPresent() && blackPlayer.isPresent() && 
				!whitePlayer.get().getIsDeleted() && 
				!blackPlayer.get().getIsDeleted()) {
			
			match.setWhitePlayer(whitePlayer.get());
			match.setBlackPlayer(blackPlayer.get());
			match.setMatchStatus(MatchStatus.PENDING);
			match.setTurnCount(0);
			
			var newMatch = matchRepo.save(match);
			return new MatchViewModel(newMatch);
		}
		
		throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Can't create match. Can't find all the players!");
	}
	
	/**
	 * Accepts a match invitation. Match status will be set to "INPROGRESS" and board will
	 * be initialized.
	 * @param matchId - id of match to create
	 */
	@Override
	public MatchViewModel acceptInvite(int matchId) {
		var match = matchRepo.findById(matchId);
		match.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No match with that id exists."));
		
		if(match.get().getMatchStatus() != MatchStatus.PENDING) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Match is not pending.");
		}
		
		try {
			var mapper = new ObjectMapper();
			mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			var chessBoard = new ChessBoard();
			chessBoard.initialize();
			var json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(chessBoard.getBoard());
			
			
			var m = match.get();
			m.setTurnCount(1);
			m.setMatchStatus(MatchStatus.INPROGRESS);
			m.setStartTimestamp(new Timestamp(System.currentTimeMillis()));
			m.setChessBoard(json);
			
			var updatedMatch = matchRepo.saveAndFlush(m);
			var whitePlayer = updatedMatch.getWhitePlayer();
			var blackPlayer = updatedMatch.getBlackPlayer();
			var message = new Message(whitePlayer, blackPlayer.getNickname() + " has accepted your invitation to a match!");
			
			messageRepo.save(message);
			
			return new MatchViewModel(updatedMatch);
		}
		catch(JsonProcessingException jpe) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error setting up new board.", jpe);
		}
		catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error accepting match invitation.", e);
		}
	}
	

	/**
	 * Gets a match with the given id.
	 * @param id - id of the match.
	 * @return - updated view model of match with turn count, players, and board
	 */
	@Override
	public MatchViewModel getMatch(int id) {
		var matchState = matchRepo.findById(id);
		matchState.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No match with that id exists."));
		return new MatchViewModel(matchState.get());
	}

	/**
	 * Updates match.
	 * @param matchState - view model with updated match data
	 * @return - updated view model of match with turn count, players, and board
	 */
	@Override
	public MatchViewModel updateMatch(MatchViewModel matchState) {
		var m = matchRepo.findById(matchState.getId());
		m.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No match with that id exists."));
		var match = m.get();
		
		match.setTurnCount(match.getTurnCount() + 1);
		match.setChessBoard(matchState.getChessBoard());
		var updatedMatch = matchRepo.save(match);
		
		return new MatchViewModel(updatedMatch);
	}
	
	/**
	 * Ends match and updates match from view model data
	 * @param matchSate - view model with updated match data
	 * @param winnerId - id of winning player
	 * @return - updated view model of match with turn count, players, and board 
	 */
	public MatchViewModel EndMatch(MatchViewModel matchState, int winnerId) {
		
		var m = matchRepo.findById(matchState.getId());
		m.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No match with that id exists."));
		var match = m.get();
		
		var winner = match.getBlackPlayer().getId() == winnerId ? match.getBlackPlayer() : match.getWhitePlayer();
		match.setTurnCount(match.getTurnCount() + 1);
		match.setChessBoard(matchState.getChessBoard());
		match.setWinningPlayer(winner);
		match.setMatchStatus(MatchStatus.COMPLETED);
		
		var updatedMatch = matchRepo.save(match);
		
		return new MatchViewModel(updatedMatch);
	}
}
