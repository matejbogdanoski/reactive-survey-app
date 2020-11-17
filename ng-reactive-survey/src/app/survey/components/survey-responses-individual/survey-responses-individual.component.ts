import { ChangeDetectionStrategy, Component, Input, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { SurveyState } from '../../store/survey.state';
import { SurveyInstance } from '../../../interfaces/survey-instance.interface';

@Component({
  selector: 'app-survey-responses-individual',
  templateUrl: './survey-responses-individual.component.html',
  styleUrls: ['./survey-responses-individual.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SurveyResponsesIndividualComponent implements OnInit {

  @Input() surveyInstances: SurveyInstance[];

  constructor(
    private _store: Store<SurveyState>
  ) { }

  ngOnInit(): void {
  }

}
