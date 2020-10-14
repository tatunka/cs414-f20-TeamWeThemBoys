package com.xgame.data.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Message {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(nullable=false)
	private String contents;
	@CreationTimestamp
	private Timestamp sentTimestamp;
	private Timestamp readTimestamp;
	
	//navigation properties
	@ManyToOne(optional=false)
	@JoinColumn(name = "userId", referencedColumnName="id", nullable = false)
	private User user;
	
	//constructors
	protected Message() {}
	
	public Message(User recipient, String contents) {
		this.contents = contents;
	}
	
	//getters and setters
	public Integer getId() {
		return id;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public Timestamp getSentTimestamp() {
		return sentTimestamp;
	}
	public Timestamp getReadTimestamp() {
		return readTimestamp;
	}
	public void setReadTimeStamp(Timestamp readTimeStamp) {
		this.readTimestamp = readTimeStamp;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
