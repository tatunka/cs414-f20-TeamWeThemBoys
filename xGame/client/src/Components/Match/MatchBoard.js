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
import Modal from "@material-ui/core/Modal";
import Button from "@material-ui/core/Button";
import { makeStyles } from "@material-ui/core";

const useStyles = makeStyles((theme) => ({
  modalStyles: {
    display: "flex",
    flexDirection: "column",
    margins: "auto",
    position: "absolute",
    left: "50%",
    top: "50%",
    transform: "translate(-50%, -50%)",
    width: "400",
    height: "400",
    maxWidth: "50%",
    maxHeight: "50%",
    backgroundColor: "white",
    border: "2px solid #000",
    borderRadius: "5px",
    alignItems: "center",
    padding: "5px",
  },
}));

const MatchBoard = (props) => {
  const classes = useStyles();
  const { boardState, activeColor, activeMatch, setActiveMatch } = props;
  const [chessBoard, setChessBoard] = useState([]);
  const [selected, setSelected] = useState([]);
  const [endGameModalOpen, setEndgameModalOpen] = useState(false);
  const [promoteModalOpen, setPromoteModalOpen] = useState(false);
  const [winningPlayer, setWinningPlayer] = useState("");
  const [promoteData, setPromoteData] = useState([]);
  const whitePromotionalSpaces = ["a5", "a6", "a7", "a8", "b8", "c8", "d8"];
  const blackPromotionalSpaces = ["h4", "h3", "h2", "h1", "g1", "f1", "e1"];
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

  const selectPiece = (selectedPieceData) => {
    if (activeMatch.status === "INPROGRESS") {
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
    if (move.status !== 400) {
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
      if (
        piece.pieceName === "\u2659" &&
        whitePromotionalSpaces.includes(piece.location)
      ) {
        setPromoteData([piece.location]);
        setPromoteModalOpen(true);
      }
      if (
        piece.pieceName === "\u265F" &&
        blackPromotionalSpaces.includes(piece.location)
      ) {
        setPromoteData([piece.location]);
        setPromoteModalOpen(true);
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

  const endGameModal = () => {
    return (
      <Modal open={endGameModalOpen} onClose={() => setEndgameModalOpen(false)}>
        <div className={classes.modalStyles}>
          <h3>Congratulations {winningPlayer}!</h3>
          <Button
            onClick={() => setEndgameModalOpen(false)}
            variant={"contained"}
            color={"secondary"}
          >
            close
          </Button>
        </div>
      </Modal>
    );
  };

  const getPromotionList = () => {
    let buttons = [];
    buttons.push(
      createBoardPiece(
        activeColor === "white" ? "\u265D" : "\u2657",
        "black",
        "BishopModal",
        () => {
          let temp = promoteData;
          temp.push("Bishop");
          setPromoteData(temp);
          setPromoteModalOpen(false);
        }
      )
    );
    buttons.push(
      createBoardPiece(
        activeColor === "white" ? "\u265E" : "\u2658",
        "black",
        "KnightModal",
        () => {
          let temp = promoteData;
          temp.push("Knight");
          setPromoteData(temp);
          setPromoteModalOpen(false);
        }
      )
    );
    buttons.push(
      createBoardPiece(
        activeColor === "white" ? "\u265C" : "\u2656",
        "black",
        "RookModal",
        () => {
          let temp = promoteData;
          temp.push("Rook");
          setPromoteData(temp);
          setPromoteModalOpen(false);
        }
      )
    );
    buttons.push(
      createBoardPiece(
        activeColor === "white" ? "\u265B" : "\u2655",
        "black",
        "QueenModal",
        () => {
          let temp = promoteData;
          temp.push("Queen");
          setPromoteData(temp);
          setPromoteModalOpen(false);
        }
      )
    );
    return buttons;
  };

  const promoteModal = () => {
    return (
      <Modal open={promoteModalOpen} onClose={() => setPromoteModalOpen(false)}>
        <div className={classes.modalStyles}>
          <h3>Choose which piece you want to promote your Pawn too</h3>
          <Grid container className={"gameBoard"}>
            {showTable(getPromotionList())}
          </Grid>
          <Button
            onClick={() => setPromoteModalOpen(false)}
            variant={"contained"}
            color={"secondary"}
          >
            close
          </Button>
        </div>
      </Modal>
    );
  };

  const makePromoteCall = async (matchId, location, pieceName) => {
    const game = await gameService.promote(matchId, location, pieceName);
    if (game) {
      setActiveMatch(game);
    }
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
      setWinningPlayer(activeMatch.winningPlayerNickname);
      setEndgameModalOpen(true);
    }
  }, [activeMatch.status]);

  if (promoteData.length === 2) {
    makePromoteCall(activeMatch.id, promoteData[0], promoteData[1]);
    setPromoteData([]);
  }

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
        {endGameModal()}
        {promoteModal()}
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
    chessBoard: PropTypes.array,
  }),
  setActiveMatch: PropTypes.func,
};
export default MatchBoard;
