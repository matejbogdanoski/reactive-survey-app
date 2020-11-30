import { CollectionViewer, DataSource } from '@angular/cdk/collections';
import { Survey } from '../../../../interfaces/survey.interface';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { SurveyService } from '../../../services/survey/survey.service';
import { catchError, finalize } from 'rxjs/operators';

export class SurveysDataSource implements DataSource<Survey> {
  private surveysSubject = new BehaviorSubject<Survey[]>([]);
  private loadingSubject = new BehaviorSubject<boolean>(false);
  public loading$ = this.loadingSubject.asObservable();

  constructor(
    private _service: SurveyService
  ) {}

  connect(collectionViewer: CollectionViewer): Observable<Survey[] | ReadonlyArray<Survey>> {
    return this.surveysSubject.asObservable();
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.surveysSubject.complete();
    this.loadingSubject.complete();
  }

  loadSurveys(page: number, size: number) {
    this.loadingSubject.next(true);
    this._service.findMySurveys(page, size).pipe(
      catchError(() => of([])),
      finalize(() => this.loadingSubject.next(false))
    ).subscribe(surveys => this.surveysSubject.next(surveys));
  }

}
