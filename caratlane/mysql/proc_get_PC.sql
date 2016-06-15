
CREATE 
    DEFINER = CURRENT_USER
PROCEDURE `getPC` (project_id INT(11))

BEGIN

    SET @TWO_DECIMALS := 2;
    SET @FOUR_DECIMALS := 4;

    SET @max := 0;
    SET @min := 0;

    -- the first allocation start_date (min) and the last (max)
    SELECT 
            CAST(MAX(`task_allocations`.`start_date`) AS DATE),
            CAST(MIN(`task_allocations`.`start_date`) AS DATE)
            INTO @max, @min 
            FROM `tasks`, `task_allocations`
            WHERE `tasks`.`project_id` = project_id
                AND `task_allocations`.`task_id` = `tasks`.`id`;

    -- make sure that tasks and allocations have been generated for that project
    IF @max IS NOT NULL THEN

        -- number of Products Completed against time
            
        -- serialize the result set
        SELECT PCi FROM (

        -- arithmetic progression --------
        SELECT @sum_product_completed := @sum_product_completed + `daily_product_completed` AS PCi
        -- -------------------------------

            FROM (
                SELECT  -- daily completed products
                    `interval`.`date` AS `date`,
                    IFNULL(`products_planned`.`daily_product_completed`, 0) AS `daily_product_completed`

                FROM (
                    SELECT  -- total nb of completed products per day
                        -- cast is needed to as start date is a DATETIME
                        CAST(`task_allocations`.`start_date` AS DATE) AS `date`,
                        SUM(`task_allocations`.`nb_products_completed`) AS `daily_product_completed`
                    FROM `task_allocations`
                    RIGHT JOIN
                        (   -- list of taks ids for that project
                            SELECT `tasks`.`id` AS `taskId`
                            FROM `tasks`
                            WHERE `tasks`.`project_id` = project_id
                        ) as `task_ids`

                    ON `task_allocations`.`task_id` = `taskId`
                    GROUP BY CAST(`task_allocations`.`start_date` AS DATE)
                ) AS `products_planned`

                RIGHT JOIN
  
                (   -- list of all dates within the interval [min,max]
                    -- @min and @max are DATE, so the result set is a set of DATE
                    SELECT ADDDATE(@min, INTERVAL @i:=@i+1 DAY) AS `date`
                    FROM (
                        SELECT a.a
                            FROM (SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS a
                            CROSS JOIN (SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS b
                            CROSS JOIN (SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS c
                        ) a
                        JOIN (SELECT @i := -1) AS `alias`
                        WHERE @i < DATEDIFF(@max, @min)
                ) AS `interval`

                -- compare DATEs columns
                ON `products_planned`.`date` = `interval`.`date`
                ORDER BY `date` -- to prevent an un-sorted interval

            ) AS `daily_completed_work`

        -- arithmetic progression --------
        CROSS JOIN
            (SELECT @sum_product_completed := 0) AS var
        -- -------------------------------

        ) AS `hpis`;    -- result set

    END IF;

END 
