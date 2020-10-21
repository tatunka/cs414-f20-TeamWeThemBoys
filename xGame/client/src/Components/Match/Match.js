import React from "react";
import { Collapse } from "reactstrap";
import PropTypes from "prop-types";

import MatchPlayerStats from './MatchPlayerStats'
import MatchBoard from './MatchBoard'

import './MatchStyle.css'


const Match = (props) => {
  const { isOpen } = props;

  let matchEntity = {id: 1, turnCount: 0, chessBoard: "", whitePlayer: {nickName:"home"}, blackPlayer: {nickName: "opponent"}}

    //add functionality to parse chessBoard into boardState
  let boardState = [{pieceName: '\u265F', location: 'a1'}]

  const determineActiveColor = () => {
    return (matchEntity.turnCount % 2 === 0) ? 'white' : 'black';
  }

  return (
    <Collapse isOpen={isOpen}>
      <div className={"fullSize"}>
            <MatchPlayerStats playerName={matchEntity.whitePlayer.nickName} activeColor={determineActiveColor()} turnCounter={matchEntity.turnCount}/>
            <MatchBoard boardState={boardState} activeColor={"white"}/>
            <MatchPlayerStats playerName={matchEntity.blackPlayer.nickName} activeColor={determineActiveColor()}/>
      </div>
    </Collapse>
  );
};

Match.propTypes = {
  isOpen: PropTypes.bool
};

Match.defaultProps = {
  isOpen: true
};

export default Match;
