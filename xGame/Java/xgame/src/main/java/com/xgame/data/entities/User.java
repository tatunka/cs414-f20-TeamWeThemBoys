package com.xgame.data.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(nullable=false, unique=true)
	private String nickname;
	@Column(nullable=false, unique=true)
	private String email;
	@Column(nullable=false, unique=true)
	private String passwordHash;
	@Column(columnDefinition="BOOLEAN DEFAULT FALSE", nullable=false)
	private Boolean isDeleted = false;
	@CreationTimestamp
	private Timestamp creationTimestamp;
	
	@OneToMany(mappedBy="user")
	private List<Message> messages = new ArrayList<>();
	@OneToMany(mappedBy="whitePlayer")
	private List<ChessMatch> whiteMatches;
	@OneToMany(mappedBy="blackPlayer")
	private List<ChessMatch> blackMatches;
	@OneToMany(mappedBy="winningPlayer")
	private List<ChessMatch> winningMatches;
	
	protected User() {}
	
	public User(String nickname, String email, String passwordHash) {
		this.nickname = nickname;
		this.email = email;
		this.passwordHash = passwordHash;
	}
	
	public Integer getId() {
		return id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPasswordHash() {
		return passwordHash;
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public Timestamp getCreationTimestamp() {
		return creationTimestamp;
	}
	public List<Message> getMessages() {
		return this.messages;
	}
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	public List<ChessMatch> getWhiteMatches() {
		return whiteMatches;
	}
	public void setWhiteMatches(List<ChessMatch> whiteMatches) {
		this.whiteMatches = whiteMatches;
	}
	public List<ChessMatch> getBlackMatches() {
		return blackMatches;
	}
	public void setblackMatches(List<ChessMatch> blackMatches) {
		this.blackMatches = blackMatches;
	}
	public List<ChessMatch> getWinningMatches() {
		return winningMatches;
	}
	public void setWinningMatches(List<ChessMatch> winningMatches) {
		this.winningMatches = winningMatches;
	}
}
