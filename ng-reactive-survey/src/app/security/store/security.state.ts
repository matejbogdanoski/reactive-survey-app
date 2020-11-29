export interface SecurityState {
  username: string;
  token: string;
  error: any;
}

export const initialState = {
  username: undefined,
  token: undefined,
  error: undefined
} as SecurityState;
