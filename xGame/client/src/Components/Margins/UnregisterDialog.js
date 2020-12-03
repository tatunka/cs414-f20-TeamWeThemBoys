import {
  Button,
  Dialog,
  DialogContent,
  DialogContentText,
  DialogTitle,
} from "@material-ui/core";
import React, { useEffect, useState } from "react";
import { Row, Col } from "reactstrap";
import PropTypes from "prop-types";

import * as userService from "../../service/userService";

const UnregisterDialog = (props) => {
  const {
    showUnregisterConfirmation,
    setShowUnregisterConfirmation,
    activeUser,
    logoutUser,
  } = props;
  const [sendRequest, setSendRequest] = useState(false);

  const deactivateRequest = async () => {
    return await userService.deregister(activeUser.id);
  };

  useEffect(() => {
    if (sendRequest) {
      const deactivate = deactivateRequest();
      if (deactivate) {
        logoutUser();
      }
    }
  }, [sendRequest]);

  return (
    <Dialog
      open={showUnregisterConfirmation}
      onClose={() => {
        setShowUnregisterConfirmation(false);
      }}
      aria-labelledby="form-dialog-title"
    >
      <DialogTitle id="form-dialog-title">Warning</DialogTitle>
      <DialogContent>
        <DialogContentText>
          Are you sure you would like to unregister your account?
        </DialogContentText>
        <Row>
          <Col className="d-flex justify-content-center">
            <Button
              color="secondary"
              variant={"contained"}
              onClick={() => {
                setSendRequest(true);
              }}
            >
              Confirm Deregistration
            </Button>
          </Col>
          <Col className="d-flex justify-content-center">
            <Button
              onClick={() => {
                setShowUnregisterConfirmation(false);
              }}
            >
              cancel
            </Button>
          </Col>
        </Row>
      </DialogContent>
    </Dialog>
  );
};

UnregisterDialog.propTypes = {
  setShowUnregisterConfirmation: PropTypes.func,
  showUnregisterConfirmation: PropTypes.bool,
};

UnregisterDialog.defaultProps = {
  setShowUnregisterConfirmation: () => {},
  showUnregisterConfirmation: false,
};

export default UnregisterDialog;
