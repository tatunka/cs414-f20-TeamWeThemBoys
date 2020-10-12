import React from "react";
import { Collapse } from "reactstrap";
import PropTypes from "prop-types";

const Match = (props) => {
  const { isOpen } = props;

  return (
    <Collapse isOpen={isOpen}>
      <div>
        <p>add match data</p>
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
