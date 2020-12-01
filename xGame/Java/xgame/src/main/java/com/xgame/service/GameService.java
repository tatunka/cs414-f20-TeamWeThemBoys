package com.xgame.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xgame.common.enums.MatchOutcome;
import com.xgame.common.enums.MatchStatus;
import com.xgame.common.viewmodels.MatchViewModel;
import com.xgame.service.engine.ChessBoard;
import com.xgame.service.engine.ChessPiece;
import com.xgame.service.engine.ChessPiece.Color;
import com.xgame.service.engine.IllegalMoveException;
import com.xgame.service.engine.IllegalPositionException;
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
	
		var fromPiece = board.getPiece(fromPosition);
		
		//check that it is this piece's color's turn
		var turnColor = match.getTurnCount() % 2 == 0 ? Color.BLACK : Color.WHITE;
		if(turnColor != fromPiece.getColor()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't make move. It is " + turnColor.toString() + "'s turn!");
		}
		
		//make a move, update local board
		board.move(fromPosition, toPosition);
		this.match.setChessBoard(stringifyBoard());
		
		//update board in database
		var outcome = this.board.getOutcome();
		var currentPlayerId = turnColor == Color.BLACK ? match.getBlackPlayerId() : match.getWhitePlayerId();
		var opponentPlayerId = turnColor == Color.BLACK ? match.getWhitePlayerId() : match.getBlackPlayerId();
		var currentPlayer = turnColor == Color.BLACK ? match.getBlackPlayerNickname() : match.getWhitePlayerNickname();
		var opponent = turnColor == Color.BLACK ? match.getWhitePlayerNickname() : match.getBlackPlayerNickname();

		if(outcome == MatchOutcome.VICTORY) {
			messageService.send(currentPlayerId, "Congratulations! You won your match against " + opponent + "!");
			messageService.send(opponentPlayerId, "Tough luck! You lost your match against " + currentPlayer + "!");
			return matchService.EndMatch(match, Optional.of(currentPlayerId));	
		}
		else if (outcome == MatchOutcome.DRAW) {
			messageService.send(currentPlayerId, "Stalemate! Your match with " + opponent + " ended in a draw!");
			messageService.send(opponentPlayerId, "Stalemate! Your match with " + currentPlayer + " ended in a draw!");
			return matchService.EndMatch(match, null);
		}
		else {
			//send message to other player that it is their turn
			messageService.send(opponentPlayerId, "It's your turn to make a move in match " + match.getId() + "!");
			return matchService.updateMatch(match);
		}
	}
	
	public String getBoard(int matchId) throws JsonMappingException, JsonProcessingException {
		this.resumeMatch(matchId);
		return board.toString();
	}
	
	@Override
	public List<String> getLegalMoves(int matchId, String piecePosition)
			throws JsonMappingException, JsonProcessingException {
		ArrayList<String> legalMoves = new ArrayList<>();

		this.resumeMatch(matchId);

		ChessPiece piece;
		try {
			piece = board.getPiece(piecePosition);

			if (piece == null) {
				return legalMoves;
			}

			System.out.println(piece);
			legalMoves = piece.legalMoves();

		} catch (IllegalPositionException e1) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Cannot get legal moves for " + piecePosition + ". Position is illegal.", e1);
		}
		return legalMoves;
	}
	
	@Override
	public MatchViewModel promotePawn(int matchId, String position, String pieceName) {
		try {
			this.resumeMatch(matchId);
			board.promotePawn(position, Class.forName("com.xgame.service.engine." + pieceName));
			this.match.setChessBoard(stringifyBoard());
			return matchService.updateMatch(this.match);
		} 
		catch (JsonProcessingException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrieve game board", e);
		}
		catch(ClassNotFoundException e) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST, "Cannot promote. Can only promote to Queen, Knight, Rook, or Bishop", e);
		}
		catch (IllegalPositionException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Position is not valid.", e);
		}
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
