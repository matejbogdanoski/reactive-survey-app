import { Pipe, PipeTransform } from '@angular/core';
import { QuestionAnswer } from '../../../interfaces/question-answer.interface';
import { SurveyQuestionOption } from '../../../interfaces/survey-question-option.interface';
import * as _ from 'lodash';

@Pipe({
  name: 'chartData'
})
export class ChartDataPipe implements PipeTransform {

  transform(value: QuestionAnswer[]): number[] {
    const options = value.map(it => JSON.parse(it.answer) as SurveyQuestionOption);
    const grouped = _.groupBy(options, 'id');
    return Object.keys(grouped).sort((id1, id2) => +id1 - +id2).map(id => grouped[id].length);
  }

}
