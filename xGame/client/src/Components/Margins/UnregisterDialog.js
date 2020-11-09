import { Button, Dialog, DialogContent, DialogContentText, DialogTitle } from '@material-ui/core';
import React from "react";
import { Row, Col } from "reactstrap";
import PropTypes from "prop-types";

const UnregisterDialog = (props) => {
    const {
        showUnregisterConfirmation,
        setShowUnregisterConfirmation,
    } = props;

    return (
        <Dialog open={showUnregisterConfirmation} onClose={() => {setShowUnregisterConfirmation(false)}} aria-labelledby="form-dialog-title">
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
                      onClick={() => {}} //Not connected to backend yet
                    >
                        Confirm Deregistration
                    </Button>
                  </Col>
                  <Col className="d-flex justify-content-center">
                    <Button onClick={() => {setShowUnregisterConfirmation(false)}}>cancel</Button>
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