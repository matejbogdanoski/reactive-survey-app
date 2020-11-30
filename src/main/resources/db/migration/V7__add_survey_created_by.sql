alter table survey.surveys
    drop column can_take_anonymously;

alter table survey.surveys
    add column created_by bigint references access.users(id);

alter table survey.survey_instances
    drop column taken_by;

alter table survey.survey_instances
    add column taken_by bigint references access.users(id);
