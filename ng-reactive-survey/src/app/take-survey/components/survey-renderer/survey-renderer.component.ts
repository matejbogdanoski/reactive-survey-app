import { ChangeDetectionStrategy, Component, Input, OnInit } from '@angular/core';
import { Survey } from '../../../interfaces/survey.interface';
import { QuestionType } from '../../../survey/enum/question-type.enum';
import { timePickerDarkThemeConfig } from '../../config/time-picker-theme.config';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { SurveyInstanceService } from '../../services/survey-instance/survey-instance.service';
import { SurveyQuestionOption } from '../../../interfaces/survey-question-option.interface';

@Component({
  selector: 'app-survey-renderer',
  templateUrl: './survey-renderer.component.html',
  styleUrls: ['./survey-renderer.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SurveyRendererComponent implements OnInit {

  @Input() surveyStructure: Survey;

  questionType = QuestionType;
  darkTheme = timePickerDarkThemeConfig;
  form = this._builder.group({});

  optionComparator = (o1: SurveyQuestionOption, o2: SurveyQuestionOption) => o1?.id === o2?.id;

  constructor(
    private _builder: FormBuilder,
    private _notification: ToastrService,
    private _service: SurveyInstanceService
  ) { }

  ngOnInit(): void {
    this.initControls(this.surveyStructure);
  }

  initControls(survey: Survey) {
    survey?.questions?.forEach(q => {
      if (q.questionType == QuestionType.CHECKBOX) {
        const group = new FormGroup({});
        q.options.forEach(o => group.addControl(this.getOptionFormControlName(o), new FormControl()));
        this.form.addControl(q.id.toString(), group);
      } else {
        this.form.addControl(q.id.toString(), new FormControl());
      }
    });
  }

  submit() {
    const value = this.form.getRawValue();
    this.surveyStructure.questions.filter(q => q.questionType === QuestionType.CHECKBOX).map(q => q.id)
      .forEach(checkboxId => {
        const checkboxGroup = value[checkboxId.toString()];
        value[checkboxId.toString()] = Object.keys(checkboxGroup).filter(key => checkboxGroup[key] === true);
      });
    this._service.addBulkQuestionAnswers(value, this.surveyStructure.id).subscribe(_ => {
      this._notification.success('Successfully submitted!');
    }, error => {
      this._notification.error(error.message);
    });
  }

  getOptionFormControlName(option: SurveyQuestionOption): string {
    return `{id: ${option.id}, label: ${option.label}}`;
  }
}
