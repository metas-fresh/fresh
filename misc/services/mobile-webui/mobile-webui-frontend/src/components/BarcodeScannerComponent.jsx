import PropTypes from 'prop-types';
import React, { useEffect, useMemo, useRef, useState } from 'react';
import { BarcodeFormat, BrowserMultiFormatReader } from '@zxing/browser';
import DecodeHintType from '@zxing/library/cjs/core/DecodeHintType';
import { toastError, toastErrorFromObj } from '../utils/toast';
import { trl } from '../utils/translations';
import { useBooleanSetting, useNumber, usePositiveNumberSetting } from '../reducers/settings';
import { debounce } from 'lodash';
import { beep } from '../utils/audio';
import * as uiTrace from '../utils/ui_trace';

const READER_HINTS = new Map().set(DecodeHintType.POSSIBLE_FORMATS, [
  BarcodeFormat.QR_CODE,
  BarcodeFormat.CODE_128,
  BarcodeFormat.ITF,
]);

const READER_OPTIONS = {
  delayBetweenScanSuccess: 2000,
  delayBetweenScanAttempts: 600,
};

const useConfigParams = () => {
  return {
    okBeepParams: {
      name: 'OK',
      beepFrequency: useNumber('barcodeScanner.onSuccess.beep.frequency', 1000),
      beepVolume: useNumber('barcodeScanner.onSuccess.beep.volume', 0.1),
      beepDurationMillis: useNumber('barcodeScanner.onSuccess.beep.durationMillis', 100),
      vibrateMillis: useNumber('barcodeScanner.onSuccess.vibrate.durationMillis', 100),
    },
    errorBeepParams: {
      name: 'error',
      beepFrequency: useNumber('barcodeScanner.onError.beep.frequency', 100),
      beepVolume: useNumber('barcodeScanner.onError.beep.volume', 0.1),
      beepDurationMillis: useNumber('barcodeScanner.onError.beep.durationMillis', 100),
      vibrateMillis: useNumber('barcodeScanner.onError.vibrate.durationMillis', 100),
    },
    isShowInputText: useBooleanSetting('barcodeScanner.showInputText'),
    triggerOnChangeIfLengthGreaterThan: usePositiveNumberSetting(
      'barcodeScanner.inputText.triggerOnChangeIfLengthGreaterThan',
      0
    ),
    textChangedDebounceMillis: usePositiveNumberSetting('barcodeScanner.inputText.debounceMillis', 300),
  };
};

