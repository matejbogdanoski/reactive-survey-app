import { Component, Input, OnInit } from '@angular/core';
import { Survey } from '../../../interfaces/survey.interface';
import { QuestionType } from '../../../survey/enum/question-type.enum';
import { timePickerDarkThemeConfig } from '../../config/time-picker-theme.config';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Component({
  selector: 'app-survey-renderer',
  templateUrl: './survey-renderer.component.html',
  styleUrls: ['./survey-renderer.component.scss']
})
export class SurveyRendererComponent implements OnInit {

  @Input() surveyStructure$: Observable<Survey>;
  surveyStructure: Survey;

  questionType = QuestionType;
  darkTheme = timePickerDarkThemeConfig;
  form = this._builder.group({});

  constructor(
    private _builder: FormBuilder
  ) { }

  ngOnInit(): void {
    this.surveyStructure$.pipe(
      tap(survey => this.initControls(survey))
    ).subscribe(surveyStructure => this.surveyStructure = surveyStructure);
  }

  initControls(survey: Survey) {
    survey?.questions?.forEach(q => {
      if (q.questionType == QuestionType.CHECKBOX) {
        const group = new FormGroup({});
        q.options.forEach(o => group.addControl(o.id.toString(), new FormControl()));
        this.form.addControl(q.id.toString(), group);
      } else {
        this.form.addControl(q.id.toString(), new FormControl());
      }
    });
  }

  submit() {
    const value = this.form.getRawValue();
    console.log(value);
  }
}
