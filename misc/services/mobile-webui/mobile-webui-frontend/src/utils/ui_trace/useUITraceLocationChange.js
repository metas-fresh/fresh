import { useLocationChange } from '../../hooks/useLocationChange';
import * as uiTrace from '../../utils/ui_trace';

export const useUITraceLocationChange = () => {
  useLocationChange(({ currentLocation, currentRoute, prevLocation }) => {
    uiTrace.setApplicationId(currentRoute?.params?.applicationId);
    uiTrace.trace({
      eventName: 'locationChanged',
      currentLocation,
      currentRoute,
      prevLocation,
    });
  });
};
