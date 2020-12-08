import { ChangeDetectionStrategy, Component, Input, OnInit } from '@angular/core';
import { Survey } from '../../../interfaces/survey.interface';
import { hasOptionsStatic, QuestionType } from '../../../survey/enum/question-type.enum';
import { timePickerDarkThemeConfig } from '../../config/time-picker-theme.config';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { SurveyInstanceService } from '../../../shared/services/survey-instance/survey-instance.service';
import { SurveyQuestionOption } from '../../../interfaces/survey-question-option.interface';
import * as _ from 'lodash';
import { QuestionAnswer } from '../../../interfaces/question-answer.interface';
import { Router } from '@angular/router';

@Component({
  selector: 'app-survey-renderer',
  templateUrl: './survey-renderer.component.html',
  styleUrls: ['./survey-renderer.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SurveyRendererComponent implements OnInit {

  @Input() surveyStructure: Survey;

  @Input() disable = false;

  @Input() set surveyAnswers(surveyAnswers: QuestionAnswer[]) {
    this._surveyAnswers = surveyAnswers;
    this.form = this._builder.group({});
    this.initControls(this.surveyStructure);
  };

  get surveyAnswers(): QuestionAnswer[] {
    return this._surveyAnswers;
  }

  private _surveyAnswers: QuestionAnswer[];

  questionType = QuestionType;
  darkTheme = timePickerDarkThemeConfig;
  form = this._builder.group({});

  optionComparator = (o1: SurveyQuestionOption, o2: SurveyQuestionOption) => o1?.id === o2?.id;

  constructor(
    private _builder: FormBuilder,
    private _notification: ToastrService,
    private _service: SurveyInstanceService,
    private _router: Router
  ) { }

  ngOnInit(): void {
    this.initControls(this.surveyStructure);
  }

  initControls(survey: Survey) {
    survey?.questions?.forEach(q => {
      const answer = this.surveyAnswers?.find(ans => ans.questionId === q.id)?.answer;
      let parsedAnswer = null;
      if (!!answer) {
        parsedAnswer = JSON.parse(answer);
      }
      const required = q.isRequired ? [Validators.required] : [];
      if (q.questionType == QuestionType.CHECKBOX) {
        const group = new FormGroup({});
        q.options.forEach(o => {
          const optionFormControlName = this.getOptionFormControlName(o);
          const isChecked = (parsedAnswer as SurveyQuestionOption[])?.find(it => it.id === o.id);
          if (!!isChecked) {
            group.addControl(optionFormControlName, new FormControl({ value: true, disabled: this.disable }));
          } else {
            group.addControl(optionFormControlName, new FormControl({ value: false, disabled: this.disable }));
          }
        });
        this.form.addControl(q.id.toString(), group);
      } else {
        if (hasOptionsStatic(q.questionType)) {
          const op = (parsedAnswer as SurveyQuestionOption);
          const surveyQuestionOption = q.options?.find(o => o?.id === op?.id);
          this.form.addControl(q.id.toString(), new FormControl({ value: surveyQuestionOption, disabled: this.disable }, ...required));
        } else {
          this.form.addControl(q.id.toString(), new FormControl({ value: parsedAnswer || '', disabled: this.disable }, ...required));
        }
      }
    });
  }

  submit() {
    const value = this.form.getRawValue();
    const checkboxIds = this.surveyStructure.questions.filter(q => q.questionType === QuestionType.CHECKBOX).map(q => q.id);
    checkboxIds.forEach(checkboxId => {
      const checkboxGroup = value[checkboxId.toString()];
      value[checkboxId.toString()] = Object.keys(checkboxGroup).filter(key => checkboxGroup[key] === true).map(it => JSON.parse(it));
    });
    const questionAnswerMap = _.cloneDeep(value);
    Object.keys(questionAnswerMap).forEach(key => {
      questionAnswerMap[key] = JSON.stringify(questionAnswerMap[key]);
    });
    this._service.addBulkQuestionAnswers(questionAnswerMap, this.surveyStructure.id).subscribe(() => {
      this._notification.success('Successfully submitted!');
      this._router.navigate(['/survey/taken-surveys']);
    }, error => {
      this._notification.error(error.error);
    });
  }

  getOptionFormControlName(option: SurveyQuestionOption): string {
    return JSON.stringify(option);
  }

  isControlInvalid(controlName: string) {
    const control = this.form.get(controlName);
    return control.invalid;
  }

  hasRequiredError(controlName: string): boolean {
    const control = this.form.get(controlName);
    return control.errors.required;
  }
}
