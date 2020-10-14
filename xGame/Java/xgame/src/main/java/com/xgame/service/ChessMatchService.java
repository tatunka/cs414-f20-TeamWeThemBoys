package com.xgame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xgame.common.enums.MatchStatus;
import com.xgame.common.viewmodels.MatchViewModel;
import com.xgame.data.ChessMatchRepository;
import com.xgame.data.UserRepository;
import com.xgame.data.entities.ChessMatch;
import com.xgame.service.interfaces.IChessMatchService;

@Service
public class ChessMatchService implements IChessMatchService {

	@Autowired
	private ChessMatchRepository matchRepo;
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public MatchViewModel createMatch(int whiteId, int blackId) {
		//TODO: get fire up chess match and serialize board.
		
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
		
		return null;
	}

	@Override
	public MatchViewModel getMatch(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getChessBoard(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateMatch(int id, String chessBoard) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
