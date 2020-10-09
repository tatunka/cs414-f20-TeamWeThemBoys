package com.xgame.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xgame.data.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	List<User> findByEmail(String email);
}
