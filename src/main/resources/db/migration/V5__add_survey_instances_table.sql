create table survey.survey_instances (
    id bigserial primary key,

    survey_id bigint references survey.surveys(id),

    --should reference a user id
    taken_by bigint,

    date_taken timestamp with time zone
);

alter table survey.question_answers
    drop column answered_by;

alter table survey.question_answers
    drop column survey_id;

alter table survey.question_answers
    add column survey_instance_id bigint references survey.survey_instances(id);
