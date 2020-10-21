import React from 'react'
import {Button} from 'reactstrap'
import Grid from '@material-ui/core/Grid';

import Rook from '../../data/images/Rook.png'
import Knight from '../../data/images/Knight.png'
import Bishop from '../../data/images/Bishop.png'
import King from '../../data/images/King.png'
import Queen from '../../data/images/Queen.png'
import Pawn from '../../data/images/Pawn.png'

const PawnPiece = {Image: <img src={Pawn}className={'chessPiece'} alt={'Pawn'}/>, blackName: '\u265F', whiteName: '\u2659'};
const RookPiece = {Image: <img src={Rook}className={'chessPiece'} alt={'Rook'}/>, blackName: '\u265C', whiteName: '\u2656'};
const KnightPiece = {Image: <img src={Knight}className={'chessPiece'} alt={'Knight'}/>, blackName: '\u265E', whiteName: '\u2658'};
const BishopPiece = {Image: <img src={Bishop}className={'chessPiece'} alt={'Bishop'}/>, blackName: '\u265D', whiteName: '\u2657'};
const QueenPiece = {Image: <img src={Queen}className={'chessPiece'} alt={'Queen'}/>, blackName: '\u265B', whiteName: '\u2655'};
const KingPiece = {Image: <img src={King}className={'chessPiece'} alt={'King'}/>, blackName: '\u265A', whiteName: '\u2654'};

export function findLocation(location){

    let number = location.charCodeAt(0)-97;
    let letter = 8 - parseInt(location[1]);
    return [letter, number];
}

export function createBoardPiece (pieceName, color, location, clickFunction){
    color = color.toLowerCase();
    let className = color + "BoardSquare";
    switch(pieceName){
        case PawnPiece.blackName:
            return(
                <Grid item key={location}>
                    <Button className={className} onClick={clickFunction}>
                        <img src={Pawn}className={'chessPiece'} alt={'Pawn'}/>
                    </Button>
                </Grid>
            );
        case PawnPiece.whiteName:
            return(
                <Grid item key={location} onClick={clickFunction}>
                    <Button className={className}>
                        <img src={Pawn}className={'white chessPiece'} alt={'Pawn'}/>
                    </Button>
                </Grid>
            );
        case RookPiece.blackName:
            return(
                <Grid item key={location}>
                    <Button className={className} onClick={clickFunction}>
                        <img src={Rook}className={'chessPiece'} alt={'Rook'}/>
                    </Button>
                </Grid>
            );
        case RookPiece.whiteName:
            return(
                <Grid item key={location}>
                    <Button className={className} onClick={clickFunction}>
                        <img src={Rook}className={'white chessPiece'} alt={'Rook'}/>
                    </Button>
                </Grid>
            );
        case KnightPiece.blackName:
            return(
                <Grid item key={location}>
                    <Button className={className} onClick={clickFunction}>
                        <img src={Knight}className={'chessPiece'} alt={'Knight'}/>
                    </Button>
                </Grid>
            );
        case KnightPiece.whiteName:
            return(
                <Grid item key={location}>
                    <Button className={className} onClick={clickFunction}>
                        <img src={Knight}className={'white chessPiece'} alt={'Knight'}/>
                    </Button>
                </Grid>
            );
        case BishopPiece.blackName:
            return(
                <Grid item key={location}>
                    <Button className={className} onClick={clickFunction}>
                        <img src={Bishop}className={'chessPiece'} alt={'Bishop'}/>
                    </Button>
                </Grid>
            );
        case BishopPiece.whiteName:
            return(
                <Grid item key={location}>
                    <Button className={className} onClick={clickFunction}>
                        <img src={Bishop}className={'white chessPiece'} alt={'Bishop'}/>
                    </Button>
                </Grid>
            );
        case QueenPiece.blackName:
            return(
                <Grid item key={location}>
                    <Button className={className} onClick={clickFunction}>
                        <img src={Queen}className={'chessPiece'} alt={'Queen'}/>
                    </Button>
                </Grid>
            );
        case QueenPiece.whiteName:
            return(
                <Grid item key={location}>
                    <Button className={className} onClick={clickFunction}>
                        <img src={Queen}className={'white chessPiece'} alt={'Queen'}/>
                    </Button>
                </Grid>
            );
        case KingPiece.blackName:
            return(
                <Grid item key={location}>
                    <Button className={className} onClick={clickFunction}>
                        <img src={King}className={'chessPiece'} alt={'King'}/>
                    </Button>
                </Grid>
            );
        case KingPiece.whiteName:
            return(
                <Grid item key={location}>
                    <Button className={className} onClick={clickFunction}>
                        <img src={King}className={'white chessPiece'} alt={'King'}/>
                    </Button>
                </Grid>
            );
        default:
            return(
                <Grid item key={location}>
                    <Button className={className} onClick={clickFunction}>
                        <img src={Pawn}className={'transparent chessPiece'} alt={'empty square'}/>
                    </Button>
                </Grid>
            );
    }
}