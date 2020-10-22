import { ChangeDetectionStrategy, Component, Input, OnInit } from '@angular/core';
import { QuestionType } from '../../enum/question-type.enum';

@Component({
  selector: 'survey-question-options-create',
  templateUrl: './survey-question-options-create.component.html',
  styleUrls: ['./survey-question-options-create.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SurveyQuestionOptionsCreateComponent implements OnInit {

  readonly QUESTION_TYPES = QuestionType;

  @Input() questionType: QuestionType;

  constructor() { }

  ngOnInit(): void {
  }

}
