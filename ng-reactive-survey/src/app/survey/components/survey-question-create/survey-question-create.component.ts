import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Store } from '@ngrx/store';
import { SurveyQuestion } from '../../interfaces/survey-question.interface';
import { getQuestionTypes, QuestionType } from '../../enum/question-type.enum';
import {
  addSurveyQuestion,
  deleteSurveyQuestion,
  duplicateSurveyQuestion,
  editSurveyQuestion,
  updateSurveyQuestionPosition
} from './survey-question-create.actions';
import { selectSurveyQuestions } from '../../store/survey.selectors';
import { SurveyState } from '../../store/survey.state';
import { CdkDragDrop } from '@angular/cdk/drag-drop';
import { FormBuilder, FormControl } from '@angular/forms';
import { tap } from 'rxjs/operators';

@Component({
  selector: 'survey-question-create',
  templateUrl: './survey-question-create.component.html',
  styleUrls: ['./survey-question-create.component.scss']
})
export class SurveyQuestionCreateComponent implements OnInit {

  questions$: Observable<SurveyQuestion[]>;
  questionTypes$: Observable<QuestionType[]>;

  form = this._builder.group({});

  constructor(
    private _store: Store<SurveyState>,
    private _builder: FormBuilder
  ) { }

  ngOnInit(): void {
    this.questions$ = this._store.select(selectSurveyQuestions).pipe(
      //todo make this better
      tap(questions => questions.forEach(q => this.form.addControl(`question_${q.id}`, new FormControl(q.name))))
    );
    this.questionTypes$ = getQuestionTypes();
  }

  createSurveyQuestion() {
    this._store.dispatch(addSurveyQuestion());
  }

  updateQuestionSurveyType(question: SurveyQuestion, questionType: QuestionType) {
    this._store.dispatch(editSurveyQuestion({ id: question.id, changes: { questionType } }));
  }

  updateQuestionSurveyName(question: SurveyQuestion) {
    const name = this.form.get(`question_${question.id}`).value;
    this._store.dispatch(editSurveyQuestion({ id: question.id, changes: { name } }));
  }

  delete(question: SurveyQuestion) {
    this._store.dispatch(deleteSurveyQuestion({ id: question.id }));
  }

  duplicate(question: SurveyQuestion) {
    this._store.dispatch(duplicateSurveyQuestion({ question }));
  }

  changePosition(event: CdkDragDrop<SurveyQuestion[]>) {
    if(event.previousIndex === event.currentIndex) return ;
    const surveyQuestion = event.item.data as SurveyQuestion;
    this._store.dispatch(updateSurveyQuestionPosition({
      id: surveyQuestion.id,
      previousIndex: event.previousIndex,
      currentIndex: event.currentIndex
    }));
  }

}
