package com.xgame.common.viewmodels;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xgame.common.enums.MatchStatus;
import com.xgame.data.IChessMatchRepository;
import com.xgame.data.entities.ChessMatch;
import com.xgame.data.entities.User;


public class ProfileViewModel {
	@Autowired
	private IChessMatchRepository matchRepo;
	
	private User user;
	private List<MatchHistoryViewModel> matchHistory;
	
	public ProfileViewModel(User user) {
		this.user = user;
		for(ChessMatch match: matchRepo.findByPlayerIdAndMatchStatus(user.getId(), MatchStatus.COMPLETED)) {
			matchHistory.add(new MatchHistoryViewModel(match, user));
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public List<MatchHistoryViewModel> getMatchHistory(){
		return matchHistory;
	}
}
