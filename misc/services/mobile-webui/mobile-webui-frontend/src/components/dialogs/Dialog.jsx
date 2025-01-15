import React from 'react';
import cx from 'classnames';
import PropTypes from 'prop-types';
import useConstructor from '../../hooks/useConstructor';
import * as uiTrace from '../../utils/ui_trace';

const Dialog = ({ children, className }) => {
  useConstructor(() => {
    uiTrace.trace({ eventName: 'dialogOpen', className });
  });

  return (
    <div className={cx('prompt-dialog', className)}>
      <article className="message is-dark">
        <div className="message-body">{children}</div>
      </article>
    </div>
  );
};

Dialog.propTypes = {
  children: PropTypes.oneOfType([PropTypes.arrayOf(PropTypes.node), PropTypes.node]),
  className: PropTypes.string,
};

export default Dialog;
