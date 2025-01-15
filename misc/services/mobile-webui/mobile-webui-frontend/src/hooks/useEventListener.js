import { useEffect } from 'react';

export const useEventListener = (type, listener) => {
  useEffect(() => {
    window.addEventListener(type, listener);
    return () => {
      window.removeEventListener(type, listener);
    };
  }, []);
};
