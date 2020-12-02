import { CollectionViewer, DataSource } from '@angular/cdk/collections';
import { SurveyInstanceGridRow } from '../../../../interfaces/survey-instance-grid.row';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { SurveyInstanceService } from '../../../../shared/services/survey-instance/survey-instance.service';
import { catchError, finalize } from 'rxjs/operators';

export class TakenSurveysDatasource implements DataSource<SurveyInstanceGridRow> {
  private surveyInstanceRowsSubject = new BehaviorSubject<SurveyInstanceGridRow[]>([]);
  private loadingSubject = new BehaviorSubject<boolean>(false);
  public loading$ = this.loadingSubject.asObservable();

  constructor(
    private _service: SurveyInstanceService
  ) {
  }

  connect(collectionViewer: CollectionViewer): Observable<SurveyInstanceGridRow[] | ReadonlyArray<SurveyInstanceGridRow>> {
    return this.surveyInstanceRowsSubject.asObservable();
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.surveyInstanceRowsSubject.complete();
    this.loadingSubject.complete();
  }

  loadSurveyInstanceGridRows(page: number, size: number) {
    this.loadingSubject.next(true);
    this._service.findAllByUser(page, size).pipe(
      catchError(() => of([])),
      finalize(() => this.loadingSubject.next(false))
    ).subscribe(rows => this.surveyInstanceRowsSubject.next(rows));
  }

}
