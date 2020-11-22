import { ChangeDetectionStrategy, Component, Input, OnDestroy, OnInit } from '@angular/core';
import { SurveyState } from '../../store/survey.state';
import { Store } from '@ngrx/store';
import { aggregateAnswer, findSurveyInstances } from './survey-responses-component.actions';
import * as _ from 'lodash';
import { Observable, Subject } from 'rxjs';
import { SurveyInstance } from '../../../interfaces/survey-instance.interface';
import { selectSurveyInstances } from '../../store/survey.selectors';
import { filter, takeUntil } from 'rxjs/operators';
import { SurveyInstanceService } from '../../../shared/services/survey-instance/survey-instance.service';

@Component({
  selector: 'app-survey-responses',
  templateUrl: './survey-responses.component.html',
  styleUrls: ['./survey-responses.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SurveyResponsesComponent implements OnInit, OnDestroy {
  private _destroySubject = new Subject<void>();

  @Input() surveyId: number;

  surveyInstances$: Observable<SurveyInstance[]>;

  constructor(
    private _store: Store<SurveyState>,
    private _service: SurveyInstanceService
  ) { }

  ngOnInit(): void {
    this._store.dispatch(findSurveyInstances({ surveyId: this.surveyId }));
    this.surveyInstances$ = this._store.select(selectSurveyInstances).pipe(
      filter(it => !_.isEmpty(it))
    );
    //todo uncomment this

    // this._service.streamAnswers(this.surveyId).pipe(
    //   takeUntil(this._destroySubject)
    // ).subscribe(answer => {
    //   this._store.dispatch(aggregateAnswer({ answer }));
    // });
  }

  ngOnDestroy(): void {
    this._destroySubject.next();
  }

}
