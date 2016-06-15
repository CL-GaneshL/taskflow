
CREATE 
    DEFINER = CURRENT_USER
PROCEDURE `getCPI` (project_id INT(11))

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

        -- calculate the Cost Performance Index (CPI)
        -- http://pmstudycircle.com/2012/05/schedule-performance-index-spi-and-cost-performance-index-cpi/
        -- CPIi = EVi / ACi.
        -- ACi = Actual Cost at the ith day
        -- EVi = NBPCi / NBPP * BAC
        -- BAC : Budget At Completion
        -- NBPCi : Total Nb Products completed at the ith day
        -- NBPP : Total Nb Products planned
        -- => CPIi = NBPCi * BAC / NBPP * ACi
      
        SET @BAC := 0.00;
        SET @total_products_planned := 0;    

        -- retrieve the hourly cost
        SELECT `hourly_cost`.`cost`
        INTO @hourly_cost
        FROM `hourly_cost` LIMIT 1;

        -- calculate the Budget at Completion (BAC)
        -- it is the total number of labour hours for that project
        -- multiplied by the hourly cost.
        SELECT ROUND(SUM(`task_allocations`.`duration`) / 60 * @hourly_cost, @FOUR_DECIMALS)
        INTO @BAC
        FROM `tasks`, `task_allocations`
        WHERE `tasks`.`project_id` = project_id
            AND `task_allocations`.`task_id` = `tasks`.`id`;
       
        -- calculate the nb of planned products
        SELECT SUM(`projects`.`nb_products`)
        INTO @total_products_planned
        FROM `projects`
        WHERE `projects`.`id` = project_id;
            
        
        SELECT CPIi FROM (   -- serialize the result set
        -- SELECT CPIi, sum_products_completed, sum_actual_cost from (

        -- arithmetic progression --------
        SELECT  
                ROUND( 
                    IFNULL((@sum_products_completed * @BAC) / ( @total_products_planned * @sum_actual_cost), 0) 
                    , @TWO_DECIMALS) AS CPIi,

                @sum_products_completed := @sum_products_completed + daily_nb_products_completed 
                    AS sum_products_completed,
                
                @sum_actual_cost := @sum_actual_cost + daily_actual_cost AS sum_actual_cost
        -- -------------------------------

            FROM (
                SELECT  -- daily completed products
                    `interval`.`date` AS `date`, 
                    IFNULL(`products_planned`.`daily_nb_products_completed`, 0) AS `daily_nb_products_completed`,
                    IFNULL(`products_planned`.`daily_completion`, 0) AS `daily_actual_cost`

                FROM (
                    SELECT  -- total nb of completed products per day
                        -- cast is needed to as start date is a DATETIME
                        CAST(`task_allocations`.`start_date` AS DATE) AS `date`,
                        SUM(`task_allocations`.`nb_products_completed`) AS `daily_nb_products_completed`,
                        SUM(`task_allocations`.`completion`) AS `daily_completion`
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
            (SELECT @sum_products_completed := 0, @sum_actual_cost := 0) AS var
        -- -------------------------------

        ) AS `cpis`;    -- result set

    END IF;

END 
