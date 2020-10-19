import React, {useState} from "react";
import {Button} from 'reactstrap'
import Grid from '@material-ui/core/Grid';

import Rook from '../../data/images/Rook.png'
import Knight from '../../data/images/Knight.png'
import Bishop from '../../data/images/Bishop.png'
import King from '../../data/images/King.png'
import Queen from '../../data/images/Queen.png'
import Pawn from '../../data/images/Pawn.png'


import './MatchStyle.css'

const MatchBoard = () => {
    const [chessBoard, setChessBoard] = useState([]);

    const emptySquare = <img src={Pawn}className={'transparent chessPiece'} alt={'empty square'}/>;
    const PawnImage = <img src={Pawn}className={'chessPiece'} alt={'Pawn'}/>;
    const RookImage = <img src={Rook}className={'chessPiece'} alt={'Rook'}/>;
    const KnightImage = <img src={Knight}className={'chessPiece'} alt={'Knight'}/>;
    const BishopImage = <img src={Bishop}className={'chessPiece'} alt={'Bishop'}/>;
    const QueenImage = <img src={Queen}className={'chessPiece'} alt={'Queen'}/>;
    const KingImage = <img src={King}className={'chessPiece'} alt={'King'}/>;

    const createBoardPiece = (pieceType, color, location) =>{
        color = color.toLowerCase();
        let className = color + "BoardSquare";
        if (pieceType === null){
            return(
                <Grid item key={location}>
                    <Button className={className}>
                        {emptySquare}
                    </Button>
                </Grid>
            );
        }
    }

    const createBoard = () => {
        let alpha = ['a', 'b', 'c', 'd','e', 'f', 'g', 'i'];
        let board = [];
        for(let row = 0; row < 8; row++){
            board.push([]);
            for(let column = 0; column < 8; column++){
                if(row%2 === 0){
                    if(column % 2 === 0){
                        board[row].push(
                            createBoardPiece(null, 'white', (alpha[7-column] + (8-row).toString()))
                        );
                    }
                    else{
                        board[row].push(
                            createBoardPiece(null, 'black', (alpha[7-column] + (8-row).toString()))
                        );
                    }
                }
                else{
                    if(column % 2 === 0){
                        board[row].push(
                            createBoardPiece(null, 'black', (alpha[7-column] + (8-row).toString()))
                        );
                    }
                    else{
                        board[row].push(
                            createBoardPiece(null, 'white', (alpha[7-column] + (8-row).toString()))
                        );
                    }
                }
            }
        }
        return board;
    }

    if(chessBoard.length === 0){
        setChessBoard(createBoard());
    }

    const showTable = () => {
        let table = [];
        for(let row = 0; row < chessBoard.length; row++){
            table.push(
                <Grid item key={"row "+ row} style={{flex:'1'}}>{chessBoard[row]}</Grid>
            );
        }
        console.log(table)
        return table;
    }

    return (
        <div className={"mainBody"}>
            <Grid container className={'gameBoard'} spacing={0}>{showTable()}</Grid>
        </div>
    );
}
export default MatchBoard