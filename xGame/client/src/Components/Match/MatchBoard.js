import React, {useState} from "react";
import Grid from '@material-ui/core/Grid';
import PropTypes from "prop-types";
import {findLocation, createBoardPiece} from './HelpfulMatchTools.js'

import './MatchStyle.css'

const MatchBoard = (props) => {
    const boardState = props.boardState;
    const activeColor = props.activeColor;
    const [chessBoard, setChessBoard] = useState([]);
    const [piecesOnBoard, setPiecesOnBoard] = useState([]);
    const [piecesToUpdate, setPiecesToUpdate] = useState({add : [], remove: []});
    const [selected, setSelected] = useState([]);

    const checkForAdditions = () => {
        let newUpdateList = piecesToUpdate;
        for(let x = 0; x < boardState.length; x++){
            let matchFound = false;
            for(let y = 0; y < piecesOnBoard.length; y++){
                if(boardState[x] === piecesOnBoard[y]){
                    matchFound = true
                }
            }
            if(!matchFound){
                newUpdateList.add.push(boardState[x]);
            }
        }
        setPiecesToUpdate(newUpdateList);
    }

    const checkForRemoval = () => {
        let newUpdateList = piecesToUpdate;
        for(let x = 0; x < piecesOnBoard.length; x++){
            let matchFound = false;
            for(let y = 0; y < boardState.length; y++){
                if(boardState[x] === piecesOnBoard[y]){
                    matchFound = true
                }
            }
            if(!matchFound){
                newUpdateList.remove.push(boardState[x]);
            }
        }
        setPiecesToUpdate(newUpdateList);
    }

    //change to alter boardState
    const addSelected = (selectedPieceName, selectedLocation) => {
        console.log('clicked')
        let tempSelected = selected;
        tempSelected.push({pieceName: selectedPieceName, location: selectedLocation})
        if(tempSelected.length === 2){
            console.log('switch')
            setSelected([]);
        }
        else{
            setSelected(tempSelected);
        }
    }

    const createBoard = () => {
        let alpha = ['a', 'b', 'c', 'd','e', 'f', 'g', 'i'];
        let board = [];
        for(let row = 0; row < 8; row++){
            board.push([]);
            for(let column = 0; column < 8; column++){
                let tempLocation = (alpha[7-column] + (8-row).toString());
                if(row%2 === 0){
                    if(column % 2 === 0){
                        board[row].push(
                            createBoardPiece(null, 'white', tempLocation, () => {addSelected(null, tempLocation)})
                        );
                    }
                    else{
                        board[row].push(
                            createBoardPiece(null, 'black', tempLocation, () => {addSelected(null, tempLocation)})
                        );
                    }
                }
                else{
                    if(column % 2 === 0){
                        board[row].push(
                            createBoardPiece(null, 'black', tempLocation, () => {addSelected(null, tempLocation)})
                        );
                    }
                    else{
                        board[row].push(
                            createBoardPiece(null, 'white', tempLocation, () => {addSelected(null, tempLocation)})
                        );
                    }
                }
            }
        }
        return board;
    }

    const populateBoard = () => {
        let newBoard = chessBoard;
        for(let piece = 0; piece < piecesToUpdate.add.length; piece++){
            let index = findLocation(piecesToUpdate.add[piece].location);
            let boardColor = '';
            if((index[0] % 2 === 0 && index[1] % 2 === 0) || (index[0] % 2 !== 0 && index[1] % 2 !== 0)){
                boardColor = 'white';
            }else{
                boardColor = 'black';
            }
            let tempPieceName = piecesToUpdate.add[piece].pieceName;
            let tempLocation = piecesToUpdate.add[piece].location
            let newBoardSquare = createBoardPiece(tempPieceName, boardColor, tempLocation, () => {addSelected(tempPieceName, tempLocation)})
            newBoard[index[1]][index[0]] = newBoardSquare;
        }
        for(let piece = 0; piece < piecesToUpdate.remove.length; piece++){
            let index = findLocation(piecesToUpdate.remove[piece].location);
            let boardColor = '';
            if((index[0] % 2 === 0 && index[1] % 2 === 0) || (index[0] % 2 !== 0 && index[1] % 2 !== 0)){
                boardColor = 'white';
            }else{
                boardColor = 'black';
            }
            let tempLocation = piecesToUpdate.remove[piece].location
            let newBoardSquare = createBoardPiece(null, boardColor, tempLocation, () => {addSelected(null, tempLocation)})
            newBoard[index[1]][index[0]] = newBoardSquare;
        }
        setPiecesToUpdate({add : [], remove: []})
        return newBoard;
    }

    const showTable = () => {
        let table = [];
        for(let row = 0; row < chessBoard.length; row++){
            table.push(
                <Grid item key={"row "+ row} style={{flex:'1'}}>{chessBoard[row]}</Grid>
            );
        }
        return table;
    }

    if(chessBoard.length === 0){
        setChessBoard(createBoard());
        checkForAdditions();
        checkForRemoval();
     }
    if((piecesToUpdate.add.length > 0 || piecesToUpdate.remove.length > 0) && chessBoard.length > 0){
        setChessBoard(populateBoard());
        setPiecesOnBoard(boardState);
    }

    return (
        <div className={"mainBody"}>
            <Grid container className={'gameBoard'} spacing={0}>{showTable()}</Grid>
        </div>
    );
}

MatchBoard.propTypes = {
    boardState: PropTypes.array,
    activeColor: PropTypes.string
}
export default MatchBoard