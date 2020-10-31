create schema if not exists survey;

create table survey.surveys (
    id bigserial primary key,
    title text,
    description text,
    natural_key text not null unique default uuid_in(
            overlay(overlay(md5(random()::text || ':' || clock_timestamp()::text) placing '4' from 13) placing
                    to_hex(floor(random() * (11 - 8 + 1) + 8)::int)::text from 17)::cstring)::text,
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
    (5, 'DATE'),
    (6, 'TIME');

create table survey.survey_questions (
    id bigserial primary key,
    survey_id bigint references survey.surveys(id) not null,
    question_type_id bigint references survey.question_types(id) not null,
    name text,
    position int not null,
    is_required boolean not null default false
);

create table survey.survey_question_options (
    id bigserial primary key,
    label text,
    position int,
    survey_question_id bigint references survey.survey_questions(id)
);

create table survey.survey_instances (
    id bigserial primary key,
    survey_id bigint references survey.surveys(id) not null,
    data jsonb
);

----test survey
insert into survey.surveys(title, description, can_take_anonymously)
values ('Test survey', 'Survey Description', true);

insert into survey.survey_questions(survey_id, question_type_id, name, position, is_required)
VALUES ((select id from survey.surveys s where s.title = 'Test survey'), 0, 'Question 1', 1, false),
((select id from survey.surveys s where s.title = 'Test survey'), 2, 'Question 2', 2, false);

insert into survey.survey_question_options(label, position, survey_question_id)
values ('Option 1', 1, (select id from survey.survey_questions where name = 'Question 2')),
('Option 2', 2, (select id from survey.survey_questions where name = 'Question 2'));
