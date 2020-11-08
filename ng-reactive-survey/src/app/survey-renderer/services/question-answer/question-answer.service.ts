import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class QuestionAnswerService {
  private readonly path = 'api/question-answers';

  constructor(
    private _http: HttpClient
  ) {}

  public addBulkQuestionAnswers(questionAnswerMap: Map<string, any>) {
    return this._http.post(`${this.path}`, questionAnswerMap);
  }
}
