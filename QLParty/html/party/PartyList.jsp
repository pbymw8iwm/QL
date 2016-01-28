<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.ql.party.ivalues.IQPartyValue"%>
<%@page import="com.ql.party.web.PartyAction"%>
<%@page import="com.ai.appframe2.web.HttpUtil"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<%
long cId = HttpUtil.getAsLong(request, "cId");
IQPartyValue[] partys = PartyAction.getPartys(cId);
boolean hasData = false;
%>  

  <div class="page-header-border">圈聚会</div>
  <div class="bd">
  <%
    SimpleDateFormat df = new SimpleDateFormat("M.d");
    int year = 0;
    boolean isNewGroup = false;
    for(IQPartyValue party : partys){ 
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
	            <p><a href='javascript:gotoPage("/party/PartyInfo.jsp?partyId=<%=party.getPartyid()%>","#pi");'>
	                 <strong><%=party.getTheme() %></strong>
	                 <small style="padding-left:5px"><%=df.format(party.getStarttime()) %></small>
	            </a></p>
	          </div>
	          <div class="weui_cell_ft">
		          <a href='javascript:gotoPage("/party/Photo.jsp?partyId=<%=party.getPartyid()%>&cId=<%=party.getCid()%>&manager=<%=party.getCreater()%>&partyName=<%=party.getTheme() %>","#pp");'>
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
			   <a href='javascript:gotoPage("/party/NewParty.jsp?cId=<%=cId%>","#pn");' class="weui_btn weui_btn_primary">创建聚会</a>
  </div>
</div>

  