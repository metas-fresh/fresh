import React, { createContext, useContext } from 'react';
import { useDispatch, useStore } from 'react-redux';
import PropTypes from 'prop-types';
import axios from 'axios';
import Cookies from 'js-cookie';

import { loginByQrCode, loginRequest, logoutRequest } from '../api/login';
import { COOKIE_EXPIRATION } from '../constants/Cookie';
import { clearToken, setToken } from '../actions/TokenActions';
import { setLanguage } from '../utils/translations';
import { getIsLoggedInFromState, getTokenFromState } from '../reducers/appHandler';
import * as uiTrace from '../utils/ui_trace/';

const authContext = createContext();

// Provider component that wraps your app and makes auth object ...
// ... available to any child component that calls useAuth().
export function ProvideAuth({ children }) {
  const auth = createAuthObject();

  //
  // If there is no token in the store but we have one in the cookie,
  // then use the cookied and "login" the store.
  // This is useful in order to not lose the token while browser refreshing (F5).
  const token = auth.userToken();
  if (!token) {
    const tokenFromCookie = Cookies.get('Token');
    const languageFromCookie = Cookies.get('Language');
    const userFullnameFromCookies = Cookies.get('UserFullname');
    if (tokenFromCookie) {
      auth.loginByToken({
        token: tokenFromCookie,
        language: languageFromCookie,
        userFullname: userFullnameFromCookies,
      });
      console.log('ProvideAuth: Logged in by token from cookie');
    }
  }

  return <authContext.Provider value={auth}>{children}</authContext.Provider>;
}

ProvideAuth.propTypes = {
  children: PropTypes.oneOfType([PropTypes.arrayOf(PropTypes.node), PropTypes.node]).isRequired,
};
/**
 * @file React hook, that provides components with the authentication abject and
 * re-renders them when authentication changes
 */
export const useAuth = () => {
  return useContext(authContext);
};

// Provider hook that creates auth object and handles state
function createAuthObject() {
  const store = useStore();
  const dispatch = useDispatch();

  const loginByToken = async ({ token, language, userFullname }) => {
    if (language) {
      setLanguage(language);
      Cookies.set('Language', language, { expires: COOKIE_EXPIRATION });
    }

    dispatch(setToken({ token, userFullname }));
    Cookies.set('Token', token, { expires: COOKIE_EXPIRATION });
    Cookies.set('UserFullname', userFullname, { expires: COOKIE_EXPIRATION });

    axios.defaults.headers.common['Authorization'] = token;
    if (language) {
      axios.defaults.headers.common['Accept-Language'] = language;
    }
  };

  const login = (username, password) => {
    return loginRequest(username, password)
      .then(async ({ error, token, language, userFullname, userId }) => {
        if (error) {
          return Promise.reject(error);
        } else {
          await loginByToken({ token, language, userFullname });
          uiTrace.login({ userId, username, userFullname, language, method: 'password' });
          return Promise.resolve();
        }
      })
      .catch((error) => {
        console.error('login error: ', error);
        return Promise.reject(error);
      });
  };

  const qrLogin = (qrCode) => {
    return loginByQrCode(qrCode)
      .then(async ({ error, token, language, userFullname, userId, username }) => {
        if (error) {
          return Promise.reject(error);
        } else {
          await loginByToken({ token, language, userFullname });
          uiTrace.login({ userId, username, userFullname, language, method: 'qrCode' });
          return Promise.resolve();
        }
      })
      .catch((error) => {
        console.error('login error: ', error);
        return Promise.reject(error);
      });
  };

  const logout = () => {
    uiTrace.logout();

    logoutRequest().catch((error) => {
      console.error('logout error: ', error);
    });

    dispatch(clearToken());

    Cookies.remove('Token', { expires: COOKIE_EXPIRATION });
    Cookies.remove('UserFullname', { expires: COOKIE_EXPIRATION });

    delete axios.defaults.headers.common['Authorization'];

    return Promise.resolve();
  };

  const getToken = () => getTokenFromState(store.getState());

  const isLoggedIn = () => getIsLoggedInFromState(store.getState());

  return {
    userToken: getToken,
    isLoggedIn,
    isLoggedInNow: isLoggedIn,
    login,
    loginByToken,
    logout,
    qrLogin,
  };
}
