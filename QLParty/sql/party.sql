DROP TABLE SocialCircle;
DROP TABLE CircleMember;

CREATE TABLE SocialCircle(
	CId bigint Primary Key,
	CName nvarchar(30) NULL,
	CType tinyint NULL,
	ImageData nvarchar(500) NULL,
	QrTicket nvarchar(500) NULL,
	QrDate date NULL,
	Creater bigint NULL,
	State tinyint NULL,
	CreateDate datetime NULL,
	DoneDate datetime NULL
);

CREATE TABLE CircleMember(
    CId bigint not null,
    UserId bigint not null,
    UserName nvarchar(30) NULL,
    Phone nvarchar(30) NULL,
	City nvarchar(30) NULL,
	Job nvarchar(30) NULL,
	State tinyint NULL,
    constraint PK_CIRCLEMEMBER primary key (CId, UserId)
);