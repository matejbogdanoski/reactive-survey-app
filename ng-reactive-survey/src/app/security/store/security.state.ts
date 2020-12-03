import { UserInfo } from '../interfaces/user-info.interface';

export interface SecurityState {
  username: string;
  token: string;
  userInfo: UserInfo;
  error: any;
}

export const initialState = {
  username: undefined,
  token: undefined,
  userInfo: {} as UserInfo,
  error: undefined
} as SecurityState;
