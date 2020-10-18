export function update<T>(entity: T, fieldsToUpdate: Partial<T>) {
  return { ...entity, ...fieldsToUpdate };
}
