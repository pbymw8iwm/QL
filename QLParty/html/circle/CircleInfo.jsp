<%@page import="com.ql.party.wechat.WechatOpImpl"%>
<%@page import="com.ai.appframe2.common.SessionManager"%>
<%@page import="com.ql.wechat.WechatCommons"%>
<%@page import="com.ql.party.web.PartyAction"%>
<%@page import="com.ql.party.ivalues.ISocialCircleValue"%>
<%@page import="com.ai.appframe2.web.HttpUtil"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>圈子信息</title>
	
    <meta http-equiv="keywords" content="聚会助手  社交圈">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <%@ include file="/CommonHead.jsp"%>
    <%@ include file="/WechatJsHead.jsp"%>
  </head>

<%
long cId = HttpUtil.getAsLong(request, "cId");
ISocialCircleValue sc = PartyAction.getSocialCircle(cId,true);
if(sc == null){
%>
<body><h3>圈子可能已经被删除</h3></body>
<%
  return;
}
long userId = SessionManager.getUser().getID();
String userName = SessionManager.getUser().getName();
%>  
  <body>
    <div class="container">
		<div class="page-header">
		  <table>
		    <tr>
		      <td rowspan="2"><img id="cImg" src="<%=sc.getImagedata() %>" class="img-thumbnail" width="100" height="100"/></td>
		      <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
		      <td><h3><%=sc.getCname() %></h3></td>
		    </tr>
		    <tr>
		      <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
		      <td><%=sc.getExtAttr("TypeName") %>圈</td>
		    </tr>
		  </table>
		</div>
		<div class="center-block">
		  <button type="button" class="btn btn-link" id="btnEdit" data-toggle="modal" data-target="#myInfoModal">编辑我的圈信息</button>
		  <%if(userId == sc.getCreater()){ %>  
		  <button type="button" class="btn btn-link" id="btnChange">转让圈主</button>
		  <%} %>
		</div>
		<div class="panel-group" id="accordion">
		  <div class="panel panel-info" id="divMember">
		    <div class="panel-heading">
		      <div class="row">
			    <a data-toggle="collapse" data-parent="#accordion" href="#cMember" id="aMember"><div class="col-xs-10"><h4>圈友(<%=sc.getExtAttr("MemberCount") %>个)</h4></div></a>
			    <div class="col-xs-2"><button type="button" class="btn btn-link" id="btnShare"><span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span></button></div>
			  </div>
		    </div>
		    <div id="cMember" class="panel-collapse collapse"></div>
		  </div>
		  <div class="panel panel-info" id="divParty">
		    <div class="panel-heading">
		      <div class="row">
			    <a data-toggle="collapse" data-parent="#accordion" href="#cParty" id="aParty"><div class="col-xs-10"><h4>聚会(<%=sc.getExtAttr("PartyCount") %>个)</h4></div></a>
			    <div class="col-xs-2"><button type="button" class="btn btn-link" id="btnParty"><span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span></button></div>
			  </div>
		    </div>
		    <div id="cParty" class="panel-collapse collapse"></div>
		  </div>
		  <br/>
		  <p class="text-right">
		    <button type="button" class="btn btn-default" id="btnQuit">退出圈子</button>
		  </p>
		</div>
	  
	  
		<div class="modal fade" id="myInfoModal" tabindex="-1" role="dialog" aria-labelledby="myInfoLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		        <h4 class="modal-title" id="myInfoLabel">我的圈信息</h4>
		      </div>
		      <div class="modal-body">
				<form role="form" class="form-horizontal" id="frmInfo" name="frmInfo">
				   <div class="sr-only">
				     <input type="text" maxlength="10" class="form-control" id="CId" name="CId" value="<%=cId%>"/>
				     <input type="text" maxlength="10" class="form-control" id="UserId" name="UserId" value="<%=userId%>"/>
				   </div>
				   <div class="form-group">
				      <label for="UserName" class="col-xs-3 control-label">昵称</label>
				      <div class="col-xs-9"><input type="text" maxlength="10" class="form-control" id="UserName" name="UserName" placeholder="请输入昵称"></div>
				   </div>
				   <div class="form-group">
				      <label for="CType" class="col-xs-3 control-label">电话</label>
				      <div class="col-xs-9"><input type="tel" class="form-control" id="Phone" name="Phone" placeholder="请输入电话"></div>
				   </div>
				   <div class="form-group">
				      <label for="Job" class="col-xs-3 control-label">工作</label>
				      <div class="col-xs-9"><input type="text" maxlength="30" class="form-control" id="Job" name="Job" placeholder="请输入工作"></div>
				   </div>
				   <div class="form-group">
				      <label for="City" class="col-xs-3 control-label">地址</label>
				      <div class="col-xs-9"><input type="text" maxlength="30" class="form-control" id="City" name="City" placeholder="请输入地址"></div>
				   </div>
				</form>
			  </div>
			    <div class="modal-footer">
			        <button type="button" class="btn btn-sm btn-success" id="btnSave">保存</button>
			        <button type="button" class="btn btn-sm btn-default" data-dismiss="modal">取消</button>
			    </div>
		    </div>
		  </div>
		</div>
	</div>
  </body>
