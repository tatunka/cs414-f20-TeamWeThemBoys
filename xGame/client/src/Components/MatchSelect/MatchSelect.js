import React from "react";
import { Collapse } from "reactstrap";
import PropTypes from "prop-types";
import { Button, Paper, Container } from '@material-ui/core';
import { List, ListItem, ListItemSecondaryAction, ListItemText } from '@material-ui/core';
import MatchCreateDialog from './MatchCreateDialog';
import IconButton from '@material-ui/core/IconButton';
import PlayIcon from '@material-ui/icons/PlayCircleOutline';
import { useEffect, useState } from "react";
import { Alert } from "@material-ui/lab";
import { Formik } from "formik";

import * as matchService from "../../service/matchService";

const MatchSelect = (props) => {
  const { isOpen, activeUser, setActiveMatch } = props;
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
          <Paper elevation={0} style={{maxHeight: 200, overflow: 'auto'}}>
            <List dense={true}>
              {matchList && matchList?.map((X) => {
                return (
                  <ListItem key={X?.id}>
                    <ListItemText
                      primary={`Player ${X?.whitePlayerNickname} vs Player ${X?.blackPlayerNickname}`}
                      secondary={`Match ID is ${X?.id} on turn ${X?.turnCount}`}
                    />
                    <ListItemSecondaryAction>
                      <IconButton
                        type="submit"
                        //onClick={(e) => setFieldValue("selectedMatch", e.target.key)}
                        onClick={() => setActiveMatch(X)}
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

        <Button variant="outlined" color="primary" onClick={() => {setShowMatchCreation(true)} }>
                Create a Match
        </Button>

        <MatchCreateDialog showMatchCreation={showMatchCreation} setShowMatchCreation={setShowMatchCreation} activeUser={activeUser} />

      </div>
    </Collapse>
  );
};

MatchSelect.propTypes = {
  activeUser: PropTypes.object,
  isOpen: PropTypes.bool,
  setActiveMatch: PropTypes.func
};

MatchSelect.defaultProps = {
  activeUser: {},
  isOpen: true,
  setActiveMatch: () => {}
};

export default MatchSelect;
