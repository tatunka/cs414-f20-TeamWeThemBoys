package com.xgame.restservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xgame.common.viewmodels.MessageViewModel;
import com.xgame.service.interfaces.IMessageService;

@RestController
public class MessageController {
	
	@Autowired
	private IMessageService messageService;
	
	@GetMapping("/message")
	public List<MessageViewModel> getMessages(@RequestParam(value ="playerId") int playerId){
		return messageService.getMessages(playerId);
	}
	
	@GetMapping("/message/invite")
	public List<MessageViewModel> getInvites(@RequestParam(value = "playerId") int playerId) {
		return messageService.getInvites(playerId);
	}
	
	@PostMapping("/message")
	public MessageViewModel sendMessage(@RequestParam(value = "playerId") int playerId,
			@RequestParam(value = "contents") String contents) {
		return messageService.sendMessage(playerId, contents);
	}
}
