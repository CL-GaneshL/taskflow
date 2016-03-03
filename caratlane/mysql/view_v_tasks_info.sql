CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = CURRENT_USER 
    SQL SECURITY DEFINER
VIEW `v_tasks_info` AS
    SELECT 
        -- ----------------------------------------------
        -- allocation's id
        -- ----------------------------------------------
        `task_allocations`.`id` AS `id`,

        -- ----------------------------------------------
        -- task's id at the origin of the allocation
        -- ----------------------------------------------
        `task_allocations`.`task_id` AS `task_id`,

        -- ----------------------------------------------
        -- create a title for that task
        -- ----------------------------------------------
        getTitle(`project_id`, `skill_id`) AS `title`,

        -- ----------------------------------------------
        -- employee's id for that allocation
        -- ----------------------------------------------
        `task_allocations`.`employee_id` AS `employee_id`,

        -- ----------------------------------------------
        -- employee's fullname for that allocation
        -- ----------------------------------------------
        (SELECT 
                `employees`.`fullName`
            FROM
                `employees`
            WHERE
                (`employees`.`id` = `task_allocations`.`employee_id`)) AS `employee_full_name`,

        -- ----------------------------------------------
        -- skill's link to that task
        -- ----------------------------------------------
        `tasks`.`skill_id` AS `skill_id`,

        -- ----------------------------------------------
        -- the project the task belongs to
        -- ----------------------------------------------
        `tasks`.`project_id` AS `project_id`,

        -- ----------------------------------------------
        -- allocation duration
        -- ----------------------------------------------
        `task_allocations`.`duration` AS `duration`,

        -- ----------------------------------------------
        -- allocation completion
        -- ----------------------------------------------
        `task_allocations`.`completion` AS `completion`,

        -- ----------------------------------------------
        -- whether or not the task is completed
        -- ----------------------------------------------
        `tasks`.`completed` AS `task_completed`,

        -- ----------------------------------------------
        -- whether or not the allocation is completed
        -- ----------------------------------------------
        `task_allocations`.`completed` AS `completed`,

        -- ----------------------------------------------
        -- the number of products completed
        -- ----------------------------------------------
        `task_allocations`.`nb_products_completed` AS `nb_products_completed`,

        -- ----------------------------------------------
        -- the project's number of products
        -- ----------------------------------------------
        (SELECT 
                `projects`.`nb_products`
            FROM
                `projects`
            WHERE
                (`projects`.`id` = `tasks`.`project_id`)) AS `project_nb_products`,

        -- ----------------------------------------------
        -- total duration of the task
        -- ----------------------------------------------
        (SELECT 
                `skills`.`duration`
            FROM
                `skills`
            WHERE
                (`skills`.`id` = `tasks`.`skill_id`)) AS `task_duration`,

        -- ----------------------------------------------
        -- if the task belongs to an open project
        -- ----------------------------------------------
        (SELECT 
                `projects`.`open`
            FROM
                `projects`
            WHERE
                (`projects`.`id` = `tasks`.`project_id`)) AS `open`,

        -- ----------------------------------------------
        -- start date of the allocation
        -- ----------------------------------------------
        `task_allocations`.`start_date` AS `start_date`

    FROM
        `tasks`, `task_allocations`
    WHERE
        (`tasks`.`id` = `task_allocations`.`task_id`)
