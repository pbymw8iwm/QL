
<%@page import="com.ql.party.ivalues.IQPartyMemberValue"%>
<%@page import="com.ql.party.web.PartyAction"%>
<%@page import="com.ai.appframe2.web.HttpUtil"%>

<%@ page contentType="text/html;charset=UTF-8"%>
<%
long pId = HttpUtil.getAsLong(request, "partyId");
IQPartyMemberValue[] pms = PartyAction.getPartyMembers(pId);
int count1 = 0,count2 = 0;
//确定参加的人数
for(IQPartyMemberValue pm : pms){
  if(pm.getState() == 1)
    count1++;
}
//待定的人数
count2 = pms.length - count1;
//取多的人数
int rows = count1 > count2 ? count1 : count2;
if(rows%4 > 0)
  rows = rows/4 + 1;
else
  rows = rows/4;
int height = rows * 100;
%>
<style type="text/css">
dl { width:100%;height:<%=height%>px;border:0px;margin:0;font-size:14px;}
dl dd {width:25%; height:100px; border:0px; float:left;text-align:center ;padding-top: 7px;}
</style>

<ul id="myTab" class="nav nav-tabs">
   <li class="active"><a href="#pm1" data-toggle="tab">确定参加&nbsp;&nbsp;<span class="badge"><%=count1 %></span></a></li>
   <li><a href="#pm2" data-toggle="tab">待定&nbsp;&nbsp;<span class="badge"><%=count2 %></span></a></li>
</ul>

<div class="tab-content">
  <div class="tab-pane fade in active" id="pm1">
    <dl>
	<%
	int count = 0;
	for(int i=0;i<pms.length;i++){
	  if(pms[i].getState() == 1){
	    count += pms[i].getPcount();
	%>
	  <dd>
	    <img src="<%=pms[i].getExtAttr("ImageData") %>" class="img-rounded" width="60" height="60"/>
	    <br/><span><%=pms[i].getUsername() %></span>&nbsp;&nbsp;<font color="gray" size="1px"><%=pms[i].getPcount() %>人</font>
	  </dd>
	<%}}%>
	</dl>
  </div>
  <div class="tab-pane fade" id="pm2">
    <dl>
	<%
	for(int i=0;i<pms.length;i++){
	  if(pms[i].getState() == 2){
	%>
	  <dd>
	    <img src="<%=pms[i].getExtAttr("ImageData") %>" class="img-rounded" width="60" height="60"/>
	    <br/><span><%=pms[i].getUsername() %></span>&nbsp;&nbsp;<font color="gray" size="1px"><%=pms[i].getPcount() %>人</font>
	  </dd>
	<%}}%>
	</dl>
  </div>
</div>
<p class="text-right">确定出席：<%=count %>人&nbsp;&nbsp;</p>


