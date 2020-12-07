import React, { useState, useEffect } from "react";
import Grid from "@material-ui/core/Grid";
import PropTypes from "prop-types";
import "./MatchStyle.css";
import {
  getPieceList,
  showTable,
  createBoardPiece
} from "./HelpfulMatchTools.js";

const MatchPlayerStats = (props) => {
  const playerName = props.playerName;
  const playerColor = props.playerColor;
  const turnCounter = props.turnCounter;
  const boardState = props.boardState;
  const isOpponent = props.isOpponent;
  const [takenPieces, setTakenPieces] = useState([]);

  const determineTaken = () => {
    let totalPieces = "";
    totalPieces =
      playerColor === "white" ? getPieceList("black") : getPieceList("white");
    for (let piece = 0; piece < boardState.length; piece++) {
      let indexFound = -1;
      for (let i = 0; i < totalPieces.length; i++) {
        if (boardState[piece].pieceName === totalPieces[i]) {
          indexFound = i;
        }
      }
      if (indexFound >= 0) {
        totalPieces.splice(indexFound, 1);
      }
    }
    setTakenPieces(totalPieces);
  };

  const generateDisplay = () => {
    let tempDisplay = [[]];
    let rowCount = 0;
    for (let i = 0; i < takenPieces.length; i++) {
      tempDisplay[rowCount].push(
        createBoardPiece(
          takenPieces[i],
          "hidden",
          playerColor + "Captured" + i,
          null
        )
      );
      if (i % 5 === 0) {
        tempDisplay.push([]);
        rowCount++;
      }
    }
    return tempDisplay;
  };

  const isPlayersTurn =
    (playerColor === "black" && turnCounter % 2 === 0) ||
    (playerColor === "white" && turnCounter % 2 === 1)
      ? true
      : false;

  // const displayTurnCounter = () => {
  //   if (!isOpponent) {
  //     return <p>Turn Number {turnCounter}</p>;
  //   } else {
  //     return <p></p>;
  //   }
  // };

  useEffect(() => {
    determineTaken();
  }, [boardState]);

  return (
    <div className={"sidebar"}>
      <h2>{isOpponent ? playerName : "You"}</h2>
      {!isOpponent && (
        <h5>
          You are <span className="font-weight-bold">{playerColor}</span>, it is
          your {!isPlayersTurn ? "opponent's " : ""} turn.
        </h5>
      )}
      <p>Pieces captured:</p>
      <Grid container className={"gameBoard"} spacing={0}>
        {showTable(generateDisplay())}
      </Grid>
    </div>
  );
};

MatchPlayerStats.propTypes = {
  playerName: PropTypes.string,
  playerColor: PropTypes.string,
  turnCounter: PropTypes.number,
  boardState: PropTypes.array
};
export default MatchPlayerStats;
