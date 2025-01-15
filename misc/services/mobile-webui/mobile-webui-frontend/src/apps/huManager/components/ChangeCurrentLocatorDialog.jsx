import { trl } from '../../../utils/translations';
import React from 'react';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import { parseLocatorQRCodeString } from '../../../utils/qrCode/locator';
import PropTypes from 'prop-types';
import DialogButton from '../../../components/dialogs/DialogButton';
import Dialog from '../../../components/dialogs/Dialog';

const ChangeCurrentLocatorDialog = ({ onOK, onClose }) => {
  return (
    <div>
      <Dialog className="screen">
        <BarcodeScannerComponent
          continuousRunning={true}
          inputPlaceholderText={trl('huManager.locator')}
          onResolvedResult={({ scannedBarcode }) => {
            const locatorQRCode = parseLocatorQRCodeString(scannedBarcode);
            onOK(locatorQRCode);
          }}
        />
        <div className="buttons is-centered">
          <DialogButton captionKey="general.cancelText" className="is-danger" onClick={onClose} />
        </div>
      </Dialog>
    </div>
  );
};

ChangeCurrentLocatorDialog.propTypes = {
  onOK: PropTypes.func.isRequired,
  onClose: PropTypes.func.isRequired,
};

export default ChangeCurrentLocatorDialog;
