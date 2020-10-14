package com.xgame.restservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xgame.common.viewmodels.MatchViewModel;
import com.xgame.service.interfaces.IChessMatchService;

@RestController
public class ChessMatchController {

	@Autowired
	IChessMatchService matchService;
	
	@PostMapping("/match")
	public MatchViewModel createMatch(
			@RequestParam(value = "whiteId") int whiteId,
			@RequestParam(value = "blackId") int blackId) {
		
		return matchService.createMatch(whiteId, blackId);
	}
}
