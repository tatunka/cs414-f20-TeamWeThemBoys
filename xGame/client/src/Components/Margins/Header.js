import React, { useState } from "react";
import PropTypes from "prop-types";
import "./MarginStyle.css";
import {
  Button,
  AppBar,
  Toolbar,
  IconButton,
  Typography,
  makeStyles,
  Drawer,
  Card,
  CardContent
} from "@material-ui/core";
import NotificationsIcon from "@material-ui/icons/Notifications";
import AccountCircle from "@material-ui/icons/AccountCircle";
import ChevronLeftIcon from "@material-ui/icons/ChevronLeft";
import ChevronRightIcon from "@material-ui/icons/ChevronRight";

import Profile from "../Profile/Profile";
import UnregisterDialog from "./UnregisterDialog";

const useStyles = makeStyles((theme) => ({
  grow: {
    flexGrow: 1
  },
  logout: {
    color: "#ffffff",
    textTransform: "inherit"
  },
  drawerPaper: {
    width: 240
  },
  notificationsDrawerHeader: {
    display: "flex",
    alignItems: "center",
    padding: theme.spacing(0, 1),
    // necessary for content to be below app bar
    ...theme.mixins.toolbar,
    justifyContent: "flex-end"
  },
  profileDrawerHeader: {
    display: "flex",
    alignItems: "center",
    padding: theme.spacing(0, 1),
    // necessary for content to be below app bar
    ...theme.mixins.toolbar,
    justifyContent: "flex-start"
  },
  notificationTitle: {
    fontSize: 14
  }
}));

const Header = (props) => {
  const { activeUser, logOutUser } = props;

  const [showNotifications, setShowNotifications] = useState(false);
  const [showProfile, setShowProfile] = useState(false);
  const [
    showUnregisterConfirmation,
    setShowUnregisterConfirmation
  ] = React.useState(false);

  const classes = useStyles();

  const Notifications = () => {
    if (activeUser?.notifications) {
      return (
        <div>
          {activeUser.notifications.map((notification) => {
            return (
              <Card key={notification?.id}>
                <CardContent>
                  <Typography
                    className={classes.notificationTitle}
                    color="textSecondary"
                    align="left"
                    gutterBottom
                  >
                    Message
                  </Typography>
                  <Typography variant="h5" component="h2" align="left">
                    {notification?.content}
                  </Typography>
                </CardContent>
              </Card>
            );
          })}
        </div>
      );
    }
    return <div>test</div>;
  };

  return (
    <div className={"header"}>
      {activeUser.isLoggedIn ? (
        <>
          <AppBar position="static">
            <Toolbar>
              <IconButton
                color="inherit"
                onClick={() => setShowNotifications(!showNotifications)}
              >
                <NotificationsIcon />
              </IconButton>
              <div className={classes.grow}></div>
              <Typography variant="h6">Legan Chess Online</Typography>
              <div className={classes.grow}></div>
              <Button onClick={logOutUser} className={classes.logout}>
                Log out
              </Button>
              <IconButton
                color="inherit"
                onClick={() => {
                  setShowProfile(!showProfile);
                }}
              >
                <AccountCircle />
              </IconButton>
            </Toolbar>
          </AppBar>
          <Drawer
            variant="persistent"
            anchor="left"
            open={showNotifications}
            classes={{ paper: classes.drawerPaper }}
          >
            <div className={classes.notificationsDrawerHeader}>
              <Typography variant="h6">Notifications</Typography>
              <IconButton onClick={() => setShowNotifications(false)}>
                <ChevronLeftIcon />
              </IconButton>
            </div>
            <Notifications />
          </Drawer>
          <Drawer
            variant="persistent"
            anchor="right"
            open={showProfile}
            classes={{ paper: classes.drawerPaper }}
          >
            <div className={classes.profileDrawerHeader}>
              <IconButton onClick={() => setShowProfile(false)}>
                <ChevronRightIcon />
              </IconButton>
              <Typography variant="h6" className="pr-2">
                Profile
              </Typography>
            </div>
            <Button variant="contained" onClick={logOutUser}>
              Log out
            </Button>
            <Button
              color="secondary"
              onClick={() => {
                setShowUnregisterConfirmation(true);
              }}
              size="small"
            >
              Deregister
            </Button>
            <Profile />
          </Drawer>
          <UnregisterDialog
            showUnregisterConfirmation={showUnregisterConfirmation}
            setShowUnregisterConfirmation={setShowUnregisterConfirmation}
          />
        </>
      ) : (
        <h3>Please Login or Register</h3>
      )}
    </div>
  );
};

Header.propTypes = {
  activeUser: PropTypes.object,
  logOutUser: PropTypes.func
};

Header.defaultProps = {
  activeUser: {},
  logOutUser: () => {}
};

export default Header;
