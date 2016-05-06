
CREATE 
    DEFINER = CURRENT_USER
PROCEDURE `getPV` (project_id INT(11))

BEGIN

    SET @TWO_DECIMALs := 2;
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

        -- calculate the Planned Value (PV)
        -- http://pmstudycircle.com/2012/05/planned-value-pv-earned-value-ev-actual-cost-ac-analysis-in-project-cost-management-2/
        -- it is for each planned day, the planned percentage of the 
        -- completed work multiplied by the project budget or BAC
        -- PVi = NBPPi / NBPP * BAC
        -- BAC : Budget At Completion
        -- NBPPi : Total Nb Products planned at the ith day
        -- NBPP : Total Nb Products planned
        
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
            
        -- serialize the result set
        SELECT PVi, sum_products_planned from (   -- select only PVi s

        -- arithmetic progression --------
        SELECT  ROUND((@sum_products_planned / @total_products_planned) * @BAC, @TWO_DECIMALS) AS PVi,

                @sum_products_planned := @sum_products_planned + daily_nb_products_planned 
                    AS sum_products_planned                
        -- -------------------------------

            FROM (
                SELECT  -- daily planned products                    
                    `interval`.`date` AS `date`, 
                    IFNULL(`products_planned`.`daily_nb_products_planned`, 0) AS `daily_nb_products_planned`

                FROM (
                    SELECT  -- total nb of planned products per day
                        -- cast is needed to as start date is a DATETIME
                        CAST(`task_allocations`.`start_date` AS DATE) AS `date`,
                        SUM(`task_allocations`.`nb_products_planned`) AS `daily_nb_products_planned`
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
            (SELECT @sum_products_planned := 0) AS var
        -- -------------------------------

        ) AS `pvis`;    -- result set

    END IF;

END 
