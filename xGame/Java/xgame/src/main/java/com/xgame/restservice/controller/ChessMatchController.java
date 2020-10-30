package com.xgame.restservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xgame.common.viewmodels.MatchInviteViewModel;
import com.xgame.common.viewmodels.MatchViewModel;
import com.xgame.service.interfaces.IChessMatchService;

@RestController
public class ChessMatchController {

	@Autowired
	IChessMatchService matchService;
	
	@GetMapping("/match/invite")
	public List<MatchInviteViewModel> getInvites(@RequestParam(value = "playerId") int playerId) {
		return matchService.getInvites(playerId);
	}
	
	@PostMapping("/match")
	public MatchViewModel createMatch(
			@RequestParam(value = "whiteId") int whiteId,
			@RequestParam(value = "blackId") int blackId) {
		
		return matchService.createMatch(whiteId, blackId);
	}
	
	@PatchMapping("/match/accept")
	public Boolean acceptInvite(@RequestParam(value = "matchId") int matchId) {
		return matchService.acceptInvite(matchId);
	}
	
	@GetMapping("/match")
	public MatchViewModel getMatch(@RequestParam(value = "matchId") int matchId) {
		return matchService.getMatch(matchId);
	}
	
}
