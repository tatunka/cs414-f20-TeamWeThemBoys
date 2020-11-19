import React, { useEffect, useState } from "react";
import Grid from "@material-ui/core/Grid";
import PropTypes from "prop-types";
import {
  findLocation,
  createBoardPiece,
  showTable
} from "./HelpfulMatchTools.js";

import "./MatchStyle.css";

const MatchBoard = (props) => {
  const boardState = props.boardState;
  const activeColor = props.activeColor;
  const [chessBoard, setChessBoard] = useState([]);
  const [selected, setSelected] = useState([]);

  //change to alter boardState
  const selectPiece = (selectedPieceName, selectedLocation) => {
    console.log("clicked");
    setSelected({
      pieceName: selectedPieceName,
      location: selectedLocation
    });
  };

  const createEmptyBoard = () => {
    let alpha = ["a", "b", "c", "d", "e", "f", "g", "h"];
    let board = [];
    for (let row = 0; row < 8; row++) {
      board.push([]);
      for (let column = 0; column < 8; column++) {
        let tempLocation = alpha[7 - row] + (8 - column).toString();
        if (row % 2 === 0) {
          if (column % 2 === 0) {
            board[row].push(
              createBoardPiece(null, "white", tempLocation, () => {
                selectPiece(null, tempLocation);
              })
            );
          } else {
            board[row].push(
              createBoardPiece(null, "black", tempLocation, () => {
                selectPiece(null, tempLocation);
              })
            );
          }
        } else {
          if (column % 2 === 0) {
            board[row].push(
              createBoardPiece(null, "black", tempLocation, () => {
                selectPiece(null, tempLocation);
              })
            );
          } else {
            board[row].push(
              createBoardPiece(null, "white", tempLocation, () => {
                selectPiece(null, tempLocation);
              })
            );
          }
        }
      }
    }
    return board;
  };

  const populateBoard = () => {
    let newBoard = createEmptyBoard();
    boardState.forEach((piece) => {
      let location = findLocation(piece.location);
      let boardColor = "";
      if (
        (location[0] % 2 === 0 && location[1] % 2 === 0) ||
        (location[0] % 2 !== 0 && location[1] % 2 !== 0)
      ) {
        boardColor = "white";
      } else {
        boardColor = "black";
      }
      let newBoardSquare = createBoardPiece(
        piece.pieceName,
        boardColor,
        piece.location,
        () => {
          selectPiece(piece.pieceName, piece.location);
        }
      );
      newBoard[location[1]][location[0]] = newBoardSquare;
    });
    return newBoard;
  };

  //modify to react to boardState change
  useEffect(() => {
    if (boardState.length === 0) {
      setChessBoard(createEmptyBoard());
    }
    if (boardState.length > 0) {
      setChessBoard(populateBoard());
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [boardState]);

  return (
    <div className={"mainBody"}>
      <Grid container className={"gameBoard"} spacing={0}>
        {showTable(chessBoard)}
      </Grid>
    </div>
  );
};

MatchBoard.propTypes = {
  boardState: PropTypes.array,
  activeColor: PropTypes.string
};
export default MatchBoard;
