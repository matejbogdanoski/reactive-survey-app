import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SurveyService } from '../../services/survey.service';
import { SurveyState } from '../../store/survey.state';
import { Store } from '@ngrx/store';
import { selectSurvey } from '../../store/survey.selectors';
import { findSurvey } from './survey-edit-page.actions';

@Component({
  templateUrl: './survey-edit.page.html',
  styleUrls: ['./survey-edit.page.scss']
})
export class SurveyEditPage implements OnInit {

  constructor(
    private _route: ActivatedRoute,
    private _surveyService: SurveyService,
    private _state: Store<SurveyState>
  ) { }

  ngOnInit(): void {
    this._state.select(selectSurvey).subscribe(survey => {
        const naturalKey = this._route.snapshot.paramMap.get('naturalKey');
        if (naturalKey !== survey.naturalKey) {
          //dispatch action to find the survey from backend
          this._state.dispatch(findSurvey({ naturalKey }));
        }
      }
    );
  }

}
