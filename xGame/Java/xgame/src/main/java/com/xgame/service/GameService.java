package com.xgame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xgame.common.enums.MatchStatus;
import com.xgame.common.viewmodels.MatchViewModel;
import com.xgame.service.engine.ChessBoard;
import com.xgame.service.engine.ChessPiece;
import com.xgame.service.engine.ChessPiece.Color;
import com.xgame.service.engine.IllegalMoveException;
import com.xgame.service.engine.IllegalPositionException;
import com.xgame.service.engine.King;
import com.xgame.service.interfaces.IChessMatchService;
import com.xgame.service.interfaces.IGameService;
import com.xgame.service.interfaces.IMessageService;

@Service
public class GameService implements IGameService {

	@Autowired
	private IChessMatchService matchService;
	@Autowired 
	private IMessageService messageService; 
	
	private ChessBoard board;
	private MatchViewModel match;
	
	@Override
	public MatchViewModel move(int matchId, String fromPosition, String toPosition) 
			throws JsonMappingException, JsonProcessingException, IllegalMoveException, IllegalPositionException {
		
		this.resumeMatch(matchId);
		
		if(this.match.getStatus() != MatchStatus.INPROGRESS) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot make a move. Match is not in progress.");
		}
	
		ChessPiece fromPiece;
		try {
			fromPiece = board.getPiece(fromPosition);
			
			if(fromPiece == null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot make move. There is no piece at " + fromPosition + ".");
			}
		} catch (IllegalPositionException e1) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot move piece at " + fromPosition + ". Position is illegal.", e1);
		}
		
		ChessPiece toPiece;
		try {
			toPiece = board.getPiece(toPosition);
		} catch (IllegalPositionException e1) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot move piece to " + toPosition + ". Position is illegal.", e1);
		}
		
		//check that it is this piece's color's turn
		var turnColor = match.getTurnCount() % 2 == 0 ? Color.BLACK : Color.WHITE;
		
		if(turnColor != fromPiece.getColor()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't make move. It is " + turnColor.toString() + "'s turn!");
		}
		
		board.move(fromPosition, toPosition);
	
		//send message to other player that it is there turn
		var opponentPlayerId = turnColor == Color.BLACK ? match.getWhitePlayerId() : match.getBlackPlayerId();
		var currentPlayerId = turnColor == Color.BLACK ? match.getBlackPlayerId() : match.getWhitePlayerId();
		messageService.sendMessage(opponentPlayerId, "It's your turn to make a move in match " + match.getId() + "!");
		
		this.match.setChessBoard(stringifyBoard());
		
		//update board
		var updatedMatch = toPiece instanceof King ? matchService.EndMatch(this.match, currentPlayerId) : 
			matchService.updateMatch(this.match);
		return  updatedMatch;
	}
	
	public String getBoard(int matchId) throws JsonMappingException, JsonProcessingException {
		this.resumeMatch(matchId);
		return board.toString();
	}
	
	private void resumeMatch(int matchId) throws JsonMappingException, JsonProcessingException {
		this.match = matchService.getMatch(matchId);
			
		var mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

		var board = mapper.readValue(match.getChessBoard(), ChessPiece[][].class);
		this.board = new ChessBoard();
		this.board.resume(board);		
	}
	
	private String stringifyBoard() throws JsonProcessingException {
		var mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this.board.getBoard());
	}
}
