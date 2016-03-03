CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = CURRENT_USER
    SQL SECURITY DEFINER
VIEW `v_skill_employees` AS
    SELECT 
        `employees_have_skills`.`employeeId` AS `employee_id`,
        `skills`.`id` AS `skill_id`
    FROM
        (`skills`
        JOIN `employees_have_skills`)
    WHERE
        (`skills`.`id` = `employees_have_skills`.`skillId`)
