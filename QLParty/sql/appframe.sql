#建立数据库
create database base;

#授权
GRANT SELECT,INSERT,UPDATE,DELETE,CREATE,DROP,ALTER,INDEX,execute  ON base.* 		TO base@"%" 	IDENTIFIED BY 'base';
GRANT SELECT,INSERT,UPDATE,DELETE,CREATE,DROP,ALTER,INDEX,execute  ON base.* 		TO base@"localhost" 	IDENTIFIED BY 'base';

#在每个数据库下建立sequence的表和函数

mysql -u root -p

use base;

set global log_bin_trust_function_creators=1;

delimiter //
CREATE FUNCTION seq_nextval(seq_name char (20)) returns int
begin
 UPDATE sys_sequences SET LAST_NUMBER= case last_number when 0 THEN last_insert_id(START_BY) else last_insert_id(LAST_NUMBER+INCREMENT_BY) end WHERE SEQUENCE_NAME=seq_name;
 RETURN last_insert_id();
end
//
delimiter ;

delimiter //
CREATE FUNCTION seq_curval(seq_name char (20)) returns int
begin
 DECLARE cur  int;
 select LAST_NUMBER into cur from sys_sequences WHERE SEQUENCE_NAME=seq_name;
 RETURN  cur;
end
//
delimiter ;
 

CREATE TABLE `sys_sequences` (
  `SEQUENCE_NAME` varchar(20) NOT NULL,
  `START_BY` BIGINT(20) UNSIGNED NOT NULL,
  `INCREMENT_BY` BIGINT(10) UNSIGNED NOT NULL,
  `LAST_NUMBER` BIGINT(20) UNSIGNED NOT NULL,
  PRIMARY KEY  (`SEQUENCE_NAME`)
) ENGINE=MyISAM ; 



-- Create table
create table CFG_TABLE_SPLIT_MAPPING
(
  MAPPING_ID           BIGINT(12) primary key,
  TABLE_NAME           VARCHAR(255) not null,
  COLUMN_NAME          VARCHAR(255) not null,
  COLUMN_CONVERT_CLASS VARCHAR(255) not null,
  STATE                CHAR(1) not null,
  REMARKS              VARCHAR(255)
);

create index IDX_CFG_TABLE_SPLIT_MAPPING_1 on CFG_TABLE_SPLIT_MAPPING (TABLE_NAME,COLUMN_NAME);

-- Create table
create table CFG_DB_ACCT
(
  DB_ACCT_CODE     VARCHAR(255)  primary key,
  USERNAME         VARCHAR(255),
  PASSWORD         VARCHAR(255),
  HOST             VARCHAR(255),
  PORT             BIGINT(5),
  SID              VARCHAR(255),
  DEFAULT_CONN_MIN BIGINT(3),
  DEFAULT_CONN_MAX BIGINT(3),
  STATE            CHAR(1),
  REMARKS          VARCHAR(1000)
);

create index IDX_CFG_DB_ACCT_1 on CFG_DB_ACCT (USERNAME);

-- Create table
create table CFG_DB_JDBC_PARAMETER
(
  PARAMETER_ID BIGINT(12) primary key,
  DB_ACCT_CODE VARCHAR(255) not null,
  SERVER_NAME  VARCHAR(255) not null,
  NAME         VARCHAR(255) not null,
  VALUE        VARCHAR(255) not null,
  STATE        CHAR(1) not null,
  REMARKS      VARCHAR(255)
);
  
 
-- Create/Recreate indexes 
create index IDX_CFG_DB_JDBC_PARAMETER_1 on CFG_DB_JDBC_PARAMETER (SERVER_NAME);
create index IDX_CFG_DB_JDBC_PARAMETER_2 on CFG_DB_JDBC_PARAMETER (DB_ACCT_CODE);

-- Create table
create table CFG_DB_RELAT
(
  DB_ACCT_CODE VARCHAR(255) not null,
  URL_NAME     VARCHAR(255),
  SERVER_NAME  VARCHAR(255) not null,
  STATE        CHAR(1),
  REMARKS      VARCHAR(255),
  primary   key(DB_ACCT_CODE,SERVER_NAME)
);
 
 
-- Create/Recreate indexes 
create index IDX_CFG_DB_RELAT_1 on CFG_DB_RELAT (SERVER_NAME);
-- Create table
create table CFG_DB_URL
(
  NAME    VARCHAR(255) primary key,
  URL     VARCHAR(4000),
  STATE   CHAR(1),
  REMARKS VARCHAR(255)
);

