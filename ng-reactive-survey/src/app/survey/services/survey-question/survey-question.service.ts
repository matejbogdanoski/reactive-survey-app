import { Injectable } from '@angular/core';
import { SurveyQuestion } from '../../interfaces/survey-question.interface';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { SurveyQuestionEditInfo } from '../../interfaces/edit-infos/survey-question-edit-info.interface';
import { map } from 'rxjs/operators';
import { moveItemInArray } from '@angular/cdk/drag-drop';
import * as _ from 'lodash';
import { Survey } from '../../interfaces/survey.interface';

@Injectable()
export class SurveyQuestionService {
  private readonly path = (surveyId: number) => `api/surveys/${surveyId}/questions`;

  constructor(
    private _http: HttpClient
  ) {
  }

  public createSurveyQuestion(survey: any): Observable<SurveyQuestion> {
    const surveyId = (survey.survey as Survey).id;
    return this._http.post<SurveyQuestion>(`${this.path(surveyId)}`, {});
  }

  public findAllBySurvey(surveyId: number): Observable<SurveyQuestion[]> {
    return this._http.get<SurveyQuestion[]>(this.path(surveyId));
  }

  public editSurveyQuestion(surveyQuestionId: number, surveyQuestionUpdated: Partial<SurveyQuestionEditInfo>,
                            survey: any): Observable<SurveyQuestion> {
    const surveyId = (survey.survey as Survey).id;
    return this._http.patch<SurveyQuestion>(`${this.path(surveyId)}/${surveyQuestionId}`, surveyQuestionUpdated);
  }

  public updateSurveyQuestionPosition(surveyQuestionId: number,
                                      previousIndex: number,
                                      currentIndex: number,
                                      //todo delete this only for mock purposes
                                      survey: any): Observable<SurveyQuestion[]> {
    const surveyQuestions = survey.survey.questions as SurveyQuestion[];
    const clonedQuestions = _.cloneDeep(surveyQuestions);
    moveItemInArray(clonedQuestions, previousIndex, currentIndex);
    return of(clonedQuestions);
    // return this._http.post<SurveyQuestion[]>(`${this.path}/${surveyQuestionId}/update-position`, { previousIndex, currentIndex });
  }

  public duplicateQuestion(surveyQuestion: SurveyQuestion,
                           //todo delete this only for mock purposes
                           survey: any
  ): Observable<SurveyQuestion[]> {
    const surveyEntity = survey.survey as Survey;
    // const surveyQuestions = surveyEntity.questions;
    // const clonedQuestions = _.cloneDeep(surveyQuestions);
    // const duplicatedQuestion = { ...surveyQuestion, id: new Date().getMilliseconds(), position: surveyQuestion.position + 1 };
    // clonedQuestions.push(duplicatedQuestion);
    // clonedQuestions.sort((q1, q2) => q1.position - q2.position);
    // return of(clonedQuestions);
    return this._http.post<SurveyQuestion[]>(`${this.path(surveyEntity.id)}/${surveyQuestion.id}/duplicate`, {});
  }

  public deleteSurveyQuestion(surveyQuestionId: number, survey: any): Observable<number> {
    const surveyId = (survey.survey as Survey).id;
    return this._http.delete(`${this.path(surveyId)}/${surveyQuestionId}`).pipe(
          map(_ => surveyQuestionId),
        );
  }
}
