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
  }
}));

const Header = (props) => {
  const { activeUser, logOutUser } = props;

  const [showNotifications, setShowNotifications] = useState(false);
  const [showProfile, setShowProfile] = useState(false);

  const Notifications = () => {
    if (activeUser?.notifications) {
      return (
        <div>
          {activeUser.notifications.map((notification) => {
            return (
              <Card key={notification?.id}>
                <CardContent>{notification?.content}</CardContent>
              </Card>
            );
          })}
        </div>
      );
    }
    return <div>test</div>;
  };

  const classes = useStyles();
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
            <Profile />
          </Drawer>
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
