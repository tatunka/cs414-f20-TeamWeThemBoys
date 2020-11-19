import React, { useEffect, useState } from "react";
import { Collapse, Row, Col } from "reactstrap";
import PropTypes from "prop-types";
import { Button } from "@material-ui/core";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faChess } from "@fortawesome/free-solid-svg-icons";

import MatchPlayerStats from "./MatchPlayerStats";
import MatchBoard from "./MatchBoard";

import "./MatchStyle.css";

const Match = (props) => {
  const { isOpen, activeMatch } = props;

  let boardStateDefault = [];

  const [boardState, setBoardState] = useState(boardStateDefault);

  useEffect(() => {
    if (
      activeMatch?.chessBoard &&
      activeMatch.chessBoard !== "This is a chessboard"
    ) {
      const newBoard = parseBoard();
      console.log("SETTING BOARD STATE", newBoard);
      setBoardState(newBoard);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [activeMatch]);

  const parseBoard = () => {
    const newBoardState = [];
    activeMatch.chessBoard.forEach((row) => {
      row.forEach((piece) => {
        if (piece) {
          const newPiece = {
            pieceName: getPieceName(piece.color, piece.type),
            location: piece.position
          };
          newBoardState.push(newPiece);
        }
      });
    });
    return newBoardState;
  };

  const getPieceName = (color, type) => {
    const isWhite = color === "WHITE";
    switch (type) {
      case "PAWN":
        return isWhite ? "\u2659" : "\u265F";
      case "ROOK":
        return isWhite ? "\u2656" : "\u265C";
      case "BISHOP":
        return isWhite ? "\u2657" : "\u265D";
      case "KNIGHT":
        return isWhite ? "\u2658" : "\u265E";
      case "KING":
        return isWhite ? "\u2654" : "\u265A";
      case "QUEEN":
        return isWhite ? "\u2655" : "\u265B";
      default:
        break;
    }
  };

  const determineActiveColor = () => {
    return activeMatch?.turnCount % 2 === 0 ? "white" : "black";
  };

  return (
    <Collapse isOpen={isOpen}>
      <Row>
        {activeMatch ? (
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
        ) : (
          <Col className="pt-3 pb-3">
            <FontAwesomeIcon icon={faChess} size="9x" />
            <div className="h3 pt-3">No match open</div>
          </Col>
        )}
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
  activeMatch: PropTypes.shape({
    id: PropTypes.number,
    whitePlayerId: PropTypes.number,
    blackPlayerId: PropTypes.number,
    whitePlayerNickname: PropTypes.string,
    blackPlayerNickname: PropTypes.string,
    turnCount: PropTypes.number,
    chessBoard: PropTypes.array
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
    turnCount: 0,
    chessBoard: []
  }
};

export default Match;
