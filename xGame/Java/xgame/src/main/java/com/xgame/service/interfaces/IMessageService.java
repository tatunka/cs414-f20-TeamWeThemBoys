package com.xgame.service.interfaces;

import java.util.List;

import com.xgame.common.viewmodels.MessageViewModel;

public interface IMessageService {
	MessageViewModel send(int playerId, String contents);
	List<MessageViewModel> getAll(int userId);
	public List<MessageViewModel> getInvites(int userId);
	void read(int messageId);
}
