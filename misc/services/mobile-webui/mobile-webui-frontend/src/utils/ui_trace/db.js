import Dexie from 'dexie';
import { v4 as uuidv4 } from 'uuid';

const db = new Dexie('uiTraceEvents');
db.version(1).stores({
  props: 'key,value',
  events: 'id, event',
});

export const saveEvent = async (event) => {
  try {
    const record = { id: event.id, event };
    await db.events.add(record);
    // console.log('saveEvent', { record });
  } catch (error) {
    console.error('Error saving event', error);
  }
};

export const getAllEvents = async () => {
  const records = await db.events.toArray();
  return records.map((record) => record.event);
};

export const clearEvents = async () => {
  await db.events.clear();
};

export const getOrCreateDeviceId = async () => {
  const deviceId = await db.props.get('device_id');
  if (!deviceId || !deviceId.value) {
    const newDeviceId = uuidv4();
    await db.props.put({ key: 'device_id', value: newDeviceId });
    return newDeviceId;
  } else {
    return deviceId.value;
  }
};
