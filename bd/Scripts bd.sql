-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema projetopizzaria
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema projetopizzaria
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `projetopizzaria` DEFAULT CHARACTER SET utf8 ;
USE `projetopizzaria` ;

-- -----------------------------------------------------
-- Table `projetopizzaria`.`clientes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projetopizzaria`.`clientes` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(100) NOT NULL,
  `sobrenome` VARCHAR(100) NOT NULL,
  `telefone` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projetopizzaria`.`status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projetopizzaria`.`status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projetopizzaria`.`status_pedidos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projetopizzaria`.`status_pedidos` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_status` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_status_pedidos_1_idx` (`id_status` ASC) VISIBLE,
  CONSTRAINT `fk_status_pedidos_1`
    FOREIGN KEY (`id_status`)
    REFERENCES `projetopizzaria`.`status` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projetopizzaria`.`formas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projetopizzaria`.`formas` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projetopizzaria`.`dimensao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projetopizzaria`.`dimensao` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projetopizzaria`.`ingredientes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projetopizzaria`.`ingredientes` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projetopizzaria`.`sabores_ingredientes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projetopizzaria`.`sabores_ingredientes` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(45) NOT NULL,
  `id_ingredientes` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_sabores_ingredientes_1_idx` (`id_ingredientes` ASC) VISIBLE,
  CONSTRAINT `fk_sabores_ingredientes_1`
    FOREIGN KEY (`id_ingredientes`)
    REFERENCES `projetopizzaria`.`ingredientes` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projetopizzaria`.`sabores`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projetopizzaria`.`sabores` (
  `id` INT NOT NULL,
  `descricao` VARCHAR(45) NOT NULL,
  `id_sabores_ingredientes` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_sabores_1_idx` (`id_sabores_ingredientes` ASC) VISIBLE,
  CONSTRAINT `fk_sabores_1`
    FOREIGN KEY (`id_sabores_ingredientes`)
    REFERENCES `projetopizzaria`.`sabores_ingredientes` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projetopizzaria`.`tipos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projetopizzaria`.`tipos` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_sabores` INT NOT NULL,
  `descricao` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_tipos_1_idx` (`id_sabores` ASC) VISIBLE,
  CONSTRAINT `fk_tipos_1`
    FOREIGN KEY (`id_sabores`)
    REFERENCES `projetopizzaria`.`sabores` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projetopizzaria`.`itens_pizzas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projetopizzaria`.`itens_pizzas` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_formas` INT NOT NULL,
  `id_dimensao` INT NOT NULL,
  `id_tipos` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_itens_pizzas_1_idx` (`id_formas` ASC) VISIBLE,
  INDEX `fk_itens_pizzas_1_idx1` (`id_dimensao` ASC) VISIBLE,
  INDEX `fk_itens_pizzas_3_idx` (`id_tipos` ASC) VISIBLE,
  CONSTRAINT `fk_itens_pizzas_1`
    FOREIGN KEY (`id_formas`)
    REFERENCES `projetopizzaria`.`formas` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_itens_pizzas_2`
    FOREIGN KEY (`id_dimensao`)
    REFERENCES `projetopizzaria`.`dimensao` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_itens_pizzas_3`
    FOREIGN KEY (`id_tipos`)
    REFERENCES `projetopizzaria`.`tipos` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projetopizzaria`.`pedidos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projetopizzaria`.`pedidos` (
  `id` INT ZEROFILL NOT NULL AUTO_INCREMENT,
  `id_clientes` INT NOT NULL,
  `id_status_pedidos` INT NOT NULL,
  `id_itens_pizzas` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_pedidos_1_idx` (`id_clientes` ASC) VISIBLE,
  INDEX `fk_pedidos_2_idx` (`id_status_pedidos` ASC) VISIBLE,
  INDEX `fk_pedidos_3_idx` (`id_itens_pizzas` ASC) VISIBLE,
  CONSTRAINT `fk_pedidos_1`
    FOREIGN KEY (`id_clientes`)
    REFERENCES `projetopizzaria`.`clientes` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pedidos_2`
    FOREIGN KEY (`id_status_pedidos`)
    REFERENCES `projetopizzaria`.`status_pedidos` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pedidos_3`
    FOREIGN KEY (`id_itens_pizzas`)
    REFERENCES `projetopizzaria`.`itens_pizzas` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
