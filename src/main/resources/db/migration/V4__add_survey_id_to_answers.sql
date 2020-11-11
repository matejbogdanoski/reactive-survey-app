alter table survey.question_answers
    add column survey_id bigint references survey.surveys(id);
