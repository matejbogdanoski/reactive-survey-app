create schema if not exists access;

create table access.users (
    id bigserial primary key,
    username text not null unique,
    email text not null,
    first_name text,
    last_name text,
    password_hash text
);

alter table survey.survey_instances
    drop column taken_by;

alter table survey.survey_instances
    add column taken_by bigint references access.users(id)
