/**
 * @module userService
 * @description exports functions for interacting with the backend user service
 */
import * as utils from "../utils/utilsCommon";
import { messageServiceURL } from "../config/urlConfig";

export const getMessages = (playerId) => {
  const url = `${messageServiceURL}?playerId=${playerId}`;

  return utils
    .basicAPI(url, "Get Messages", {
      method: "GET",
      body: {}
    })
    .then((response) => {
      if (response) {
        return response;
      }
      return false;
    })
    .catch((error) => {
      console.error("Error:", error);
      return false;
    });
};
