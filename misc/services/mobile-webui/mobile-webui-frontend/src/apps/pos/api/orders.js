import axios from 'axios';
import { apiBasePath } from '../../../constants';
import { unboxAxiosResponse } from '../../../utils';

export const getOpenOrdersArray = () => {
  return axios
    .get(`${apiBasePath}/pos/orders`)
    .then((response) => unboxAxiosResponse(response))
    .then((data) => data.list);
};

export const updateOrder = ({ order }) => {
  return axios.post(`${apiBasePath}/pos/orders`, order).then((response) => unboxAxiosResponse(response));
};

export const changeOrderStatusToDraft = ({ order_uuid }) => {
  return axios.post(`${apiBasePath}/pos/orders/${order_uuid}/draft`).then((response) => unboxAxiosResponse(response));
};
export const changeOrderStatusToWaitingPayment = ({ order_uuid }) => {
  return axios
    .post(`${apiBasePath}/pos/orders/${order_uuid}/waitingPayment`)
    .then((response) => unboxAxiosResponse(response));
};

export const changeOrderStatusToVoid = ({ order_uuid }) => {
  return axios.post(`${apiBasePath}/pos/orders/${order_uuid}/void`).then((response) => unboxAxiosResponse(response));
};

export const changeOrderStatusToComplete = ({ order_uuid }) => {
  return axios
    .post(`${apiBasePath}/pos/orders/${order_uuid}/complete`)
    .then((response) => unboxAxiosResponse(response));
};
