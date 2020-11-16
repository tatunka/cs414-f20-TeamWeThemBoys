package com.xgame.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xgame.common.enums.MatchStatus;
import com.xgame.data.entities.ChessMatch;

public interface IChessMatchRepository extends JpaRepository<ChessMatch, Integer> {
	List<ChessMatch> findByBlackPlayerIdAndMatchStatus(Integer id, MatchStatus matchStatus);
	List<ChessMatch> findByMatchStatus(MatchStatus matchStatus);
}
