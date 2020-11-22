import { Pipe, PipeTransform } from '@angular/core';
import { QuestionAnswer } from '../../../interfaces/question-answer.interface';
import { SurveyQuestionOption } from '../../../interfaces/survey-question-option.interface';
import * as _ from 'lodash';

@Pipe({
  name: 'chartLabels'
})
export class ChartLabelsPipe implements PipeTransform {

  transform(value: QuestionAnswer[]): string[] {
    const labels = value.map(it => {
      const json = JSON.parse(it.answer);
      if (Array.isArray(json)) {
        return json.map(o => o as SurveyQuestionOption);
      } else {
        return json as SurveyQuestionOption;
      }
    });
    const flattened = _.flatMap(labels)
      .sort((o1, o2) => o1.id - o2.id)
      .map(option => option.label);
    return [...new Set(flattened)];
  }

}
