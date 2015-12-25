-- sequence
insert into sys_sequences(sequence_name,start_by,increment_by,last_number)
  values('SEQ_WUSER',1,10,0);
insert into sys_sequences(sequence_name,start_by,increment_by,last_number)
  values('SEQ_SC',1,10,0);
insert into sys_sequences(sequence_name,start_by,increment_by,last_number)
  values('SEQ_CM',1,10,0);
  
insert into cfg_id_generator(table_name,domain_id,generator_type,sequence_name,step_by)
  values('WECHATUSER',1,'S','SEQ_WUSER',10); 
insert into cfg_id_generator(table_name,domain_id,generator_type,sequence_name,step_by)
  values('SOCIALCIRCLE',1,'S','SEQ_SC',10); 
insert into cfg_id_generator(table_name,domain_id,generator_type,sequence_name,step_by)
  values('CIRCLEMEMBER',1,'S','SEQ_CM',10); 
  

-- 配置数据
insert into CfStaticData(CodeType,CodeValue,CodeName,SortId,State,Remarks)
  values('CircleType','1','亲属',1,1,'圈子分类');
insert into CfStaticData(CodeType,CodeValue,CodeName,SortId,State,Remarks)
  values('CircleType','2','同学',2,1,'圈子分类');
insert into CfStaticData(CodeType,CodeValue,CodeName,SortId,State,Remarks)
  values('CircleType','3','朋友',3,1,'圈子分类');
insert into CfStaticData(CodeType,CodeValue,CodeName,SortId,State,Remarks)
  values('CircleType','4','同事',4,1,'圈子分类');
insert into CfStaticData(CodeType,CodeValue,CodeName,SortId,State,Remarks)
  values('CircleType','5','社交',5,1,'圈子分类');
insert into CfStaticData(CodeType,CodeValue,CodeName,SortId,State,Remarks)
  values('CircleType','99','其他',99,1,'圈子分类');
  
 
