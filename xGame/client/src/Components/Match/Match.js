import React from "react";
import { Collapse } from "reactstrap";
import PropTypes from "prop-types";

import MatchPlayerStats from "./MatchPlayerStats";
import MatchBoard from "./MatchBoard";

import "./MatchStyle.css";

const Match = (props) => {
  const { isOpen } = props;

  let matchEntity = {
    id: 1,
    turnCount: 0,
    chessBoard: "",
    whitePlayer: { nickName: "home" },
    blackPlayer: { nickName: "opponent" },
  };

  //add functionality to parse chessBoard into boardState
  let boardState = [
    { pieceName: "\u2659", location: "d1" },
    { pieceName: "\u2659", location: "e2" },
    { pieceName: "\u2659", location: "f2" },
    { pieceName: "\u2659", location: "f3" },
    { pieceName: "\u2659", location: "g4" },
    { pieceName: "\u2659", location: "g3" },
    { pieceName: "\u2659", location: "h5" },
    { pieceName: "\u2659", location: "e4" },
    { pieceName: "\u2656", location: "e1" },
    { pieceName: "\u2656", location: "h4" },
    { pieceName: "\u2657", location: "f1" },
    { pieceName: "\u2657", location: "h2" },
    { pieceName: "\u2658", location: "g1" },
    { pieceName: "\u2658", location: "h3" },
    { pieceName: "\u2655", location: "g2" },
    { pieceName: "\u2654", location: "h1" },
    { pieceName: "\u265F", location: "a4" },
    { pieceName: "\u265F", location: "b5" },
    { pieceName: "\u265F", location: "b6" },
    { pieceName: "\u265F", location: "c6" },
    { pieceName: "\u265F", location: "c7" },
    { pieceName: "\u265F", location: "d5" },
    { pieceName: "\u265F", location: "d7" },
    { pieceName: "\u265F", location: "e8" },
    { pieceName: "\u265C", location: "d8" },
    { pieceName: "\u265C", location: "a5" },
    { pieceName: "\u265D", location: "a7" },
    { pieceName: "\u265D", location: "c8" },
    { pieceName: "\u265E", location: "a6" },
    { pieceName: "\u265E", location: "b8" },
    { pieceName: "\u265B", location: "b7" },
    { pieceName: "\u265A", location: "a8" },
  ];

  const determineActiveColor = () => {
    return matchEntity.turnCount % 2 === 0 ? "white" : "black";
  };

  return (
    <Collapse isOpen={isOpen}>
      <div className={"fullSize"}>
        <MatchPlayerStats
          playerName={matchEntity.whitePlayer.nickName}
          activeColor={determineActiveColor()}
          turnCounter={matchEntity.turnCount}
          boardState={boardState}
        />
        <MatchBoard boardState={boardState} activeColor={"white"} />
        <MatchPlayerStats
          playerName={matchEntity.blackPlayer.nickName}
          activeColor={determineActiveColor()}
          turnCounter={matchEntity.turnCount}
          boardState={boardState}
          opponent={true}
        />
      </div>
    </Collapse>
  );
};

Match.propTypes = {
  isOpen: PropTypes.bool,
};

Match.defaultProps = {
  isOpen: true,
};

export default Match;
