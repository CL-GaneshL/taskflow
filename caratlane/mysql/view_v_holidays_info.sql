CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = CURRENT_USER
    SQL SECURITY DEFINER
VIEW `v_holidays_info` AS
    SELECT 
        `holidays`.`id` AS `id`,
        `holidays`.`title` AS `title`,
        `holidays`.`employee_id` AS `employee_id`,
        (SELECT 
                `employees`.`fullName`
            FROM
                `employees`
            WHERE
                (`employees`.`id` = `holidays`.`employee_id`)) AS `employee_full_name`,
        `holidays`.`start_date` AS `start_date`,
        `holidays`.`start_morning_shift` AS `start_morning_shift`,
        `holidays`.`start_afternoon_shift` AS `start_afternoon_shift`,
        `holidays`.`end_date` AS `end_date`,
        `holidays`.`end_morning_shift` AS `end_morning_shift`,
        `holidays`.`end_afternoon_shift` AS `end_afternoon_shift`
    FROM
        (`holidays`
        JOIN `employees`)
    WHERE
        (`holidays`.`employee_id` = `employees`.`id`)