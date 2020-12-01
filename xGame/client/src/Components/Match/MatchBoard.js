import React, { useEffect, useState } from "react";
import Grid from "@material-ui/core/Grid";
import PropTypes from "prop-types";
import {
  findLocation,
  createBoardPiece,
  showTable,
} from "./HelpfulMatchTools.js";

import "./MatchStyle.css";
import * as gameService from "../../service/gameService";

const MatchBoard = (props) => {
  const boardState = props.boardState;
  const activeColor = props.activeColor;
  const matchId = props.matchId;
  const [chessBoard, setChessBoard] = useState([]);
  const [selected, setSelected] = useState(null);
  const whitePieces = [
    "\u2659",
    "\u2656",
    "\u2657",
    "\u2658",
    "\u2654",
    "\u2655",
  ];
  const blackPieces = [
    "\u265F",
    "\u265C",
    "\u265D",
    "\u265E",
    "\u265A",
    "\u265B",
  ];

  const selectPiece = async (selectedPieceData) => {
    if (
      (activeColor === "white" ? whitePieces : blackPieces).includes(
        selectedPieceData.pieceName
      )
    ) {
      if (selected === null) {
        setSelected(selectedPieceData);
        const moves = await gameService.legalMoves(
          matchId,
          selectedPieceData.location
        );
        if (moves.length > 0) {
          console.log("before highlight", chessBoard);
          highlightSquares(moves);
        } else {
          setChessBoard(populateBoard());
        }
      }
    } else {
      if (selected !== null) {
        console.log("else here");
        // const move = await gameService.move(
        //   matchId,
        //   selected.location,
        //   selectedPieceData.location
        // );
      }
    }
  };

  const highlightSquares = (moveList) => {
    console.log("chessBoard", chessBoard);
    let tempBoard = populateBoard(); //[...chessBoard];
    console.log("tempboard", tempBoard);
    for (let i = 0; i < moveList.length; i++) {
      let location = findLocation(moveList[i]);
      let matchFound = false;
      boardState.forEach((piece) => {
        if (piece.location === moveList[i]) {
          matchFound = true;
          let newBoardSquare = createBoardPiece(
            piece.pieceName,
            "blue",
            piece.location,
            () => {
              selectPiece({
                pieceName: piece.pieceName,
                location: piece.location,
                squareColor: "blue",
              });
            }
          );
          tempBoard[location[1]][location[0]] = newBoardSquare;
        }
      });
      if (!matchFound) {
        tempBoard[location[1]][location[0]] = createBoardPiece(
          null,
          "blue",
          moveList[i],
          () => {
            selectPiece({
              pieceName: null,
              location: moveList[i],
              squareColor: "blue",
            });
          }
        );
      }
    }
    console.log("changed tempBoard", tempBoard);
    setChessBoard(tempBoard);
  };

  const createEmptyBoard = () => {
    let alpha = ["a", "b", "c", "d", "e", "f", "g", "h"];
    let board = [];
    for (let row = 0; row < 8; row++) {
      board.push([]);
      for (let column = 0; column < 8; column++) {
        let tempLocation = alpha[row] + (8 - column).toString();
        if (row % 2 === 0) {
          if (column % 2 === 0) {
            board[row].push(
              createBoardPiece(null, "white", tempLocation, () => {
                selectPiece({
                  pieceName: null,
                  location: tempLocation,
                  squareColor: "white",
                });
              })
            );
          } else {
            board[row].push(
              createBoardPiece(null, "black", tempLocation, () => {
                selectPiece({
                  pieceName: null,
                  location: tempLocation,
                  squareColor: "black",
                });
              })
            );
          }
        } else {
          if (column % 2 === 0) {
            board[row].push(
              createBoardPiece(null, "black", tempLocation, () => {
                selectPiece({
                  pieceName: null,
                  location: tempLocation,
                  squareColor: "black",
                });
              })
            );
          } else {
            board[row].push(
              createBoardPiece(null, "white", tempLocation, () => {
                selectPiece({
                  pieceName: null,
                  location: tempLocation,
                  squareColor: "white",
                });
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
          selectPiece({
            pieceName: piece.pieceName,
            location: piece.location,
            squareColor: boardColor,
          });
        }
      );
      newBoard[location[1]][location[0]] = newBoardSquare;
    });
    return newBoard;
  };

  useEffect(() => {
    if (boardState.length === 0) {
      console.log("boardstate empty");
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
  activeColor: PropTypes.string,
  matchId: PropTypes.number,
};
export default MatchBoard;
