import { Component, OnInit, ViewChild } from '@angular/core';
import { TakenSurveysDatasource } from './datasource/taken-surveys.datasource';
import { Observable } from 'rxjs';
import { MatPaginator } from '@angular/material/paginator';
import { SurveyInstanceService } from '../../../shared/services/survey-instance/survey-instance.service';

@Component({
  templateUrl: './taken-surveys-list.page.html',
  styleUrls: ['./taken-surveys-list.page.scss']
})
export class TakenSurveysListPage implements OnInit {
  displayedColumns: string[] = ['rowNumber', 'surveyTitle', 'dateTaken'];
  dataSource: TakenSurveysDatasource;
  length$: Observable<number>;

  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(
    private _surveyInstanceService: SurveyInstanceService
  ) { }

  ngOnInit(): void {
    this.dataSource = new TakenSurveysDatasource(this._surveyInstanceService);
    this.dataSource.loadSurveyInstanceGridRows(0, 5);
    this.length$ = this._surveyInstanceService.countAllByUser();
  }

  loadPage() {
    this.dataSource.loadSurveyInstanceGridRows(this.paginator.pageIndex, this.paginator.pageSize);
  }

  getRouterLink(id: number): string {
    return `../instance/${id}`;
  }

}
