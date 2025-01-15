import { useHistory, useRouteMatch } from 'react-router-dom';
import { ViewHeader } from '../ViewHeader';
import ScreenToaster from '../../components/ScreenToaster';
import React, { useEffect } from 'react';
import { useSelector } from 'react-redux';
import PropTypes from 'prop-types';
import { getCaptionFromHeaders, useHomeLocation } from '../../reducers/headers';
import { isWfProcessLoaded } from '../../reducers/wfProcesses';
import { trl } from '../../utils/translations';
import { useApplicationInfo } from '../../reducers/applications';
import { isApplicationFullScreen } from '../../apps';
import { useUITraceLocationChange } from '../../utils/ui_trace/useUITraceLocationChange';
import * as uiTrace from '../../utils/ui_trace';

export const ApplicationLayout = ({ applicationId, Component }) => {
  const history = useHistory();

  //
  // If the required process was not loaded,
  // then redirect to home
  const redirectToHome = isWFProcessRequiredButNotLoaded();
  useEffect(() => {
    if (redirectToHome) {
      history.push('/');
    }
  }, [redirectToHome]);

  const applicationInfo = useApplicationInfo({ applicationId });
  const homeLocation = useHomeLocation();

  const captionFromHeaders = useSelector((state) => getCaptionFromHeaders(state));
  const caption = captionFromHeaders ? captionFromHeaders : applicationInfo.caption;

  useEffect(() => {
    document.title = caption;
  }, [caption]);

  useUITraceLocationChange();

  if (redirectToHome) {
    return null;
  }

  if (isApplicationFullScreen(applicationId)) {
    return (
      <div className="app-container app-container-fullscreen">
        <Component />
        <ScreenToaster />
      </div>
    );
  }

  return (
    <div className="app-container">
      <div className="app-header">
        <div className="columns is-mobile">
          <div className="column is-2 app-icon">
            <span className="icon">
              <i className={applicationInfo.iconClassNames} />
            </span>
          </div>
          <div className="column is-10 app-caption">
            <span>{caption}</span>
          </div>
        </div>
      </div>
      <div className="app-content">
        <ViewHeader />
        <Component />
        <ScreenToaster />
      </div>
      <div className="app-footer">
        <div className="columns is-mobile">
          <div className="column is-half">
            <BottomButton captionKey="general.Back" icon="fas fa-chevron-left" onClick={() => history.goBack()} />
          </div>
          <div className="column is-half">
            <BottomButton
              captionKey="general.Home"
              icon={homeLocation.iconClassName}
              onClick={() => history.push(homeLocation.location)}
            />
          </div>
        </div>
      </div>
    </div>
  );
};

ApplicationLayout.propTypes = {
  applicationId: PropTypes.string,
  Component: PropTypes.any.isRequired,
};

const BottomButton = ({ captionKey, icon, onClick: onClickParam }) => {
  const caption = trl(captionKey);
  const onClick = uiTrace.traceFunction(onClickParam, { eventName: 'buttonClick', captionKey, caption, icon });

  return (
    <button className="button is-fullwidth" onClick={onClick}>
      <span className="icon">
        <i className={icon} />
      </span>
      <span>{caption}</span>
    </button>
  );
};
BottomButton.propTypes = {
  captionKey: PropTypes.string.isRequired,
  icon: PropTypes.string.isRequired,
  onClick: PropTypes.func.isRequired,
};

const isWFProcessRequiredButNotLoaded = () => {
  const { params } = useRouteMatch();
  const wfProcessId = params.wfProcessId || params.workflowId;
  if (!wfProcessId) {
    return false;
  }

  const isWFProcessLoaded = useSelector((state) => isWfProcessLoaded(state, wfProcessId));
  return !isWFProcessLoaded;
};
