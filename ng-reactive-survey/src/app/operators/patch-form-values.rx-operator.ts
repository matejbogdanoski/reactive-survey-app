import { FormGroup } from '@angular/forms';
import { tap } from 'rxjs/operators';

export const patchFormValues = <T>(formGroup: FormGroup) => tap(values => {
  if (!formGroup || !values) { return; }
  const keys = Object.keys(values);
  keys.forEach(key => formGroup.contains(key) && formGroup.patchValue({ [key]: values[key] }));
});
