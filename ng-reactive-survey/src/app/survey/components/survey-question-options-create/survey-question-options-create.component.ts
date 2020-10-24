import { ChangeDetectionStrategy, Component, Input, OnInit } from '@angular/core';
import { hasOptions, QuestionType } from '../../enum/question-type.enum';
import { SurveyQuestionOption } from '../../interfaces/survey-question-option.interface';
import { CdkDragDrop } from '@angular/cdk/drag-drop';
import { FormBuilder, FormControl } from '@angular/forms';

@Component({
  selector: 'survey-question-options-create',
  templateUrl: './survey-question-options-create.component.html',
  styleUrls: ['./survey-question-options-create.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SurveyQuestionOptionsCreateComponent implements OnInit {

  readonly QUESTION_TYPES = QuestionType;

  form = this._builder.group({});

  @Input() questionType: QuestionType;
  @Input() questionOptions: SurveyQuestionOption[];
  hasOptions = hasOptions;

  constructor(
    private _builder: FormBuilder
  ) { }

  ngOnInit(): void {
    //todo make this better
    this.questionOptions.forEach(qo => {
      this.form.addControl(`option_${qo.id}`, new FormControl(qo.label));
    });
  }

  deleteOption(option: SurveyQuestionOption) {
    //todo not yet implemented
  }

  addOption() {
    //todo not yet implemented

  }

  changeOptionPosition(event: CdkDragDrop<SurveyQuestionOption[]>) {
    //todo not yet implemented
    if (event.previousIndex === event.currentIndex) {
      return;
    }
  }

  updateQuestionOptionLabel(option: SurveyQuestionOption) {
    //todo not yet implemented
  }

}
