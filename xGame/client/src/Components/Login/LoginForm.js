import React from "react";
import PropTypes from "prop-types";
import { Row, Col } from "reactstrap";
import { Formik } from "formik";
import { Button, TextField } from "@material-ui/core";

const LoginForm = (props) => {
  const { setShowLoginForm } = props;

  return (
    <Formik
      onSubmit={(values) => {
        console.log("TODO: Hook up registration API", values);
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
  setShowLoginForm: PropTypes.func
};

LoginForm.defaultProps = {
  setShowLoginForm: () => {}
};

export default LoginForm;
