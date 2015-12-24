<%@page import="com.ai.appframe2.common.SessionManager"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>创建聚会</title>
	
    <meta http-equiv="keywords" content="聚会助手  社交圈">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <%@ include file="/CommonHead.jsp"%>
  </head>
  
  <body>
    <div class="container">
		<h3>创建聚会</h3>
		<img src="<%=SessionManager.getUser().get("image") %>" class="img-rounded" width="50" height="50"/>
	</div>
  </body>
</html>
