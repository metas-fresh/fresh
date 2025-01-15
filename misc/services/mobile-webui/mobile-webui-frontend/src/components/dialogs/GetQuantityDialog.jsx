import React, { useCallback, useEffect, useRef, useState } from 'react';
import PropTypes from 'prop-types';
import cx from 'classnames';

import { trl } from '../../utils/translations';

import QtyInputField from '../QtyInputField';
import QtyReasonsRadioGroup from '../QtyReasonsRadioGroup';
import DateInput from '../DateInput';
import * as ws from '../../utils/websocket';
import { qtyInfos } from '../../utils/qtyInfos';
import { formatQtyToHumanReadableStr } from '../../utils/qtys';
import { useBooleanSetting } from '../../reducers/settings';
import BarcodeScannerComponent from '../BarcodeScannerComponent';
import { parseQRCodeString } from '../../utils/qrCode/hu';
import { toastErrorFromObj } from '../../utils/toast';
import { doFinally } from '../../utils';
import YesNoDialog from './YesNoDialog';
import DialogButton from './DialogButton';
import Dialog from './Dialog';
import * as uiTrace from './../../utils/ui_trace';

const GetQuantityDialog = ({
  readOnly: readOnlyParam = false,
  hideQtyInput = false,
  //
  userInfo,
  qtyTarget,
  qtyTargetCaption,
  totalQty,
  qtyAlreadyOnScale,
  qtyCaption,
  packingItemName,
  uom,
  qtyRejectedReasons,
  scaleDevice,
  scaleTolerance,
  //
  catchWeight: catchWeightParam,
  catchWeightUom,
  //
  isShowBestBeforeDate = false,
  bestBeforeDate: bestBeforeDateParam = '',
  isShowLotNo = false,
  lotNo: lotNoParam = '',
  isShowCloseTargetButton = false,
  //
  validateQtyEntered,
  getConfirmationPromptForQty,
  onQtyChange,
  onCloseDialog,
}) => {
  const [isProcessing, setProcessing] = useState(false);
  const [confirmationDialogProps, setConfirmationDialogProps] = useState({
    promptQuestion: '',
    onQtyChangePayload: undefined,
  });

  const allowManualInput = useBooleanSetting('qtyInput.AllowManualInputWhenScaleDeviceExists');
  const doNotValidateQty = useBooleanSetting('qtyInput.DoNotValidate');
  const useZeroAsInitialValue = useBooleanSetting('qtyInput.useZeroAsInitialValue');

  const [qtyInfo, setQtyInfo] = useState(qtyInfos.invalidOfNumber(useZeroAsInitialValue ? 0 : qtyTarget));
  const [rejectedReason, setRejectedReason] = useState(null);
  const [useScaleDevice, setUseScaleDevice] = useState(!!scaleDevice);

  const useCatchWeight = !scaleDevice && catchWeightUom;
  const [catchWeight, setCatchWeight] = useState(
    qtyInfos.invalidOfNumber(useZeroAsInitialValue ? 0 : catchWeightParam)
  );
  const [showCatchWeightQRCodeReader, setShowCatchWeightQRCodeReader] = useState(useCatchWeight);

  const onQtyEntered = (qtyInfo) => setQtyInfo(qtyInfo);
  const onReasonSelected = (reason) => setRejectedReason(reason);
  const onCatchWeightEntered = (qtyInfo) => setCatchWeight(qtyInfo);

  const [bestBeforeDate, setBestBeforeDate] = useState(bestBeforeDateParam);
  const [isBestBeforeDateValid, setIsBestBeforeDateValid] = useState(true);
  const onBestBeforeDateEntered = ({ date, isValid }) => {
    setBestBeforeDate(date);
    setIsBestBeforeDateValid(isValid);
  };

  const [lotNo, setLotNo] = useState(lotNoParam);
  const onLotNoEntered = (e) => {
    const lotNoNew = e.target.value ? e.target.value : '';
    //console.log('onLotNoEntered', { lotNoNew, e });
    setLotNo(lotNoNew);
  };

  const isQtyRejectedRequired = Array.isArray(qtyRejectedReasons) && qtyRejectedReasons.length > 0;
  const qtyRejected =
    isQtyRejectedRequired && qtyInfos.isValid(qtyInfo)
      ? Math.max(qtyTarget - qtyInfos.toNumberOrString(qtyInfo), 0)
      : 0;

  const isQtyValid =
    doNotValidateQty ||
    (qtyInfo?.isQtyValid &&
      (qtyRejected === 0 || rejectedReason != null) &&
      (!useCatchWeight || catchWeight?.isQtyValid));
  const allValid = (readOnlyParam || (isQtyValid && (!isShowBestBeforeDate || isBestBeforeDateValid))) && !isProcessing;
  const readOnly = readOnlyParam || isProcessing;

  const getConfirmationPrompt = useCallback(
    async (qtyInput) => {
      return getConfirmationPromptForQty && (await getConfirmationPromptForQty(qtyInput));
    },
    [getConfirmationPromptForQty]
  );

  const fireOnQtyChange = useCallback(
    (payload) => {
      setProcessing(true);
      const promise = onQtyChange(payload)?.catch?.((error) => toastErrorFromObj(error));
      doFinally(promise, () => setProcessing(false));
    },
    [onQtyChange]
  );

  const onDialogYes = async ({ isCloseTarget }) => {
    if (isProcessing) return;

    if (allValid) {
      const inputQtyEnteredAndValidated = qtyInfos.toNumberOrString(qtyInfo);

      let qtyEnteredAndValidated = inputQtyEnteredAndValidated;
      if (!!qtyAlreadyOnScale && typeof inputQtyEnteredAndValidated === 'number') {
        qtyEnteredAndValidated = Math.max(inputQtyEnteredAndValidated - qtyAlreadyOnScale, 0);
      }

      const onQtyChangePayload = {
        qtyEnteredAndValidated: qtyEnteredAndValidated,
        qtyRejected,
        qtyRejectedReason: qtyRejected > 0 ? rejectedReason : null,
        catchWeight: useCatchWeight ? qtyInfos.toNumberOrString(catchWeight) : null,
        catchWeightUom: useCatchWeight ? catchWeightUom : null,
        bestBeforeDate: isShowBestBeforeDate ? bestBeforeDate : null,
        lotNo: isShowLotNo ? lotNo : null,
        isCloseTarget: !!isCloseTarget,
      };
      uiTrace.putContext(onQtyChangePayload);

      const confirmationPrompt = await getConfirmationPrompt(qtyEnteredAndValidated);
      if (confirmationPrompt) {
        setConfirmationDialogProps({
          promptQuestion: confirmationPrompt,
          onQtyChangePayload,
        });
        return;
      }

      fireOnQtyChange(onQtyChangePayload);
    }
  };

  const readQtyFromQrCode = useCallback(
    async (result) => {
      const qrCode = parseQRCodeString(result.scannedBarcode);
      if (!qrCode.weightNet || !qrCode.weightNetUOM) {
        throw { messageKey: 'activities.picking.qrcode.missingQty' };
      }
      if (qrCode.weightNetUOM !== catchWeightUom) {
        throw { messageKey: 'activities.picking.qrcode.differentUOM' };
      }

      const onQtyChangePayload = {
        qtyEnteredAndValidated: 1,
        catchWeight: qrCode.weightNet,
        catchWeightUom: catchWeightUom,
        bestBeforeDate: qrCode.bestBeforeDate,
        lotNo: qrCode.lotNo,
        productNo: qrCode.productNo,
        barcodeType: qrCode.barcodeType,
        isDone: false,
      };

      const confirmationPrompt = await getConfirmationPrompt(1);

      if (confirmationPrompt) {
        setConfirmationDialogProps({
          promptQuestion: confirmationPrompt,
          onQtyChangePayload,
        });
        return;
      }

      // console.log('readQtyFromQrCode', { qrCode, result, catchWeightUom });
      fireOnQtyChange(onQtyChangePayload);
    },
    [catchWeightUom, fireOnQtyChange]
  );

  const wsClientRef = useRef(null);
  useEffect(() => {
    if (scaleDevice && useScaleDevice) {
      if (!wsClientRef.current) {
        wsClientRef.current = ws.connectAndSubscribe({
          topic: scaleDevice.websocketEndpoint,
          debug: false,
          onWebsocketMessage: (message) => {
            if (useScaleDevice) {
              const { value } = JSON.parse(message.body);

              const newQtyCandidate = qtyInfos.invalidOfNumber(value);

              setQtyInfo((prev) => {
                if (!prev || newQtyCandidate.qty !== prev.qty) {
                  return newQtyCandidate;
                }

                return prev;
              });
            }
          },
          headers: {
            qtyTarget: totalQty || '0',
            positiveTolerance: scaleTolerance?.positiveTolerance || '0',
            negativeTolerance: scaleTolerance?.negativeTolerance || '0',
            uom: uom,
          },
        });
      }
    }

    return () => {
      if (wsClientRef.current) {
        ws.disconnectClient(wsClientRef.current);
        wsClientRef.current = null;
      }
    };
  }, [scaleDevice, useScaleDevice]);

  const isCustomView = () => {
    return showCatchWeightQRCodeReader;
  };

  const getCustomView = () => {
    if (showCatchWeightQRCodeReader) {
      return getQRCodeCatchWeightView();
    } else {
      return <></>;
    }
  };

  const getQRCodeCatchWeightView = () => {
    return (
      <>
        <table className="table">
          <tbody>
            {qtyTargetCaption && (
              <tr>
                <th>{qtyTargetCaption}</th>
                <td>{formatQtyToHumanReadableStr({ qty: Math.max(qtyTarget, 0), uom })}</td>
              </tr>
            )}
            {userInfo &&
              userInfo.map((item) => (
                <tr key={computeKeyFromUserInfoItem(item)}>
                  <th>{computeCaptionFromUserInfoItem(item)}</th>
                  <td>{item.value}</td>
                </tr>
              ))}
            <tr>
              <td colSpan="2">
                <BarcodeScannerComponent continuousRunning={true} onResolvedResult={readQtyFromQrCode} />
              </td>
            </tr>
          </tbody>
        </table>
        <div className="buttons is-centered">
          <DialogButton
            captionKey="activities.picking.switchToManualInput"
            onClick={() => setShowCatchWeightQRCodeReader(false)}
          />
          <DialogButton captionKey="general.closeText" className="is-danger" onClick={onCloseDialog} />
        </div>
      </>
    );
  };

  if (confirmationDialogProps?.promptQuestion && confirmationDialogProps.onQtyChangePayload) {
    return (
      <YesNoDialog
        promptQuestion={confirmationDialogProps.promptQuestion}
        onYes={() => {
          fireOnQtyChange(confirmationDialogProps.onQtyChangePayload);
          setConfirmationDialogProps(undefined);
        }}
        onNo={() => setConfirmationDialogProps(undefined)}
      />
    );
  }

  return (
    <div>
      <Dialog className="get-qty-dialog">
        {isCustomView() && getCustomView()}
        {!isCustomView() && (
          <>
            <div className="table-container">
              <table className="table">
                <tbody>
                  {qtyTargetCaption && (
                    <tr>
                      <th>{qtyTargetCaption}</th>
                      <td>{formatQtyToHumanReadableStr({ qty: Math.max(qtyTarget, 0), uom })}</td>
                    </tr>
                  )}
                  {userInfo &&
                    userInfo.map((item) => (
                      <tr key={computeKeyFromUserInfoItem(item)}>
                        <th>{computeCaptionFromUserInfoItem(item)}</th>
                        <td>{item.value}</td>
                      </tr>
                    ))}
                  {!hideQtyInput && (
                    <tr>
                      <th>{qtyCaption ?? trl('general.Qty')}</th>
                      <td>
                        <QtyInputField
                          qty={qtyInfos.toNumberOrString(qtyInfo)}
                          uom={uom}
                          validateQtyEntered={validateQtyEntered}
                          readonly={useScaleDevice || readOnly}
                          onQtyChange={onQtyEntered}
                          isRequestFocus={true}
                        />
                      </td>
                    </tr>
                  )}
                  {packingItemName && (
                    <tr>
                      <th>{trl('general.PackingItemName')}</th>
                      <td>{packingItemName}</td>
                    </tr>
                  )}
                  {scaleDevice && allowManualInput && (
                    <tr>
                      <td colSpan="2">
                        <div className="buttons has-addons">
                          <button
                            className={cx('button', {
                              'is-success': useScaleDevice,
                              'is-selected': useScaleDevice,
                            })}
                            onClick={() => setUseScaleDevice(true)}
                          >
                            {scaleDevice.caption}
                          </button>
                          <button
                            className={cx('button', {
                              'is-success': !useScaleDevice,
                              'is-selected': !useScaleDevice,
                            })}
                            onClick={() => setUseScaleDevice(false)}
                          >
                            Manual
                          </button>
                        </div>
                      </td>
                    </tr>
                  )}
                  {isShowBestBeforeDate && (
                    <tr>
                      <th>{trl('general.BestBeforeDate')}</th>
                      <td>
                        <div className="field">
                          <div className="control">
                            <DateInput
                              type="date"
                              value={bestBeforeDate}
                              disabled={readOnly}
                              onChange={onBestBeforeDateEntered}
                            />
                          </div>
                        </div>
                      </td>
                    </tr>
                  )}
                  {isShowLotNo && (
                    <tr>
                      <th>{trl('general.LotNo')}</th>
                      <td>
                        <div className="field">
                          <div className="control">
                            <input
                              className="input"
                              type="text"
                              value={lotNo}
                              disabled={readOnly}
                              onChange={onLotNoEntered}
                            />
                          </div>
                        </div>
                      </td>
                    </tr>
                  )}
                  {useCatchWeight && (
                    <tr>
                      <th>{trl('general.CatchWeight')}</th>
                      <td>
                        <>
                          <QtyInputField
                            qty={qtyInfos.toNumberOrString(catchWeight)}
                            uom={catchWeightUom}
                            onQtyChange={onCatchWeightEntered}
                            readonly={readOnly}
                          />
                          <DialogButton
                            captionKey="activities.picking.switchToQrCodeInput"
                            className="button"
                            onClick={() => setShowCatchWeightQRCodeReader(true)}
                          />
                        </>
                      </td>
                    </tr>
                  )}
                  {qtyRejected > 0 && (
                    <>
                      <tr>
                        <th>{trl('general.QtyRejected')}</th>
                        <td>{formatQtyToHumanReadableStr({ qty: qtyRejected, uom })}</td>
                      </tr>
                      <tr>
                        <td colSpan={2}>
                          <QtyReasonsRadioGroup
                            reasons={qtyRejectedReasons}
                            selectedReason={rejectedReason}
                            disabled={qtyRejected === 0}
                            onReasonSelected={onReasonSelected}
                          />
                        </td>
                      </tr>
                    </>
                  )}
                </tbody>
              </table>
            </div>
            <div className="buttons is-centered">
              {isShowCloseTargetButton && (
                <>
                  <DialogButton
                    captionKey="activities.picking.confirmDoneAndCloseTarget"
                    className="is-success"
                    disabled={!allValid}
                    onClick={() => onDialogYes({ isCloseTarget: true })}
                  />
                  <br />
                </>
              )}
              <DialogButton
                captionKey="activities.picking.confirmDone"
                className="is-success"
                disabled={!allValid}
                onClick={() => onDialogYes({ isCloseTarget: false })}
              />
              <DialogButton
                captionKey="general.cancelText"
                className="is-danger"
                disabled={isProcessing}
                onClick={onCloseDialog}
              />
            </div>
          </>
        )}
      </Dialog>
    </div>
  );
};

