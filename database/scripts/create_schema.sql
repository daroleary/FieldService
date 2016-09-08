CREATE DATABASE FieldService;

GRANT ALL PRIVILEGES ON FieldService.* TO mysql@localhost
IDENTIFIED BY 'test_q1w2e3';

DROP TABLE field_signal;
DROP TABLE status;
DROP TABLE equipment;

CREATE TABLE equipment (
  equipment_id INT NOT NULL AUTO_INCREMENT,
  asset_number VARCHAR(100),

  PRIMARY KEY (equipment_id),
  UNIQUE (asset_number),
  INDEX (equipment_id),
  INDEX (asset_number),
  INDEX (equipment_id, asset_number)
);

CREATE TABLE status (
  status_code VARCHAR(100),

  INDEX (status_code)
);

ALTER TABLE status
  ADD UNIQUE (status_code);

CREATE TABLE field_signal (
  signal_id       INT NOT NULL AUTO_INCREMENT,
  equipment_id    INT NOT NULL,
  status_code     VARCHAR(100) NOT NULL,
  entry_date_time TIMESTAMP NOT NULL,
  entry_year      YEAR(4),
  entry_month     INT(2),

  PRIMARY KEY (signal_id),
  INDEX (equipment_id),
  INDEX (status_code),
  INDEX (equipment_id, status_code),
  INDEX (entry_date_time),
  INDEX (equipment_id, entry_date_time),
  INDEX (equipment_id, entry_date_time, status_code),
  INDEX (entry_year),
  INDEX (equipment_id, entry_year),
  INDEX (equipment_id, status_code, entry_year),
  INDEX (status_code, entry_year),
  INDEX (entry_month),
  INDEX (equipment_id, entry_month),
  INDEX (equipment_id, status_code, entry_month),
  INDEX (equipment_id, status_code, entry_year, entry_month),
  INDEX (status_code, entry_month),
  INDEX (status_code, entry_year, entry_month),

  FOREIGN KEY (status_code)
  REFERENCES status (status_code)
    ON DELETE RESTRICT,

  FOREIGN KEY (equipment_id)
  REFERENCES equipment (equipment_id)
    ON DELETE CASCADE
);

DROP TRIGGER IF EXISTS year_and_month_after_insert;

DELIMITER //

CREATE TRIGGER year_and_month_after_insert
BEFORE INSERT
ON field_signal FOR EACH ROW
  SET NEW.entry_year = YEAR(NEW.entry_date_time),
      NEW.entry_month = MONTH(NEW.entry_date_time);

DELIMITER ;

DROP TRIGGER IF EXISTS year_and_month_before_update;

DELIMITER //

CREATE TRIGGER year_and_month_before_update
BEFORE UPDATE
ON field_signal FOR EACH ROW
  SET NEW.entry_year = YEAR(NEW.entry_date_time),
      NEW.entry_month = MONTH(NEW.entry_date_time);

DELIMITER ;