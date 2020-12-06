import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SurveyInvitation } from '../../interfaces/survey-invitation.interface';
import { HttpClient, HttpParams } from '@angular/common/http';
import { SurveyInvitationGridRow } from '../../interfaces/grid-rows/survey-invitation-grid.row';

@Injectable()
export class SurveyInvitationsService {
  private readonly path = 'api/survey-invitations';

  constructor(
    private _http: HttpClient
  ) {}

  public createSurveyInvitations(surveyId: number, username: string) {
    return this._http.post(`${this.path}`, { surveyId, username });
  }

  public findAllSurveyInvitations(surveyId: number): Observable<SurveyInvitation[]> {
    return this._http.get<SurveyInvitation[]>(`${this.path}/${surveyId}`);
  }

  public findSurveyInvitationsPage(page: number, size: number): Observable<SurveyInvitationGridRow[]> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    return this._http.get<SurveyInvitationGridRow[]>(`${this.path}/pending`, { params });
  }
}

