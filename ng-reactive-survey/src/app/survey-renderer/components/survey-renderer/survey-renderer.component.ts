import { Component, Input, OnInit } from '@angular/core';
import { Survey } from '../../../interfaces/survey.interface';

@Component({
  selector: 'app-survey-renderer',
  templateUrl: './survey-renderer.component.html',
  styleUrls: ['./survey-renderer.component.scss']
})
export class SurveyRendererComponent implements OnInit {

  @Input() surveyStructure: Survey;

  constructor() { }

  ngOnInit(): void {
  }

}
