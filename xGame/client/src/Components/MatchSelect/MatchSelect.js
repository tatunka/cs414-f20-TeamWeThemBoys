import React from "react";
import { Collapse } from "reactstrap";
import PropTypes from "prop-types";
import { Button } from '@material-ui/core';
import MatchCreateDialog from './MatchCreateDialog';

const MatchSelect = (props) => {
  const { isOpen } = props;
  const [showMatchCreation, setShowMatchCreation] = React.useState(false);


  return (
    <Collapse isOpen={isOpen}>
      <div>
        <p>add match selection data</p>
        <Button variant="outlined" color="primary" onClick={() => {setShowMatchCreation(true)} }>
                Create a Match
        </Button>

        <MatchCreateDialog showMatchCreation={showMatchCreation} setShowMatchCreation={setShowMatchCreation} />

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
