
-- =====================================================================
-- Name         : taskflow-schema-1.1.0_1.1.1.sql
-- Author       : William Desheulles (WD Media)
-- Created      : 30-05-2016
-- Description  : TaskFlow database migration script from 1.1.0 to 1.1.1.
-- Copyright    : © 2016, Caratlane, All Rights Reserved
-- =====================================================================

-- MySQL Workbench Synchronization

/*!50003 DROP PROCEDURE IF EXISTS `getCPI` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER = CURRENT_USER PROCEDURE `getCPI`(project_id INT(11))
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

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getEV` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER = CURRENT_USER PROCEDURE `getEV`(project_id INT(11))
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

        -- calculate the Planned Value (PV)
        -- http://pmstudycircle.com/2012/05/planned-value-pv-earned-value-ev-actual-cost-ac-analysis-in-project-cost-management-2/
        -- Earned Value is the value of the work actually completed to date
        -- Take the actual percentage of the completed work and multiply it by 
        -- the project budget and you will get the Earned Value.
        -- Earned Value = % of completed work X BAC
        -- EVi = NBPCi * BAC / NBPP 
        -- BAC : Budget At Completion
        -- NBPCi : Total Nb Products completed at the ith day
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
        SELECT EVi, sum_products_completed from (   -- select only EVi s

        -- arithmetic progression --------
        SELECT  ROUND((@sum_products_completed / @total_products_planned) * @BAC, @TWO_DECIMALS) AS EVi,

                @sum_products_completed := @sum_products_completed + daily_nb_products_completed 
                    AS sum_products_completed                
        -- -------------------------------

            FROM (
                SELECT  -- daily completed products
                    `interval`.`date` AS `date`, 
                    IFNULL(`products_planned`.`daily_nb_products_completed`, 0) AS `daily_nb_products_completed`

                FROM (
                    SELECT  -- total nb of completed products per day
                        -- cast is needed to as start date is a DATETIME
                        CAST(`task_allocations`.`start_date` AS DATE) AS `date`,
                        SUM(`task_allocations`.`nb_products_completed`) AS `daily_nb_products_completed`
                    FROM `task_allocations`
                    RIGHT JOIN
                        (   -- list of tasks ids for that project
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
            (SELECT @sum_products_completed := 0) AS var
        -- -------------------------------

        ) AS `evis`;    -- result set

    END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getHC` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER = CURRENT_USER PROCEDURE `getHC`(project_id INT(11))
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

        -- number of hours consumed against time
            
        -- serialize the result set
        SELECT HCi from (

        -- arithmetic progression --------
        SELECT  `daily_completion`, @sum_completion := @sum_completion + `daily_completion` AS HCi
        -- -------------------------------

            FROM (
                SELECT  -- daily completed products
                    `interval`.`date` AS `date`,
                    IFNULL(`products_planned`.`daily_completion`, 0) AS `daily_completion`

                FROM (
                    SELECT  -- total nb of completed products per day
                        -- cast is needed to as start date is a DATETIME
                        CAST(`task_allocations`.`start_date` AS DATE) AS `date`,
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
            (SELECT @sum_completion := 0) AS var
        -- -------------------------------

        ) AS `hpis`;    -- result set

    END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getHP` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER = CURRENT_USER PROCEDURE `getHP`(project_id INT(11))
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

        -- number of hours planned against time
            
        -- serialize the result set
        SELECT HPi from (

        -- arithmetic progression --------
        SELECT  `daily_duration`, @sum_duration := @sum_duration + `daily_duration` AS HPi
        -- -------------------------------

            FROM (
                SELECT  -- daily completed products
                    `interval`.`date` AS `date`,
                    IFNULL(`products_planned`.`daily_duration`, 0) AS `daily_duration`

                FROM (
                    SELECT  -- total nb of completed products per day
                        -- cast is needed to as start date is a DATETIME
                        CAST(`task_allocations`.`start_date` AS DATE) AS `date`,
                        SUM(`task_allocations`.`duration`) AS `daily_duration`
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
            (SELECT @sum_duration := 0) AS var
        -- -------------------------------

        ) AS `hpis`;    -- result set

    END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getIndicators` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER = CURRENT_USER PROCEDURE `getIndicators`(project_id INT(11))
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

        -- calculate the indicators until today
        SET @today := null;
        SELECT CAST(NOW() AS DATE) INTO @today;

        IF @min > @today THEN
            -- the project has not been started yet
            SELECT 0 AS EVi, 0 AS PVi, 0 AS CPIi, 0 AS SPIi;
        ELSE

        IF @max > @today THEN
            SET @max := @today;
        END IF;
                
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
            
        -- SELECT EVi, PVi, CPIi, SPIi FROM ( -- serialize the result set
        SELECT EVi, PVi, CPIi, SPIi, sum_products_completed, sum_products_planned, sum_actual_cost FROM (        

        -- arithmetic progression --------
        SELECT  
                @sum_products_completed := @sum_products_completed + daily_nb_products_completed 
                    AS sum_products_completed,

                @sum_products_planned := @sum_products_planned + daily_nb_products_planned 
                    AS sum_products_planned,

                @sum_actual_cost := @sum_actual_cost + daily_actual_cost AS sum_actual_cost,

                ROUND((@sum_products_completed / @total_products_planned) * @BAC, @TWO_DECIMALS) AS EVi,
                ROUND((@sum_products_planned / @total_products_planned) * @BAC, @TWO_DECIMALS) AS PVi,
                ROUND((@sum_products_completed / @sum_products_planned), @TWO_DECIMALS) AS SPIi,
                ROUND( IFNULL((@sum_products_completed * @BAC) / ( @total_products_planned * @sum_actual_cost), 0), @TWO_DECIMALS) AS CPIi
        -- -------------------------------

            FROM (
                SELECT  -- daily completed products
                    `interval`.`date` AS `date`, 
                    IFNULL(`products_planned`.`daily_nb_products_completed`, 0) AS `daily_nb_products_completed`,
                    IFNULL(`products_planned`.`daily_nb_products_planned`, 0) AS `daily_nb_products_planned`,
                    IFNULL(`products_planned`.`daily_completion`, 0) AS `daily_actual_cost`

                FROM (
                    SELECT  -- total nb of completed products per day
                        -- cast is needed to as start date is a DATETIME
                        CAST(`task_allocations`.`start_date` AS DATE) AS `date`,
                        SUM(`task_allocations`.`nb_products_completed`) AS `daily_nb_products_completed`,
                        SUM(`task_allocations`.`nb_products_planned`) AS `daily_nb_products_planned`,
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
            (SELECT 
                @sum_products_completed := 0, 
                @sum_products_planned := 0, 
                @sum_actual_cost := 0
            ) AS var
        -- -------------------------------

        ) AS `indicators`;    -- result set

        END IF;

    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getLabels` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER = CURRENT_USER PROCEDURE `getLabels`(project_id INT(11))
BEGIN

    DECLARE max DATETIME;
    DECLARE min DATETIME;

    -- the first allocation start_date (min) and the last (max)
    SELECT MAX(`task_allocations`.`start_date`), MIN(`task_allocations`.`start_date`) 
            INTO max, min 
            FROM `tasks`, `task_allocations`
            WHERE `tasks`.`project_id` = project_id
                AND `task_allocations`.`task_id` = `tasks`.`id`;

    -- list of all dates within the interval [min,max]
    SELECT DATE_FORMAT(ADDDATE(min, INTERVAL @i:=@i+1 DAY), '%d/%m') AS `label`
        FROM (
            SELECT a.a
                FROM (SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS a
                CROSS JOIN (SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS b
                CROSS JOIN (SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS c
        ) a
        JOIN (SELECT @i := -1) r1
        WHERE @i < DATEDIFF(max, min);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getPV` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER = CURRENT_USER PROCEDURE `getPV`(project_id INT(11))
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
                        (   -- list of tasks ids for that project
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

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getSPI` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER = CURRENT_USER PROCEDURE `getSPI`(project_id INT(11))
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

        -- calculate the Schedule Performance Index (SPI)
        -- http://pmstudycircle.com/2012/05/schedule-performance-index-spi-and-cost-performance-index-cpi/
        -- SPIi = EVi / PVi
        -- EVi = NBPCi / NBPP * BAC
        -- PVi = NBPPi / NBPP * BAC
        -- BAC : Budget At Completion
        -- NBPPi : Total Nb Products planned at the ith day
        -- NBPCi : Total Nb Products completed at the ith day
        -- NBPP : Total Nb Products planned
        -- => SPIi = NBPCi / NBPPi
                    
        SELECT SPIi FROM ( -- serialize the result set
        -- SELECT SPIi, sum_products_completed, sum_products_planned FROM (

        -- arithmetic progression --------
        SELECT  
                @sum_products_completed := @sum_products_completed + daily_nb_products_completed  
                    AS sum_products_completed,

                @sum_products_planned := @sum_products_planned + daily_nb_products_planned 
                    AS sum_products_planned,

                ROUND((@sum_products_completed / @sum_products_planned), @TWO_DECIMALS) AS SPIi
        -- -------------------------------

            FROM (
                SELECT
                    `interval`.`date` AS `date`, 
                    IFNULL(`products_planned`.`daily_nb_products_completed`, 0) AS `daily_nb_products_completed`,
                    IFNULL(`products_planned`.`daily_nb_products_planned`, 0) AS `daily_nb_products_planned`

                FROM (
                    SELECT  -- total nb of completed products per day
                        -- cast is needed to as start date is a DATETIME
                        CAST(`task_allocations`.`start_date` AS DATE) AS `date`,
                        SUM(`task_allocations`.`nb_products_completed`) AS `daily_nb_products_completed`,
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

            ) AS `daily_activity`

        -- arithmetic progression --------
        CROSS JOIN
            (SELECT @sum_products_completed := 0, @sum_products_planned := 0) AS var
        -- -------------------------------

        ) AS `spiis`;    -- result set

    END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;


