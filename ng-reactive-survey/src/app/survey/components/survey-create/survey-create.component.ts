import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Store } from '@ngrx/store';
import { editSurvey } from './survey-create.actions';
import { Survey } from '../../interfaces/survey.interface';
import { selectSurvey } from '../../store/survey.selectors';
import { SurveyState } from '../../store/survey.state';

@Component({
  selector: 'app-survey-create',
  templateUrl: './survey-create.component.html',
  styleUrls: ['./survey-create.component.scss']
})
export class SurveyCreateComponent implements OnInit {

  form: FormGroup = this._formDefinition;

  constructor(
    private _builder: FormBuilder,
    private _store: Store<SurveyState>
  ) { }

  ngOnInit(): void {
    this._store.select(selectSurvey).subscribe(data => {
      this.form.patchValue(data);
    });
  }

  onFormSubmit() {
    const survey = this.form.getRawValue() as Survey;
    this._store.dispatch(editSurvey({ survey }));
  }

  get _formDefinition() {
    return this._builder.group({
      title: [''],
      description: [''],
      canTakeAnonymously: ['']
    });
  }

}
