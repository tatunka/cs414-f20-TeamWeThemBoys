import React from "react";
import { Collapse } from "reactstrap";
import PropTypes from "prop-types";
import { Button, Paper, Container } from "@material-ui/core";
import {
  List,
  ListItem,
  ListItemSecondaryAction,
  ListItemText
} from "@material-ui/core";
import MatchCreateDialog from "./MatchCreateDialog";
import IconButton from "@material-ui/core/IconButton";
import PlayIcon from "@material-ui/icons/PlayCircleOutline";
import { useEffect, useState } from "react";
import { Alert } from "@material-ui/lab";
import { Formik } from "formik";

import * as matchService from "../../service/matchService";

const MatchSelect = (props) => {
  const { isOpen, activeUser, activeMatch, setActiveMatch } = props;
  const [showMatchCreation, setShowMatchCreation] = React.useState(false);
  const [selectError, setSelectError] = useState("");
  var [matchList, setMatchList] = useState([]);

  useEffect(() => {
    async function fetchData() {
      setMatchList(await matchService.getActiveMatches(activeUser?.id));
      if (matchList?.error) setSelectError(matchList?.message);
    }
    fetchData();
  }, [activeUser]);

  return (
    <Collapse isOpen={isOpen}>
      <div>
        <br />
        {selectError && (
          <Alert
            onClose={() => {
              setSelectError("");
            }}
            severity="error"
          >
            {selectError}
          </Alert>
        )}
        <Container maxWidth="sm">
          <Paper elevation={0} style={{ maxHeight: 200, overflow: "auto" }}>
            <List dense={true}>
              {matchList &&
                matchList?.map((match) => {
                  const opponentName =
                    match?.whitePlayerNickname === activeUser?.nickname
                      ? match?.blackPlayerNickname
                      : match?.whitePlayerNickname;
                  return (
                    <ListItem key={match?.id}>
                      <ListItemText
                        primary={`Match against ${opponentName}`}
                        secondary={`Turn count: ${match?.turnCount}, ID: ${match?.id}`}
                      />
                      <ListItemSecondaryAction>
                        <IconButton
                          type="submit"
                          onClick={() => setActiveMatch(match)}
                          edge="end"
                          aria-label="play"
                        >
                          <PlayIcon />
                        </IconButton>
                      </ListItemSecondaryAction>
                    </ListItem>
                  );
                })}
            </List>
          </Paper>
        </Container>

        <Button
          variant="outlined"
          color="primary"
          onClick={() => {
            setShowMatchCreation(true);
          }}
        >
          Create a Match
        </Button>

        <MatchCreateDialog
          showMatchCreation={showMatchCreation}
          setShowMatchCreation={setShowMatchCreation}
          activeUser={activeUser}
        />
      </div>
    </Collapse>
  );
};

MatchSelect.propTypes = {
  activeUser: PropTypes.object,
  isOpen: PropTypes.bool,
  setActiveMatch: PropTypes.func,
  activeMatch: PropTypes.shape({
    id: PropTypes.number,
    whitePlayerId: PropTypes.number,
    blackPlayerId: PropTypes.number,
    whitePlayerNickname: PropTypes.string,
    blackPlayerNickname: PropTypes.string,
    turnCount: PropTypes.number,
    chessBoard: PropTypes.array
  })
};

MatchSelect.defaultProps = {
  activeUser: {},
  isOpen: true,
  setActiveMatch: () => {},
  activeMatch: {
    id: 0,
    whitePlayerId: 0,
    blackPlayerId: 0,
    whitePlayerNickname: "",
    blackPlayerNickname: "",
    turnCount: 0,
    chessBoard: []
  }
};

export default MatchSelect;
