import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';
import { checkLoginRequest } from '../../api/login';
import { CONNECTION_ERROR_RETRY_INTERVAL_MILLIS } from '../../constants/Constants';
import { getCurrentActiveLanguage } from '../../utils/locale';

const ErrorScreen = ({ errorType }) => {
  const [intervalId, setIntervalId] = useState(null);
  const [activeLang, setActiveLang] = useState(getCurrentActiveLanguage());

  const pingServer = async () => {
    try {
      const response = await checkLoginRequest();
      if (response && response.status === 200) {
        window.location.reload();
      }
    } catch (e) {
      console.error('checkLoginRequest error:', e);
    }
  };

  useEffect(() => {
    if (errorType) {
      const id = setInterval(pingServer, CONNECTION_ERROR_RETRY_INTERVAL_MILLIS);
      setIntervalId(id);
    }

    return () => {
      if (intervalId) {
        clearInterval(intervalId);
      }
    };
  }, [errorType, intervalId]);

  useEffect(() => {
    setActiveLang(getCurrentActiveLanguage());
  }, []);

  const title = counterpart.translate(`window.error.${errorType}.title`, { lang: activeLang });
  const description = counterpart.translate(`window.error.${errorType}.description`, { lang: activeLang });

  return (
    <div className="screen-freeze">
      <h3>{title}</h3>
      <p>{description}</p>
    </div>
  );
};

ErrorScreen.propTypes = {
  errorType: PropTypes.string,
};

export default ErrorScreen;
