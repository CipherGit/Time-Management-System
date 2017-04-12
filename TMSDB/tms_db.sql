-- MySQL Script generated by MySQL Workbench
-- 04/12/17 11:33:38
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema tsm_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema tsm_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `tsm_db` DEFAULT CHARACTER SET utf8 ;
USE `tsm_db` ;

-- -----------------------------------------------------
-- Table `tsm_db`.`schedule`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tsm_db`.`schedule` ;

CREATE TABLE IF NOT EXISTS `tsm_db`.`schedule` (
  `schedule_id` INT NOT NULL,
  `indiv_sched` VARCHAR(200) NULL,
  PRIMARY KEY (`schedule_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tsm_db`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tsm_db`.`user` ;

CREATE TABLE IF NOT EXISTS `tsm_db`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NULL,
  `name` VARCHAR(45) NULL,
  `email` VARCHAR(45) NOT NULL,
  `schedule_id` INT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  INDEX `fk_user_schedule1_idx` (`schedule_id` ASC),
  CONSTRAINT `fk_user_schedule1`
    FOREIGN KEY (`schedule_id`)
    REFERENCES `tsm_db`.`schedule` (`schedule_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tsm_db`.`friends`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tsm_db`.`friends` ;

CREATE TABLE IF NOT EXISTS `tsm_db`.`friends` (
  `user1_id` INT NOT NULL,
  `user2_id` INT NOT NULL,
  PRIMARY KEY (`user1_id`, `user2_id`),
  INDEX `fk_user_has_user_user2_idx` (`user2_id` ASC),
  INDEX `fk_user_has_user_user1_idx` (`user1_id` ASC),
  CONSTRAINT `fk_user_has_user_user1`
    FOREIGN KEY (`user1_id`)
    REFERENCES `tsm_db`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_user_user2`
    FOREIGN KEY (`user2_id`)
    REFERENCES `tsm_db`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tsm_db`.`activity`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tsm_db`.`activity` ;

CREATE TABLE IF NOT EXISTS `tsm_db`.`activity` (
  `activity_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `start_date` VARCHAR(45) NULL,
  `end_date` VARCHAR(45) NULL,
  `schedule_id` INT NOT NULL,
  PRIMARY KEY (`activity_id`),
  INDEX `fk_activity_schedule1_idx` (`schedule_id` ASC),
  CONSTRAINT `fk_activity_schedule1`
    FOREIGN KEY (`schedule_id`)
    REFERENCES `tsm_db`.`schedule` (`schedule_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tsm_db`.`group`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tsm_db`.`group` ;

CREATE TABLE IF NOT EXISTS `tsm_db`.`group` (
  `group_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `description` VARCHAR(500) NULL,
  `user_id` INT NOT NULL,
  `group_sched` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`group_id`),
  INDEX `fk_group_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_group_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `tsm_db`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tsm_db`.`user_group`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tsm_db`.`user_group` ;

CREATE TABLE IF NOT EXISTS `tsm_db`.`user_group` (
  `user_user_id` INT NOT NULL,
  `group_group_id` INT NOT NULL,
  PRIMARY KEY (`user_user_id`, `group_group_id`),
  INDEX `fk_user_has_group_group1_idx` (`group_group_id` ASC),
  INDEX `fk_user_has_group_user1_idx` (`user_user_id` ASC),
  CONSTRAINT `fk_user_has_group_user1`
    FOREIGN KEY (`user_user_id`)
    REFERENCES `tsm_db`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_group_group1`
    FOREIGN KEY (`group_group_id`)
    REFERENCES `tsm_db`.`group` (`group_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
