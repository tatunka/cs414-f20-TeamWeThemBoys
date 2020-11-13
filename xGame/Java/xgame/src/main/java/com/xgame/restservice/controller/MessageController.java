package com.xgame.restservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xgame.common.viewmodels.MessageViewModel;
import com.xgame.service.interfaces.IMessageService;

@CrossOrigin
@RestController
public class MessageController {
	
	@Autowired
	private IMessageService messageService;
	
	@GetMapping("/message")
	public List<MessageViewModel> getMessages(@RequestParam(value ="playerId") int playerId){
		return messageService.getAll(playerId);
	}
	
	@GetMapping("/message/invite")
	public List<MessageViewModel> getInvites(@RequestParam(value = "playerId") int playerId) {
		return messageService.getInvites(playerId);
	}
	
	@PostMapping("/message")
	public MessageViewModel sendMessage(@RequestParam(value = "playerId") int playerId,
			@RequestParam(value = "contents") String contents) {
		return messageService.send(playerId, contents);
	}
	
	@PatchMapping("/message")
	public void readMessage(@RequestParam(value = "messageId") int messageId) {
		messageService.read(messageId);
	}
	
	@PatchMapping("/messages")
	public void readMessages(@RequestParam(value = "playerId") int playerId) {
		messageService.readAll(playerId);
	}
}
