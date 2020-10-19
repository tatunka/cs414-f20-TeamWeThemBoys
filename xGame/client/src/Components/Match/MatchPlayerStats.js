import React from "react";
import './MatchStyle.css'

const MatchPlayerStats = (props) => {
    const {playerColor} = props;
    return(
        <div className={'sidebar'}>
            <p>{playerColor} player name and pieces captured</p>
        </div>
    )
}
export default MatchPlayerStats