import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Store } from '@ngrx/store';
import { SurveyQuestion } from '../../interfaces/survey-question.interface';
import { QuestionType } from '../../enum/question-type.enum';
import { addSurveyQuestion, deleteSurveyQuestion, updateSurveyQuestion } from './survey-question-create.actions';
import { selectSurveyQuestions } from '../../store/survey.selectors';
import { SurveyState } from '../../store/survey.state';

@Component({
  selector: 'survey-question-create',
  templateUrl: './survey-question-create.component.html',
  styleUrls: ['./survey-question-create.component.scss']
})
export class SurveyQuestionCreateComponent implements OnInit {

  QUESTION_TYPE = QuestionType;

  questions$: Observable<SurveyQuestion[]>;

  constructor(private _store: Store<SurveyState>) { }

  ngOnInit(): void {
    this.questions$ = this._store.select(selectSurveyQuestions);
  }

  createSurveyQuestion() {
    this._store.dispatch(addSurveyQuestion());
  }

  updateQuestionSurveyType(question: SurveyQuestion, questionType: QuestionType) {
    this._store.dispatch(updateSurveyQuestion({ surveyQuestion: question, changes: { questionType } }));
  }

  delete(question: SurveyQuestion) {
    this._store.dispatch(deleteSurveyQuestion({ surveyQuestion: question }));
  }

}
