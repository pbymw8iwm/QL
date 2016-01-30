<%
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", -10);
%>

<link rel="stylesheet" href="https://res.wx.qq.com/open/libs/weui/0.3.0/weui.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/style/weui_sample.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/style/ql.css">
				
<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
		
<script src="<%=request.getContextPath()%>/js/Common.js" language="JavaScript"></script>
<script src="<%=request.getContextPath()%>/js/Globe.jsp" language="JavaScript"></script>

<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0">

<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=0">