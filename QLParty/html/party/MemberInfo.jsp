
<%@page import="com.ql.party.ivalues.IQPartyMemberValue"%>
<%@page import="com.ql.party.web.PartyAction"%>
<%@page import="com.ai.appframe2.web.HttpUtil"%>

<%@ page contentType="text/html;charset=UTF-8"%>
<%
long pId = HttpUtil.getAsLong(request, "partyId");
String partyName = HttpUtil.getAsString(request, "partyName");
IQPartyMemberValue[] pms = PartyAction.getPartyMembers(pId);
int count1 = 0,count2 = 0;
//确定参加的人数
for(IQPartyMemberValue pm : pms){
  if(pm.getState() == 1)
    count1++;
}
//待定的人数
count2 = pms.length - count1;

int rows = count1;
if(rows%4 > 0)
  rows = rows/4 + 1;
else
  rows = rows/4;
int height1 = rows * 100;

rows = count2;
if(rows%4 > 0)
  rows = rows/4 + 1;
else
  rows = rows/4;
int height2 = rows * 100;
%>
<style type="text/css">
.pmb {}
.pmb dl { width:100%;border:0px;margin:0;font-size:14px;}
.pmb dl dd {width:25%; height:100px; border:0px; float:left;text-align:center ;padding-top: 7px;}
</style>

<%if("".equals(partyName) == false){ %>
<div class="page-header-group">
		    <span class="left"><%=partyName %></span>
		    <div class="right">
	        </div>
	    </div>
<%} %>
    <div class="bd">
      <%if("".equals(partyName)){ %>
        <div class="weui_cells_title">参加 (<%=count1 %>人)</div>
      <%} %>
	    <div class="weui_cells">
	      <div class="weui_cell">
		   <div class="weui_cell_bd weui_cell_primary pmb">
		     <dl style="heigth:<%=height1%>px;">
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
	      </div>
	     </div>
	 <%if("".equals(partyName) == false && count2 > 0){ %>    
       <div class="weui_cells_title">待定 (<%=count2 %>人)</div>
	    <div class="weui_cells">
	      <div class="weui_cell">
		   <div class="weui_cell_bd weui_cell_primary pmb">
		     <dl style="heigth:<%=height2%>px;">
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
	     </div>
      </div>
     <%} %>

<p class="text-right" style="padding-top:10px;padding-right:5px;">确定出席(含家属)：<%=count %>人</p>


