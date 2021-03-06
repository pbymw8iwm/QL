<%@page import="com.ql.party.ivalues.IPartyPhotoValue"%>
<%@page import="com.ai.appframe2.common.SessionManager"%>
<%@page import="com.ql.party.web.PartyAction"%>
<%@page import="com.ai.appframe2.web.HttpUtil"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>精彩时刻</title>
	
    <meta http-equiv="keywords" content="聚会助手  社交圈">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <%@ include file="/CommonHead.jsp"%>
    <%@ include file="/WechatJsHead.jsp"%>
  </head>

<%
long partyId = HttpUtil.getAsLong(request, "partyId");
long cId = HttpUtil.getAsLong(request, "cId");
long manager = HttpUtil.getAsLong(request, "manager");
long userId = SessionManager.getUser().getID();
boolean isM = userId == manager;
IPartyPhotoValue[] phs = PartyAction.getPhotoes(partyId);
int total = phs.length + 2;
int rows = total/4;
if(total%4 > 0)
  rows += 1;
int height = rows * 100;
%>  
  
<style type="text/css">
dl { width:100%;height:<%=height%>px;border:0px;margin:0;font-size:14px;}
dl dd {width:25%; height:100px; border:0px; float:left;text-align:center ;padding-top: 7px;}
</style>
  
  <body >
    <div class="container">
		<dl>
		<%for(IPartyPhotoValue ph:phs){ %>
		  <dd>
		    <img src="<%=ph.getPhotodata() %>-200" data-src="<%=ph.getPhotodata() %>" class="img-rounded" width="70" height="70" xname="iPhoto"/>
		    <br/>
		    <%if(isM || userId == ph.getUserid()){ %>
		    <input type="checkbox" xid="<%=ph.getPhotoid()%>" xuser="<%=ph.getUserid()%>" class="sr-only">
		    <%} %>
		  </dd>
		<%} %>
		  <dd><button type="button" class="btn btn-default btn-lg" id="btnAdd" style="width:70px;height:70px;"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></button></dd>
		  <dd><button type="button" class="btn btn-default btn-lg" id="btnDel" style="width:70px;height:70px;"><span class="glyphicon glyphicon-minus" aria-hidden="true"></span></button></dd>
		</dl>
		
		<div class="navbar-fixed-bottom text-center sr-only" id="divInfo">
		  正在上传，请等待。。。
		</div>
		<div class="navbar-fixed-bottom text-center sr-only" id="divDel">
		   <button type="button" class="btn btn-success" id="btnSave">删除</button>&nbsp;&nbsp;&nbsp;&nbsp;
		   <button type="button" class="btn btn-default" id="btnCancel">取消</button>
		   <br/><br/>
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

var mediaIds = [];  
$(document).ready(function(){
  var imgArray = [];
  $("[xname='iPhoto']").each(function(index, el) {
     var itemSrc = $(this).attr('data-src');
     imgArray.push(itemSrc);
  });

  $("[xname='iPhoto']").click(function(){
    wx.previewImage({
	    current: $(this).attr("data-src"), // 当前显示图片的http链接
	    urls: imgArray // 需要预览的图片http链接列表
	});
  });

  $("#btnAdd").click(function(){
    wx.chooseImage({
	    count: 9, // 默认9
	    sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有  
	    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
	    success: function (res) {
	        $("#divInfo").removeClass("sr-only");
	        var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
	        uploadImg(localIds);
	    }
	});
  }); 
  $("#btnDel").click(function(){
    $(":checkbox").removeClass("sr-only");
    $("#divDel").removeClass("sr-only");
    $("#btnAdd").addClass("sr-only");
    $("#btnDel").addClass("sr-only");
  }); 
  $("#btnCancel").click(function(){
    $(":checkbox").addClass("sr-only");
    $("#divDel").addClass("sr-only");
    $("#btnAdd").removeClass("sr-only");
    $("#btnDel").removeClass("sr-only");
  }); 
  $("#btnSave").click(function(){
    var ids = [];
    var users = [];
	$(":checked").each(function(){
      ids.push($(this).attr("xid"));
      users.push($(this).attr("xuser"));
    });
    if(ids.length == 0){
      alert("请选择要删除的照片\n\n聚会创建者可删除任意照片\n普通圈友只可删除自己上传的照片");
      return;
    }
    if(confirm("确定删除所选照片？") == false)
      return;
    var datas = ids.join(",")+";"+users.join(",");
	$.ajax({ 
				type: "post", 
				async: false,
				processData: false,
				url: _gModuleName+"/business/com.ql.party.web.PartyAction?action=delPartyPhoto&partyId=<%=partyId%>&cId=<%=cId%>",
				data: datas, 
				contentType: "text/html; charset=UTF-8",
				success: function(data, textStatus){
				  if(textStatus == "success"){
				    if(data.flag == true){
				      window.location.reload();
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
});

function uploadImg(localIds){
  var localId = localIds.pop();
  if(localId != null){
      wx.uploadImage({
					    localId: localId, // 需要上传的图片的本地ID，由chooseImage接口获得
					    isShowProgressTips: 1, // 默认为1，显示进度提示
					    success: function (res) {
					        mediaIds.push(res.serverId); // 返回图片的服务器端ID
					        uploadImg(localIds);
					    }
				  });
  }
  else{
      dealImg();
  }
}

function dealImg(){
	var datas = mediaIds.join(",");
	$.ajax({ 
				type: "post", 
				async: false,
				processData: false,
				url: _gModuleName+"/business/com.ql.party.web.PartyAction?action=addPartyPhoto&partyId=<%=partyId%>&cId=<%=cId%>",
				data: datas, 
				contentType: "text/html; charset=UTF-8",
				success: function(data, textStatus){
				  if(textStatus == "success"){
				    if(data.flag == true){
				      window.location.reload();
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