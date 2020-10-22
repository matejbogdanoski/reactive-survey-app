import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Store } from '@ngrx/store';
import { SurveyQuestion } from '../../interfaces/survey-question.interface';
import { getQuestionTypes, QuestionType } from '../../enum/question-type.enum';
import { addSurveyQuestion, deleteSurveyQuestion, editSurveyQuestion } from './survey-question-create.actions';
import { selectSurveyQuestions } from '../../store/survey.selectors';
import { SurveyState } from '../../store/survey.state';

@Component({
  selector: 'survey-question-create',
  templateUrl: './survey-question-create.component.html',
  styleUrls: ['./survey-question-create.component.scss']
})
export class SurveyQuestionCreateComponent implements OnInit {

  questions$: Observable<SurveyQuestion[]>;
  questionTypes$: Observable<QuestionType[]>;

  constructor(private _store: Store<SurveyState>) { }

  ngOnInit(): void {
    this.questions$ = this._store.select(selectSurveyQuestions);
    this.questionTypes$ = getQuestionTypes();
  }

  createSurveyQuestion() {
    this._store.dispatch(addSurveyQuestion());
  }

  updateQuestionSurveyType(question: SurveyQuestion, questionType: QuestionType) {
    this._store.dispatch(editSurveyQuestion({ id: question.id, changes: { questionType } }));
  }

  delete(question: SurveyQuestion) {
    this._store.dispatch(deleteSurveyQuestion({ id: question.id }));
  }

  duplicate(question: SurveyQuestion) {
    //todo not yet implemented
  }

}
