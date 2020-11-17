import { ChangeDetectionStrategy, Component, Input, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { SurveyState } from '../../store/survey.state';
import { SurveyInstance } from '../../../interfaces/survey-instance.interface';

@Component({
  selector: 'app-survey-responses-summary',
  templateUrl: './survey-responses-summary.component.html',
  styleUrls: ['./survey-responses-summary.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SurveyResponsesSummaryComponent implements OnInit {

  @Input() surveyInstances: SurveyInstance[];

  constructor(
    private _store: Store<SurveyState>
  ) { }

  ngOnInit(): void {
  }

}
