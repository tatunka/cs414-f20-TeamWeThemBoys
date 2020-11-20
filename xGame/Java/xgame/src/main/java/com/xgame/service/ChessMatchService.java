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
import com.xgame.common.enums.MatchOutcome;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.xgame.common.enums.MatchStatus;
import com.xgame.common.viewmodels.MatchViewModel;
import com.xgame.data.IChessMatchRepository;
import com.xgame.data.IMessageRepository;
import com.xgame.data.IUserRepository;
import com.xgame.data.entities.ChessMatch;
import com.xgame.data.entities.Message;
import com.xgame.service.engine.ChessBoard;
import com.xgame.service.interfaces.IChessMatchService;

@CrossOrigin
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
			match.setIsDrawSuggestedByWhite(false);
			match.setIsDrawSuggestedByBlack(false);
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
	
	/**
	 * Suggests a draw for a given player. If both players have suggested a draw, match ends in a draw.
	 * @param matchId - Id of match where a draw is being suggested
	 * @param playerId - player suggesting the draw
	 * @return Outcome of match, if any
	 */
	public MatchOutcome suggestDraw(int matchId, int playerId) {
		var m = matchRepo.findById(matchId);
		m.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No match with that ID exists."));
		var u = userRepo.findById(playerId);
		u.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user with that ID exists."));
		
		var match = m.get();
		var player = u.get();
		var opponentId = playerId == match.getWhitePlayer().getId() ? 
				match.getBlackPlayer().getId() : 
				match.getWhitePlayer().getId();
		var opponent = userRepo.findById(opponentId);
		
		//update match with draw suggestion
		if(match.getWhitePlayer().getId() == playerId) {
			match.setIsDrawSuggestedByWhite(true);
		}
		else if(match.getBlackPlayer().getId() == playerId) {
			match.setIsDrawSuggestedByBlack(true);
		}
		else {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST, "Player with ID " + playerId + " is not participating in match " + matchId + ".");
		}
		
		//end match in draw if both players have suggested it
		var whiteDraw = match.getIsDrawSuggestedByWhite();
		var blackDraw = match.getIsDrawSuggestedByBlack();
		if(whiteDraw != null && whiteDraw && blackDraw != null && blackDraw) {
			match.setMatchStatus(MatchStatus.COMPLETED);
			match.setMatchOutcome(MatchOutcome.DRAW);
			
			messageRepo.save(new Message(player, "You have accepted the draw. Match " + matchId + " is over!"));
			messageRepo.save(new Message(opponent.get(), player.getNickname() + " has agreed to the draw. Match " + matchId + " is over!"));
			
			var updatedMatch = matchRepo.save(match);
			return updatedMatch.getMatchOutcome();
		}
		
		//send message to opponent
		if(match.getWhitePlayer().getId() == playerId) {		
			var message = new Message(opponent.get(), player.getNickname() + " has suggested a draw in match " + matchId + "!");
			messageRepo.save(message);
		}
		else if(match.getBlackPlayer().getId() == playerId) {
			var message = new Message(opponent.get(), player.getNickname() + " has suggested a draw in match " + matchId + "!");
			messageRepo.save(message);
		}
		
		//update match. send back outcome, if any
		var updatedMatch = matchRepo.save(match);
		return updatedMatch.getMatchOutcome();
	}
	
	/**
	 * Denies a draw proposed by a given player's opponent. Undo opponent suggestion, match remains ongoing.
	 * @param matchId - Id of match where a draw is being denied
	 * @param playerId - player denying the draw
	 * @return Status of match
	 */
	@Override
	public MatchStatus denyDraw(int matchId, int playerId) {
		var m = matchRepo.findById(matchId);
		m.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No match with that ID exists."));
		var u = userRepo.findById(playerId);
		u.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user with that ID exists."));
		
		var match = m.get();
		var player = u.get();
		var opponentId = playerId == match.getWhitePlayer().getId() ? 
				match.getBlackPlayer().getId() : 
				match.getWhitePlayer().getId();
		var opponent = userRepo.findById(opponentId);
		
		if(match.getMatchStatus() != MatchStatus.INPROGRESS) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST, "Cannot propose draw for match " + matchId + " because it is not in progress.");
		}
		
		var whiteDraw = match.getIsDrawSuggestedByWhite();
		var blackDraw = match.getIsDrawSuggestedByBlack();
		
		//if player did not suggest draw, flip bit of other player
		if(match.getWhitePlayer().getId() == playerId && !whiteDraw && blackDraw) {
			match.setIsDrawSuggestedByBlack(false);
		}
		else if(match.getBlackPlayer().getId() == playerId && !blackDraw && whiteDraw) {
			match.setIsDrawSuggestedByWhite(false);
		}
		else {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST, "Cannot propose draw again for match " + matchId + ".");
		}
			
		messageRepo.save(new Message(player, "You have denied the draw. Match " + matchId + " is still going!"));
		messageRepo.save(new Message(opponent.get(), player.getNickname() + " has denied to the draw. Match " + matchId + " is still going!"));

		return match.getMatchStatus();
	}
}
