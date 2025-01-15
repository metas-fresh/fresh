import { postEventsToBackend } from '../../api/ui_trace';
import { clearEvents, getAllEvents } from './db';
import { useEventListener } from '../../hooks/useEventListener';
import { usePeriodicTask } from '../../hooks/usePeriodicTask';

const SYNC_INTERVAL_MILLIS = 1000;

const syncEventsToBackend = async (reason = 'programmatic') => {
  // console.log(`Attempting to sync UI events (${reason}) ...`);

  if (!navigator.onLine) {
    console.log(`Skip syncing because not online (${reason})`);
    return;
  }

  try {
    const events = await getAllEvents();
    if (!events?.length) return;

    // console.log('Posting to backend', { events });
    await postEventsToBackend(events);
    await clearEvents();
    // console.log('Posting to backend DONE');
  } catch (error) {
    console.error('Error syncing events:', error);
  }
};

export const useUIEventsTracing = () => {
  useEventListener('online', () => syncEventsToBackend('back online'));
  usePeriodicTask('sync UI events to backend', SYNC_INTERVAL_MILLIS, () => syncEventsToBackend('periodic task'));
};
