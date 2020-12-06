import React, { useEffect, useState } from "react";
import Grid from "@material-ui/core/Grid";
import PropTypes from "prop-types";
import {
  findLocation,
  createBoardPiece,
  showTable
} from "./HelpfulMatchTools.js";

import "./MatchStyle.css";
import * as gameService from "../../service/gameService";
import Modal from "@material-ui/core/Modal";
import Button from "@material-ui/core/Button";
import { makeStyles } from "@material-ui/core";

const useStyles = makeStyles((theme) => ({
  modalStyles: {
    display: "flex",
    flexDirection: "column",
    top: "50%",
    left: "50%",
    position: "fixed",
    width: "400",
    height: "400",
    backgroundColor: "white",
    border: "2px solid #000",
    borderRadius: "5px",
    alignItems: "center",
    padding: "5px"
  }
}));

const MatchBoard = (props) => {
  const classes = useStyles();
  const {
    boardState,
    activeColor,
    activeMatch,
    setActiveMatch,
    playerId
  } = props;
  const [chessBoard, setChessBoard] = useState([]);
  const [selected, setSelected] = useState([]);
  const [modalOpen, setModalOpen] = useState(false);
  const [matchCompletedText, setMatchCompletedText] = useState("");
  const whitePieces = [
    "\u2659",
    "\u2656",
    "\u2657",
    "\u2658",
    "\u2654",
    "\u2655"
  ];
  const blackPieces = [
    "\u265F",
    "\u265C",
    "\u265D",
    "\u265E",
    "\u265A",
    "\u265B"
  ];

  const selectPiece = (selectedPieceData) => {
    if (
      activeMatch.status === "INPROGRESS" &&
      (activeColor === "white"
        ? playerId === activeMatch.whitePlayerId
        : playerId === activeMatch.blackPlayerId)
    ) {
      if (
        (activeColor === "white" ? whitePieces : blackPieces).includes(
          selectedPieceData.pieceName
        )
      ) {
        let tempSelected = [];
        tempSelected.push(selectedPieceData);
        setSelected(tempSelected);
      } else {
        let tempSelected = selected;
        tempSelected.push(selectedPieceData);
        setSelected(tempSelected);
      }
    }
  };

  const showLegalMoves = async () => {
    const moves = await gameService.legalMoves(
      activeMatch.id,
      selected[0].location
    );
    if (moves.length > 0) {
      highlightSquares(moves);
    } else {
      setChessBoard(populateBoard());
    }
  };

  const makeMove = async () => {
    const move = await gameService.move(
      activeMatch.id,
      selected[0].location,
      selected[2].location
    );
    if (move.status !== 400 && move.status !== 500) {
      setActiveMatch(move);
    } else {
      setSelected([]);
    }
  };

  const highlightSquares = (moveList) => {
    let tempBoard = populateBoard(); //[...chessBoard];
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
                squareColor: "blue"
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
              squareColor: "blue"
            });
          }
        );
      }
    }
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
                  squareColor: "white"
                });
              })
            );
          } else {
            board[row].push(
              createBoardPiece(null, "black", tempLocation, () => {
                selectPiece({
                  pieceName: null,
                  location: tempLocation,
                  squareColor: "black"
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
                  squareColor: "black"
                });
              })
            );
          } else {
            board[row].push(
              createBoardPiece(null, "white", tempLocation, () => {
                selectPiece({
                  pieceName: null,
                  location: tempLocation,
                  squareColor: "white"
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
            squareColor: boardColor
          });
        }
      );
      newBoard[location[1]][location[0]] = newBoardSquare;
    });
    return newBoard;
  };

  useEffect(() => {
    if (boardState.length === 0) {
      setChessBoard(createEmptyBoard());
    }
    if (boardState.length > 0) {
      setChessBoard(populateBoard());
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [boardState]);

  useEffect(() => {
    if (activeMatch.status === "COMPLETED") {
      setMatchCompletedText(
        activeMatch.winningPlayerNickname
          ? `${activeMatch.winningPlayerNickname} wins!`
          : `Match ended in a draw`
      );
      setModalOpen(true);
    }
  }, [activeMatch.status]);

  if (selected.length === 1) {
    showLegalMoves();
    let tempSelected = selected;
    tempSelected.push({});
    setSelected(tempSelected);
  } else if (selected.length === 3) {
    makeMove();
    setSelected([]);
  } else if (selected.length > 3) {
    setSelected([]);
  }

  return (
    <div className={"mainBody"}>
      <Grid container className={"gameBoard"} spacing={0}>
        {showTable(chessBoard)}
        <Modal open={modalOpen} onClose={() => setModalOpen(false)}>
          <div className={classes.modalStyles}>
            <h3>{matchCompletedText}</h3>
            <Button
              onClick={() => setModalOpen(false)}
              variant={"contained"}
              color={"secondary"}
            >
              close
            </Button>
          </div>
        </Modal>
      </Grid>
    </div>
  );
};

MatchBoard.propTypes = {
  boardState: PropTypes.array,
  activeColor: PropTypes.string,
  activeMatch: PropTypes.shape({
    id: PropTypes.number,
    whitePlayerId: PropTypes.number,
    blackPlayerId: PropTypes.number,
    whitePlayerNickname: PropTypes.string,
    blackPlayerNickname: PropTypes.string,
    turnCount: PropTypes.number,
    chessBoard: PropTypes.array
  }),
  setActiveMatch: PropTypes.func,
  playerId: PropTypes.number
};
export default MatchBoard;
