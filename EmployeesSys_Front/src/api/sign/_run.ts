import axios, { type AxiosResponse } from "axios";

import { config } from "@/api/_server";

export const signIn: Function = async (username: string, password: string) => {
  var result: AxiosResponse<any, any> | null = null;
  try {
    result = await axios({
      method: 'get',
      url: '/api/',
      params: { password, username }
    });
  } catch (err) {
    console.error(err);
  }
  return result?.data;
}

export const signUp: Function = async (username: string, password: string, name: string) => {
  var result: AxiosResponse<any, any> | null = null;
  try {
    result = await axios({
      method: 'post',
      url: '/api/',
      data: { password, username, name }
    });
  } catch (err) {
    console.error(err);
  }
  return result?.data;
}