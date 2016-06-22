-- sequence
insert into sys_sequences(sequence_name,start_by,increment_by,last_number)
  values('SEQ_ALBUM',1,10,0);
  
insert into cfg_id_generator(table_name,domain_id,generator_type,sequence_name,step_by)
  values('ALBUMPHOTO',1,'S','SEQ_ALBUM',10); 

-- 配置数据
insert into CfStaticData(CodeType,CodeValue,CodeName,SortId,State,Remarks)
  values('LabelGroup','1','时间',1,1,'标签组');
insert into CfStaticData(CodeType,CodeValue,CodeName,SortId,State,Remarks)
  values('LabelGroup','2','节日',2,1,'标签组');
insert into CfStaticData(CodeType,CodeValue,CodeName,SortId,State,Remarks)
  values('LabelGroup','3','人物',3,2,'标签组');
insert into CfStaticData(CodeType,CodeValue,CodeName,SortId,State,Remarks)
  values('LabelGroup','4','城市',4,2,'标签组');
insert into CfStaticData(CodeType,CodeValue,CodeName,SortId,State,Remarks)
  values('LabelGroup','5','地点',5,2,'标签组');
insert into CfStaticData(CodeType,CodeValue,CodeName,SortId,State,Remarks)
  values('LabelGroup','6','事件',6,2,'标签组');
insert into CfStaticData(CodeType,CodeValue,CodeName,SortId,State,Remarks)
  values('LabelGroup','7','其它',7,2,'标签组');
  
insert into AlbumLabel(LabelId,GroupId,UserId,LabelName,State)
  values(10,1,-1,'2016.',1);
insert into AlbumLabel(LabelId,GroupId,UserId,LabelName,State)
  values(11,1,-1,'2015.',1);
insert into AlbumLabel(LabelId,GroupId,UserId,LabelName,State)
  values(12,1,-1,'2014.',1);
insert into AlbumLabel(LabelId,GroupId,UserId,LabelName,State)
  values(13,1,-1,'其它',1);
insert into AlbumLabel(LabelId,GroupId,UserId,LabelName,State)
  values(14,2,-1,'元旦',1);
insert into AlbumLabel(LabelId,GroupId,UserId,LabelName,State)
  values(15,2,-1,'春节',1);
insert into AlbumLabel(LabelId,GroupId,UserId,LabelName,State)
  values(16,2,-1,'元宵',1);
insert into AlbumLabel(LabelId,GroupId,UserId,LabelName,State)
  values(17,2,-1,'清明',1);
insert into AlbumLabel(LabelId,GroupId,UserId,LabelName,State)
  values(18,2,-1,'五一',1);
insert into AlbumLabel(LabelId,GroupId,UserId,LabelName,State)
  values(19,2,-1,'端午',1);
insert into AlbumLabel(LabelId,GroupId,UserId,LabelName,State)
  values(20,2,-1,'中秋',1);
insert into AlbumLabel(LabelId,GroupId,UserId,LabelName,State)
  values(21,2,-1,'国庆',1);
insert into AlbumLabel(LabelId,GroupId,UserId,LabelName,State)
  values(22,2,-1,'七夕',1);
insert into AlbumLabel(LabelId,GroupId,UserId,LabelName,State)
  values(23,2,-1,'情人节',1);
insert into AlbumLabel(LabelId,GroupId,UserId,LabelName,State)
  values(24,2,-1,'圣诞',1);
insert into AlbumLabel(LabelId,GroupId,UserId,LabelName,State)
  values(25,2,-1,'感恩节',1);
