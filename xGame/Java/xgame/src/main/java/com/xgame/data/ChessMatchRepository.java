package com.xgame.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xgame.data.entities.ChessMatch;

public interface ChessMatchRepository extends JpaRepository<ChessMatch, Integer> {

}
