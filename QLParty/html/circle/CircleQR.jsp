<%@page import="java.net.URLDecoder"%>
<%@page import="com.ai.appframe2.web.HttpUtil"%>
<%@page import="com.ql.wechat.WechatCommons"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<%
String userName = HttpUtil.getAsString(request, "userName");
String cName = HttpUtil.getAsString(request, "cName");
String cImg = URLDecoder.decode(HttpUtil.getAsString(request, "cImg"),"UTF-8"); 
String ticket = HttpUtil.getAsString(request, "ticket");
%> 
  <head>
    <title><%=userName %>分享的圈子</title>
	
    <meta http-equiv="keywords" content="聚会助手  社交圈">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <%@ include file="/CommonHead.jsp"%>
  </head>
  <body>
    <div class="container">
        <br/>
		<div class="panel panel-success">
		  <div class="panel-heading">圈名片</div>
		  <div class="panel-body">
		    <p class="help-block text-center">
				<img src="<%=cImg %>" class="img-rounded" width="80" height="80"/>
				<br/>
				<B><%=cName %></B>
				<br/>
				<img src="<%=WechatCommons.Url_ShowQr + ticket%>" class="img-rounded" width="200" height="200"/>
				<br/>
				扫码加入此圈，参与聚会，分享照片
			</p>
		  </div>
		</div>
	</div>
  </body>
</html>
