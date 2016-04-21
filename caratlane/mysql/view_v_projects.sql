CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = CURRENT_USER
    SQL SECURITY DEFINER
VIEW `v_projects` AS
    SELECT 
        `projects`.`id` AS `id`,
        `projects`.`reference` AS `reference`,
        `projects`.`template_id` AS `template_id`,
        `projects`.`nb_products` AS `nb_products`,
        `projects`.`priority` AS `priority`,
        `projects`.`start_date` AS `start_date`,
        `projects`.`end_date` AS `end_date`,
        `projects`.`open` AS `open`
    FROM
        `projects`
    ORDER BY `projects`.`priority` DESC , `projects`.`start_date` ASC