create table CFG_DYNC_TABLE_SPLIT
(
  GROUP_NAME      VARCHAR(255) not null,
  TABLE_NAME      VARCHAR(255) not null,
  TABLE_NAME_EXPR VARCHAR(255) not null,
  CONVERT_CLASS   VARCHAR(255) not null,
  STATE           CHAR(1) not null,
  REMARKS         VARCHAR(255),
  primary key(GROUP_NAME,TABLE_NAME)
);

create table CFG_I18N_RESOURCE
(
  RES_KEY VARCHAR(15) primary key,
  ZH_CN   VARCHAR(1000) not null,
  EN_US   VARCHAR(1000),
  STATE   CHAR(1) not null,
  REMARK  VARCHAR(255)
);

-- Create table
create table CFG_ID_GENERATOR
(
  TABLE_NAME             VARCHAR(100)  primary key,
  DOMAIN_ID              BIGINT(6) not null,
  GENERATOR_TYPE         CHAR(1) not null,
  SEQUENCE_NAME          VARCHAR(60),
  MAX_ID                 BIGINT(12),
  START_VALUE            BIGINT(12),
  MIN_VALUE              BIGINT(12),
  MAX_VALUE              BIGINT(12),
  INCREMENT_VALUE        BIGINT(6),
  CYCLE_FLAG             CHAR(1),
  CACHE_SIZE             BIGINT(6),
  SEQUENCE_CREATE_SCRIPT VARCHAR(1000),
  HIS_TABLE_NAME         VARCHAR(100),
  HIS_SEQUENCE_NAME      VARCHAR(60),
  HIS_DATA_FLAG          CHAR(1),
  HIS_MAX_ID             BIGINT(12),
  HIS_DONECODE_FLAG      CHAR(1),
  STEP_BY                BIGINT(6),
  HIS_STEP_BY            BIGINT(6)
) ;

-- Create table
create table CFG_ID_GENERATOR_WRAPPER
(
  TABLE_NAME             									VARCHAR(100)  primary key,
  TABLE_SEQ_WRAPPER_IMPL        					VARCHAR(1000),
  HIS_TABLE_SEQ_WRAPPER_IMPL      				VARCHAR(1000)
) ;

-- Create table
create table CFG_METHOD_CENTER
(
  SERVICE_IMPL_CLASSNAME           VARCHAR(255) not null,
  METHOD_NAME           					 VARCHAR(255) not null,
  PARAMETER_COUNT          				 BIGINT(6) not null,
  PARAMETER_INDEX 								 BIGINT(6) not null,
  PARAMETER_FUNCTION 							 VARCHAR(255) not null,
  CENTER_TYPE 							 			 VARCHAR(255) not null,  
  STATE                						 CHAR(1) not null,
  REMARKS              						 VARCHAR(255),
  primary   key(SERVICE_IMPL_CLASSNAME,METHOD_NAME,PARAMETER_COUNT)
);

-- Create table
create table CFG_SERVICE_CONTROL(
SERVER_NAME   VARCHAR(1000) NOT NULL,
SERVICE_NAME  VARCHAR(1000) NOT NULL,
METHOD_NAME   VARCHAR(1000),
LIMIT_COUNT 	BIGINT(6) NOT NULL,
STATE CHAR(1)   NOT NULL,
REMARKS VARCHAR(255)
);

-- Create table
create table CFG_TABLE_SPLIT
(
  TABLE_NAME      VARCHAR(255) primary key,
  TABLE_NAME_EXPR VARCHAR(255) not null,
  STATE           CHAR(1) not null,
  REMARKS         VARCHAR(255)
);

insert into CFG_SERVICE_CONTROL (SERVER_NAME, SERVICE_NAME, METHOD_NAME, LIMIT_COUNT, STATE, REMARKS)
values ('DEFAULT', 'DEFAULT', 'DEFAULT', 100, 'U', '默认');
