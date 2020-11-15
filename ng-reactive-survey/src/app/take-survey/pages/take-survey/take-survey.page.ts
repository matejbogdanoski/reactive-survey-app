import { Component, OnInit } from '@angular/core';
import { Survey } from '../../../interfaces/survey.interface';
import { Observable } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { TakeSurveyState } from '../../store/take-survey.state';
import { Store } from '@ngrx/store';
import { selectFullSurvey } from '../../store/take-survey.selectors';
import { findFullSurvey } from './take-survey-page.actions';
import * as _ from 'lodash';

@Component({
  selector: 'app-take-survey',
  templateUrl: './take-survey.page.html',
  styleUrls: ['./take-survey.page.scss']
})
export class TakeSurveyPage implements OnInit {

  surveyStructure$: Observable<Survey>;
  isEmpty = _.isEmpty;

  constructor(
    private _route: ActivatedRoute,
    private _state: Store<TakeSurveyState>
  ) { }

  ngOnInit(): void {
    this._state.dispatch(findFullSurvey({ naturalKey: this._route.snapshot.paramMap.get('naturalKey') }));
    this.surveyStructure$ = this._state.select(selectFullSurvey);
  }

}
