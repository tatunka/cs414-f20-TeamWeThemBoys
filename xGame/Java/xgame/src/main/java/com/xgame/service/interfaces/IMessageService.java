package com.xgame.service.interfaces;

import java.util.List;

import com.xgame.common.viewmodels.MessageViewModel;

public interface IMessageService {
	MessageViewModel sendMessage(int playerId, String contents);
	List<MessageViewModel> getMessages(int userId);
	public List<MessageViewModel> getInvites(int userId);
}
