import { Injectable } from '@angular/core';
import { SurveyQuestion } from '../../interfaces/survey-question.interface';
import { HttpClient } from '@angular/common/http';
import { concat, Observable } from 'rxjs';
import { SurveyQuestionOption } from '../../interfaces/survey-question-option.interface';
import * as _ from 'lodash';
import { moveItemInArray } from '@angular/cdk/drag-drop';
import { map } from 'rxjs/operators';

@Injectable()
export class SurveyQuestionOptionService {
  private readonly path = (questionId: number) => `api/surveys/questions/${questionId}/question-options`;

  constructor(
    private _http: HttpClient
  ) {
  }

  public findAllByQuestion(questionId: number): Observable<SurveyQuestionOption[]> {
    return this._http.get<SurveyQuestionOption[]>(`${this.path(questionId)}`);
  }

  public addNewQuestionOption(surveyQuestion: SurveyQuestion): Observable<SurveyQuestionOption> {
    const surveyQuestionId = surveyQuestion.id;
    return this._http.post<SurveyQuestionOption>(this.path(surveyQuestionId), {});
  }

  deleteQuestionOption(surveyQuestion: SurveyQuestion, surveyQuestionOption: SurveyQuestionOption): Observable<number> {
    const optionId = surveyQuestionOption.id;
    return this._http.delete(`${this.path(surveyQuestion.id)}/${optionId}`).pipe(
      map(_ => optionId)
    );
  }

  updateQuestionOptionPosition(surveyQuestion: SurveyQuestion, optionId: number, previousIndex: number,
                               currentIndex: number): Observable<SurveyQuestionOption[]> {
    const questionClone = _.cloneDeep(surveyQuestion);
    const options = questionClone.options;
    options.find(it => it.id === optionId).position = currentIndex;
    moveItemInArray(options, previousIndex, currentIndex);
    const observables = options.map((o, index) => this.updatePosition(surveyQuestion, o.id, index + 1));
    return concat(...observables).pipe(
      map(_ => options)
    );
  }

  private updatePosition(surveyQuestion: SurveyQuestion, optionId: number, position: number): Observable<SurveyQuestionOption> {
    return this._http.patch<SurveyQuestionOption>(`${this.path(surveyQuestion.id)}/${optionId}/update-position/${position}`, {});
  }

  updateQuestionOptionLabel(surveyQuestion: SurveyQuestion, optionId: number, changedLabel: string): Observable<SurveyQuestionOption> {
    return this._http.patch<SurveyQuestionOption>(`${this.path(surveyQuestion.id)}/${optionId}`, { changedLabel });
  }
}
