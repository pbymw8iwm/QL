DROP TABLE SocialCircle;
DROP TABLE CircleMember;
DROP TABLE Party;
DROP TABLE PartyMember;
DROP TABLE PartyPhoto;
DROP TABLE PartyMsg;

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

CREATE TABLE Party(
	PartyId bigint Primary Key,
    CId bigint NULL,
	Theme nvarchar(50) NULL,
	StartTime datetime NULL,
	EndTime datetime NULL,
	GatheringPlace nvarchar(100) NULL,
	QrTicket nvarchar(500) NULL,
	QrDate date NULL,
	Creater bigint NULL,
	State tinyint NULL,
	DoneDate datetime NULL,
	Remarks nvarchar(500) NULL
);

CREATE TABLE PartyMember(
    PartyId bigint not null,
    UserId bigint not null,
    PCount int null,
	State tinyint NULL,
	Remarks nvarchar(500) NULL,
    constraint PK_CIRCLEMEMBER primary key (PartyId, UserId)
);

CREATE TABLE PartyPhoto(
	PhotoId bigint Primary Key,
    PartyId bigint NULL,
    CId bigint NULL,
	PhotoData nvarchar(500) NULL,
	UserId bigint NULL,
	State tinyint NULL,
	DoneDate datetime NULL
);

CREATE TABLE PartyMsg(
	MsgId bigint Primary Key,
    PartyId bigint NULL,
	Message nvarchar(500) NULL,
	UserId bigint NULL,
	State tinyint NULL,
	DoneDate datetime NULL
);

