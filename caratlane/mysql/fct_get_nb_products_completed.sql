
CREATE 
    DEFINER = CURRENT_USER
 FUNCTION `getNbProductsCompleted` (project_id INT)
 RETURNS TEXT
    DETERMINISTIC
    READS SQL DATA
    SQL SECURITY DEFINER
   
BEGIN
        DECLARE result INT;

        SELECT IFNULL(SUM(`task_allocations`.`nb_products_completed`), 0) INTO result
                FROM `tasks`, `task_allocations`
                WHERE `tasks`.`project_id` = project_id
                    AND `task_allocations`.`task_id` = `tasks`.`id`;
    
        RETURN (result);
END 
