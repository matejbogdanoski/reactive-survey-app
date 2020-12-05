import { FormGroup } from '@angular/forms';

export function confirmPasswordValidator(controlName: string, matchingControlName: string) {
  return (form: FormGroup) => {
    const control = form.get(controlName);
    const matchingControl = form.get(matchingControlName);

    if (matchingControl.errors && !matchingControl.errors.confirmPasswordValidator) {
      return;
    }

    if (control.value !== matchingControl.value) {
      matchingControl.setErrors({ confirmPasswordValidator: true });
    } else {
      matchingControl.setErrors(null);
    }
  };
}
