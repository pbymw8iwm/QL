<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>我的相册</title>
	
    <meta http-equiv="keywords" content="相册 聚会助手  社交圈">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <%@ include file="/CommonHead.jsp"%>
    <%@ include file="/WechatJsHead.jsp"%>
    
  </head>
 
  <body>
<div id="container">
</div>
<%@ include file="/WeuiCommon.jsp"%>
  </body>
</html>

<script src="<%=request.getContextPath()%>/js/Main.js" language="JavaScript"></script>
<script type="text/javascript">
gotoPage("/album/Album.jsp","#AM");
</script>
