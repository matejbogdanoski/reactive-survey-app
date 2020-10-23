import { Pipe, PipeTransform } from '@angular/core';
import { QuestionType } from '../enum/question-type.enum';

@Pipe({
  name: 'questionTypeIcon'
})
export class QuestionTypeIconPipe implements PipeTransform {

  transform(value: QuestionType, ...args: unknown[]): string {
    switch (value) {
      case QuestionType.CHECKBOX:
        return 'check_box';
      case QuestionType.TIME:
        return 'access_time';
      case QuestionType.DATE:
        return 'calendar_today';
      case QuestionType.SHORT_TEXT:
        return 'short_text';
      case QuestionType.LONG_TEXT:
        return 'notes';
      case QuestionType.DROPDOWN:
        return 'arrow_drop_down_circle';
      case QuestionType.MULTIPLE_CHOICE:
        return 'radio_button_checked';
      default:
        return '';
    }
  }

}
