import React, { useState } from "react";
import PropTypes from "prop-types";
import { Row, Col } from "reactstrap";
import { Formik } from "formik";
import { Button, TextField } from "@material-ui/core";
import { Alert } from "@material-ui/lab";

import * as userService from "../../service/userService";

const SignUpForm = (props) => {
  const { setShowLoginForm, logInUser } = props;

  const [registerError, setRegisterError] = useState("");

  return (
    <Formik
      onSubmit={async (values) => {
        const user = await userService.register(values);
        if (user?.error) setRegisterError(user?.message);
        else logInUser({ ...user, isLoggedIn: true });
      }}
      initialValues={{
        email: "",
        nickname: "",
        password: ""
      }}
    >
      {(formProps) => {
        const { setFieldValue, values } = formProps;
        return (
          <Col>
            <Row className="pb-3">
              <Col>
                {registerError && (
                  <Alert
                    onClose={() => {
                      setRegisterError("");
                    }}
                    severity="error"
                  >
                    {registerError}
                  </Alert>
                )}
              </Col>
            </Row>
            <Row>
              <Col>
                <TextField
                  color="secondary"
                  id="nickname"
                  variant="outlined"
                  style={{ width: "80%" }}
                  name="nickname"
                  label="Username"
                  type="nickname"
                  value={values?.["nickname"] || ""}
                  onChange={(e) => setFieldValue("nickname", e.target.value)}
                />
              </Col>
            </Row>
            <Row className="pt-2">
              <Col>
                <TextField
                  color="secondary"
                  id="email"
                  variant="outlined"
                  style={{ width: "80%" }}
                  name="email"
                  label="Email Address"
                  value={values?.["email"] || ""}
                  onChange={(e) => setFieldValue("email", e.target.value)}
                />
              </Col>
            </Row>
            <Row className="pt-2">
              <Col>
                <TextField
                  color="secondary"
                  id="password"
                  variant="outlined"
                  style={{ width: "80%" }}
                  name="password"
                  label="Password"
                  type="password"
                  value={values?.["password"] || ""}
                  onChange={(e) => setFieldValue("password", e.target.value)}
                />
              </Col>
            </Row>
            <Row className="pt-2">
              <Col className="d-flex justify-content-center">
                <Button
                  color="secondary"
                  variant="contained"
                  style={{ textTransform: "none" }}
                  onClick={formProps.handleSubmit}
                >
                  Sign Up
                </Button>
              </Col>
            </Row>
            <Row className="pt-2">
              <Col className="d-flex justify-content-center">
                <div>Already have an account? </div>
                <Button
                  size="small"
                  color="secondary"
                  style={{ textTransform: "none" }}
                  onClick={() => setShowLoginForm(true)}
                >
                  Log In
                </Button>
              </Col>
            </Row>
          </Col>
        );
      }}
    </Formik>
  );
};

SignUpForm.propTypes = {
  setShowLoginForm: PropTypes.func,
  logInUser: PropTypes.func
};

SignUpForm.defaultProps = {
  setShowLoginForm: () => {},
  logInUser: () => {}
};

export default SignUpForm;
