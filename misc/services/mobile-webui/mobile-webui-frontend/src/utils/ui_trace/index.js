import axios from 'axios';
import { getOrCreateDeviceId, saveEvent } from './db';
import { v4 as uuidv4 } from 'uuid';
import { doFinally } from '../index';
import { useCallback } from 'react';

const HEADER_UITraceId = 'X-Ui-Trace-Id'; // important: keep this upper/lower case because that's how the header will be formated anyway
const PROP_applicationId = 'applicationId';
const PROP_User = 'uiTraceUser';

let funcRunningContext = null;
let modalDialog = {};

export const login = (properties) => {
  const userStr = sessionStorage.getItem(PROP_User);
  const user = userStr ? JSON.parse(userStr) : {};
  const userChanged = { ...user, ...properties };
  sessionStorage.setItem(PROP_User, JSON.stringify(userChanged));

  trace({ eventName: 'login' });
};

export const logout = () => {
  trace({ eventName: 'logout' });
  sessionStorage.removeItem(PROP_User);
};

const getUser = () => {
  let userStr = null;
  try {
    userStr = sessionStorage.getItem(PROP_User);
    return userStr ? JSON.parse(userStr) : {};
  } catch (error) {
    console.log('Failed converting user from sessionStorage', { error, userStr });
  }
};

export const trace = async (properties) => {
  const event = await createEvent(properties);

  // noinspection ES6MissingAwait
  saveEvent(event);

  return event;
};

export const traceFunction = (func, properties) => {
  if (!func) return func;

  return async (...args) => {
    const id = uuidv4();
    let propertiesEffective;
    if (typeof properties === 'function') {
      propertiesEffective = { id, ...properties(...args) };
    } else {
      propertiesEffective = { id, ...properties };
    }

    const prevId = setCurrentEventId(id);
    const prevRunningContext = funcRunningContext;

    funcRunningContext = prevRunningContext ? { ...prevRunningContext } : {};

    return doFinally(func(...args), () => {
      trace(propertiesEffective);

      setCurrentEventId(prevId);
      funcRunningContext = prevRunningContext;
    });
  };
};

export const useTraceCallback = (func, properties) => {
  return useCallback(traceFunction(func, properties), [func]);
};

const setCurrentEventId = (id) => {
  const prevId = axios.defaults.headers.common[HEADER_UITraceId];
  if (id) {
    axios.defaults.headers.common[HEADER_UITraceId] = id;
  } else {
    delete axios.defaults.headers.common[HEADER_UITraceId];
  }
  return prevId;
};

const createEvent = async (properties) => {
  const id = properties?.id ? properties.id : uuidv4();

  return {
    ...properties,
    ...funcRunningContext,
    id,
    timestamp: Date.now(),
    device: await getDeviceMetadata(),
    user: getUser(),
    page: {
      url: window?.location?.href,
      title: document.title,
      referrer: document?.referrer,
    },
    modalDialog,
    applicationId: sessionStorage.getItem(PROP_applicationId),
  };
};

const getDeviceMetadata = async () => {
  return {
    deviceId: await getOrCreateDeviceId(),
    tabId: getOrCreateTabId(),
    userAgent: navigator.userAgent,
    platform: navigator.platform,
    language: navigator.language,
    screenSize: `${window.screen.width}x${window.screen.height}`,
  };
};

const getOrCreateTabId = () => {
  let tabId = sessionStorage.getItem('tab_id');
  if (!tabId) {
    tabId = uuidv4();
    sessionStorage.setItem('tab_id', tabId);
  }
  return tabId;
};

export const setApplicationId = (applicationId) => {
  if (applicationId) {
    sessionStorage.setItem(PROP_applicationId, applicationId);
  } else {
    sessionStorage.removeItem(PROP_applicationId);
  }
  //console.log('setApplicationId', { applicationId, applicationId2: sessionStorage.getItem(PROP_applicationId) });
};

export const putContext = (properties) => {
  if (!properties) return;
  if (funcRunningContext == null) return;

  funcRunningContext = { ...funcRunningContext, ...properties };
};

export const setModalDialogName = (name) => {
  modalDialog.name = name;
};
