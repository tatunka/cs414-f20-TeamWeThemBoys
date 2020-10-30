package com.xgame.common.viewmodels;

import java.sql.Timestamp;

import com.xgame.common.enums.MessageType;
import com.xgame.data.entities.ChessMatch;
import com.xgame.data.entities.Message;

public class MessageViewModel {

	private Integer id;
	private MessageType type;
	private String content;
	private Timestamp timestamp;
	
	//constructors
	public MessageViewModel(Message message) {
		this.id = message.getId();
		this.type = MessageType.MESSAGE;
		this.content = message.getContents();
		this.timestamp = message.getSentTimestamp();
	}
	
	public MessageViewModel(ChessMatch match, String content) {
		this.id = match.getId();
		this.type = MessageType.INVITATION;
		this.content = content;
		this.timestamp = match.getCreationTimestamp();
	}

	//getters
	public int getId() {
		return id;
	}
	public String getContent() {
		return content;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}

	public MessageType getType() {
		return type;
	}
}
