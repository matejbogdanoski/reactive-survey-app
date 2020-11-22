import { Pipe, PipeTransform } from '@angular/core';
import { QuestionAnswer } from '../../../interfaces/question-answer.interface';
import { SurveyQuestionOption } from '../../../interfaces/survey-question-option.interface';

@Pipe({
  name: 'chartLabels'
})
export class ChartLabelsPipe implements PipeTransform {

  transform(value: QuestionAnswer[]): string[] {
    return value.map(it => JSON.parse(it.answer) as SurveyQuestionOption)
      .sort((o1, o2) => o1.id - o2.id)
      .map(option => option.label);
  }

}
