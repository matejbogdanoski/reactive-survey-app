import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { SurveyInstancePreview } from '../../../interfaces/survey-instance-preview.interface';
import { Store } from '@ngrx/store';
import { SurveyState } from '../../store/survey.state';
import { selectSinglePreviewInstance } from '../../store/survey.selectors';
import { ActivatedRoute } from '@angular/router';
import { findInstancePreview } from './single-instance-preview.actions';
import { filter } from 'rxjs/operators';
import * as _ from 'lodash';

@Component({
  templateUrl: './single-instance-preview.page.html',
  styleUrls: ['./single-instance-preview.page.scss']
})
export class SingleInstancePreviewPage implements OnInit {

  surveyInstancePreview$: Observable<SurveyInstancePreview>;

  constructor(
    private _store: Store<SurveyState>,
    private _route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    const instanceId = +this._route.snapshot.paramMap.get('id');
    this._store.dispatch(findInstancePreview({ instanceId }));
    this.surveyInstancePreview$ = this._store.select(selectSinglePreviewInstance).pipe(
      filter(it => !_.isEmpty(it))
    );
  }

}
