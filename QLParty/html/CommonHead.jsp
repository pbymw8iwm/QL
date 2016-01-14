<%
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", -10);
%>

<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/common.css">
		
<!-- 
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">-->
		
<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
		
<script src="http://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<script src="<%=request.getContextPath()%>/js/Common.js" language="JavaScript"></script>
<script src="<%=request.getContextPath()%>/js/Globe.jsp" language="JavaScript"></script>

<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0">

<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=0">