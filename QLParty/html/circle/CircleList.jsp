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
ISocialCircleValue[] scs = PartyAction.getSocialCircles(true);
String userName = SessionManager.getUser().getName();
%>  
  <body>
    <div class="container">
      
	  <%for(ISocialCircleValue sc : scs){ %>
	    <div class="page-header">
	  	  <table>
		    <tr>
		      <td rowspan="2"><img id="cImg<%=sc.getCid() %>" src="<%=sc.getImagedata() %>" class="img-thumbnail" width="80" height="80"/></td>
		      <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
		      <td><h3><a href='<%=request.getContextPath()%>/circle/CircleInfo.jsp?cId=<%=sc.getCid()%>' id="a<%=sc.getCid()%>"><%=sc.getCname() %></a></h3></td>
		    </tr>
		    <tr>
		      <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
		      <td><%=sc.getExtAttr("TypeName") %>圈
		  	&nbsp;&nbsp;&nbsp;&nbsp;<a data-toggle="modal" href="#mInfo" xname="aInfo" cid="<%=sc.getCid()%>" xtype="m"><%=sc.getExtAttr("MemberCount") %>个成员</a>
		  	&nbsp;&nbsp;&nbsp;&nbsp;<a data-toggle="modal" href="#mInfo" xname="aInfo" cid="<%=sc.getCid()%>" xtype="p"><%=sc.getExtAttr("PartyCount") %>个聚会</a>
		  	&nbsp;&nbsp;&nbsp;&nbsp;<a xname="aShare" cid="<%=sc.getCid()%>"><span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span></a></td>
		    </tr>
		  </table>
		</div>
	  <%} %>
	  
	  <div class="modal fade" id="mInfo" tabindex="-1" role="dialog" aria-labelledby="mInfoLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		        <h4 class="modal-title" id="mInfoLabel"></h4>
		      </div>
		      <div class="modal-body" id="divInfo"></div>
		    </div>
		  </div>
		</div>
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

var lastType = null;
var lastId = null;  
$(document).ready(function(){
  $("[xname='aInfo']").click(function(){
    var cId = $(this).attr("cid");
    var type = $(this).attr("xtype");
    if(lastType == type && lastId == cId)
      return;
    lastType = type;
    lastId = cId;
    if(type == "m"){
      $("#mInfoLabel").text("圈友信息");
      $("#divInfo").empty();
      $("#divInfo").load("<%=request.getContextPath()%>/circle/_MemberInfo.jsp?t=1&cId="+cId);
    }
    else{
      $("#mInfoLabel").text("聚会信息");
      $("#divInfo").empty();
      $("#divInfo").load("<%=request.getContextPath()%>/party/_PartyList.jsp?cId="+cId);
    }
  });

  $("[xname='aShare']").click(function(){
    var cId = $(this).attr("cid");
    var cName = $("#a"+cId).text();
    var img = $("#cImg"+cId).attr("src");
    var cLink = "";
    $.ajax({ 
                cache: false,
				async: false,
				type: "get", 
				url: _gModuleName+"/business/com.ql.party.web.PartyAction?action=getCircleShareLink&cId="+cId,
				contentType: "text/html; charset=UTF-8",
				success: function(data, textStatus){
				  if(textStatus == "success"){
				    if(data.flag == true){
				    	cLink = data.msg;
				    }
				    else
				      alert(data.msg);
				  }
	      },
	      error:function(httpRequest,errType,ex ){
	        alert(ex);
	      }
		});
    var shareMsg = {
	    title: '<%=userName%>邀您加入'+cName, // 分享标题
	    desc: '加入圈子，参与聚会，分享照片', // 分享描述
	    link: cLink, // 分享链接
	    imgUrl: img, // 分享图标
	};
    wx.onMenuShareAppMessage(shareMsg);
    wx.onMenuShareTimeline(shareMsg);
    
    alert("请点击右上角的三个小点，分享到朋友或朋友圈，邀请朋友加入圈子！");
  }); 
});
</script>