import React from "react";
import { Collapse } from "reactstrap";
import PropTypes from "prop-types";
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import { Row, Col } from "reactstrap";
import { Formik, Field } from "formik";

const MatchSelect = (props) => {
  const { isOpen } = props;
  const [open, setOpen] = React.useState(false);

  const handleClickOpen = () => {
      setOpen(true);
    };

  const handleClose = () => {
      setOpen(false);
  };


  return (
    <Collapse isOpen={isOpen}>
      <div>
        <p>add match selection data</p>
        <Button variant="outlined" color="primary" onClick={handleClickOpen}>
                Create a Match
        </Button>


        <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title">
                <DialogTitle id="form-dialog-title">Match Creation</DialogTitle>
                <DialogContent>
                  <DialogContentText>
                    To start a match, find an opponent to send a game request!
                  </DialogContentText>
                  <Formik
                        onSubmit={(values) => {
                          console.log("TODO: Hook up log-in API", values);
                          handleClose();
                        }}
                        initialValues={{
                          opponent: "",
                          picked: "White"
                        }}
                      >
                        {(formProps) => {
                          const { setFieldValue, values } = formProps;
                          return (
                            <Col>
                              <Row>
                                <Col>
                                  <TextField //this doesn't actually do anything yet
                                    id="opponent"
                                    variant="outlined"
                                    style={{ width: "80%" }}
                                    name="opponent"
                                    label="Search for a user"
                                    value={values?.["email"] || ""}
                                    onChange={(e) => setFieldValue("opponent", e.target.value)}
                                  />
                                </Col>
                              </Row>
                              <br></br>
                              <Row>
                                <Col>
                                  <div role="group" aria-labelledby="my-radio-group">
                                      <p>
                                      <div>Your starting color: {values.picked}</div>
                                      <label>
                                        <Field type="radio" name="picked" value="Black" />
                                        Black
                                      </label>
                                      <br></br>
                                      <label>
                                        <Field type="radio" name="picked" value="White" />
                                        White
                                      </label>
                                      </p>
                                    </div>
                                </Col>
                              </Row>
                              <Row className="pt-2">
                                <Col className="d-flex justify-content-center">
                                  <Button
                                    color="primary"
                                    variant="contained"
                                    style={{ textTransform: "none" }}
                                    onClick={formProps.handleSubmit}
                                  >
                                    Create Match
                                  </Button>
                                </Col>
                                <Row className="pt-2">
                                  <Col className="d-flex justify-content-center">
                                    <Button
                                      size="small"
                                      color="primary"
                                      style={{ textTransform: "none" }}
                                      onClick={handleClose}
                                    >
                                      Cancel
                                    </Button>
                                  </Col>
                                </Row>
                              </Row>
                            </Col>
                          );
                        }}
                      </Formik>
                </DialogContent>
              </Dialog>


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
