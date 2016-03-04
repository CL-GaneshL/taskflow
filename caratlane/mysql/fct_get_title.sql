
CREATE 
    DEFINER = CURRENT_USER
 FUNCTION `getTitle` (project_id INT, skill_id INT)
 RETURNS TEXT
    DETERMINISTIC
    READS SQL DATA
    SQL SECURITY DEFINER
   
BEGIN
	DECLARE reference TEXT;
        DECLARE designation TEXT;
        DECLARE result TEXT;
    
	SELECT `projects`.`reference` INTO reference
		FROM `projects` 
		WHERE `projects`.`id` = project_id;

	SELECT `skills`.`designation` INTO designation
		FROM `skills` 
		WHERE `skills`.`id` = skill_id;

        SET result = CONCAT(reference, ' - ', designation);

        RETURN (result);
END 
