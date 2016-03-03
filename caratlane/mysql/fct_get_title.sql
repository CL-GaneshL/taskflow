
CREATE FUNCTION `getTitle` (project_id INT, skill_id INT)
RETURNS TEXT
BEGIN
	DECLARE ref TEXT;
        DECLARE des TEXT;
        DECLARE res TEXT;
    
	SELECT `projects`.`reference` INTO ref
		FROM `projects` 
		WHERE `projects`.`id` = project_id;

	SELECT `skills`.`designation` INTO des
		FROM `skills` 
		WHERE `skills`.`id` = skill_id;

        SET res = CONCAT(ref, ' - ', des);

RETURN (res);
END 