const BarcodeScannerComponent = ({
  resolveScannedBarcode,
  onResolvedResult,
  inputPlaceholderText,
  continuousRunning,
}) => {
  const {
    isShowInputText,
    triggerOnChangeIfLengthGreaterThan,
    textChangedDebounceMillis,
    okBeepParams,
    errorBeepParams,
  } = useConfigParams();

  const videoRef = useRef();
  const inputTextRef = useRef();
  const scanningStatusRef = useRef({ running: false, done: false });
  const [isProcessing, setProcessing] = useState(false);

  const validateScannedBarcodeAndForward0 = async ({ scannedBarcode, controls = null }) => {
    inputTextRef?.current?.select();

    const scanningStatus = scanningStatusRef.current;
    if (scanningStatus.running || scanningStatus.done) {
      console.log('Ignore scanned barcode because we are already running or done', { scannedBarcode, scanningStatus });
      return;
    }
    scanningStatus.running = true;
    setProcessing(true);

    // console.log('Resolving scanned barcode', {
    //   scannedBarcode,
    //   resolveScannedBarcode,
    //   onResolvedResult,
    //   scanningStatus: { ...scanningStatus },
    // });

    try {
      let resolvedResult;
      if (resolveScannedBarcode) {
        resolvedResult = await resolveScannedBarcode({ scannedBarcode });
      } else {
        resolvedResult = { scannedBarcode, error: null };
      }
      console.log('Got resolvedResult', resolvedResult);

      if (resolvedResult.error) {
        toastError({ plainMessage: resolvedResult.error });
        beep(errorBeepParams);
        scanningStatus.done = false; // not done yet
      } else {
        await onResolvedResult(resolvedResult);

        if (!continuousRunning) {
          scanningStatus.done = true;
          controls?.stop();
        }

        beep(okBeepParams);
      }
    } catch (error) {
      beep(errorBeepParams);
      toastErrorFromObj(error);
    } finally {
      scanningStatus.running = false;
      setProcessing(false);

      if (inputTextRef?.current) {
        inputTextRef.current.value = '';
      }
    }
  };

  const validateScannedBarcodeAndForward = uiTrace.traceFunction(
    validateScannedBarcodeAndForward0,
    ({ scannedBarcode }) => ({ eventName: 'barcodeScanned', scannedBarcode })
  );

  const mountedRef = useRef(true);
  useEffect(() => {
    mountedRef.current = true;

    const codeReader = new BrowserMultiFormatReader(READER_HINTS, READER_OPTIONS);
    codeReader.decodeFromVideoDevice(undefined, videoRef.current, (result, error, controls) => {
      if (mountedRef.current === false) {
        controls.stop();
      } else if (typeof result !== 'undefined') {
        validateScannedBarcodeAndForward({ scannedBarcode: result.text, controls });
      }
    });

    return function cleanup() {
      mountedRef.current = false;
    };
  }, []);

  const handleInputTextChanged = (e) => {
    const scannedBarcode = e.target.value;

    if (
      scannedBarcode &&
      triggerOnChangeIfLengthGreaterThan &&
      triggerOnChangeIfLengthGreaterThan > 0 &&
      scannedBarcode.length >= triggerOnChangeIfLengthGreaterThan
    ) {
      validateScannedBarcodeAndForward({ scannedBarcode });
    }
  };

  const handleInputTextChangedDebounced = useMemo(() => {
    return debounce(handleInputTextChanged, textChangedDebounceMillis);
  }, [textChangedDebounceMillis]);
  useEffect(() => {
    return () => handleInputTextChangedDebounced.cancel();
  });

  const handleInputTextKeyPress = (e) => {
    if (e.key === 'Enter') {
      const scannedBarcode = e.target.value;

      validateScannedBarcodeAndForward({ scannedBarcode });
    }
  };

  const handleInputTextFocus = () => {
    inputTextRef?.current?.select();
  };

  const handleInputTextBlur = () => {
    setTimeout(() => {
      inputTextRef?.current?.focus();
    }, 2000);
  };

  useEffect(() => {
    videoRef?.current?.scrollIntoView({ behaviour: 'smooth', block: 'center', inline: 'end' });
    inputTextRef?.current?.focus();
  });

  return (
    <div className="barcode-scanner">
      {isProcessing && <Spinner />}
      <video key="video" ref={videoRef} width="100%" height="100%" />
      {isShowInputText && !isProcessing && (
        <input
          key="input-text"
          ref={inputTextRef}
          className="input-text"
          type="text"
          placeholder={inputPlaceholderText || trl('components.BarcodeScannerComponent.scanTextPlaceholder')}
          onFocus={handleInputTextFocus}
          onBlur={handleInputTextBlur}
          onChange={handleInputTextChangedDebounced}
          onKeyUp={handleInputTextKeyPress}
        />
      )}
    </div>
  );
};

const Spinner = () => {
  return (
    <div className="loading">
      <i className="loading-icon fas fa-solid fa-spinner fa-spin" />
    </div>
  );
};

BarcodeScannerComponent.propTypes = {
  //
  // Props:
  resolveScannedBarcode: PropTypes.func,
  onResolvedResult: PropTypes.func.isRequired,
  inputPlaceholderText: PropTypes.string,
  continuousRunning: PropTypes.bool,
};

export default BarcodeScannerComponent;
