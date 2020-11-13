package com.xgame.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.xgame.common.enums.MatchStatus;
import com.xgame.common.viewmodels.MessageViewModel;
import com.xgame.data.IChessMatchRepository;
import com.xgame.data.IMessageRepository;
import com.xgame.data.IUserRepository;
import com.xgame.data.entities.Message;
import com.xgame.service.interfaces.IMessageService;

@Service
public class MessageService implements IMessageService {
	
	@Autowired
	private IMessageRepository messageRepo;
	@Autowired
	private IChessMatchRepository matchRepo;
	@Autowired
	private IUserRepository userRepo;

	/**
	 * Sends a message to a player with the given contents.
	 * @param userId - the id of the player to send the message to
	 * @param contents - the contents of the message
	 * @return - a view model of the newly created message
	 */
	@Override
	public MessageViewModel send(int userId, String contents) {
		var user = userRepo.findById(userId);
		user.orElseThrow(() ->  new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found. Can't send message"));
		var message = messageRepo.save(new Message(user.get(), contents));
		
		return new MessageViewModel(message);
	}

	/**
	 * Gets all notifications and match invitations for a single user.
	 * @param userId - the id of the user
	 * @return - a list of messages
	 */
	@Override
	public List<MessageViewModel> getAll(int userId) {
		var messages = messageRepo.findByUserIdAndReadTimestampIsNull(userId);
		var invites = matchRepo.findByBlackPlayerIdAndMatchStatus(userId, MatchStatus.PENDING);
		
		try {
			List<MessageViewModel> viewModels = invites
					.stream()
					.map(i -> new MessageViewModel(i, i.getWhitePlayer().getNickname()))
					.collect(Collectors.toList());
			
			viewModels.addAll(messages
					.stream()
					.map(m -> new MessageViewModel(m))
					.collect(Collectors.toList()));
			
			return viewModels;
		}
		catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown error.", e);
		}
	}
	
	/**
	 * Gets all match invitations for a single user
	 * @param userId - the id of the user
	 * @return - a list of messages
	 */
	@Override
	public List<MessageViewModel> getInvites(int userId) {
		var matches = matchRepo.findByBlackPlayerIdAndMatchStatus(userId, MatchStatus.PENDING);
		
		return matches
				.stream()
				.map(m -> new MessageViewModel(m, m.getWhitePlayer().getNickname()))
				.collect(Collectors.toList());
	}
	
	/**
	 * Marks he message as read by specifying a time read.
	 * @param messageId - id of message
	 */
	@Override
	public void read(int messageId) {
		var m = messageRepo.findById(messageId);
		m.orElseThrow(() ->  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't find match with that Id."));
		
		var match = m.get();
		match.setReadTimeStamp(new Timestamp(System.currentTimeMillis()));
		
		messageRepo.save(match);
	}
	
	@Override
	public void readAll(int userId) {
		var messages = messageRepo.findByUserIdAndReadTimestampIsNull(userId);
		var timeStamp = new Timestamp(System.currentTimeMillis());
		
		for(var message : messages) {
			message.setReadTimeStamp(timeStamp);
		}
		
		messageRepo.saveAll(messages);
	}
}
