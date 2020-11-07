import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Survey } from '../../interfaces/survey.interface';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class SurveyRendererService {
  private readonly path = `api/survey-renderer`;

  constructor(
    private _http: HttpClient
  ) {}

  public findFullSurveyByNaturalKey(naturalKey: string): Observable<Survey> {
    return this._http.get<Survey>(`${this.path}/${naturalKey}`);
  }
}
