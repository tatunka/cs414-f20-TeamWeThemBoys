import React from "react";
import { Collapse } from "reactstrap";
import PropTypes from "prop-types";

const Profile = (props) => {
  const { isOpen } = props;

  return (
    <Collapse isOpen={isOpen}>
      <div>
        <p>add profile data</p>
      </div>
    </Collapse>
  );
};

Profile.propTypes = {
  isOpen: PropTypes.bool
};

Profile.defaultProps = {
  isOpen: true
};

export default Profile;
