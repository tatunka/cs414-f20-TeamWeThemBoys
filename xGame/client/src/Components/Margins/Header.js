import React from "react";
import PropTypes from "prop-types";

const Header = (props) => {
  const { activeUser } = props;
  return (
    <div>
      <h5>Active User:</h5>
      <p>
        id: {activeUser?.id}, nickname: {activeUser?.nickname}, email:{" "}
        {activeUser?.email}
      </p>
    </div>
  );
};

Header.propTypes = {
  activeUser: PropTypes.object
};

Header.defaultProps = {
  activeUser: {}
};

export default Header;
