import { ChangeDetectionStrategy, Component, Input, OnInit } from '@angular/core';
import { SurveyState } from '../../store/survey.state';
import { Store } from '@ngrx/store';
import { findSurveyInstances } from './survey-responses-component.actions';
import * as _ from 'lodash';
import { Observable } from 'rxjs';
import { SurveyInstance } from '../../../interfaces/survey-instance.interface';
import { selectSurveyInstances } from '../../store/survey.selectors';

@Component({
  selector: 'app-survey-responses',
  templateUrl: './survey-responses.component.html',
  styleUrls: ['./survey-responses.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SurveyResponsesComponent implements OnInit {

  @Input() surveyId: number;

  surveyInstances$: Observable<SurveyInstance[]>;

  isEmpty = _.isEmpty;

  constructor(
    private _state: Store<SurveyState>
  ) { }

  ngOnInit(): void {
    this._state.dispatch(findSurveyInstances({ surveyId: this.surveyId }));
    this.surveyInstances$ = this._state.select(selectSurveyInstances);
  }

}
