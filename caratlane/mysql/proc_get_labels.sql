
CREATE 
    DEFINER = CURRENT_USER
PROCEDURE `getLabels` (project_id INT(11))

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

END 
