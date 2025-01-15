import React, { useCallback } from 'react';
import PropTypes from 'prop-types';

import { trl } from '../../../utils/translations';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import DialogButton from '../../../components/dialogs/DialogButton';
import Dialog from '../../../components/dialogs/Dialog';

const UnpickDialog = ({ onSubmit, onCloseDialog }) => {
  const onResolvedQrCode = useCallback(
    ({ scannedBarcode }) => {
      onSubmit({ unpickToTargetQRCode: scannedBarcode });
    },
    [onSubmit]
  );

  return (
    <Dialog className="screen">
      <table className="table">
        <tbody>
          <tr>
            <td colSpan="2">
              <BarcodeScannerComponent
                inputPlaceholderText={trl('activities.picking.scanTargetHU')}
                onResolvedResult={onResolvedQrCode}
              />
            </td>
          </tr>
        </tbody>
      </table>
      <div className="buttons is-centered">
        <DialogButton captionKey="activities.picking.skip" onClick={onSubmit} />
        <DialogButton captionKey="general.closeText" className="is-danger" onClick={onCloseDialog} />
      </div>
    </Dialog>
  );
};

UnpickDialog.propTypes = {
  // Callbacks
  onSubmit: PropTypes.func.isRequired,
  onCloseDialog: PropTypes.func.isRequired,
};

export default UnpickDialog;