</html>
<script language="javascript">

  var shareMsg = {
	    title: '<%=userName%>邀您加入<%=sc.getCname()%>', // 分享标题
	    desc: '加入圈子，参与聚会，分享照片', // 分享描述
	    link: '<%=WechatCommons.getUrlView(WechatOpImpl.Type_JoinCircle+sc.getCid())%>', // 分享链接
	    //link: 'http://<%=WechatCommons.ServerIp%>/circle/CircleQR.jsp?userName=<%=userName%>&cName=<%=sc.getCname()%>&ticket=<%=sc.getQrticket()%>&cImg='+encodeURIComponent('<%=sc.getImagedata()%>'), // 分享链接
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
  
var myInfo = null;  
$(document).ready(function(){
  $("#btnShare").click(function(){
    alert("请点击右上角的三个小点，分享到朋友或朋友圈，邀请朋友加入圈子！");
  }); 
  
  $("#btnParty").click(function(){
    window.location = "<%=request.getContextPath()%>/party/NewParty.jsp?cId=<%=cId%>&cName=<%=sc.getCname() %>";
  }); 
  
  $("#aMember").click(function(){
    if($("#cMember").html() == "")
    	$("#cMember").load("<%=request.getContextPath()%>/circle/_MemberInfo.jsp?cId=<%=cId%>");
  });
  
  $("#aParty").click(function(){
    if($("#cParty").html() == ""){
      if(<%=sc.getExtAttr("PartyCount") %> == 0)
        $("#cParty").text("无");
      else
        $("#cParty").load("<%=request.getContextPath()%>/party/CurrentParty.jsp?cId=<%=cId%>");
    }
  });
  
  $("#btnEdit").click(function(){
    if(myInfo != null)
      return;
    
    //_MemberInfo.jsp中是否查询过用户信息，mInfo是其中的变量
    if(typeof(mInfo) != "undefined"){
      for(var i=0;i<mInfo.length;i++){
        if(mInfo[i].UserId == <%=userId%>){
          myInfo = mInfo[i];
          break;
        }
      }
    }
    else{
       $.ajax({ 
                cache: false,
				async: false,
				type: "get", 
				url: _gModuleName+"/business/com.ql.party.web.PartyAction?action=getSelfCircleInfo&cId=<%=cId%>",
				contentType: "text/html; charset=UTF-8",
				success: function(data, textStatus){
				  if(textStatus == "success"){
				    if(data.flag == true){
				    	myInfo = data.msg;
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
    if(myInfo != null){
      $("#UserName").val(myInfo.UserName);
      $("#Phone").val(myInfo.Phone);
      $("#Job").val(myInfo.Job);
      $("#City").val(myInfo.City);
    }
    
  }); 
  
  $("#btnSave").click(function(){
	    if($('#UserName').val() == ""){
	      $('#UserName').focus();
	      return;
	    }
	    var datas = JSON.stringify($('#frmInfo').serializeObject());
	    $.ajax({ 
				type: "post", 
				async: false,
				processData: false,
				url: _gModuleName+"/business/com.ql.party.web.PartyAction?action=saveCircleMemberInfo",
				data: datas, 
				contentType: "text/html; charset=UTF-8",
				success: function(data, textStatus){
				  if(textStatus == "success"){
				    if(data.flag == true){
				    	$('#myInfoModal').modal('hide');
				    	myInfo.UserName = $("#UserName").val();
      					myInfo.Phone = $("#Phone").val();
                        myInfo.Job = $("#Job").val();
      					myInfo.City = $("#City").val();
      					if(typeof(mInfo) != "undefined"){
					      for(var i=0;i<mInfo.length;i++){
					        if(mInfo[i].UserId == <%=userId%>){
					          mInfo[i].UserName = $("#UserName").val();
		      				  mInfo[i].Phone = $("#Phone").val();
		                      mInfo[i].Job = $("#Job").val();
		      				  mInfo[i].City = $("#City").val();
		      				  $("#s<%=userId%>").text($("#UserName").val());
					          break;
					        }
					      }
					    }
				    }
				    else
				      alert(data.msg);
				  }
	      },
	      error:function(httpRequest,errType,ex ){
	        alert(ex);
	      }
			}); 
  });
  
  $("#btnQuit").click(function(){
    if(confirm("确定退出当前圈子？")){
      $.ajax({ 
				type: "post", 
				async: false,
				processData: false,
				url: _gModuleName+"/business/com.ql.party.web.PartyAction?action=quitCircle&cId=<%=sc.getCid()%>",
				contentType: "text/html; charset=UTF-8",
				success: function(data, textStatus){
				  if(textStatus == "success"){
				    if(data.flag == true){
				      history.back(-1);
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
  
  $("#btnChange").click(function(){
    alert("todo...");
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
				      var r = Math.random();
				      shareMsg.imgUrl = shareMsg.imgUrl + r;
				      //shareMsg.link = shareMsg.link + r;
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

</script>