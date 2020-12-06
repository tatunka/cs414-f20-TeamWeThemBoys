import {
  Dialog,
  DialogContent,
  DialogContentText,
  DialogTitle
} from "@material-ui/core";
import React from "react";
import { Row, Col } from "reactstrap";
import PropTypes from "prop-types";
import { Button, ButtonGroup } from "@material-ui/core";

import * as matchService from "../../service/matchService";

const MatchDrawDialog = (props) => {
  const { showDrawDialog, refreshActiveMatch, activeMatch, activeUser } = props;

  const acceptDraw = async () => {
    await matchService.suggestDraw(activeMatch?.id, activeUser?.id);
    await refreshActiveMatch();
  };

  const declineDraw = async () => {
    await matchService.denyDraw(activeMatch?.id, activeUser?.id);
    await refreshActiveMatch();
  };

  return (
    <Dialog open={showDrawDialog} aria-labelledby="form-dialog-title">
      <DialogTitle id="form-dialog-title">Match Paused</DialogTitle>
      <DialogContent>
        <DialogContentText>
          Your opponent has suggested a draw!
        </DialogContentText>
        <Row className="pb-3">
          <Col align="center">
            <ButtonGroup variant="contained">
              <Button onClick={acceptDraw}>Accept</Button>
              <Button color="secondary" onClick={declineDraw}>
                Deny
              </Button>
            </ButtonGroup>
          </Col>
        </Row>
      </DialogContent>
    </Dialog>
  );
};

MatchDrawDialog.propTypes = {
  refreshActiveMatch: PropTypes.func,
  activeUser: PropTypes.object,
  showDrawDialog: PropTypes.bool
};

MatchDrawDialog.defaultProps = {
  refreshActiveMatch: () => {},
  activeUser: {},
  showDrawDialog: true
};

export default MatchDrawDialog;
