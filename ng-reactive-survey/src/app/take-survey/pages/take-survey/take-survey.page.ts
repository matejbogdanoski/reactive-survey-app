import { Component, OnDestroy, OnInit } from '@angular/core';
import { Survey } from '../../../interfaces/survey.interface';
import { Observable } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { TakeSurveyState } from '../../store/take-survey.state';
import { Store } from '@ngrx/store';
import { selectFullSurvey } from '../../store/take-survey.selectors';
import { clearFullSurvey, findFullSurvey } from './take-survey-page.actions';
import * as _ from 'lodash';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-take-survey',
  templateUrl: './take-survey.page.html',
  styleUrls: ['./take-survey.page.scss']
})
export class TakeSurveyPage implements OnInit, OnDestroy {

  surveyStructure$: Observable<Survey>;

  constructor(
    private _route: ActivatedRoute,
    private _store: Store<TakeSurveyState>
  ) { }

  ngOnInit(): void {
    this._store.dispatch(findFullSurvey({ naturalKey: this._route.snapshot.paramMap.get('naturalKey') }));
    this.surveyStructure$ = this._store.select(selectFullSurvey).pipe(
      filter(it => !_.isEmpty(it))
    );
  }

  ngOnDestroy(): void {
    this._store.dispatch(clearFullSurvey());
  }

}
