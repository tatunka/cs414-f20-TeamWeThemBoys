package com.xgame.data.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Message {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(nullable=false)
	private String contents;
	@Column(nullable=false, columnDefinition="DATETIME DEFAULT NOW()")
	private Date sentDateTime;
	private Date readDateTime;
	
	//navigation properties
	@ManyToOne(optional=false)
	@JoinColumn(name = "userId", referencedColumnName="id", nullable = false)
	private User user;
	
	//constructors
	protected Message() {}
	
	public Message(String contents, Date sentDateTime) {
		this.contents = contents;
		this.sentDateTime = sentDateTime;
	}
	
	//getters and setters
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public Date getSentDateTime() {
		return sentDateTime;
	}
	public void setSentDateTime(Date sentDateTime) {
		this.sentDateTime = sentDateTime;
	}
	public Date getReadDateTime() {
		return readDateTime;
	}
	public void setReadDateTime(Date readDateTime) {
		this.readDateTime = readDateTime;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
