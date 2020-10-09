import React from "react";
import { Collapse } from "reactstrap";
import PropTypes from 'prop-types';

const Login = (props) => {
    const { isOpen } = props;

    return (
        <Collapse isOpen={isOpen}>
            <div>
                <p>add login data</p>
            </div>
        </Collapse>
    );
}

Login.propTypes = {
    isOpen: PropTypes.bool
}

Login.defaultProps = {
    isOpen: false
}

export default Login;