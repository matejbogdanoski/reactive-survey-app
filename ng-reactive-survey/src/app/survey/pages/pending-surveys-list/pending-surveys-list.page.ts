import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { PendingSurveysDatasource } from './datasource/pending-surveys.datasource';
import { Observable, of } from 'rxjs';
import { MatPaginator } from '@angular/material/paginator';
import { SurveyInvitationsService } from '../../services/survey-invitations/survey-invitations.service';
import { catchError, tap } from 'rxjs/operators';

@Component({
  templateUrl: './pending-surveys-list.page.html',
  styleUrls: ['./pending-surveys-list.page.scss']
})
export class PendingSurveysListPage implements OnInit, AfterViewInit {
  displayedColumns: string[] = ['rowNumber', 'surveyTitle', 'surveyDescription'];
  dataSource: PendingSurveysDatasource;
  length$: Observable<number>;

  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(
    private _service: SurveyInvitationsService
  ) { }

  ngOnInit(): void {
    this.dataSource = new PendingSurveysDatasource(this._service);
    this.dataSource.loadPendingSurveysGridRows(0, 5);
    this.length$ = this._service.countAllPendingSurveys().pipe(
      catchError(err => of(10))
    );
  }

  ngAfterViewInit(): void {
    this.paginator.page.pipe(
      tap(() => this.loadPage())
    ).subscribe();
  }

  loadPage() {
    this.dataSource.loadPendingSurveysGridRows(this.paginator.pageIndex, this.paginator.pageSize);
  }

  getSurveyLink(naturalKey: string) {
    return `../../take-survey/${naturalKey}`;
  }

}
