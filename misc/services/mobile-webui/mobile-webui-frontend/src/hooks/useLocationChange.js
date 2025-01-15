import { useEffect } from 'react';
import { useRouteMatch } from 'react-router-dom';

export const useLocationChange = (onChange) => {
  const currentRoute = useRouteMatch();

  const trackLocation = () => {
    const currentLocation = window.location.href;
    const lastKnownLocation = sessionStorage.getItem('lastKnownLocation');
    // console.log('trackLocation', { currentLocation, lastKnownLocation });

    if (currentLocation !== lastKnownLocation) {
      sessionStorage.setItem('lastKnownLocation', currentLocation);
      onChange({
        currentLocation,
        currentRoute,
        prevLocation: lastKnownLocation,
      });
    }
  };

  useEffect(() => {
    window.addEventListener('popstate', trackLocation);
    window.addEventListener('hashchange', trackLocation);

    trackLocation();

    return () => {
      window.removeEventListener('popstate', trackLocation);
      window.removeEventListener('hashchange', trackLocation);
    };
  }, []);
};
