
-- =====================================================================
-- Name         : taskflow-schema-1.0.4_1.1.0.sql
-- Author       : William Desheulles (WD Media)
-- Created      : 16-05-2016
-- Description  : TaskFlow database migration script from 1.0.4 to 1.1.0.
-- Copyright    : © 2016, Caratlane, All Rights Reserved
-- =====================================================================

-- MySQL Workbench Synchronization

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

ALTER TABLE `task_allocations` 
CHANGE COLUMN `nb_products_planned` `nb_products_planned` INT(11) NOT NULL DEFAULT '0' ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
