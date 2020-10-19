import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Survey } from '../interfaces/survey.interface';
import { SurveyQuestion } from '../interfaces/survey-question.interface';
import { QuestionType } from '../enum/question-type.enum';
import { QuestionOption } from '../interfaces/question-option.interface';

@Injectable()
export class SurveyService {
  private readonly path = `api/surveys`;

  private readonly mockSurvey = {
    naturalKey: 'b2a3d658-1244-11eb-adc1-0242ac120002',
    canTakeAnonymously: false,
    description: 'Survey Description',
    title: 'Survey Title',
    questions: [
      {
        id: new Date().getMilliseconds(),
        questionType: QuestionType.MULTIPLE_CHOICE,
        position: 1,
        name: 'Untitled Question',
        options: [
          {
            id: 1,
            label: 'Option 1'
          } as QuestionOption
        ],
        isRequired: false
      } as SurveyQuestion
    ]
  } as Survey;

  constructor(
    private _http: HttpClient
  ) {
  }

  public createSurvey(): Observable<Survey> {
    return of({ id: new Date().getMilliseconds(), ...this.mockSurvey });
    // return this._http.post<Survey>(`${this.path}`, {});
  }

  public findSurveyByNaturalKey(naturalKey: string): Observable<Survey> {
    return of(this.mockSurvey);
    // return this._http.get<Survey>(`${this.path}/natural-key/${naturalKey}`);
  }
}
