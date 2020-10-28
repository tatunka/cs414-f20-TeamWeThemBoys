import React, { useState } from "react";
import PropTypes from "prop-types";
import { Row, Col } from "reactstrap";
import { Formik } from "formik";
import { Button, TextField } from "@material-ui/core";
import { Alert } from "@material-ui/lab";

import * as userService from "../../service/userService";

const LoginForm = (props) => {
  const { setShowLoginForm, setActiveUser } = props;

  const [loginError, setLoginError] = useState("");

  return (
    <Formik
      onSubmit={async (values) => {
        const user = await userService.login(values);
        if (user?.error) setLoginError(user?.message);
        else setActiveUser({ ...user, isLoggedIn: true });
      }}
      initialValues={{
        email: "",
        password: ""
      }}
    >
      {(formProps) => {
        const { setFieldValue, values } = formProps;
        return (
          <Col>
            <Row className="pb-3">
              <Col>
                {loginError && (
                  <Alert
                    onClose={() => {
                      setLoginError("");
                    }}
                    severity="error"
                  >
                    {loginError}
                  </Alert>
                )}
              </Col>
            </Row>
            <Row>
              <Col>
                <TextField
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
                  color="primary"
                  variant="contained"
                  style={{ textTransform: "none" }}
                  onClick={formProps.handleSubmit}
                >
                  Log In
                </Button>
              </Col>
            </Row>
            <Row className="pt-2">
              <Col className="d-flex justify-content-center">
                <div>Don't have an account? </div>
                <Button
                  size="small"
                  color="primary"
                  style={{ textTransform: "none" }}
                  onClick={() => setShowLoginForm(false)}
                >
                  Sign up
                </Button>
              </Col>
            </Row>
          </Col>
        );
      }}
    </Formik>
  );
};

LoginForm.propTypes = {
  setShowLoginForm: PropTypes.func,
  setActiveUser: PropTypes.func
};

LoginForm.defaultProps = {
  setShowLoginForm: () => {},
  setActiveUser: () => {}
};

export default LoginForm;
