DROP TABLE CfStaticData;
DROP TABLE WechatUser;

CREATE TABLE CfStaticData(
   CodeType             nvarchar(20)         not null,
   CodeValue            nvarchar(20)         not null,
   CodeName             nvarchar(200)        null,
   ExtValue             nvarchar(40)         null,
   SortId               tinyint              null,
   State                tinyint              null,
   Remarks              nvarchar(200)        null,
   constraint PK_CFSTATICDATA primary key (CodeType, CodeValue)
);

CREATE TABLE WechatUser(
	OpenId nvarchar(100) Primary Key,
	UserId bigint NULL,
	UserType tinyint NULL,
	Name nvarchar(30) NULL,
	Gender tinyint NULL,
	City nvarchar(30) NULL,
	ImageData nvarchar(500) NULL,
	State tinyint NULL,
	CreateDate datetime NULL,
	DoneDate datetime NULL
);

insert into sys_sequences(sequence_name,start_by,increment_by,last_number)
  values('SEQ_WUSER',10,1,0);
insert into cfg_id_generator(table_name,domain_id,generator_type,sequence_name,step_by)
  values('WECHATUSER',1,'S','SEQ_WUSER',1); 