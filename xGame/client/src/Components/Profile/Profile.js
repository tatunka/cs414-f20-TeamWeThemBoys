import React from "react";
import PropTypes from "prop-types";
import { Typography, Card, CardContent } from "@material-ui/core";

const Profile = (props) => {
  const { profileData } = props;

  const getMatchResult = (outcome, winningPlayer) => {
    switch (outcome) {
      case "VICTORY":
        return winningPlayer === profileData?.user ? "Victory" : "Defeat";
      case "FORFEIT":
        return winningPlayer === profileData?.user
          ? "Victory by forfeit"
          : "Defeat by forfeit";
      default:
        return "Draw";
    }
  };

  const MatchHistory = () => {
    if (profileData?.matchHistory) {
      return (
        <div>
          <h5>Match History:</h5>
          {profileData?.matchHistory.map((match) => {
            const matchResult = getMatchResult(
              match?.outcome,
              match?.winningPlayer
            );

            return (
              <Card key={match?.id}>
                <CardContent>
                  <Typography variant="h6" component="h2" align="left">
                    {matchResult}
                    {" against " + match?.opponentNickname}
                  </Typography>

                  <Typography color="textSecondary" align="left" gutterBottom>
                    {`after ${match.moveCount} moves.`}
                  </Typography>
                  <Typography color="textSecondary" align="left" gutterBottom>
                    {`You played as ${match.color.toLowerCase()}`}
                  </Typography>
                </CardContent>
              </Card>
            );
          })}
        </div>
      );
    } else {
      return <div>No completed matches</div>;
    }
  };

  return (
    <div>
      <h2>Player {profileData.user}</h2>
      <MatchHistory />
    </div>
  );
};

Profile.propTypes = {
  profileData: PropTypes.object
};

Profile.defaultProps = {
  profileData: {}
};

export default Profile;
