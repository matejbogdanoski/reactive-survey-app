import { Component, Input, OnInit } from '@angular/core';
import { Survey } from '../../../interfaces/survey.interface';
import { QuestionType } from '../../../survey/enum/question-type.enum';
import { timePickerDarkThemeConfig } from '../../config/time-picker-theme.config';

@Component({
  selector: 'app-survey-renderer',
  templateUrl: './survey-renderer.component.html',
  styleUrls: ['./survey-renderer.component.scss']
})
export class SurveyRendererComponent implements OnInit {

  @Input() surveyStructure: Survey;

  questionType = QuestionType;
  darkTheme = timePickerDarkThemeConfig;

  constructor() { }

  ngOnInit(): void {
  }

  submit() {

  }
}
