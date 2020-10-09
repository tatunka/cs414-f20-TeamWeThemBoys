package com.xgame.service.interfaces;

import java.util.List;

import com.xgame.common.viewmodels.MessageViewModel;

public interface IMessageService {
	Boolean sendMessage(String email, String contents);
	List<MessageViewModel> getMessages(String email);
}
