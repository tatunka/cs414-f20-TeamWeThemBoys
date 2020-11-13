package com.xgame.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xgame.data.entities.Message;

public interface IMessageRepository extends JpaRepository<Message, Integer> {
	List<Message> findByUserId(Integer id);
	List<Message> findByUserIdAndReadTimestampIsNull(Integer id);
}
