<%@page import="com.ql.party.ivalues.IQPartyValue"%>
<%@page import="com.ql.party.web.PartyAction"%>
<%@page import="com.ai.appframe2.web.HttpUtil"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<style type="text/css">
a:hover,
a:focus {  
  text-decoration: none;
}
</style>
<%
long cId = HttpUtil.getAsLong(request, "cId");
IQPartyValue[] partys = PartyAction.getPartys(cId);
boolean hasData = false;
%>  

  <div class="bd">
    <div class="weui_cells_title">参与的</div>
    <div class="weui_cells">
      <%for(IQPartyValue party : partys){ 
          if(party.getExtAttr("PState") == null)
            continue;
          hasData = true;
      %>
      <%@ include file="/party/_PartyList.jsp"%>
      <%} %>
      <%if(hasData == false){ %>
      <div class="page-group">&nbsp;&nbsp;&nbsp;&nbsp;无</div>
      <%} %>
	</div>
	
	<div class="weui_cells_title">未参与</div>
    <div class="weui_cells">
      <%
        hasData = false;
        for(IQPartyValue party : partys){ 
          if(party.getExtAttr("PState") != null)
            continue;
          hasData = true;
      %>
      <%@ include file="/party/_PartyList.jsp"%>
      <%} %>
      <%if(hasData == false){ %>
      <div class="page-group">&nbsp;&nbsp;&nbsp;&nbsp;无</div>
      <%} %>
	</div>
  </div>

<script src="<%=request.getContextPath()%>/party/PartyList.js" language="JavaScript"></script>