<%@page import="com.ql.party.ivalues.IQPartyValue"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.ql.party.web.PartyAction"%>
<%@page import="com.ai.appframe2.web.HttpUtil"%>
<%@page import="com.ql.wechat.WechatCommons"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<%
Long partyId = HttpUtil.getAsLong(request, "partyId");
IQPartyValue party = PartyAction.getParty(partyId,false);
%> 
  <head>
    <title>邀您参加聚会</title>
	
    <meta http-equiv="keywords" content="聚会助手  社交圈">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <%@ include file="/CommonHead.jsp"%>
  </head>
  <body>
    <div class="container">
        <br/>
		<div class="panel panel-success">
		  <div class="panel-heading"><%=party.getTheme() %></div>
		  <div class="panel-body">
		    <p class="help-block text-center">
				来自：<%=party.getCname() %>&nbsp;&nbsp;-&nbsp;&nbsp;<%=party.getUsername() %>
				<br/>
				<B><%=party.getExtAttr("PTime") %></B>
				<br/>
				地点：<B><%=party.getGatheringplace() %></B>
				<br/><br/>
				<button type="button" class="btn btn-link" id="btnInfo" data-toggle="modal" data-target="#infoModal">查看参与情况</button>
			</p>
		  </div>
		  <%if(StringUtils.isNotBlank(party.getRemarks())){ %>
		  <div class="panel-footer"><%=party.getRemarks() %></div>
		  <%} %>
		</div>
		<br/>
		<p class="help-block text-center">
		      参加聚会请长按扫描下方二维码：
		  <img src="<%=WechatCommons.Url_ShowQr + party.getQrticket()%>" class="img-rounded" width="200" height="200"/>
		  <br/>
		      扫码参加聚会，分享精彩时刻
		</p>

		<div class="modal fade" id="infoModal" tabindex="-1" role="dialog" aria-labelledby="infoLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		        <h4 class="modal-title" id="infoLabel">参加的圈友</h4>
		      </div>
		      <div class="modal-body" id="divInfo"></div>
		    </div>
		  </div>
		</div>
	</div>
  </body>
</html>

<script language="javascript">
$(document).ready(function(){
  $("#btnInfo").click(function(){
    var div = $("#divInfo");
    if(div.html() == "")
      div.load("<%=request.getContextPath()%>/party/_MemberInfo.jsp?partyId=<%=partyId%>");
  });
});
</script>