import React from 'react';
import PropTypes from 'prop-types';
import * as uiTrace from '../../utils/ui_trace';

const ApplicationButton = ({ caption, iconClassNames, onClick: onClickParam }) => {
  const onClick = uiTrace.traceFunction(onClickParam, { eventName: 'buttonClick', caption, iconClassNames });

  return (
    <button className="button is-outlined complete-btn is-fullwidth" onClick={onClick}>
      <div className="full-size-btn">
        <div className="left-btn-side">
          <i className={iconClassNames} />
        </div>
        <div className="caption-btn">
          <div className="rows">
            <div className="row pl-5">{caption}</div>
          </div>
        </div>
      </div>
    </button>
  );
};

ApplicationButton.propTypes = {
  caption: PropTypes.string.isRequired,
  iconClassNames: PropTypes.string,
  onClick: PropTypes.func.isRequired,
};

export default ApplicationButton;
