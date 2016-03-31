<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.ql.party.ivalues.IQPartyValue"%>
<%@page import="com.ql.party.web.PartyAction"%>
<%@page import="com.ai.appframe2.web.HttpUtil"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<%
long cId = HttpUtil.getAsLong(request, "cId");
String cName = HttpUtil.getAsString(request, "cName");
IQPartyValue[] partys = PartyAction.getPartys(cId);
boolean hasData = false;
%>  

<div class="page-header-group">
<div class="border">
		    <span class="left"><%=cName %> - 聚会</span>
		    <div class="right"></div>
		  </div>
	    </div>
  <div class="bd" style="padding-top:15px;">
  <%
    SimpleDateFormat df = new SimpleDateFormat("M.d");
    int year = 0;
    boolean isNewGroup = false;
    for(IQPartyValue party : partys){ 
      String tmp = null;
      if(party.getExtAttr("PState") == null)
        tmp = "txt-default";
      else{ 
        long state = ((Long)party.getExtAttr("PState")).longValue();
        if(state == 1)
          tmp = "txt-info";
        else
          tmp = "txt-warning";
      }
      if(party.getStarttime().getYear() != year){
        if(year != 0){
  %>
      </div> <!-- end  weui_cells -->
  <%    }
        isNewGroup = true;
        year = party.getStarttime().getYear();
  %>
      <div class="weui_cells_title"><%=year+1900 %>年</div>
      <div class="weui_cells">
  <%  } %>
      <div class="page-group">
          <div class="weui_cell">
	          <div class="weui_cell_bd weui_cell_primary">
	            <p><a href='javascript:gotoPage("/party/party/PartyInfo.jsp?partyId=<%=party.getPartyid()%>","#pi");'>
	                 <strong><%=party.getTheme() %></strong>
	                 <small style="padding-left:5px"><%=df.format(party.getStarttime()) %>
	                   <span class="glyphicon glyphicon-glass <%=tmp%>" style="padding-left:5px"/>
	                 </small>
	            </a></p>
	          </div>
	          <div class="weui_cell_ft">
		          <a href='javascript:gotoPage("/party/party/Photo.jsp?partyId=<%=party.getPartyid()%>&cId=<%=party.getCid()%>&manager=<%=party.getCreater()%>&partyName=<%=party.getTheme() %>","#pp");'>
	   		        <span class="glyphicon glyphicon-picture" aria-hidden="true"></span>
	   		      </a>
	          </div>
          </div>
      </div>
      
  <%} 
    if(isNewGroup){
  %>
    </div> <!-- end  weui_cells -->
  <%} %>
  
  <div class="weui_cells"></div>
  <div class="bd spacing">
			   <a href='javascript:gotoPage("/party/party/NewParty.jsp?cId=<%=cId%>&cName=<%=cName %>","#pn");' class="weui_btn weui_btn_primary">创建聚会</a>
  </div>
</div>

  