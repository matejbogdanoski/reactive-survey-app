import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Survey } from '../../interfaces/survey.interface';
import { SurveyEditInfo } from '../../interfaces/edit-infos/survey-edit-info.interface';

@Injectable()
export class SurveyService {
  private readonly path = `api/surveys`;

  constructor(
    private _http: HttpClient
  ) {
  }

  public createSurvey(): Observable<Survey> {
    return this._http.post<Survey>(`${this.path}`, {});
  }

  public editSurveyInfo(survey: any, surveyUpdated: Partial<SurveyEditInfo>): Observable<Survey> {
    const surveyId = (survey.survey as Survey).id;
    return this._http.patch<Survey>(`${this.path}/${surveyId}`, { ...surveyUpdated });
  }

  public findSurveyByNaturalKey(naturalKey: string): Observable<Survey> {
    return this._http.get<Survey>(`${this.path}/natural-key/${naturalKey}`);
  }

  public findSurveyById(id: number): Observable<Survey> {
    return this._http.get<Survey>(`${this.path}/${id}`);
  }
}
