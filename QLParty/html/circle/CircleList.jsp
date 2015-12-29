<%@page import="com.ai.appframe2.common.SessionManager"%>
<%@page import="com.ql.party.web.PartyAction"%>
<%@page import="com.ql.party.ivalues.ISocialCircleValue"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>我的圈子</title>
	
    <meta http-equiv="keywords" content="聚会助手  社交圈">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <%@ include file="/CommonHead.jsp"%>
    <%@ include file="/WechatJsHead.jsp"%>
  </head>
<%
ISocialCircleValue[] scs = PartyAction.getSocialCircles();
String userName = SessionManager.getUser().getName();
%>  
  <body>
    <div class="container">
      
	  <%for(ISocialCircleValue sc : scs){ %>
	    <div class="page-header">
	  	  <table>
		    <tr>
		      <td rowspan="2"><img id="cImg<%=sc.getCid() %>" src="<%=sc.getImagedata() %>" class="img-rounded" width="80" height="80"/></td>
		      <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
		      <td><h3><a href='<%=request.getContextPath()%>/circle/CircleInfo.jsp?cId=<%=sc.getCid()%>' id="a<%=sc.getCid()%>"><%=sc.getCname() %></a></h3></td>
		    </tr>
		    <tr>
		      <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
		      <td><%=sc.getExtAttr("TypeName") %>圈
		  	&nbsp;&nbsp;&nbsp;&nbsp;<%=sc.getExtAttr("MemberCount") %>个成员
		  	&nbsp;&nbsp;&nbsp;&nbsp;<%=sc.getExtAttr("PartyCount") %>个聚会
		  	&nbsp;&nbsp;&nbsp;&nbsp;<a xname="aShare" cid="<%=sc.getCid()%>" ticket="<%=sc.getQrticket()%>">分享</a></td>
		    </tr>
		  </table>
		</div>
	  <%} %>
	</div>
  </body>
</html>
<script language="javascript">

wx.ready(function(){
  wx.hideOptionMenu();
  wx.showMenuItems({
    menuList: ['menuItem:share:appMessage','menuItem:share:timeline'] // 要显示的菜单项，所有menu项见附录3
  });

});
wx.error(function(res){
//    alert(res.errMsg);
});
  
$(document).ready(function(){
  $("[xname='aShare']").click(function(){
    var cName = $("#a"+$(this).attr("cid")).text();
    var ticket = $(this).attr("ticket");
    var img = $("#cImg"+$(this).attr("cid")).attr("src");
    alert(cName);
    alert(ticket);
    alert(img);
    var shareMsg = {
	    title: '<%=userName%>邀您加入'+cName, // 分享标题
	    desc: '加入圈子，参与聚会，分享照片', // 分享描述
	    link: 'http://<%=WechatCommons.ServerIp%>/circle/CircleQR.jsp?userName=<%=userName%>&cName='+cName+'&ticket='+ticket+'&cImg='+encodeURIComponent(img), // 分享链接
	    imgUrl: img, // 分享图标
	};
    wx.onMenuShareAppMessage(shareMsg);
    wx.onMenuShareTimeline(shareMsg);
    
    alert("请点击右上角的三个小点，分享到朋友或朋友圈！");
  }); 
});
</script>