const computeKeyFromUserInfoItem = ({ caption = null, captionKey = null, value }) => {
  return `userInfo_${caption || captionKey || value || '?'}`;
};

const computeCaptionFromUserInfoItem = ({ caption = null, captionKey = null }) => {
  if (caption) {
    return caption;
  } else if (captionKey) {
    return trl(captionKey);
  } else {
    // shall not happen
    return '';
  }
};

GetQuantityDialog.propTypes = {
  // Properties
  hideQtyInput: PropTypes.bool,
  readOnly: PropTypes.bool,
  userInfo: PropTypes.array,
  qtyTarget: PropTypes.number.isRequired,
  qtyTargetCaption: PropTypes.string,
  totalQty: PropTypes.number,
  qtyAlreadyOnScale: PropTypes.number,
  qtyCaption: PropTypes.string,
  packingItemName: PropTypes.string,
  uom: PropTypes.string.isRequired,
  qtyRejectedReasons: PropTypes.arrayOf(PropTypes.object),
  scaleDevice: PropTypes.object,
  scaleTolerance: PropTypes.object,
  catchWeight: PropTypes.number,
  catchWeightUom: PropTypes.string,
  isShowBestBeforeDate: PropTypes.bool,
  bestBeforeDate: PropTypes.string,
  isShowLotNo: PropTypes.bool,
  lotNo: PropTypes.string,
  isShowCloseTargetButton: PropTypes.bool,

  // Callbacks
  validateQtyEntered: PropTypes.func,
  getConfirmationPromptForQty: PropTypes.func,
  onQtyChange: PropTypes.func.isRequired,
  onCloseDialog: PropTypes.func,
};

export default GetQuantityDialog;
