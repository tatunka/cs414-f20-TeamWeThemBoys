import React from "react";
import PropTypes from "prop-types";
import "./MarginStyle.css";
import { Button } from "@material-ui/core";
import UnregisterDialog from './UnregisterDialog';

const Header = (props) => {
  const { activeUser, setActiveUser } = props;
  const [showUnregisterConfirmation, setShowUnregisterConfirmation] = React.useState(false);

  return (
    <div className={"header"}>
      {activeUser.isLoggedIn ? (
        <div>
          <h5>Active User:</h5>
          <p>
            id: {activeUser?.id}, nickname: {activeUser?.nickname}, email:{" "}
            {activeUser?.email}
          </p>
          <Button
            variant={"contained"}
            onClick={() => setActiveUser({ isLoggedIn: false })}
          >
            Log Out
          </Button>
          <Button
            color="secondary"
            onClick={() => {setShowUnregisterConfirmation(true)} }
            size="small"
          >
            Deregister
          </Button>
          <UnregisterDialog showUnregisterConfirmation={showUnregisterConfirmation} setShowUnregisterConfirmation={setShowUnregisterConfirmation} />
        </div>
      ) : (
        <div>
          <h3>Please Login or Register</h3>
        </div>
      )}
    </div>
  );
};

Header.propTypes = {
  activeUser: PropTypes.object,
  setActiveUser: PropTypes.func,
};

Header.defaultProps = {
  activeUser: {},
};

export default Header;
