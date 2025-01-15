import * as uiTrace from '../../utils/ui_trace';
import cx from 'classnames';
import { trl } from '../../utils/translations';
import React from 'react';
import PropTypes from 'prop-types';

const DialogButton = ({ captionKey, caption: captionParam, className, disabled, onClick }) => {
  let caption = '';
  if (captionParam) {
    caption = captionParam;
  } else if (captionKey) {
    caption = trl(captionKey);
  }

  const fireOnClick = uiTrace.traceFunction(onClick, { eventName: 'dialogButtonClick', captionKey, caption });

  return (
    <button className={cx('button', className)} disabled={disabled} onClick={fireOnClick}>
      {caption}
    </button>
  );
};

DialogButton.propTypes = {
  captionKey: PropTypes.string.isRequired,
  caption: PropTypes.string,
  className: PropTypes.string,
  disabled: PropTypes.bool,
  onClick: PropTypes.func.isRequired,
};

export default DialogButton;
