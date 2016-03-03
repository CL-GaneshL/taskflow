CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = CURRENT_USER
    SQL SECURITY DEFINER
VIEW `v_project_skills` AS
    SELECT 
        `skills`.`id` AS `id`,
        `projects`.`id` AS `project_id`,
        `skills`.`duration` AS `duration`
    FROM
        (((`projects`
        JOIN `skills`)
        JOIN `project_templates`)
        JOIN `project_templates_have_skills`)
    WHERE
        ((`projects`.`template_id` = `project_templates`.`id`)
            AND (`project_templates`.`id` = `project_templates_have_skills`.`template_id`)
            AND (`project_templates_have_skills`.`skill_id` = `skills`.`id`))
