import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Store } from '@ngrx/store';
import { editSurvey } from './survey-create.actions';
import { selectSurvey } from '../../store/survey.selectors';
import { SurveyState } from '../../store/survey.state';
import { patchFormValues } from '../../../operators/patch-form-values.rx-operator';
import { SurveyEditInfo } from '../../interfaces/survey-edit-info.interface';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'app-survey-create',
  templateUrl: './survey-create.component.html',
  styleUrls: ['./survey-create.component.scss']
})
export class SurveyCreateComponent implements OnInit, OnDestroy {

  form: FormGroup = this._formDefinition;

  private _destroySubject = new Subject<void>();

  constructor(
    private _builder: FormBuilder,
    private _store: Store<SurveyState>
  ) { }

  ngOnInit(): void {
    this._store.select(selectSurvey).pipe(
      takeUntil(this._destroySubject),
      patchFormValues(this.form)
    ).subscribe();
  }

  onValueUpdate() {
    const surveyEditInfo = this.form.getRawValue() as SurveyEditInfo;
    this._store.dispatch(editSurvey({ surveyEditInfo }));
  }

  get _formDefinition() {
    return this._builder.group({
      title: [''],
      description: [''],
      canTakeAnonymously: ['']
    });
  }

  ngOnDestroy(): void {
    this._destroySubject.next();
  }

}
