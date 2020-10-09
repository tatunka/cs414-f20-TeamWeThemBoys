package com.xgame.common.viewmodels;

public class TestViewModel {
	private long id;
	private String content;
	
	public TestViewModel(Integer id, String content) {
		this.id = id;
		this.content = content;
	}
	
	public long getId() {
		return id;
	}
	
	public String getContent() {
		return content;
	}
}