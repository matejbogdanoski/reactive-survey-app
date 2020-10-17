import { Component, OnInit } from '@angular/core';
import * as actions from './survey-question-create.actions';
import * as surveyQuestionSelectors from './survey-question-create.selectors';
import { Observable } from 'rxjs';
import { Store } from '@ngrx/store';
import { SurveyQuestionCreateState } from './survey-question-create.state';
import { SurveyQuestionCreate } from '../interfaces/survey-question.interface';
import { QuestionType } from '../enum/question-type.enum';
import { Survey } from '../interfaces/survey.interface';

@Component({
  templateUrl: './survey-question-create.component.html',
  styleUrls: ['./survey-question-create.component.scss']
})
export class SurveyQuestionCreateComponent implements OnInit {

  QUESTION_TYPE = QuestionType;

  questions$: Observable<SurveyQuestionCreate[]>;

  constructor(private store: Store<SurveyQuestionCreateState>) { }

  ngOnInit(): void {
    this.questions$ = this.store.select(surveyQuestionSelectors.selectAll);
  }

  createSurveyQuestion() {
    const survey: SurveyQuestionCreate = {
      id: new Date().getMilliseconds(),
      isRequired: false,
      name: 'Untitled question',
      options: [],
      questionType: QuestionType.MULTIPLE_CHOICE,
      position: -1
    };

    this.store.dispatch(new actions.Create(survey));
  }

  updateQuestionSurveyType(id: number, questionType: QuestionType) {
    this.store.dispatch(new actions.Update(id, {
      questionType
    }));
  }

  delete(id: number) {
    this.store.dispatch(new actions.Delete(id));
  }

}
