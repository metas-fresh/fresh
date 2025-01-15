import { useEffect } from 'react';

export const usePeriodicTask = (taskName, intervalMillis, taskFunc) => {
  useEffect(() => {
    let timeoutId;

    const periodicTask = async () => {
      try {
        await taskFunc();
      } finally {
        // Reschedule the task
        timeoutId = setTimeout(periodicTask, intervalMillis);
      }
    };

    // Start the periodic task
    periodicTask();
    console.log(`Started periodic task: ${taskName}`);

    return () => {
      clearTimeout(timeoutId);
      console.log(`Stopped periodic task: ${taskName}`);
    };
  }, []);
};
