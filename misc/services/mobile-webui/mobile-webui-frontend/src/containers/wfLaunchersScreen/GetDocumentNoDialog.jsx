/*
 * #%L
 * ic114
 * %%
 * Copyright (C) 2024 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import { trl } from '../../utils/translations';
import React, { useEffect, useRef, useState } from 'react';
import PropTypes from 'prop-types';
import Dialog from '../../components/dialogs/Dialog';
import DialogButton from '../../components/dialogs/DialogButton';

const computeIsValid = ({ documentNo }) => !!documentNo;

const GetDocumentNoDialog = ({ documentNo: documentNoInitial, onOK, onClear }) => {
  const [documentNo, setDocumentNo] = useState(documentNoInitial ? documentNoInitial : '');
  const inputRef = useRef();

  const isValid = computeIsValid({ documentNo });

  useEffect(() => {
    if (inputRef.current) {
      inputRef.current.focus();
      inputRef.current.select();
    }
  }, []);

  const handleDocumentNoChanged = (e) => {
    const documentNoNew = e.target.value ? e.target.value : '';
    setDocumentNo(documentNoNew);

    if (e.key === 'Enter') {
      const isValidNew = computeIsValid({ documentNo: documentNoNew });
      if (isValidNew) {
        onOK(documentNoNew);
      }
    }
  };

  return (
    <div>
      <Dialog className="get-documentNo-dialog">
        <>
          <div className="table-container">
            <table className="table">
              <tbody>
                <tr>
                  <th>{trl('general.DocumentNo')}</th>
                  <td>
                    <div className="field">
                      <div className="control">
                        <input
                          ref={inputRef}
                          className="input"
                          type="text"
                          value={documentNo}
                          tabIndex="1"
                          onChange={handleDocumentNoChanged}
                          onKeyUp={handleDocumentNoChanged}
                        />
                      </div>
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          <div className="buttons is-centered">
            <DialogButton
              captionKey="general.OK"
              className="is-success"
              disabled={!isValid}
              onClick={() => onOK(documentNo)}
            />
            <DialogButton captionKey="general.clearText" className="button is-danger" onClick={() => onClear()} />
          </div>
        </>
      </Dialog>
    </div>
  );
};

GetDocumentNoDialog.propTypes = {
  documentNo: PropTypes.string,
  onOK: PropTypes.func.isRequired,
  onClear: PropTypes.func.isRequired,
};

export default GetDocumentNoDialog;
