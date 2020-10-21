import React from "react";
import PropTypes from "prop-types";
import './MatchStyle.css'
import {findLocation} from './HelpfulMatchTools.js'

const MatchPlayerStats = (props) => {
    const {playerName, boardState} = props;
    return(
        <div className={'sidebar'}>
            <p>{playerName} player name and pieces captured</p>
        </div>
    )
}

MatchPlayerStats.propTypes = {
    playerName: PropTypes.string,
    boardState: PropTypes.array,
}
export default MatchPlayerStats