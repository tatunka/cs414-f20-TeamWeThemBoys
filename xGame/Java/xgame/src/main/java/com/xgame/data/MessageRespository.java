package com.xgame.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xgame.data.entities.Message;

public interface MessageRespository extends JpaRepository<Message, Integer> {

}
