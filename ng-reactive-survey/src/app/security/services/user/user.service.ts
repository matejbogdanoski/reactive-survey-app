import { Injectable } from '@angular/core';
import { UserCreateRequest } from '../../interfaces/request/user-create.request';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { UserInfo } from '../../interfaces/user-info.interface';
import { UpdateUserInfoRequest } from '../../interfaces/request/update-user-info.request';
import { UpdatePasswordRequest } from '../../interfaces/request/update-password.request';
import { AuthenticationResponse } from '../../interfaces/response/authentication.response';
import * as _ from 'lodash';
import { SecurityState } from '../../store/security.state';

@Injectable()
export class UserService {
  private readonly path = `api/users`;

  constructor(
    private _http: HttpClient
  ) {}

  public register(request: UserCreateRequest): Observable<UserCreateRequest> {
    return this._http.post<UserCreateRequest>(`${this.path}/signup`, request);
  }

  public findUserInfo(): Observable<UserInfo> {
    return this._http.get<UserInfo>(`${this.path}/user-info`);
  }

  public updateUserInfo(userId: number, userInfoRequest: UpdateUserInfoRequest): Observable<UserInfo> {
    return this._http.patch<UserInfo>(`${this.path}/user-info/${userId}`, userInfoRequest);
  }

  public updatePassword(updatePasswordRequest: UpdatePasswordRequest, state: SecurityState): Observable<AuthenticationResponse> {
    const updateRequest = _.cloneDeep(updatePasswordRequest);
    updateRequest.token = state.token;
    return this._http.patch<AuthenticationResponse>(`${this.path}/update-password`, updateRequest);
  }

}
