package com.xgame.common.viewmodels;

import java.util.List;

import com.xgame.data.entities.ChessMatch;
import com.xgame.data.entities.User;



public class ProfileViewModel {
	private User user;
	private List<MatchViewModel> matchHistory;
	
	public ProfileViewModel(User user) {
		this.setUser(user);
		for(ChessMatch match : user.getBlackMatches()) {
			matchHistory.add(new MatchViewModel(match));
		}
		
		for(ChessMatch match : user.getWhiteMatches()) {
			matchHistory.add(new MatchViewModel(match));
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public List<MatchViewModel> getMatchHistory(){
		return matchHistory;
	}
}
