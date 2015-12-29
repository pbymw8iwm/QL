<%@page import="com.ai.appframe2.common.SessionManager"%>
<%@page import="com.ql.wechat.WechatCommons"%>
<%@page import="com.ql.party.web.PartyAction"%>
<%@page import="com.ql.party.ivalues.ISocialCircleValue"%>
<%@page import="com.ai.appframe2.web.HttpUtil"%>
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
long cId = HttpUtil.getAsLong(request, "cId");
ISocialCircleValue sc = PartyAction.getSocialCircle(cId);
long userId = SessionManager.getUser().getID();
String userName = SessionManager.getUser().getName();
%>  
  <body>
    <div class="container">
      <%if(sc == null){ %>
        <h3>此圈不存在，可能已经被删除</h3>
      <%}else{ %>
		<div class="page-header">
		  <table>
		    <tr>
		      <td rowspan="2"><img id="cImg" src="<%=sc.getImagedata() %>" class="img-rounded" width="100" height="100"/></td>
		      <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
		      <td><h3><%=sc.getCname() %></h3></td>
		    </tr>
		    <tr>
		      <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
		      <td><%=sc.getExtAttr("TypeName") %>圈
		  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="btn btn-link" id="btnShare">请点右上角分享↑</button></td>
		    </tr>
		  </table>
		  	
		  	
		</div>
		<div class="center-block">
		  <button type="button" class="btn btn-link" >编辑我的圈信息</button>
		  <button type="button" class="btn btn-link" >创建聚会</button>
		</div>
		<div class="panel-group" id="accordion">
		  <div class="panel panel-info" id="divMember">
		    <div class="panel-heading">
		      <a data-toggle="collapse" data-parent="#accordion" href="#cMember"><h4 class="panel-title">圈友</h4></a>
		    </div>
		    <div id="cMember" class="panel-collapse collapse">
		      成员。。。
		    </div>
		  </div>
		  <div class="panel panel-info" id="divParty">
		    <div class="panel-heading">
		      <a data-toggle="collapse" data-parent="#accordion" href="#cParty"><h4 class="panel-title">聚会</h4></a>
		    </div>
		    <div id="cParty" class="panel-collapse collapse">
		      聚会。。。
		    </div>
		  </div>
		</div>
	  <%} %>
	</div>
  </body>
</html>
<script language="javascript">

<%if(sc != null){ %>
  var shareMsg = {
	    title: '<%=userName%>邀您加入<%=sc.getCname()%>', // 分享标题
	    desc: '加入圈子，参与聚会，分享照片', // 分享描述
	    link: 'http://<%=WechatCommons.ServerIp%>/circle/CircleQR.jsp?userName=<%=userName%>&cName=<%=sc.getCname()%>&ticket=<%=sc.getQrticket()%>&cImg='+encodeURIComponent('<%=sc.getImagedata()%>'), // 分享链接
	    imgUrl: '<%=sc.getImagedata()%>', // 分享图标
	    type: 'link', // 分享类型,music、video或link，不填默认为link
	    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
	    success: function () { 
	        // 用户确认分享后执行的回调函数
	    },
	    cancel: function () { 
	        // 用户取消分享后执行的回调函数
	    }
	};
	
wx.ready(function(){
  wx.hideOptionMenu();
  wx.showMenuItems({
    menuList: ['menuItem:share:appMessage','menuItem:share:timeline'] // 要显示的菜单项，所有menu项见附录3
  });

  wx.onMenuShareAppMessage(shareMsg);
  wx.onMenuShareTimeline(shareMsg);
});
wx.error(function(res){
//    alert(res.errMsg);
});
  
$(document).ready(function(){
  $("#btnShare").click(function(){
    alert("请点击右上角的三个小点，分享到朋友或朋友圈！");
  }); 


<%if(userId == sc.getCreater()){ %>  
  $("#cImg").click(function(){
    wx.chooseImage({
	    count: 1, // 默认9
	    sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
	    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
	    success: function (res) {
	        var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
	        jQuery(function(){
	          $.each( localIds, function(i, n){
	              $("#cImg").attr("src",n);
	              wx.uploadImage({
					    localId: n, // 需要上传的图片的本地ID，由chooseImage接口获得
					    isShowProgressTips: 1, // 默认为1，显示进度提示
					    success: function (res) {
					        var serverId = res.serverId; // 返回图片的服务器端ID
					        dealImg(serverId);
					    }
				  });
	            });
	        });
	    }
	});
  });
<%}%>
});

function dealImg(serverId){
	$.ajax({ 
				type: "post", 
				async: false,
				processData: false,
				url: _gModuleName+"/business/com.ql.party.web.PartyAction?action=changeCircleImg&cId=<%=sc.getCid()%>&mediaId="+serverId,
				contentType: "text/html; charset=UTF-8",
				success: function(data, textStatus){
				  if(textStatus == "success"){
				    if(data.flag == true){
				      shareMsg.imgUrl = shareMsg.imgUrl + Math.random();
				      shareMsg.link = shareMsg.link + Math.random();
					  wx.onMenuShareAppMessage(shareMsg);
					  wx.onMenuShareTimeline(shareMsg);
				    }
				    else
				      alert(data.msg);
				  }
	      },
	      error:function(httpRequest,errType,ex ){
	        alert(ex);
	      }
	}); 
}

<%}%>
</script>