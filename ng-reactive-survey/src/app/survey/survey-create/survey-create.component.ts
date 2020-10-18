import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Store } from '@ngrx/store';
import { SurveyCreateState } from './survey-create.state';
import { createSurvey } from './survey-create.actions';
import { SurveyCreate } from '../interfaces/survey-create.interface';
import { selectSurvey } from './survey-create.selectors';

@Component({
  selector: 'app-survey-create',
  templateUrl: './survey-create.component.html',
  styleUrls: ['./survey-create.component.scss']
})
export class SurveyCreateComponent implements OnInit {

  form: FormGroup = this._formDefinition;

  constructor(
    private _builder: FormBuilder,
    private _store: Store<SurveyCreateState>
  ) { }

  ngOnInit(): void {
    this._store.select(selectSurvey).subscribe(data => {
      this.form.patchValue(data);
    });
  }

  onFormSubmit() {
    const survey = this.form.getRawValue() as SurveyCreate;
    this._store.dispatch(createSurvey({ survey }));
  }

  get _formDefinition() {
    return this._builder.group({
      title: [''],
      description: [''],
      canTakeAnonymously: ['']
    });
  }

}
