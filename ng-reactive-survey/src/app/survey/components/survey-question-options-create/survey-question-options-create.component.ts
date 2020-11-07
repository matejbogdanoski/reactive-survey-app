import { ChangeDetectionStrategy, Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { hasOptions, QuestionType } from '../../enum/question-type.enum';
import { SurveyQuestionOption } from '../../../interfaces/survey-question-option.interface';
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

  hasOptions = hasOptions;
  form = this._builder.group({});

  @Input() questionType: QuestionType;
  @Input() questionOptions: SurveyQuestionOption[];

  @Output() onAddOption = new EventEmitter<void>();
  @Output() onDeleteOption = new EventEmitter<SurveyQuestionOption>();
  @Output() onChangeOptionPosition = new EventEmitter<{ option: SurveyQuestionOption, previousIndex: number, currentIndex: number }>();
  @Output() onChangeOptionLabel = new EventEmitter<{ option: SurveyQuestionOption, newLabel: string }>();

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
    this.onDeleteOption.emit(option);
  }

  addOption() {
    this.onAddOption.emit();
  }

  changeOptionPosition(event: CdkDragDrop<SurveyQuestionOption[]>) {
    if (event.previousIndex === event.currentIndex) {
      return;
    }
    const option = event.item.data as SurveyQuestionOption;
    this.onChangeOptionPosition.emit({
      option: option,
      currentIndex: event.currentIndex,
      previousIndex: event.previousIndex
    });
  }

  updateQuestionOptionLabel(option: SurveyQuestionOption) {
    const newLabel = this.form.get(`option_${option.id}`).value;
    this.onChangeOptionLabel.emit({ option, newLabel: newLabel });
  }

}
