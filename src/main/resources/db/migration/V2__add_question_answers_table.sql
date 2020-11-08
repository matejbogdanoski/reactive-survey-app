drop table if exists survey.survey_instances;

create table survey.question_answers(
    id bigserial primary key,
    survey_question_id bigint references survey.survey_questions(id) not null,
    answer text,
    --user id
    answered_by bigint
)
