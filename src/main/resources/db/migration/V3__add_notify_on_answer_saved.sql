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
