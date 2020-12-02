import { createAction, props } from '@ngrx/store';

const source = '[Single Instance Preview Page]';

export const findInstancePreview = createAction(`${source} Find instance preview`, props<{ instanceId: number }>());
