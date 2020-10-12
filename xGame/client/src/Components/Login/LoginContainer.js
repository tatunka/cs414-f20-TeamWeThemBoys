import React, { useState } from "react";
import { makeStyles } from "@material-ui/core/styles";
import PropTypes from "prop-types";
import { Row, Col } from "reactstrap";
import Card from "@material-ui/core/Card";
import CardActionArea from "@material-ui/core/CardActionArea";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import CardMedia from "@material-ui/core/CardMedia";
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";

import LoginForm from "./LoginForm";
import SignUpForm from "./SignUpForm";

const useStyles = makeStyles({
  root: {
    maxWidth: 400
  },
  media: {
    height: 400
  }
});

const LoginContainer = (props) => {
  const [showLoginForm, setShowLoginForm] = useState(true);

  const classes = useStyles();

  return (
    <Col className="d-flex justify-content-center">
      <Card className={classes.root}>
        <CardMedia
          className={classes.media}
          image={require("../../data/images/LeganChessStartingPosition.png")}
          title="Legan Chess Starting Position"
        />
        <CardContent>
          <Typography gutterBottom variant="h4">
            Legan Chess Online
          </Typography>
          <Typography
            gutterBottom
            variant="body2"
            color="textSecondary"
            component="p"
          >
            Legan Chess is a chess variant with its main differences being the
            start position (shown above) and pawn movement. Pawns move
            diagonally and capture orthogonally. There is no castling and no en
            passant, but otherwise standard chess rules apply.
          </Typography>
          <Typography variant="body1" color="textSecondary" component="p">
            Log in to play now!
          </Typography>
        </CardContent>
        {showLoginForm ? (
          <LoginForm setShowLoginForm={setShowLoginForm} />
        ) : (
          <SignUpForm setShowLoginForm={setShowLoginForm} />
        )}
      </Card>
    </Col>
  );
};

LoginContainer.propTypes = {
  isOpen: PropTypes.bool
};

LoginContainer.defaultProps = {
  isOpen: false
};

export default LoginContainer;
