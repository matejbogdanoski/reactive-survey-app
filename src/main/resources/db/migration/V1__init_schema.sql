create schema if not exists survey;

create table survey.surveys (
    id bigserial primary key,
    name text
);

create table survey.question_types (
    id bigint primary key,
    name text
);

create table survey.questions (
    id bigserial primary key,
    question_type_id bigint references survey.question_types(id)
);

create table survey.survey_questions (
    id bigserial primary key,
    question_id bigint references survey.questions(id),
    survey_id bigint references survey.surveys(id)
);
