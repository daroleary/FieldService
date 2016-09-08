CREATE TABLE equipment (equipment_id INT NOT NULL AUTO_INCREMENT, asset_number VARCHAR(100), PRIMARY KEY (equipment_id), UNIQUE (asset_number), INDEX (equipment_id), INDEX (asset_number), INDEX (equipment_id, asset_number));
CREATE TABLE status (status_code VARCHAR(100),INDEX (status_code));
ALTER TABLE status ADD UNIQUE (status_code);
CREATE TABLE field_signal (signal_id INT NOT NULL AUTO_INCREMENT, equipment_id INT NOT NULL, status_code VARCHAR(100) NOT NULL, entry_date_time TIMESTAMP NOT NULL, entry_year YEAR(4), entry_month INT(2), PRIMARY KEY (signal_id), INDEX (equipment_id), INDEX (status_code), INDEX (equipment_id, status_code), INDEX (entry_date_time), INDEX (equipment_id, entry_date_time), INDEX (equipment_id, entry_date_time, status_code), INDEX (entry_year), INDEX (equipment_id, entry_year), INDEX (equipment_id, status_code, entry_year), INDEX (status_code, entry_year), INDEX (entry_month), INDEX (equipment_id, entry_month), INDEX (equipment_id, status_code, entry_month), INDEX (equipment_id, status_code, entry_year, entry_month), INDEX (status_code, entry_month), INDEX (status_code, entry_year, entry_month), FOREIGN KEY (status_code) REFERENCES status (status_code) ON DELETE RESTRICT, FOREIGN KEY (equipment_id) REFERENCES equipment (equipment_id) ON DELETE CASCADE);