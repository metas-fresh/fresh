/*
 * #%L
 * me04
 * %%
 * Copyright (C) 2023 metas GmbH
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

import React, { useEffect, useRef, useState } from 'react';
import counterpart from 'counterpart';
import classnames from 'classnames';
import PropTypes from 'prop-types';

export const Login2FAView = ({ onSubmit }) => {
  const inputRef = useRef(null);
  const [pending, setPending] = useState(false);
  const [code, setCode] = useState('');
  const [error, setError] = useState('');

  const isSubmitEnabled = !pending && code.length === 6;

  const handleFocus = () => {
    inputRef.current.focus();
    inputRef.current.select();
  };

  useEffect(() => {
    if (!pending) {
      handleFocus();
    }
  }, [pending]);

  const handleSubmit = () => {
    if (!isSubmitEnabled) return;

    setPending(true);
    onSubmit({
      code,
      setError: (errorMsg) => {
        setError(errorMsg);
        setPending(false);
      },
    });
  };

  return (
    <div
      onKeyUp={(e) => {
        if (!pending && e.key === 'Enter') {
          handleSubmit();
        }
      }}
    >
      {error && <div className="input-error">{error}</div>}
      <div>
        <div className="form-control-label">
          <small>{counterpart.translate('login.2FA.caption')}</small>
        </div>
        <input
          ref={inputRef}
          type="text"
          value={code}
          onChange={(e) => {
            const newCode = e.target.value;
            setCode(newCode);
            setError('');

            if (newCode.length === 6) {
              handleSubmit();
            }
          }}
          name="code2FA"
          className={classnames('input-primary input-block', {
            'input-error': error,
            'input-disabled': pending,
          })}
          disabled={pending}
        />
      </div>
      <div className="mt-2">
        <button
          className="btn btn-sm btn-block btn-meta-success"
          onClick={handleSubmit}
          disabled={!isSubmitEnabled}
        >
          {counterpart.translate('login.send.caption')}
        </button>
      </div>
    </div>
  );
};

Login2FAView.propTypes = {
  onSubmit: PropTypes.func.isRequired,
};
