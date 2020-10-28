import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Survey } from '../../interfaces/survey.interface';
import { QuestionType } from '../../enum/question-type.enum';
import { SurveyEditInfo } from '../../interfaces/edit-infos/survey-edit-info.interface';
import { SurveyQuestionOption } from '../../interfaces/survey-question-option.interface';
import { SurveyQuestion } from '../../interfaces/survey-question.interface';

@Injectable()
export class SurveyService {
  private readonly path = `api/surveys`;

  private readonly mockSurvey = {
    naturalKey: 'b2a3d658-1244-11eb-adc1-0242ac120002',
    canTakeAnonymously: false,
    description: null,
    title: 'Untitled Survey',
    questions: [
      {
        id: new Date().getMilliseconds(),
        questionType: QuestionType.MULTIPLE_CHOICE,
        position: 1,
        name: 'Untitled Question',
        options: [
          {
            id: 1,
            label: 'Option 1',
            position: 1
          } as SurveyQuestionOption
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

  public editSurveyInfo(surveyId: number, surveyUpdated: Partial<SurveyEditInfo>): Observable<Survey> {
    return of({
      ...this.mockSurvey,
      title: surveyUpdated.title || this.mockSurvey.title,
      description: surveyUpdated.description || this.mockSurvey.description,
      canTakeAnonymously: surveyUpdated.canTakeAnonymously || this.mockSurvey.canTakeAnonymously
    });
    // return this._http.patch<Survey>(`${this.path}/${surveyId}`, { surveyUpdated });
  }

  public findSurveyByNaturalKey(naturalKey: string): Observable<Survey> {
    // return of(this.mockSurvey);
    return this._http.get<Survey>(`${this.path}/natural-key/${naturalKey}`);
  }
}
