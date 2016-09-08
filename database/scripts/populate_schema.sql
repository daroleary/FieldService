CREATE TABLE tmp_import (
  import_id       INT NOT NULL AUTO_INCREMENT,
  asset_number    VARCHAR(100),
  status_code     VARCHAR(100),
  entry_date_time TIMESTAMP,

  PRIMARY KEY (import_id),
  INDEX (import_id),
  INDEX (asset_number),
  INDEX (status_code),
  INDEX (entry_date_time),
  INDEX (asset_number, entry_date_time),
  INDEX (import_id, asset_number, entry_date_time)
);

LOAD DATA
LOCAL INFILE '/Users/daroleary/dev/IdeaProjects/FieldService/database/scripts/query_result.csv'
INTO TABLE tmp_import
FIELDS TERMINATED BY ','
OPTIONALLY ENCLOSED BY '\"'
LINES TERMINATED BY '\n'
IGNORE 1 LINES
(asset_number, status_code, @entry_date)
SET entry_date_time = TIMESTAMP(str_to_date(@entry_date, '%Y-%m-%d %H:%i:%s'));

INSERT INTO status
  (SELECT DISTINCT UPPER(status_code)
   FROM tmp_import);

CREATE TABLE tmp_ordered_assets AS
  (SELECT asset_number
   FROM (
    (SELECT
       asset_number,
       MIN(entry_date_time) max_entry_date_time
     FROM tmp_import
     GROUP BY asset_number
     ORDER BY max_entry_date_time ASC)) ordered_assets);

INSERT INTO equipment (asset_number)
  (SELECT asset_number
   FROM tmp_ordered_assets);

CREATE TABLE tmp_ordered_fs AS
  (SELECT
    equipment.equipment_id,
    UPPER(tmp_import.status_code) status_code,
    tmp_import.entry_date_time
  FROM tmp_import
    INNER JOIN equipment ON (equipment.asset_number = tmp_import.asset_number)
  ORDER BY tmp_import.entry_date_time ASC);

INSERT INTO field_signal (equipment_id, status_code, entry_date_time)
  SELECT
    tmp_ordered_fs.equipment_id,
    tmp_ordered_fs.status_code,
    tmp_ordered_fs.entry_date_time
  FROM tmp_ordered_fs
  ORDER BY tmp_ordered_fs.entry_date_time ASC;

DROP TABLE tmp_ordered_assets;
DROP TABLE tmp_ordered_fs;
DROP TABLE tmp_import;