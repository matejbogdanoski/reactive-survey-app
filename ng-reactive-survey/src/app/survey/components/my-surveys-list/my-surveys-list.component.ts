import { AfterViewInit, ChangeDetectionStrategy, Component, OnInit, ViewChild } from '@angular/core';
import { SurveyService } from '../../services/survey/survey.service';
import { MatPaginator } from '@angular/material/paginator';
import { Observable } from 'rxjs';
import { SurveysDataSource } from './datasource/surveys.datasource';
import { tap } from 'rxjs/operators';

@Component({
  selector: 'app-my-surveys-list',
  templateUrl: './my-surveys-list.component.html',
  styleUrls: ['./my-surveys-list.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class MySurveysListComponent implements OnInit, AfterViewInit {
  displayedColumns: string[] = ['rowNumber', 'title', 'description'];
  dataSource: SurveysDataSource;
  length$: Observable<number>;

  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(
    private _service: SurveyService
  ) { }

  ngOnInit(): void {
    this.dataSource = new SurveysDataSource(this._service);
    this.dataSource.loadSurveys(0, 5);
    this.length$ = this._service.countMySurveys();
  }

  ngAfterViewInit(): void {
    this.paginator.page.pipe(
      tap(() => this.loadSurveysPage())
    ).subscribe();
  }

  loadSurveysPage() {
    this.dataSource.loadSurveys(this.paginator.pageIndex, this.paginator.pageSize);
  }

  getRouterLink(id: number){
    return `../edit/${id}`;
  }

}
