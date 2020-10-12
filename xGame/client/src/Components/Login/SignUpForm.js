import React from "react";
import PropTypes from "prop-types";
import { Row, Col } from "reactstrap";
import { Formik } from "formik";
import { Button, TextField } from "@material-ui/core";

const SignUpForm = (props) => {
  const { setShowLoginForm } = props;

  return (
    <Formik
      onSubmit={(values) => {
        console.log("TODO: Hook up registration API", values);
      }}
      initialValues={{
        email: "",
        username: "",
        password: ""
      }}
    >
      {(formProps) => {
        const { setFieldValue, values } = formProps;
        return (
          <Col>
            <Row>
              <Col>
                <TextField
                  color="secondary"
                  id="username"
                  variant="outlined"
                  style={{ width: "80%" }}
                  name="username"
                  label="Username"
                  type="username"
                  value={values?.["username"] || ""}
                  onChange={(e) => setFieldValue("username", e.target.value)}
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
  setShowLoginForm: PropTypes.func
};

SignUpForm.defaultProps = {
  setShowLoginForm: () => {}
};

export default SignUpForm;
