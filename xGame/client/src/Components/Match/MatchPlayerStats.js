import React, { useState } from "react";
import Grid from "@material-ui/core/Grid";
import PropTypes from "prop-types";
import "./MatchStyle.css";
import {
  getPieceList,
  showTable,
  createBoardPiece,
} from "./HelpfulMatchTools.js";

const MatchPlayerStats = (props) => {
  const playerName = props.playerName;
  const activeColor = props.activeColor;
  const turnCounter = props.turnCounter;
  const boardState = props.boardState;
  const opponent = props.opponent;
  const [takenPieces, setTakenPieces] = useState([]);
  const [changed, setChanged] = useState(true);
  const [pieceDisplay, setPieceDisplay] = useState([]);

  const determineTaken = () => {
    let totalPieces = "";
    if (opponent) {
      totalPieces =
        activeColor === "white" ? getPieceList("white") : getPieceList("black");
    } else {
      totalPieces =
        activeColor === "white" ? getPieceList("black") : getPieceList("white");
    }
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
          activeColor + "Captured" + i,
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

  const displayTurnCounter = () => {
    if (!opponent) {
      return <p>Turn Number {turnCounter}</p>;
    } else {
      return <p></p>;
    }
  };

  if (changed) {
    determineTaken();
    setChanged(false);
  }

  return (
    <div className={"sidebar"}>
      <p>{playerName} player name and pieces captured</p>
      {displayTurnCounter()}
      <Grid container className={"gameBoard"} spacing={0}>
        {showTable(generateDisplay())}
      </Grid>
    </div>
  );
};

MatchPlayerStats.propTypes = {
  playerName: PropTypes.string,
  activeColor: PropTypes.string,
  turnCounter: PropTypes.number,
  boardState: PropTypes.array,
};
export default MatchPlayerStats;
