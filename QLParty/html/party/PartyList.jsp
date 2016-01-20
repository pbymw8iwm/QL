<%@page import="com.ql.party.ivalues.ISocialCircleValue"%>
<%@page import="com.ql.party.web.PartyAction"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>聚会列表</title>
	
    <meta http-equiv="keywords" content="聚会助手  社交圈">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <%@ include file="/CommonHead.jsp"%>
    <%@ include file="/WechatJsHead.jsp"%>
  </head>
<style type="text/css">
a:hover,
a:focus {  
  text-decoration: none;
}
</style>
<%
ISocialCircleValue[] scs = PartyAction.getSocialCircles(false);
%>    
  <body>
    <div class="container">
    <br/>
	  <%if(scs == null || scs.length == 0){ %>
	  无
	  <%}else{ %>
	  <div class="panel-group" id="accordionP">
	    <%for(ISocialCircleValue sc : scs){ %>
	    <div class="panel panel-info">
		    <div class="panel-heading">
		      <a data-toggle="collapse" data-parent="#accordionP" href="#collapseP<%=sc.getCid()%>" xname="aInfo" cid="<%=sc.getCid()%>">
		        <h4 class="panel-title"><img src="<%=sc.getImagedata() %>" class="img-rounded" width="50" height="50"/>&nbsp;&nbsp;<%=sc.getCname() %></h4>
		      </a>
		    </div>
		    <div id="collapseP<%=sc.getCid()%>" class="panel-collapse collapse"></div>
	    </div>
	    <%} %>
	  </div>
	  <%} %>
	</div>
  </body>
</html>

<script language="javascript">
$(document).ready(function(){
$("[xname='aa']").click(function(){
$("#tt").load("<%=request.getContextPath()%>/index.jsp");
});
  $("[xname='aInfo']").click(function(){
    var cId = $(this).attr("cid");
    var div = $("#collapseP"+cId);
    if(div.html() == "")
      div.load("<%=request.getContextPath()%>/party/_PartyList.jsp?cId="+cId);
  });
});
</script>