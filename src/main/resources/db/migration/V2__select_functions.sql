drop function survey.find_options(_survey_question_id bigint);
create or replace function survey.find_options(_survey_question_id bigint) returns jsonb
as
$$
begin
    return (select json_agg(
            json_build_object(
                    'id', sqo.id,
                    'surveyQuestionId', sqo.survey_question_id,
                    'label', sqo.label,
                    'position', sqo.position
                )
        )
            from survey.survey_question_options sqo
            where sqo.survey_question_id = _survey_question_id);
end;
$$ language plpgsql;

drop function survey.find_survey_by_natural_key(_natural_key text);
create or replace function survey.find_survey_by_natural_key(_natural_key text)
    returns table (
        id bigint,
        title text,
        natural_key text,
        can_take_anonymously bool,
        description text,
        questions json
    )
as
$$
begin

    return query select s.id, s.title, s.natural_key, s.can_take_anonymously, s.description,
                     json_agg(
                             json_build_object(
                                     'id', sq.id,
                                     'surveyId', s.id,
                                     'questionTypeId', sq.question_type_id,
                                     'name', sq.name,
                                     'position', sq.position,
                                     'isRequired', sq.is_required,
                                     'options', survey.find_options(sq.id)
                                 )
                         ) as questions
                 from survey.surveys s
                          join survey.survey_questions sq on s.id = sq.survey_id
                 where s.natural_key = _natural_key
                 group by 1;

end;
$$ language plpgsql;
