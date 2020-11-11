import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-survey-responses',
  templateUrl: './survey-responses.component.html',
  styleUrls: ['./survey-responses.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SurveyResponsesComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
