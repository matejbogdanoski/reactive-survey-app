import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SurveyInstance } from '../../../interfaces/survey-instance.interface';

@Injectable()
export class SurveyInstanceService {
  private readonly path = 'api/survey-instances';

  constructor(
    private _http: HttpClient
  ) {}

  public addBulkQuestionAnswers(questionAnswerMap: Map<string, any>, surveyId: number) {
    return this._http.post(`${this.path}/${surveyId}`, questionAnswerMap);
  }

  public findAllInstancesBySurveyId(surveyId: number): Observable<SurveyInstance[]> {
    return this._http.get<SurveyInstance[]>(`${this.path}/${surveyId}`)
  }
}
