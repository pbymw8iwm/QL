<%@page import="com.ql.party.ivalues.IQPartyValue"%>
<%@page import="com.ql.party.web.PartyAction"%>
<%@page import="com.ai.appframe2.web.HttpUtil"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<%
IQPartyValue[] partys = PartyAction.getPartys(0);
boolean hasData = false;
String stateClass = null;
%>  

  <div class="page-header-border">当前聚会</div>
  <div class="bd">
    <div class="weui_cells_title">参与的</div>
      <%for(IQPartyValue party : partys){ 
          if(party.getExtAttr("PState") == null)
            continue;
          long state = ((Long)party.getExtAttr("PState")).longValue();
          if(state == 1)
            stateClass = "txt-info";
          else
            stateClass = "txt-warning";
          hasData = true;
      %>
      <%@ include file="/party/party/_Partys.jsp"%>
      <%} %>
      <%if(hasData == false){ %>
      <div class="weui_cells"><div class="page-group">&nbsp;&nbsp;&nbsp;&nbsp;无</div></div>
      <%} %>
	
	<div class="weui_cells_title">未参与</div>
      <%
        hasData = false;
        stateClass = "txt-default";
        for(IQPartyValue party : partys){ 
          if(party.getExtAttr("PState") != null)
            continue;
          hasData = true;
      %>
      <%@ include file="/party/party/_Partys.jsp"%>
      <%} %>
      <%if(hasData == false){ %>
      <div class="weui_cells"><div class="page-group">&nbsp;&nbsp;&nbsp;&nbsp;无</div></div>
      <%} %>
  </div>

<script src="<%=request.getContextPath()%>/party/party/Partys.js" language="JavaScript"></script>