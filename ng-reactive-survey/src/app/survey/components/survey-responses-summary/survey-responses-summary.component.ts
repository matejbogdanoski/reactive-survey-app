import { ChangeDetectionStrategy, Component, Input, OnInit } from '@angular/core';
import { SurveyInstance } from '../../../interfaces/survey-instance.interface';
import * as _ from 'lodash';
import { QuestionAnswer } from '../../../interfaces/question-answer.interface';
import { hasOptions } from '../../enum/question-type.enum';

@Component({
  selector: 'app-survey-responses-summary',
  templateUrl: './survey-responses-summary.component.html',
  styleUrls: ['./survey-responses-summary.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SurveyResponsesSummaryComponent implements OnInit {

  aggregatedQuestions: _.Dictionary<QuestionAnswer[]>;
  private _surveyInstances: SurveyInstance[];
  hasOptions = hasOptions;

  @Input() set surveyInstances(surveyInstances: SurveyInstance[]) {
    this._surveyInstances = surveyInstances;
    const questionAnswers = _.flatMap(this.surveyInstances.map(i => i.questionAnswers));
    this.aggregatedQuestions = _.groupBy(questionAnswers, 'questionId');
  };

  get surveyInstances(): SurveyInstance[] {
    return this._surveyInstances;
  }

  constructor() { }

  ngOnInit(): void {
  }

}
