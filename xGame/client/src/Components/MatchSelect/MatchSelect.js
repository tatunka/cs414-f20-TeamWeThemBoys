import React from "react";
import { Collapse } from "reactstrap";
import PropTypes from "prop-types";

const MatchSelect = (props) => {
  const { isOpen } = props;

  return (
    <Collapse isOpen={isOpen}>
      <div>
        <p>add match selection data</p>
      </div>
    </Collapse>
  );
};

MatchSelect.propTypes = {
  isOpen: PropTypes.bool
};

MatchSelect.defaultProps = {
  isOpen: true
};

export default MatchSelect;
