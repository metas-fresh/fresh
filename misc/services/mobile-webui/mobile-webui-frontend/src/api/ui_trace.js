import axios from 'axios';
import { apiBasePath } from '../constants';
import { unboxAxiosResponse } from '../utils';

export const postEventsToBackend = (events) => {
  return axios.post(`${apiBasePath}/trace`, { events }).then((response) => unboxAxiosResponse(response));
};
