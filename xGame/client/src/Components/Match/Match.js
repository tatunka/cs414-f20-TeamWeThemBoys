import React from "react";
import { Collapse, Row } from "reactstrap";
import PropTypes from "prop-types";
import { Button } from "@material-ui/core";

import MatchPlayerStats from "./MatchPlayerStats";
import MatchBoard from "./MatchBoard";

import "./MatchStyle.css";

const Match = (props) => {
  const { isOpen, activeMatch } = props;

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
    { pieceName: "\u265A", location: "a8" }
  ];

  const determineActiveColor = () => {
    return activeMatch?.turnCount % 2 === 0 ? "white" : "black";
  };

  return (
    <Collapse isOpen={isOpen}>
      <Row>
        <div className={"fullSize"}>
          <MatchPlayerStats
            playerName={activeMatch?.whitePlayerNickname}
            activeColor={determineActiveColor()}
            turnCounter={activeMatch?.turnCount}
            boardState={boardState}
          />
          <MatchBoard boardState={boardState} activeColor={"white"} />
          <MatchPlayerStats
            playerName={activeMatch?.blackPlayerNickname}
            activeColor={determineActiveColor()}
            turnCounter={activeMatch?.turnCount}
            boardState={boardState}
            opponent={true}
          />
        </div>
      </Row>
      <Row>
        <div className={"center"}>
          <Button variant="contained" color="secondary">
            Forfeit
          </Button>
        </div>
      </Row>
    </Collapse>
  );
};

Match.propTypes = {
  isOpen: PropTypes.bool,
  activeMatch: PropTypes.objectOf({
    id: PropTypes.number,
    whitePlayerId: PropTypes.number,
    blackPlayerId: PropTypes.number,
    whitePlayerNickname: PropTypes.string,
    blackPlayerNickname: PropTypes.string,
    turnCount: PropTypes.number
  })
};

Match.defaultProps = {
  isOpen: true,
  activeMatch: {
    id: 0,
    whitePlayerId: 0,
    blackPlayerId: 0,
    whitePlayerNickname: "",
    blackPlayerNickname: "",
    turnCount: 0
  }
};

export default Match;
