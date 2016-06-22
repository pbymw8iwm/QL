DROP TABLE AlbumLabel;
DROP TABLE AlbumPhoto;


create table AlbumLabel(
  LabelId int not null,
  GroupId tinyint not null, 
  UserId bigint not null,
  LabelName nvarchar(30),
  State tinyint null,
  DoneDate datetime null,
  constraint PK_ALBUMLABEL primary key (LabelId,GroupId, UserId)
);

CREATE TABLE AlbumPhoto(
	PhotoId bigint Primary Key,
	PhotoData nvarchar(500) NULL,
	Labels nvarchar(100) NULL,
	UserId bigint NULL,
	State tinyint NULL,
	DoneDate datetime NULL
);