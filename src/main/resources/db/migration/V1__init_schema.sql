create schema if not exists survey;

create table survey.surveys (
    id bigserial primary key,
    title text,
    description text,
    natural_key uuid unique not null,
    can_take_anonymously boolean not null default false
);

create table survey.question_types (
    id bigint primary key,
    name text
);

insert into survey.question_types(id, name)
values (0, 'SHORT_TEXT'),
    (1, 'LONG_TEXT'),
    (2, 'MULTIPLE_CHOICE'),
    (3, 'CHECKBOX'),
    (4, 'DROPDOWN'),
    (5, 'LINEAR_SCALE'),
    (6, 'DATE'),
    (7, 'TIME');

create table survey.survey_questions (
    id bigserial primary key,
    survey_id bigint references survey.surveys(id) not null,
    question_type_id bigint references survey.question_types(id) not null,
    name text,
    options jsonb,
    position int not null,
    is_required boolean not null default false
);

create table survey.survey_instances (
    id bigserial primary key,
    survey_id bigint references survey.surveys(id) not null,
    data jsonb
);
