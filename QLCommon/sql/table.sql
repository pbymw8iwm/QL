DROP TABLE WechatUser;

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