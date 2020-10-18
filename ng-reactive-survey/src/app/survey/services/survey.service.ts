import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SurveyState } from '../survey-state';
import { SurveyCreate } from '../interfaces/survey-create.interface';

@Injectable()
export class SurveyService {
  private readonly path = `api/surveys`;

  constructor(
    private _http: HttpClient
  ) {
  }

  public createSurvey(survey: SurveyState): Observable<SurveyCreate> {
    return this._http.post<SurveyCreate>(`${this.path}`, { survey });
  }
}
