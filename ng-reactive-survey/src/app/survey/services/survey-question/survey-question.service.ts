import { Injectable } from '@angular/core';
import { SurveyQuestion } from '../../interfaces/survey-question.interface';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { QuestionType } from '../../enum/question-type.enum';
import { SurveyQuestionEditInfo } from '../../interfaces/edit-infos/survey-question-edit-info.interface';
import { map } from 'rxjs/operators';
import { SurveyQuestionOption } from '../../interfaces/survey-question-option.interface';

@Injectable()
export class SurveyQuestionService {
  private readonly path = `api/surveys/questions`;

  private readonly mockQuestion = {
    questionType: QuestionType.MULTIPLE_CHOICE,
    position: 1,
    name: 'Untitled Question',
    options: [
      {
        id: 1,
        label: 'Option 1'
      } as SurveyQuestionOption
    ],
    isRequired: false
  } as SurveyQuestion;

  constructor(
    private _http: HttpClient
  ) {
  }

  public createSurveyQuestion(): Observable<SurveyQuestion> {
    return of({ id: new Date().getMilliseconds(), ...this.mockQuestion });
    // return this._http.post<SurveyQuestion>(`${this.path}`, { });
  }

  public editSurveyQuestion(surveyQuestionId: number, surveyQuestionUpdated: Partial<SurveyQuestionEditInfo>): Observable<SurveyQuestion> {
    return of({
      ...this.mockQuestion,
      isRequired: surveyQuestionUpdated.isRequired || this.mockQuestion.isRequired,
      name: surveyQuestionUpdated.name || this.mockQuestion.name,
      position: surveyQuestionUpdated.position || this.mockQuestion.position,
      questionType: surveyQuestionUpdated.questionType || this.mockQuestion.questionType,
      id: surveyQuestionId
    });
    // return this._http.patch<SurveyQuestion>(`${this.path}/${surveyQuestionId}`, { surveyQuestionUpdated });
  }

  public deleteSurveyQuestion(surveyQuestionId: number): Observable<number> {
    return of({}).pipe(
      map(_ => surveyQuestionId)
    );
    // return this._http.delete(`${this.path}/${surveyQuestionId}`).pipe(
    //       map(_ => surveyQuestionId),
    //     );
  }
}
