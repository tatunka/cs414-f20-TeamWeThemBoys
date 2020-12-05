/**
 * @module messageService
 * @description exports functions for interacting with the backend message service
 */
import * as utils from "../utils/utilsCommon";
import { messageServiceURL } from "../config/urlConfig";

export const getMessages = (playerId) => {
  const url = `${messageServiceURL}?playerId=${playerId}`;

  return utils
    .basicAPI(url, "Get Messages", {
      method: "GET"
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

export const readAllMessages = (playerId) => {
  const url = `${messageServiceURL}s?playerId=${playerId}`;

  return utils
    .basicAPI(url, "Read All Messages", {
      method: "PATCH"
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

export const readMessage = (messageId) => {
  const url = `${messageServiceURL}?messageId=${messageId}`;

  return utils
    .basicAPI(url, "Read One Message", {
      method: "PATCH"
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
