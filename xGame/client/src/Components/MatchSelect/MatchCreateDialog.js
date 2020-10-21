import { Button, TextField, Dialog, DialogContent, DialogContentText, DialogTitle } from '@material-ui/core';
import React from "react";
import { Row, Col } from "reactstrap";
import PropTypes from "prop-types";
import { Formik, Field } from "formik";

const MatchCreateDialog = (props) => {

  const [showMatchCreation, setShowMatchCreation] = React.useState(false);


  return (

    <Dialog open={showMatchCreation} onClose={setShowMatchCreation(false)} aria-labelledby="form-dialog-title">
            <DialogTitle id="form-dialog-title">Match Creation</DialogTitle>
            <DialogContent>
              <DialogContentText>
                To start a match, find an opponent to send a game request!
              </DialogContentText>
              <Formik
                    onSubmit={(values) => {
                      console.log("TODO: Hook up log-in API", values);
                      setShowMatchCreation();
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
                              <Col className="d-flex justify-content-center">
                                <Button
                                  size="small"
                                  color="primary"
                                  style={{ textTransform: "none" }}
                                  onClick={setShowMatchCreation(false)}
                                >
                                  Cancel
                                </Button>
                              </Col>
                          </Row>
                        </Col>
                      );
                    }}
                  </Formik>
            </DialogContent>
          </Dialog>

  );
};

MatchCreateDialog.propTypes = {
  setShowMatchCreation: PropTypes.func,
  showMatchCreation: PropTypes.bool
};

MatchCreateDialog.defaultProps = {
  setShowMatchCreation: () => {},
  showMatchCreation: false
};

export default MatchCreateDialog;