DROP TABLE WechatUser;

CREATE TABLE WechatUser(
	OpenId nvarchar(100) Primary Key,
	UserId bigint NULL,
	UserType tinyint NULL,
	State tinyint NULL
);