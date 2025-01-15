import React, { useState } from 'react';
import PropTypes from 'prop-types';

import { trl } from '../../utils/translations';

import QtyInputField from '../QtyInputField';
import { qtyInfos } from '../../utils/qtyInfos';
import { toastError } from '../../utils/toast';
import DialogButton from './DialogButton';
import Dialog from './Dialog';
import * as uiTrace from '../../utils/ui_trace';

const ReadQtyDialog = ({
  qtyLabelTrlKey,
  submitButtonTrlKey,
  cancelButtonTrlKey,
  isReadOnly = false,
  //
  onSubmit,
  onCloseDialog,
}) => {
  const [nrOfCopies, setNrOfCopies] = useState(qtyInfos.invalidOfNumber(1));

  const onQtyEntered = (qtyInfo) => setNrOfCopies(qtyInfo);

  const readQty = () => {
    if (!nrOfCopies || !nrOfCopies.isQtyValid) {
      toastError({ plainMessage: 'Invalid number' });
      return;
    }

    const submitRequest = {
      qty: qtyInfos.toNumberOrString(nrOfCopies),
    };
    uiTrace.putContext(submitRequest);
    onSubmit(submitRequest);
  };

  return (
    <div>
      <Dialog className="get-qty-dialog">
        <table className="table">
          <tbody>
            <tr>
              <th>{trl(qtyLabelTrlKey || 'general.Qty')}</th>
              <td>
                <QtyInputField
                  readonly={isReadOnly}
                  qty={qtyInfos.toNumberOrString(nrOfCopies)}
                  onQtyChange={onQtyEntered}
                  isRequestFocus={true}
                />
              </td>
            </tr>
          </tbody>
        </table>
        <div className="buttons is-centered">
          <DialogButton
            captionKey={cancelButtonTrlKey || 'general.cancelText'}
            disabled={isReadOnly}
            className="is-danger"
            onClick={onCloseDialog}
          />
          <DialogButton
            captionKey={submitButtonTrlKey || 'general.okText'}
            disabled={isReadOnly}
            className="is-success"
            onClick={readQty}
          />
        </div>
      </Dialog>
    </div>
  );
};

ReadQtyDialog.propTypes = {
  qtyLabelTrlKey: PropTypes.string,
  submitButtonTrlKey: PropTypes.string,
  cancelButtonTrlKey: PropTypes.string,
  isReadOnly: PropTypes.bool,
  // Callbacks
  onCloseDialog: PropTypes.func,
  onSubmit: PropTypes.func.isRequired,
};

export default ReadQtyDialog;
