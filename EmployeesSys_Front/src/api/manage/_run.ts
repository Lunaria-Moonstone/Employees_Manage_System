import axios, { Axios, type AxiosResponse } from "axios";

import { config } from '@/api/_server';

/**
 * get user list
 * @param data 
 * @returns user list
 */
export const getUsers: Function = async (data: { [key: string]: string | number }) => {
  var result: AxiosResponse<any, any> | null = null;
  try {
    result = await axios({
      method: 'get',
      url: '/api/',
      params: data
    });
  } catch (err) {
    console.error(err);
  }
  return result?.data;
};

/**
 * del single user
 * @param id 
 * @returns user deleted success or not 
 */
export const delUser: Function = async (id: string) => {
  var result: AxiosResponse<any, any> | null = null;
  try {
    result = await axios({
      method: 'delete',
      url: '/api/',
      params: { id }
    });
  } catch (err) {
    console.error(err);
    return false;
  }
  console.log(result)
  return result?.data.ok;
};

/**
 * delete users
 * @param id_list 
 * @returns users deleted success or not 
 */
export const delUsers: Function = async (id_list: string[]) => {
  // try {
  //   for (var id of id_list) {
  //     await axios({
  //       method: 'delete',
  //       url: config['user'],
  //       params: { id }
  //     });
  //   }
  // } catch (err) {
  //   console.error(err);
  //   return false;
  // }
  // return true;
  var successCnt: number = 0;
  for (var id of id_list) {
    if ((await delUser(id)))
      successCnt += 1
  }
  return successCnt;
};

/**
 * edit user info 
 * @param id 
 * @param data 
 * @returns 
 */
export const editUser: Function = async (id: string, data: { [key: string]: string | number }) => {
  var result: AxiosResponse<any, any> | null = null;
  try {
    result = await axios({
      method: 'put',
      url: '/api/',
      params: { id },
      data: data
    });
  } catch (err) {
    console.error(err);
    return false;
  }
  return result?.data.ok;
}

export const getUser: Function = async (id: string) => {
  var result: AxiosResponse<any, any> | null = null;
  try {
    result = await axios({
      url: '/api/',
      method: 'get',
      params: { id }
    });
  } catch (err) {
    console.log(err);
  }
  return result?.data;
}

export const addUser: Function = async (data: { [key: string]: string | number }) => {
  var result: AxiosResponse<any, any> | null = null;
  data['password'] = '123456'
  console.log(data)
  try {
    result = await axios({
      url: '/api/',
      method: 'post',
      data: data
    });
  } catch (err) {
    console.log(err);
  }
  return result?.data;
}