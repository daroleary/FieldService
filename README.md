# Field Service
#### Setup MySql database
Download and install from http://dev.mysql.com/downloads/mysql/

###### Mysql console
```sh 
sudo /usr/local/mysql/support-files/mysql.server stop
sudo mysqld_safe --skip-grant-tables
mysql -u root
```
###### Reset with root password
```sh
sudo /usr/local/mysql/bin/mysqladmin -u root -p'OLD_PASSWORD' password NEW_PASSWORD
```
###### Reset without root password
```sh
mysql> FLUSH PRIVILEGES;
mysql> ALTER USER 'root'@'localhost' IDENTIFIED BY 'MyNewPass';
mysql> FLUSH PRIVILEGES
```
##### Create mysql user (login as root)
```sh
stop server from settings -> MySQL console
sudo /usr/local/mysql/bin/mysqld_safe --skip-grant-tables
sudo /usr/local/mysql/bin/mysql -u root -p'NEW_PASSWORD'
CREATE USER 'mysql'@'localhost' IDENTIFIED BY 'MYSQL_USER_PASSWORD';
```
##### Create FieldService schema 
```
Connect to DB using:
user: root
password: NEW_PASSWORD
jdbc:mysql://localhost:3306

CREATE DATABASE FieldService;
GRANT ALL PRIVILEGES ON FieldService.* TO mysql@localhost
IDENTIFIED BY 'test_q1w2e3';

Try connecting to DB using:
user: mysql
password: NEW_PASSWORD
jdbc:mysql://localhost:3306/FieldService
```
##### Schema & Data population
database/scripts/create_schema.sql
database/scripts/populate_schema.sql

Please note that redeploying the DB will reset the database

##### Add JBoss plugin to IDEA
[Add JBoss Integration Plugin](images/jboss_integration_plugin.png)
##### Add JBoss runtime configuration
[Configure Application server](images/JBoss_runtime_1.png)
[Add gradle artifact](images/JBoss_runtime_2.png)
##### When running Integration test cases
[Arquillian - manual container configuration](images/arquillian_config.png)