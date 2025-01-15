import React, { useMemo } from 'react';
import PropTypes from 'prop-types';
import * as CompleteStatus from '../../../constants/CompleteStatus';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import ButtonQuantityProp from '../../../components/buttons/ButtonQuantityProp';
import {
  pickingLineScreenLocation,
  pickingScanScreenLocation,
  reopenClosedLUScreenLocation,
  selectPickTargetScreenLocation,
  selectTUPickTargetScreenLocation,
} from '../../../routes/picking';
import { useHistory } from 'react-router-dom';
import { trl } from '../../../utils/translations';
import { getLinesArrayFromActivity } from '../../../reducers/wfProcesses';
import { isAllowPickingAnyHUForActivity } from '../../../utils/picking';
import {
  useCurrentPickTarget,
  useCurrentTUPickTarget,
} from '../../../reducers/wfProcesses/picking/useCurrentPickTarget';

export const COMPONENTTYPE_PickProducts = 'picking/pickProducts';

const PickProductsActivity = ({ applicationId, wfProcessId, activityId, activity }) => {
  const {
    dataStored: { isUserEditable, isPickWithNewLU, isAllowNewTU },
  } = activity;

  const groupedLines = useMemo(() => {
    const lines = getLinesArrayFromActivity(activity);
    lines.sort((a, b) => (a?.sortingIndex ?? 0) - (b.sortingIndex ?? 0));
    let currentGroupKey = undefined;
    const groups = [];
    let currentGroup = [];

    for (const line of lines) {
      if (currentGroupKey === undefined) {
        currentGroupKey = line.displayGroupKey;
      }
      if (currentGroupKey !== line.displayGroupKey) {
        groups.push([...currentGroup]);
        currentGroupKey = line.displayGroupKey;
        currentGroup = [line];
      } else {
        currentGroup.push(line);
      }
    }
    groups.push([...currentGroup]);

    return groups;
  }, [getLinesArrayFromActivity, activity]);

  const allowPickingAnyHU = isAllowPickingAnyHUForActivity({ activity });

  const history = useHistory();

  const currentPickTarget = useCurrentPickTarget({ wfProcessId, activityId });

  const isLUScanRequiredAndMissing = isPickWithNewLU && !currentPickTarget;

  const onSelectPickTargetClick = () => {
    history.push(selectPickTargetScreenLocation({ applicationId, wfProcessId, activityId }));
  };

  const onReopenClosedLUClicked = () => {
    history.push(reopenClosedLUScreenLocation({ applicationId, wfProcessId }));
  };

  const currentTUPickTarget = useCurrentTUPickTarget({ wfProcessId, activityId });
  const onSelectTUPickTargetClick = () => {
    history.push(selectTUPickTargetScreenLocation({ applicationId, wfProcessId, activityId }));
  };

  const onScanButtonClick = () => {
    history.push(pickingScanScreenLocation({ applicationId, wfProcessId, activityId }));
  };
  const onLineButtonClick = ({ lineId }) => {
    history.push(pickingLineScreenLocation({ applicationId, wfProcessId, activityId, lineId }));
  };

  const isLineReadOnly = (line) => {
    const tuTargetIsSetButCurrentLineHasItsOwnPacking = currentTUPickTarget && line.pickingUnit === 'TU';
    const tuTargetIsNotSetButCurrentLineMustBePlacedOnTUs =
      isPickWithNewLU && isAllowNewTU && !currentTUPickTarget && line.pickingUnit === 'CU';
    return (
      !isUserEditable ||
      isLUScanRequiredAndMissing ||
      tuTargetIsSetButCurrentLineHasItsOwnPacking ||
      tuTargetIsNotSetButCurrentLineMustBePlacedOnTUs
    );
  };

  const isAtLeastOneReadOnlyLine = (groupedLines) => {
    return groupedLines.some((lines) => lines.some((line) => isLineReadOnly(line)));
  };

  return (
    <div className="mt-5">
      {isPickWithNewLU && (
        <ButtonWithIndicator
          caption={trl('activities.picking.reopenLU')}
          disabled={!isUserEditable}
          onClick={onReopenClosedLUClicked}
        />
      )}
      {isPickWithNewLU && (
        <ButtonWithIndicator
          caption={
            currentPickTarget?.caption
              ? trl('activities.picking.pickingTarget.Current') + ': ' + currentPickTarget?.caption
              : trl('activities.picking.pickingTarget.New')
          }
          disabled={!isUserEditable}
          onClick={onSelectPickTargetClick}
        />
      )}
      {isAllowNewTU && (
        <ButtonWithIndicator
          caption={
            currentTUPickTarget?.caption
              ? trl('activities.picking.tuPickingTarget.Current') + ': ' + currentTUPickTarget?.caption
              : trl('activities.picking.tuPickingTarget.New')
          }
          disabled={!isUserEditable || isLUScanRequiredAndMissing}
          onClick={onSelectTUPickTargetClick}
        />
      )}
      <br />

      {allowPickingAnyHU && (
        <ButtonWithIndicator
          caption={trl('activities.picking.scanQRCode')}
          disabled={isAtLeastOneReadOnlyLine(groupedLines)}
          onClick={onScanButtonClick}
        />
      )}
      {groupedLines &&
        groupedLines.map((group, index) => {
          const getDisplayLines = (lines) => {
            return lines.map((lineItem) => {
              const lineId = lineItem.pickingLineId;
              const { uom, qtyToPick, qtyPicked } = lineItem;

              return (
                <ButtonWithIndicator
                  key={lineId}
                  caption={lineItem.caption}
                  completeStatus={lineItem.completeStatus || CompleteStatus.NOT_STARTED}
                  disabled={isLineReadOnly(lineItem)}
                  onClick={() => onLineButtonClick({ lineId })}
                >
                  <ButtonQuantityProp
                    qtyCurrent={qtyPicked}
                    qtyTarget={qtyToPick}
                    uom={uom}
                    applicationId={applicationId}
                  />
                </ButtonWithIndicator>
              );
            });
          };

          return (
            <React.Fragment key={index}>
              {getDisplayLines(group)}
              {index !== groupedLines.length - 1 && <br />}
            </React.Fragment>
          );
        })}
    </div>
  );
};

PickProductsActivity.propTypes = {
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  activity: PropTypes.object.isRequired,
};

export default PickProductsActivity;
