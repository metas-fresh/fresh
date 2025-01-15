import PropTypes from 'prop-types';
import React from 'react';

import { trl } from '../../utils/translations';
import UserInstructions from '../UserInstructions';
import DialogButton from './DialogButton';
import Dialog from './Dialog';

const YesNoDialog = ({ promptQuestion, userInstructions, onYes, onNo }) => {
  const promptQuestionEffective = promptQuestion
    ? promptQuestion
    : trl('activities.confirmButton.default.promptQuestion');

  return (
    <Dialog className="yes-no-dialog">
      <strong>{promptQuestionEffective}</strong>
      <UserInstructions text={userInstructions} />

      <div className="buttons is-centered">
        <DialogButton captionKey="activities.confirmButton.default.yes" className="is-success" onClick={onYes} />
        <DialogButton captionKey="activities.confirmButton.default.no" className="is-danger" onClick={onNo} />
      </div>
    </Dialog>
  );
};

YesNoDialog.propTypes = {
  promptQuestion: PropTypes.string,
  userInstructions: PropTypes.string,
  onYes: PropTypes.func.isRequired,
  onNo: PropTypes.func.isRequired,
};

export default YesNoDialog;
