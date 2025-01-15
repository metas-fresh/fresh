import React from 'react';
import PropTypes from 'prop-types';
import cx from 'classnames';

import * as CompleteStatus from '../../constants/CompleteStatus';
import HazardIcon from '../HazardIcon';
import AllergenIcon from '../AllergenIcon';
import * as uiTrace from '../../utils/ui_trace';

const SYMBOLS_SIZE_PX = 25;

const ButtonWithIndicator = ({
  caption,
  showWarningSign,
  typeFASIconName,
  hazardSymbols = null,
  allergens = null,
  isDanger,
  completeStatus,
  disabled,
  onClick,
  children,
  additionalCssClass,
}) => {
  const indicatorClassName = getIndicatorClassName(completeStatus);

  const allergensWithColor = allergens != null && allergens.filter((allergen) => allergen.color != null);

  const displayAllergens = allergensWithColor && allergensWithColor.length > 0;

  const displayHazards = hazardSymbols != null && hazardSymbols.length > 0;

  const displayHazardsAndAllergens = displayHazards || displayAllergens;

  const fireOnClick = uiTrace.traceFunction(onClick, {
    eventName: 'buttonClick',
    caption,
    showWarningSign,
    completeStatus,
    typeFASIconName,
    hazardSymbols,
    allergens,
    isDanger,
  });

  return (
    <button
      className={cx('button is-outlined is-fullwidth complete-btn', { 'is-danger': isDanger }, additionalCssClass)}
      disabled={!!disabled}
      onClick={fireOnClick}
    >
      <div className="full-size-btn">
        <div className="left-btn-side">
          {showWarningSign && (
            <span>
              {/* IMPORTANT: the wrapping "span" needs to be here in case we are clearing showWarningSign so to avoid: DOMException: Failed to execute 'removeChild' on 'Node'*/}
              <i className="fas fa-exclamation-triangle warning-sign" />
            </span>
          )}
          {typeFASIconName && (
            <span>
              {/* IMPORTANT: the wrapping "span" needs to be here in case we are clearing typeFASIconName so to avoid: DOMException: Failed to execute 'removeChild' on 'Node'*/}
              <i key="icon" className={`fas fa-solid ${typeFASIconName}`} />
            </span>
          )}
        </div>
        <div className="caption-btn">
          <div className="rows">
            <div className="row">
              <span>{caption}</span>
            </div>
            {displayHazardsAndAllergens && (
              <div
                className="row hazard-icons-btn"
                style={{
                  minHeight: SYMBOLS_SIZE_PX + 'px',
                  maxHeight: SYMBOLS_SIZE_PX + 'px',
                }}
              >
                <AllergenIcon allergens={allergensWithColor} size={SYMBOLS_SIZE_PX} />
                {hazardSymbols &&
                  hazardSymbols.map((hazardSymbol, symbolIndex) => (
                    <HazardIcon
                      key={symbolIndex}
                      imageId={hazardSymbol.imageId}
                      caption={hazardSymbol.name}
                      size={SYMBOLS_SIZE_PX}
                    />
                  ))}
              </div>
            )}
            {children}
          </div>
        </div>
        {indicatorClassName && (
          <div className="right-btn-side">
            <span className={indicatorClassName} />
          </div>
        )}
      </div>
    </button>
  );
};

const getIndicatorClassName = (completeStatus) => {
  switch (completeStatus) {
    case CompleteStatus.NOT_STARTED:
      return 'indicator-red';
    case CompleteStatus.COMPLETED:
      return 'indicator-green';
    case CompleteStatus.IN_PROGRESS:
      return 'indicator-yellow';
    default:
      return '';
  }
};

ButtonWithIndicator.propTypes = {
  caption: PropTypes.string.isRequired,
  showWarningSign: PropTypes.bool,
  typeFASIconName: PropTypes.string,
  hazardSymbols: PropTypes.array,
  allergens: PropTypes.array,
  isDanger: PropTypes.bool,
  completeStatus: PropTypes.string,
  disabled: PropTypes.bool,
  children: PropTypes.node,
  additionalCssClass: PropTypes.string,
  onClick: PropTypes.func.isRequired,
};

export default ButtonWithIndicator;
