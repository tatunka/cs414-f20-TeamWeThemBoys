import React, { useEffect, useState } from "react";
import { Collapse, Row, Col } from "reactstrap";
import PropTypes from "prop-types";
import { Button, ButtonGroup } from "@material-ui/core";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faChess } from "@fortawesome/free-solid-svg-icons";

import MatchPlayerStats from "./MatchPlayerStats";
import MatchBoard from "./MatchBoard";
import MatchDrawDialog from "./MatchDrawDialog";
import * as matchService from "../../service/matchService";

import "./MatchStyle.css";

const Match = (props) => {
  const {
    isOpen,
    activeMatch,
    setActiveMatch,
    activeUser,
    refreshActiveMatch
  } = props;

  let boardStateDefault = [];

  const [boardState, setBoardState] = useState(boardStateDefault);

  useEffect(() => {
    if (
      activeMatch?.chessBoard &&
      activeMatch.chessBoard !== "This is a chessboard"
    ) {
      const newBoard = parseBoard();
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

  const activeColor = activeMatch?.turnCount % 2 === 0 ? "black" : "white";

  const handleForfeitClick = () => {
    matchService.forfeitMatch(activeMatch?.id, activeUser?.id);
  };

  const handleDrawClick = async () => {
    await matchService.suggestDraw(activeMatch?.id, activeUser?.id);
    refreshActiveMatch();
  };

  console.log("ACTIVE USER:", activeUser);

  const showDrawDialog =
    activeMatch?.drawSuggestedByWhite !== activeMatch?.drawSuggestedByBlack &&
    ((activeMatch?.blackPlayerId === activeUser?.id &&
      activeMatch?.drawSuggestedByWhite) ||
      (activeMatch?.whitePlayerId === activeUser?.id &&
        activeMatch?.drawSuggestedByBlack));

  return (
    <div>
      <MatchDrawDialog
        activeUser={activeUser}
        activeMatch={activeMatch}
        showDrawDialog={showDrawDialog}
        refreshActiveMatch={refreshActiveMatch}
      />
      <Collapse isOpen={isOpen}>
        <Row>
          {activeMatch ? (
            <>
              <Row className="fullSize">
                <MatchPlayerStats
                  playerName={activeMatch?.whitePlayerNickname}
                  activeColor={activeColor}
                  turnCounter={activeMatch?.turnCount}
                  boardState={boardState}
                />
                <MatchBoard
                  boardState={boardState}
                  activeMatch={activeMatch}
                  activeColor={activeColor}
                  setActiveMatch={setActiveMatch}
                  playerId={activeUser?.id}
                />
                <MatchPlayerStats
                  playerName={activeMatch?.blackPlayerNickname}
                  activeColor={activeColor}
                  turnCounter={activeMatch?.turnCount}
                  boardState={boardState}
                  opponent={true}
                />
              </Row>
              <Row className="fullSize">
                <div className="d-flex justify-content-center">
                  <ButtonGroup variant="contained">
                    <Button onClick={handleDrawClick}>Draw</Button>
                    <Button color="secondary" onClick={handleForfeitClick}>
                      Forfeit
                    </Button>
                  </ButtonGroup>
                </div>
              </Row>
            </>
          ) : (
            <Col className="pt-3 pb-3">
              <FontAwesomeIcon icon={faChess} size="9x" />
              <div className="h3 pt-3">No match open</div>
            </Col>
          )}
        </Row>
      </Collapse>
    </div>
  );
};

Match.propTypes = {
  activeUser: PropTypes.object,
  isOpen: PropTypes.bool,
  setActiveMatch: PropTypes.func,
  refreshActiveMatch: PropTypes.func,
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
  activeUser: {},
  isOpen: true,
  setActiveMatch: () => {},
  refreshActiveMatch: () => {},
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
