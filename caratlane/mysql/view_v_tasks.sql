CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = CURRENT_USER
    SQL SECURITY DEFINER
VIEW `v_tasks` AS
    SELECT 
        `tasks`.`id` AS `id`,
--        `tasks`.`employee_id` AS `employee_id`,
        `tasks`.`skill_id` AS `skill_id`,
        `tasks`.`project_id` AS `project_id`,
--        `tasks`.`completion` AS `completion`,
        `tasks`.`completed` AS `completed`,
        `skills`.`duration` AS `duration`
    FROM
        (`tasks`
        JOIN `skills`)
    WHERE
        ((`tasks`.`skill_id` = `skills`.`id`))
