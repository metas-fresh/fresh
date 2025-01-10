import counterpart from 'counterpart';
import React from 'react';
import PropTypes from 'prop-types';
import MomentTZ from 'moment-timezone';

const TIMESTAMP_DISPLAY_FORMAT = 'L LTS z';

const ChangeLogModal = ({ data }) => {
  if (!data?.createdByUsername) {
    return null;
  }

  return (
    <div className="col-12">
      <CreatedUpdatedInfo
        createdByUsername={data.createdByUsername}
        createdTimestamp={data.createdTimestamp}
        lastChangedByUsername={data.lastChangedByUsername}
        lastChangedTimestamp={data.lastChangedTimestamp}
      />
      {data.rowsData && (
        <div className="panel panel-spaced changelog-rows">
          <h5>{counterpart.translate('view.about.row')}:</h5>
          <CreatedUpdatedInfo
            createdByUsername={data.rowsData.createdByUsername}
            createdTimestamp={data.rowsData.createdTimestamp}
            lastChangedByUsername={data.rowsData.lastChangedByUsername}
            lastChangedTimestamp={data.rowsData.lastChangedTimestamp}
          />
        </div>
      )}
    </div>
  );
};
ChangeLogModal.propTypes = {
  data: PropTypes.object.isRequired,
};

//
//
//

const CreatedUpdatedInfo = ({
  createdByUsername,
  createdTimestamp,
  lastChangedByUsername,
  lastChangedTimestamp,
}) => {
  return (
    <>
      <div className="panel panel-spaced panel-distance panel-bordered panel-primary">
        <Field
          caption={counterpart.translate('view.about.createdBy')}
          value={createdByUsername}
        />
        <Field
          caption={counterpart.translate('view.about.created')}
          value={MomentTZ(createdTimestamp).format(TIMESTAMP_DISPLAY_FORMAT)}
        />
      </div>
      <div className="panel panel-spaced panel-distance panel-bordered panel-secondary">
        <Field
          caption={counterpart.translate('view.about.updatedBy')}
          value={lastChangedByUsername}
        />
        <Field
          caption={counterpart.translate('view.about.updated')}
          value={MomentTZ(lastChangedTimestamp).format(
            TIMESTAMP_DISPLAY_FORMAT
          )}
        />
      </div>
    </>
  );
};
CreatedUpdatedInfo.propTypes = {
  createdByUsername: PropTypes.string,
  createdTimestamp: PropTypes.string,
  lastChangedByUsername: PropTypes.string,
  lastChangedTimestamp: PropTypes.string,
};

//
//
//

const Field = ({ caption, value }) => {
  return (
    <div className="elements-line">
      <div className="form-group row  form-field-Value">
        <div className="form-control-label col-sm-3">{caption}</div>
        <div className="col-sm-9 ">
          <div className="input-body-container">
            <span />
            <div className="input-block input-icon-container input-disabled input-secondary pulse-off">
              <input
                className="input-field js-input-field"
                disabled
                tabIndex="-1"
                type="text"
                value={value}
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
Field.propTypes = {
  caption: PropTypes.string.isRequired,
  value: PropTypes.string,
};

//
//
//

export default ChangeLogModal;
