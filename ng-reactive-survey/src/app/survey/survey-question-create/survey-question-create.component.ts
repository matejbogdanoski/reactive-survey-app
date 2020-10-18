import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Store } from '@ngrx/store';
import { SurveyQuestionCreateState } from './survey-question-create.state';
import { SurveyQuestionCreate } from '../interfaces/survey-question.interface';
import { QuestionType } from '../enum/question-type.enum';
import { addSurveyQuestion, deleteSurveyQuestion, updateSurveyQuestion } from './survey-question-create.actions';
import { selectSurveyQuestions } from './survey-question-create.selectors';

@Component({
  templateUrl: './survey-question-create.component.html',
  styleUrls: ['./survey-question-create.component.scss']
})
export class SurveyQuestionCreateComponent implements OnInit {

  QUESTION_TYPE = QuestionType;

  questions$: Observable<SurveyQuestionCreate[]>;

  constructor(private store: Store<SurveyQuestionCreateState>) { }

  ngOnInit(): void {
    this.questions$ = this.store.select(selectSurveyQuestions);
  }

  createSurveyQuestion() {
    const surveyQuestion: SurveyQuestionCreate = {
      isRequired: false,
      name: 'Untitled question',
      options: [],
      questionType: QuestionType.MULTIPLE_CHOICE,
      position: -1
    };

    this.store.dispatch(addSurveyQuestion({ surveyQuestion }));
  }

  updateQuestionSurveyType(question: SurveyQuestionCreate, questionType: QuestionType) {
    this.store.dispatch(updateSurveyQuestion({ surveyQuestion: question, changes: { questionType } }));
  }

  delete(question: SurveyQuestionCreate) {
    this.store.dispatch(deleteSurveyQuestion({ surveyQuestion: question }));
  }

}
