import React, { useCallback, useEffect, useState } from 'react';

import ScreenToaster from '../components/ScreenToaster';
import LogoHeader from '../components/LogoHeader';
import { getMobileConfiguration } from '../api/configuration';
import ButtonWithIndicator from '../components/buttons/ButtonWithIndicator';
import UserAndPassAuth from './authMethods/UserAndPassAuth';
import QrCodeAuth from './authMethods/QrCodeAuth';
import { trl } from '../utils/translations';
import { useLocation } from 'react-router-dom';
import { useAuth } from '../hooks/useAuth';
import { useUITraceLocationChange } from '../utils/ui_trace/useUITraceLocationChange';

const KNOWN_AUTH_METHODS = {
  QR_CODE: 'qrCode',
  USER_PASS: 'userAndPass',
};

const VIEW = {
  LOGIN: 'login',
  ALTERNATIVE_METHODS: 'alternativeMethods',
};

const LoginScreen = () => {
  const [currentAuthMethod, setCurrentAuthMethod] = useState(KNOWN_AUTH_METHODS.USER_PASS);
  const [availableAuthMethods, setAvailableAuthMethods] = useState([]);
  const [currentView, setCurrentView] = useState(VIEW.LOGIN);

  const auth = useAuth();
  const location = useLocation();
  const { from } = location.state || { from: { pathname: '/' } };

  const getAuthMethodScreen = useCallback(() => {
    if (currentAuthMethod === KNOWN_AUTH_METHODS.USER_PASS) {
      return <UserAndPassAuth />;
    } else if (currentAuthMethod === KNOWN_AUTH_METHODS.QR_CODE) {
      return <QrCodeAuth />;
    } else {
      console.log('Unknown auth method! Falling back to user and pass!');
      return <UserAndPassAuth />;
    }
  }, [UserAndPassAuth, QrCodeAuth, currentAuthMethod]);

  const getLoginScreen = useCallback(() => {
    return (
      <>
        {getAuthMethodScreen()}
        {availableAuthMethods && availableAuthMethods.length === 2 && (
          <div className="section is-size-5" style={{ paddingTop: 0 }}>
            <div className="container px-6">
              <ButtonWithIndicator
                caption={trl('login.alternativeMethods')}
                onClick={() => toggleAuthMethod(availableAuthMethods.find((method) => method !== currentAuthMethod))}
                additionalCssClass={'alternative-button'}
              />
            </div>
          </div>
        )}
        {availableAuthMethods && availableAuthMethods.length > 2 && (
          <div className="section is-size-5" style={{ paddingTop: 0 }}>
            <div className="container px-6">
              <ButtonWithIndicator
                caption={trl('login.alternativeMethods')}
                onClick={() => setCurrentView(VIEW.ALTERNATIVE_METHODS)}
                additionalCssClass={'alternative-button'}
              />
            </div>
          </div>
        )}
      </>
    );
  }, [ButtonWithIndicator, getAuthMethodScreen, availableAuthMethods, setCurrentView]);

  const toggleAuthMethod = useCallback(
    (method) => {
      setCurrentAuthMethod(method);
      setCurrentView(VIEW.LOGIN);
    },
    [setCurrentAuthMethod, setCurrentView]
  );

  const getAlternativeMethodsScreen = useCallback(() => {
    return (
      <div className="pt-3 section">
        {availableAuthMethods.map((method, index) => (
          <ButtonWithIndicator
            key={index}
            caption={trl(`login.authMethod.${method}`)}
            onClick={() => toggleAuthMethod(method)}
          />
        ))}
      </div>
    );
  }, [ButtonWithIndicator, availableAuthMethods, toggleAuthMethod]);

  useEffect(() => {
    if (auth.isLoggedIn()) {
      console.log(`LoginScreen: ALREADY LOGGED IN. Forwarding to `, from);
      history.replace(from);
    }
  }, []);

  useEffect(() => {
    document.title = 'mobile UI';
  }, []);

  useEffect(() => {
    getMobileConfiguration().then((config) => {
      setCurrentAuthMethod(KNOWN_AUTH_METHODS[config.defaultAuthMethod] || KNOWN_AUTH_METHODS.USER_PASS);

      const authMethods = config.availableAuthMethods
        .map((method) => KNOWN_AUTH_METHODS[method])
        .filter((method) => method !== null && method !== undefined);

      setAvailableAuthMethods(authMethods);
    });
  }, []);

  useUITraceLocationChange();

  return (
    <div className="login-view">
      <LogoHeader />
      {currentView === VIEW.LOGIN && getLoginScreen()}
      {currentView === VIEW.ALTERNATIVE_METHODS && getAlternativeMethodsScreen()}
      <ScreenToaster />
    </div>
  );
};

export default LoginScreen;
