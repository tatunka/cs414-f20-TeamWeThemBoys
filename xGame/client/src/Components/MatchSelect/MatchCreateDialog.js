import { Button, TextField, Dialog, DialogContent, DialogContentText, DialogTitle } from '@material-ui/core';
import React, { useState } from "react";
import { Row, Col } from "reactstrap";
import PropTypes from "prop-types";
import { Formik } from "formik";
import { Select, MenuItem } from '@material-ui/core';
import { Alert } from "@material-ui/lab";

import * as userService from "../../service/userService";
import * as matchService from "../../service/matchService";


const MatchCreateDialog = (props) => {
    const {
        activeUser,
        showMatchCreation,
        setShowMatchCreation
    } = props;

    const [selectedOpponent, setSelectedOpponent] = useState("");
    const [apiError, setApiError] = useState("");
    var [userSearchResults, setUserSearchResults] = useState([]);

    const handleSelectChange = (event) => {
        setSelectedOpponent(event.target.value);
    };

    const MakeItem = function(X) {
        return <MenuItem key={X.id} value={X.id}>{X.nickname}</MenuItem>;
    };

    return (
        <Dialog open={showMatchCreation} onClose={() => {setShowMatchCreation(false)}} aria-labelledby="form-dialog-title">
            <DialogTitle id="form-dialog-title">Match Creation</DialogTitle>
            <DialogContent>
                <DialogContentText>
                    To start a match, find an opponent to send a game request!
                </DialogContentText>
                <Row className="pb-3">
                  <Col>
                    {apiError && (
                      <Alert
                        onClose={() => {
                          setApiError("");
                        }}
                        severity="error"
                      >
                        {apiError}
                      </Alert>
                    )}
                  </Col>
                </Row>
                <Formik
                    onSubmit={async (values) => {
                        if(values?.userResults) {
                            const user = await userService.search(values?.userResults)
                            if (user?.error) setApiError(user?.message);
                            else setUserSearchResults({user});
                        }
                    }}
                    initialValues={{
                        userResults: "",
                    }}
                >
                    {(formProps) => {
                        const { setFieldValue, values } = formProps;
                        return (
                            <Col>
                                <Row className="pt-2">
                                    <Col xs="auto" className="d-flex justify-content-center">
                                        <TextField
                                            id="userResults"
                                            variant="outlined"
                                            name="userResults"
                                            label="Search for a user"
                                            value={values?.["userResults"] || ""}
                                            onChange={(e) => setFieldValue("userResults", e.target.value)}
                                        />
                                    </Col>
                                    <Col xs="3" className="d-flex justify-content-center">
                                        <Button
                                            color="primary"
                                            variant="contained"
                                            style={{ textTransform: "none" }}
                                            onClick={formProps.handleSubmit}
                                        >
                                            Search
                                        </Button>
                                    </Col>
                                </Row>
                                <br />
                            </Col>
                        );
                    }}
                </Formik>
                <Formik
                    onSubmit={async (values) => {
                        const match = await matchService.createMatch(values);
                        if (match?.error) setApiError(match?.message);
                        setShowMatchCreation(false);
                    }}
                    initialValues={{
                        blackID: "",
                        whiteID: activeUser?.id
                    }}
                >
                    {(formProps) => {
                        const { setFieldValue, values } = formProps;
                        return (
                            <Col>
                                <Row className="pt-2">
                                    <Col className="d-flex justify-content-center">
                                        <Select
                                            id="blackID"
                                            variant="outlined"
                                            style={{ width: "80%" }}
                                            name="blackID"
                                            onChange={handleSelectChange}
                                            value={values?.["blackID"] || ""}
                                            onChange={(e) => setFieldValue("blackID", e.target.value)}
                                        >
                                            {userSearchResults?.user?.map(MakeItem)}
                                        </Select>
                                    </Col>
                                </Row>
                                <br />
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
                                            onClick={() => {setShowMatchCreation(false)}}
                                        >
                                            Cancel
                                        </Button>
                                    </Col>
                                </Row>
                                <br />
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
  activeUser: PropTypes.object,
  showMatchCreation: PropTypes.bool,
  userSearchResults: PropTypes.array,
};

MatchCreateDialog.defaultProps = {
  activeUser: {},
  setShowMatchCreation: () => {},
  showMatchCreation: false,
};

export default MatchCreateDialog;
