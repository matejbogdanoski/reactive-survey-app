import { CollectionViewer, DataSource } from '@angular/cdk/collections';
import { PendingSurveyGridRow } from '../../../interfaces/grid-rows/pending-survey-grid.row';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { SurveyInvitationsService } from '../../../services/survey-invitations/survey-invitations.service';
import { catchError, finalize } from 'rxjs/operators';

export class PendingSurveysDatasource implements DataSource<PendingSurveyGridRow> {

  private pendingSurveyRowsSubject = new BehaviorSubject<PendingSurveyGridRow[]>([]);
  private loadingSubject = new BehaviorSubject<boolean>(false);
  public loading$ = this.loadingSubject.asObservable();

  constructor(
    private _service: SurveyInvitationsService
  ) {}

  connect(collectionViewer: CollectionViewer): Observable<PendingSurveyGridRow[] | ReadonlyArray<PendingSurveyGridRow>> {
    return this.pendingSurveyRowsSubject.asObservable();
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.pendingSurveyRowsSubject.complete();
    this.loadingSubject.complete();
  }

  loadPendingSurveysGridRows(page: number, size: number) {
    this.loadingSubject.next(true);
    this._service.findSurveyInvitationsPage(page, size).pipe(
      catchError(() => of([])),
      finalize(() => this.loadingSubject.next(false))
    ).subscribe(rows => this.pendingSurveyRowsSubject.next(rows));
  }

  count(): number {
    return this.pendingSurveyRowsSubject.value.length;
  }

}
