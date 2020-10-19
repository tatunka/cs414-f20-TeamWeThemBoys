import React from "react";
import { Collapse } from "reactstrap";
import PropTypes from "prop-types";

import MatchPlayerStats from './MatchPlayerStats'
import MatchBoard from './MatchBoard'

import './MatchStyle.css'


const Match = (props) => {
  const { isOpen } = props;

  return (
    <Collapse isOpen={isOpen}>
      <div className={"fullSize"}>
            <MatchPlayerStats playerColor={"White"}  />
            <MatchBoard />
            <MatchPlayerStats playerColor={"Black"} />
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
