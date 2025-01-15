import axios from 'axios';
import { apiBasePath } from '../constants';
import { unboxAxiosResponse } from '../utils';

export const loginRequest = (username, password) => {
  return axios
    .post(`${apiBasePath}/auth`, {
      username,
      password,
    })
    .then((response) => unboxAxiosResponse(response));
};

export const loginByQrCode = (qrCode) => {
  return axios.post(`${apiBasePath}/auth/by-qrcode`, { qrCode }).then((response) => unboxAxiosResponse(response));
};

export const logoutRequest = () => {
  return axios.post(`${apiBasePath}/userWorkflows/logout`);
  //.then((response) => unboxAxiosResponse(response)) // don't unbox it because there is no response
};
