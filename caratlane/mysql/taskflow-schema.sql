-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: caratlane_taskflow
-- ------------------------------------------------------
-- Server version	5.5.45

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employees` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `employeeId` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `firstName` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `lastName` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fullName` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `avatar` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '../../img/caratlane/avatar-150.png',
  `location` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `productivity` double(8,2) unsigned NOT NULL DEFAULT '8.00',
  `isProjectManager` tinyint(1) NOT NULL DEFAULT '0',
  `isTeamLeader` tinyint(1) NOT NULL DEFAULT '0',
  `employementType` enum('Intern','FTE') COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `employees_employeeid_unique` (`employeeId`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `employees_have_skills`
--

DROP TABLE IF EXISTS `employees_have_skills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employees_have_skills` (
  `employeeId` int(10) unsigned NOT NULL,
  `skillId` int(10) unsigned NOT NULL,
  UNIQUE KEY `employees_have_skills_employeeid_skillid_unique` (`employeeId`,`skillId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `holidays`
--

DROP TABLE IF EXISTS `holidays`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `holidays` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `employee_id` int(10) unsigned NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `non_working_days`
--

DROP TABLE IF EXISTS `non_working_days`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `non_working_days` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `type` enum('WEEKEND','NON-WORKING') COLLATE utf8_unicode_ci NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=567 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `privileges`
--

DROP TABLE IF EXISTS `privileges`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `privileges` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `privilege_name` enum('READ_SKILL_PRIVILEGE','REMOVE_SKILL_PRIVILEGE','ADD_SKILL_PRIVILEGE','CLOSE_SKILL_PRIVILEGE','CREATE_SKILL_PRIVILEGE','READ_PROJECT_PRIVILEGE','REMOVE_PROJECT_PRIVILEGE','ADD_PROJECT_PRIVILEGE','CLOSE_PROJECT_PRIVILEGE','CREATE_PROJECT_PRIVILEGE','READ_PROJECT_TEMPLATE_PRIVILEGE','REMOVE_PROJECT_TEMPLATE_PRIVILEGE','ADD_PROJECT_TEMPLATE_PRIVILEGE','CLOSE_PROJECT_TEMPLATE_PRIVILEGE','CREATE_PROJECT_TEMPLATE_PRIVILEGE','READ_PROFILE_PRIVILEGE','UPDATE_PROFILE_PRIVILEGE','CREATE_SKILL_PRIVILEGE') COLLATE utf8_unicode_ci NOT NULL,
  `designation` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `privileges_privilege_name_unique` (`privilege_name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `project_templates`
--

DROP TABLE IF EXISTS `project_templates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_templates` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `reference` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `designation` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `open` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `project_templates_have_skills`
--

DROP TABLE IF EXISTS `project_templates_have_skills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_templates_have_skills` (
  `template_id` int(10) unsigned NOT NULL,
  `skill_id` int(10) unsigned NOT NULL,
  UNIQUE KEY `project_templates_have_skills_template_id_skill_id_unique` (`template_id`,`skill_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `projects`
--

DROP TABLE IF EXISTS `projects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `projects` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `reference` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `template_id` int(10) unsigned NOT NULL,
  `nb_products` int(10) unsigned NOT NULL,
  `priority` tinyint(3) unsigned NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date DEFAULT NULL,
  `open` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `role_name` enum('ADMIN_ROLE','PROJECT_MANAGER_ROLE','TEAM_LEAD_ROLE','EMPLOYEE_ROLE') COLLATE utf8_unicode_ci NOT NULL,
  `designation` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `roles_role_name_unique` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `roles_have_privileges`
--

DROP TABLE IF EXISTS `roles_have_privileges`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles_have_privileges` (
  `role_id` int(10) unsigned NOT NULL,
  `privilege_id` int(10) unsigned NOT NULL,
  UNIQUE KEY `roles_have_privileges_role_id_privilege_id_unique` (`role_id`,`privilege_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `skills`
--

DROP TABLE IF EXISTS `skills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `skills` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `reference` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `designation` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `duration` int(11) NOT NULL DEFAULT '0',
  `open` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `task_allocations`
--

DROP TABLE IF EXISTS `task_allocations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task_allocations` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `task_id` int(10) unsigned NOT NULL,
  `employee_id` int(10) unsigned NOT NULL,
  `start_date` datetime NOT NULL,
  `completion` int(11) NOT NULL DEFAULT '0',
  `nb_products_completed` int(11) NOT NULL DEFAULT '0',
  `completed` tinyint(1) NOT NULL DEFAULT '0',
  `duration` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `task_allocations_task_id_foreign` (`task_id`),
  KEY `task_allocations_employee_id_foreign` (`employee_id`),
  CONSTRAINT `task_allocations_employee_id_foreign` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`),
  CONSTRAINT `task_allocations_task_id_foreign` FOREIGN KEY (`task_id`) REFERENCES `tasks` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=437 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tasks`
--

DROP TABLE IF EXISTS `tasks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tasks` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `skill_id` int(10) unsigned NOT NULL,
  `project_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tasks_skill_id_foreign` (`skill_id`),
  KEY `tasks_project_id_foreign` (`project_id`),
  CONSTRAINT `tasks_project_id_foreign` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`),
  CONSTRAINT `tasks_skill_id_foreign` FOREIGN KEY (`skill_id`) REFERENCES `skills` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teams`
--

DROP TABLE IF EXISTS `teams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teams` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `teamName` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `teamLeaderId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `teams_teamname_unique` (`teamName`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teams_have_employees`
--

DROP TABLE IF EXISTS `teams_have_employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teams_have_employees` (
  `teamId` int(10) unsigned NOT NULL,
  `employeeId` int(10) unsigned NOT NULL,
  UNIQUE KEY `teams_have_employees_teamid_employeeid_unique` (`teamId`,`employeeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `employeeId` int(10) unsigned NOT NULL,
  `role_id` int(10) unsigned NOT NULL,
  `username` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(60) COLLATE utf8_unicode_ci NOT NULL DEFAULT '$2y$10$cOFUgnMIq5UCD5qOrv1FmOn5Nf3MdncTBsqfJ8Inf3kNXopBdySki',
  `remember_token` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  UNIQUE KEY `users_username_password_unique` (`username`,`password`),
  UNIQUE KEY `users_employeeid_unique` (`employeeId`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view `v_holidays_info`
--

DROP TABLE IF EXISTS `v_holidays_info`;
/*!50001 DROP VIEW IF EXISTS `v_holidays_info`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `v_holidays_info` AS SELECT 
 1 AS `id`,
 1 AS `title`,
 1 AS `employee_id`,
 1 AS `employee_full_name`,
 1 AS `start_date`,
 1 AS `end_date`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_project_skills`
--

DROP TABLE IF EXISTS `v_project_skills`;
/*!50001 DROP VIEW IF EXISTS `v_project_skills`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `v_project_skills` AS SELECT 
 1 AS `id`,
 1 AS `project_id`,
 1 AS `duration`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_projects`
--

DROP TABLE IF EXISTS `v_projects`;
/*!50001 DROP VIEW IF EXISTS `v_projects`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `v_projects` AS SELECT 
 1 AS `id`,
 1 AS `reference`,
 1 AS `template_id`,
 1 AS `nb_products`,
 1 AS `priority`,
 1 AS `start_date`,
 1 AS `end_date`,
 1 AS `open`,
 1 AS `nb_products_completed`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_skill_employees`
--

DROP TABLE IF EXISTS `v_skill_employees`;
/*!50001 DROP VIEW IF EXISTS `v_skill_employees`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `v_skill_employees` AS SELECT 
 1 AS `employee_id`,
 1 AS `skill_id`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_tasks_info`
--

DROP TABLE IF EXISTS `v_tasks_info`;
/*!50001 DROP VIEW IF EXISTS `v_tasks_info`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `v_tasks_info` AS SELECT 
 1 AS `id`,
 1 AS `task_id`,
 1 AS `title`,
 1 AS `employee_id`,
 1 AS `employee_full_name`,
 1 AS `skill_id`,
 1 AS `project_id`,
 1 AS `duration`,
 1 AS `completion`,
 1 AS `completed`,
 1 AS `nb_products_completed`,
 1 AS `project_nb_products`,
 1 AS `task_duration`,
 1 AS `open`,
 1 AS `start_date`*/;
SET character_set_client = @saved_cs_client;

--
-- Dumping routines for database 'testschema'
--
/*!50003 DROP FUNCTION IF EXISTS `getNbProductsCompleted` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER = CURRENT_USER FUNCTION `getNbProductsCompleted`(project_id INT) RETURNS text CHARSET utf8
    READS SQL DATA
    DETERMINISTIC
BEGIN
        DECLARE result INT;

        SELECT IFNULL(SUM(`task_allocations`.`nb_products_completed`), 0) INTO result
                FROM `tasks`, `task_allocations`
                WHERE `tasks`.`project_id` = project_id
                    AND `task_allocations`.`task_id` = `tasks`.`id`;
    
        RETURN (result);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `getTitle` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER = CURRENT_USER FUNCTION `getTitle`(project_id INT, skill_id INT) RETURNS text CHARSET latin1
    READS SQL DATA
    DETERMINISTIC
BEGIN
	DECLARE reference TEXT;
        DECLARE designation TEXT;
        DECLARE result TEXT;
    
	SELECT `projects`.`reference` INTO reference
		FROM `projects` 
		WHERE `projects`.`id` = project_id;

	SELECT `skills`.`designation` INTO designation
		FROM `skills` 
		WHERE `skills`.`id` = skill_id;

        SET result = CONCAT(reference, ' - ', designation);

        RETURN (result);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Final view structure for view `v_holidays_info`
--

/*!50001 DROP VIEW IF EXISTS `v_holidays_info`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_unicode_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER = CURRENT_USER SQL SECURITY DEFINER */
/*!50001 VIEW `v_holidays_info` AS select `holidays`.`id` AS `id`,`holidays`.`title` AS `title`,`holidays`.`employee_id` AS `employee_id`,(select `employees`.`fullName` from `employees` where (`employees`.`id` = `holidays`.`employee_id`)) AS `employee_full_name`,`holidays`.`start_date` AS `start_date`,`holidays`.`end_date` AS `end_date` from (`holidays` join `employees`) where (`holidays`.`employee_id` = `employees`.`id`) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_project_skills`
--

/*!50001 DROP VIEW IF EXISTS `v_project_skills`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_unicode_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER = CURRENT_USER SQL SECURITY DEFINER */
/*!50001 VIEW `v_project_skills` AS select `skills`.`id` AS `id`,`projects`.`id` AS `project_id`,`skills`.`duration` AS `duration` from (((`projects` join `skills`) join `project_templates`) join `project_templates_have_skills`) where ((`projects`.`template_id` = `project_templates`.`id`) and (`project_templates`.`id` = `project_templates_have_skills`.`template_id`) and (`project_templates_have_skills`.`skill_id` = `skills`.`id`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_projects`
--

/*!50001 DROP VIEW IF EXISTS `v_projects`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER = CURRENT_USER SQL SECURITY DEFINER */
/*!50001 VIEW `v_projects` AS select `projects`.`id` AS `id`,`projects`.`reference` AS `reference`,`projects`.`template_id` AS `template_id`,`projects`.`nb_products` AS `nb_products`,`projects`.`priority` AS `priority`,`projects`.`start_date` AS `start_date`,`projects`.`end_date` AS `end_date`,`projects`.`open` AS `open`,`GETNBPRODUCTSCOMPLETED`(`projects`.`id`) AS `nb_products_completed` from `projects` order by `projects`.`priority` desc,`projects`.`start_date` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_skill_employees`
--

/*!50001 DROP VIEW IF EXISTS `v_skill_employees`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_unicode_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER = CURRENT_USER SQL SECURITY DEFINER */
/*!50001 VIEW `v_skill_employees` AS select `employees_have_skills`.`employeeId` AS `employee_id`,`skills`.`id` AS `skill_id` from (`skills` join `employees_have_skills`) where (`skills`.`id` = `employees_have_skills`.`skillId`) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_tasks_info`
--

/*!50001 DROP VIEW IF EXISTS `v_tasks_info`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_unicode_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER = CURRENT_USER SQL SECURITY DEFINER */
/*!50001 VIEW `v_tasks_info` AS select `task_allocations`.`id` AS `id`,`task_allocations`.`task_id` AS `task_id`,`getTitle`(`tasks`.`project_id`,`tasks`.`skill_id`) AS `title`,`task_allocations`.`employee_id` AS `employee_id`,(select `employees`.`fullName` from `employees` where (`employees`.`id` = `task_allocations`.`employee_id`)) AS `employee_full_name`,`tasks`.`skill_id` AS `skill_id`,`tasks`.`project_id` AS `project_id`,`task_allocations`.`duration` AS `duration`,`task_allocations`.`completion` AS `completion`,`task_allocations`.`completed` AS `completed`,`task_allocations`.`nb_products_completed` AS `nb_products_completed`,(select `projects`.`nb_products` from `projects` where (`projects`.`id` = `tasks`.`project_id`)) AS `project_nb_products`,(select `skills`.`duration` from `skills` where (`skills`.`id` = `tasks`.`skill_id`)) AS `task_duration`,(select `projects`.`open` from `projects` where (`projects`.`id` = `tasks`.`project_id`)) AS `open`,`task_allocations`.`start_date` AS `start_date` from (`tasks` join `task_allocations`) where (`tasks`.`id` = `task_allocations`.`task_id`) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-04-28 13:59:24
