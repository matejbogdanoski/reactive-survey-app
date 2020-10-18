import { Injectable } from '@angular/core';
import { SurveyQuestionCreate } from '../interfaces/survey-question.interface';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class SurveyQuestionService {
  private readonly path = `api/survey/questions`;

  constructor(
    private _http: HttpClient
  ) {
  }

  public createSurveyQuestion(surveyQuestion: SurveyQuestionCreate): Observable<SurveyQuestionCreate> {
    return this._http.post<SurveyQuestionCreate>(`${this.path}`, { surveyQuestion });
  }
}
