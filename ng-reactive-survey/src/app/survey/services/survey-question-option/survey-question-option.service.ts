import { Injectable } from '@angular/core';
import { SurveyQuestion } from '../../interfaces/survey-question.interface';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { SurveyQuestionOption } from '../../interfaces/survey-question-option.interface';
import * as _ from 'lodash';
import { moveItemInArray } from '@angular/cdk/drag-drop';

@Injectable()
export class SurveyQuestionOptionService {
  private readonly path = (questionId: number) => `api/surveys/questions/${questionId}/question-options`;

  constructor(
    private _http: HttpClient
  ) {
  }

  public addNewQuestionOption(
    //todo this will be id
    surveyQuestion: SurveyQuestion
  ): Observable<SurveyQuestion> {
    const clonedQuestion = _.cloneDeep(surveyQuestion);
    const id = clonedQuestion.options[surveyQuestion.options.length - 1].id + 1;
    const newOne = {
      id: id,
      label: `Option ${id}`,
      position: id
    } as SurveyQuestionOption;
    clonedQuestion.options.push(newOne);
    return of(clonedQuestion);
    // return this._http.post<SurveyQuestionOption>(this.path(surveyQuestionId), {});
  }

  deleteQuestionOption(surveyQuestion: SurveyQuestion, surveyQuestionOption: SurveyQuestionOption): Observable<SurveyQuestion> {
    const surveyClone = _.cloneDeep(surveyQuestion);
    surveyClone.options = surveyQuestion.options.filter(op => op.id != surveyQuestionOption.id);
    return of(surveyClone);
    // return this._http.delete(`${this.path(surveyQuestionId)}/${surveyQuestionOption.id}`);
  }

  updateQuestionOptionPosition(surveyQuestion: SurveyQuestion, optionId: number, previousIndex: number,
                               currentIndex: number): Observable<SurveyQuestion> {
    const questionClone = _.cloneDeep(surveyQuestion);
    const options = questionClone.options;
    options.find(it => it.id === optionId).position = currentIndex;
    moveItemInArray(options, previousIndex, currentIndex);
    return of(questionClone);
    // return this._http.patch(`${this.path(surveyQuestionId)}/${surveyQuestionOption.id}/update-position`, {
    // currentIndex,
    // previousIndex
    // });
  }

  updateQuestionOptionLabel(surveyQuestion: SurveyQuestion, optionId: number, changedLabel: string) {
    const questionClone = _.cloneDeep(surveyQuestion);
    const options = questionClone.options;
    options.find(it => it.id === optionId).label = changedLabel;
    return of(questionClone);
    // return this._http.patch(`${this.path(surveyQuestionId)}/${optionId}`, {changedLabel})
  }
}
