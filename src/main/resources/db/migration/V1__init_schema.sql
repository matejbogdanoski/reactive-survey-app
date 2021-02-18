create schema if not exists access;
create table access.users (
    id bigserial primary key,
    username text not null unique,
    email text not null,
    first_name text,
    last_name text,
    password_hash text
);

create schema if not exists survey;
create table survey.surveys (
    id bigserial primary key,
    title text,
    description text,
    natural_key text not null unique default uuid_in(
            overlay(overlay(md5(random()::text || ':' || clock_timestamp()::text) placing '4' from 13) placing
                    to_hex(floor(random() * (11 - 8 + 1) + 8)::int)::text from 17)::cstring)::text,
    created_by bigint references access.users(id)
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
    survey_id bigint references survey.surveys(id),
    taken_by bigint references access.users(id),
    date_taken timestamp with time zone
);

create table survey.question_answers(
    id bigserial primary key,
    survey_question_id bigint references survey.survey_questions(id) not null,
    answer text,
    survey_instance_id bigint references survey.survey_instances(id)
);

create table survey.survey_invitations (
    id bigserial,
    survey_id bigint references survey.surveys(id),
    user_id bigint references access.users(id),
    taken bool default false
);

--notification
CREATE OR REPLACE FUNCTION notify_event() RETURNS TRIGGER AS
$$
DECLARE
    payload JSON;
BEGIN
    payload = row_to_json(NEW);
    PERFORM pg_notify('answer_saved_notification', payload::text);
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;;

CREATE TRIGGER notify_login_event
    AFTER INSERT OR UPDATE OR DELETE
    ON survey.question_answers
    FOR EACH ROW
EXECUTE PROCEDURE notify_event();;
