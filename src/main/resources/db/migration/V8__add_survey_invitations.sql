create table survey.survey_invitations (
    id bigserial,
    survey_id bigint references survey.surveys(id),
    user_id bigint references access.users(id),
    taken bool default false
);
