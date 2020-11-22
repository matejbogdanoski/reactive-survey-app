import { Injectable, NgZone } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SurveyInstance } from '../../../interfaces/survey-instance.interface';
import { QuestionType } from '../../../survey/enum/question-type.enum';

export interface AnswerDTO {
  id: number;
  surveyQuestionId: number;
  surveyInstanceId: number;
  answer: string;
  questionName: string;
  questionType: QuestionType;
}

@Injectable()
export class SurveyInstanceService {
  private readonly path = 'api/survey-instances';
  private zone = new NgZone({ enableLongStackTrace: false });

  constructor(
    private _http: HttpClient
  ) {}

  public addBulkQuestionAnswers(questionAnswerMap: Map<string, string>, surveyId: number) {
    return this._http.post(`${this.path}/${surveyId}`, questionAnswerMap);
  }

  public findAllInstancesBySurveyId(surveyId: number): Observable<SurveyInstance[]> {
    return this._http.get<SurveyInstance[]>(`${this.path}/${surveyId}`);
  }

  public streamAnswers(surveyId: number): Observable<AnswerDTO> {
    const eventSource = new EventSource(`${this.path}/stream-answers/${surveyId}`);
    return new Observable(observer => {
      eventSource.onmessage = message => this.zone.run(() => observer.next(JSON.parse(message.data) as AnswerDTO));
      eventSource.onerror = error => this.zone.run(() => observer.error(error));
      return () => eventSource.close();
    });
  }
}
