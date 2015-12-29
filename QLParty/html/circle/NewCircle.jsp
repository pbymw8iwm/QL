<%@page import="com.ai.appframe2.common.SessionManager"%>
<%@page import="com.ql.party.web.PartyAction"%>
<%@page import="com.ql.ivalues.ICfStaticDataValue"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>创建圈子</title>
	
    <meta http-equiv="keywords" content="聚会助手  社交圈">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <%@ include file="/CommonHead.jsp"%>
    <%@ include file="/WechatJsHead.jsp"%>
  </head>

<%
long userId = SessionManager.getUser().getID();
ICfStaticDataValue[] sdatas = PartyAction.getStaticDatas("CircleType");
%>  
  <body>
    <div class="container">
		<div class="page-header">
		  	<h3>创建圈子</h3>
			</div>
		  <form role="form" class="form-horizontal" id="frmInfo" name="frmInfo">
			   <div class="form-group">
			      <label for="CName" class="col-xs-2 control-label">圈名</label>
			      <div class="col-xs-10"><input type="text" maxlength="10" class="form-control" id="CName" name="CName" placeholder="请输入圈名"></div>
			   </div>
			   <div class="form-group">
			      <label for="CType" class="col-xs-2 control-label">分类</label>
			      <div class="col-xs-10">
			        		 <select class="form-control" id="CType" name="CType">
			        		 <%for(ICfStaticDataValue sd : sdatas){ %>
						         <option value="<%=sd.getCodevalue() %>"><%=sd.getCodename() %></option>
						     <%} %>
						      </select>
			      </div>
			   </div>
			   <div class="form-group">
			      <label class="col-xs-2 control-label">头像</label>
			      <div class="col-xs-10"><button type="button" class="btn btn-link" id="btnImg">上传</button>
			        <br/><img id="cImg" src="" class="img-rounded sr-only" width="100" height="100"/>
			      </div>
			   </div>
			   <p class="text-right"><button type="button" class="btn btn-default" id="btnSave">创建</button></p>
			</form>
	</div>
  </body>
</html>
<script language="javascript">
var mediaId = null;
$(document).ready(function(){
  $("#btnImg").click(function(){
    wx.chooseImage({
	    count: 1, // 默认9
	    sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
	    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
	    success: function (res) {
	        var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
	        jQuery(function(){
	          $.each( localIds, function(i, n){
	              $("#cImg").attr("src",n);
	              $("#cImg").removeClass("sr-only");
	              wx.uploadImage({
					    localId: n, // 需要上传的图片的本地ID，由chooseImage接口获得
					    isShowProgressTips: 1, // 默认为1，显示进度提示
					    success: function (res) {
					        mediaId = res.serverId; // 返回图片的服务器端ID
					    }
				  });
	            });
	        });
	    }
	});
  });

  $("#btnSave").click(function(){
	    if($('#CName').val() == ""){
	      $('#CName').focus();
	      return;
	    }
	    if(mediaId == null){
	      $("#btnImg").click();
	      return;
	    }
	    var datas = JSON.stringify($('#frmInfo').serializeObject());
	    $.ajax({ 
				type: "post", 
				async: false,
				processData: false,
				url: _gModuleName+"/business/com.ql.party.web.PartyAction?action=createCircle&mediaId="+mediaId,
				data: datas, 
				contentType: "text/html; charset=UTF-8",
				success: function(data, textStatus){
				  if(textStatus == "success"){
				    if(data.flag == true){
				    	window.location = _gModuleName+"/circle/CircleInfo.jsp?cId="+data.msg;
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
  ); 
  //window.location = _gModuleName+"/circle/CircleInfo.jsp?cId=11";
});
</script>