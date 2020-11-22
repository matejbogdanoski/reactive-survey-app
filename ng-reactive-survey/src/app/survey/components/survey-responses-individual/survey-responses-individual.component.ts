import { ChangeDetectionStrategy, Component, Input, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { SurveyState } from '../../store/survey.state';
import { SurveyInstance } from '../../../interfaces/survey-instance.interface';
import { PageEvent } from '@angular/material/paginator';
import { Survey } from '../../../interfaces/survey.interface';
import { Observable } from 'rxjs';
import { selectSurvey } from '../../store/survey.selectors';
import { QuestionAnswer } from '../../../interfaces/question-answer.interface';
import * as _ from 'lodash';

@Component({
    selector: 'app-survey-responses-individual',
    templateUrl: './survey-responses-individual.component.html',
    styleUrls: ['./survey-responses-individual.component.scss'],
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class SurveyResponsesIndividualComponent implements OnInit {

    @Input() surveyInstances: SurveyInstance[];
    currentPage = 0;
    survey$: Observable<Survey>;
    answers: QuestionAnswer[];

    constructor(
        private _store: Store<SurveyState>
    ) { }

    ngOnInit(): void {
        this.answers = this.surveyInstances[this.currentPage].questionAnswers;
        this.survey$ = this._store.select(selectSurvey);
    }

    onPageChange(pageEvent: PageEvent) {
        this.currentPage = pageEvent.pageIndex;
        this.answers = _.cloneDeep(this.surveyInstances[this.currentPage].questionAnswers);
    }

}
