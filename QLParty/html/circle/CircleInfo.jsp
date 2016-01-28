<%@page import="com.ql.party.wechat.WechatOpImpl"%>
<%@page import="com.ai.appframe2.common.SessionManager"%>
<%@page import="com.ql.wechat.WechatCommons"%>
<%@page import="com.ql.party.web.PartyAction"%>
<%@page import="com.ql.party.ivalues.IQSocialCircleValue"%>
<%@page import="com.ai.appframe2.web.HttpUtil"%>
<%@ page contentType="text/html;charset=UTF-8"%>


<%
long cId = HttpUtil.getAsLong(request, "cId");
IQSocialCircleValue sc = PartyAction.getSocialCircleByUser(cId,true);
if(sc == null){
%>
<h3>您未加入此圈子，或者圈子已经被删除</h3>
<%
  return;
}
long userId = SessionManager.getUser().getID();
String userName = SessionManager.getUser().getName();
boolean isManager = userId == sc.getCreater();
%>  

  <div class="page-header-group">
    <span class="left">圈子信息</span>
    <a class="right" href="javascript:ciShare();"><span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span></a>
  </div>
    <div class="bd">
        <div class="weui_cells <%if(isManager){ %>weui_cells_access<%}%>">
          <a id="ciImg" class="weui_cell" href="javascript:;">
              <div class="weui_cell_hd"><img src="<%=sc.getImagedata() %>" style="width:80px;height:80px;margin-right:10px;display:block"></div>
	          <div class="weui_cell_bd weui_cell_primary">
	            <p><%=sc.getCname() %></p>
	            <p><span class="help-block" ><%=sc.getExtAttr("TypeName") %></span></p>
	          </div>
	          <div class="weui_cell_ft"></div>
          </a>
          <a class="weui_cell" href="javascript:gotoPage(null,'#ciqr');">
	          <div class="weui_cell_bd weui_cell_primary">
	            <p>圈名片</p>
	          </div>
	          <div class="weui_cell_ft"><img src="<%=request.getContextPath() %>/images/barcode-2d.png"/></div>
          </a>
          <a class="weui_cell" href='javascript:gotoPage("/circle/MemberInfo.jsp?cId=<%=sc.getCid()%>&cName=<%=sc.getCname() %>","#cm");'>
	          <div class="weui_cell_bd weui_cell_primary">
	            <p>圈友</p>
	          </div>
	          <div class="weui_cell_ft"><%=sc.getExtAttr("MemberCount") %>人</div>
          </a>
          <a class="weui_cell" href='javascript:gotoPage("/party/PartyList.jsp?cId=<%=sc.getCid()%>&cName=<%=sc.getCname() %>","#pl");'>
	          <div class="weui_cell_bd weui_cell_primary">
	            <p>聚会</p>
	          </div>
	          <div class="weui_cell_ft"><%=sc.getExtAttr("PartyCount") %>个</div>
          </a>
        </div>
        <div class="weui_cells_title">我的圈信息</div>
        <div class="weui_cells weui_cells_access">
          <a class="weui_cell">
	          <div class="weui_cell_bd weui_cell_primary">
	            <p>昵称</p>
	          </div>
	          <div class="weui_cell_ft"><%=sc.getUsername() %></div>
          </a>
          <a class="weui_cell">
	          <div class="weui_cell_bd weui_cell_primary">
	            <p>电话</p>
	          </div>
	          <div class="weui_cell_ft"><%=sc.getPhone()==null?"":sc.getPhone() %></div>
          </a>
          <a class="weui_cell">
	          <div class="weui_cell_bd weui_cell_primary">
	            <p>工作</p>
	          </div>
	          <div class="weui_cell_ft"><%=sc.getJob()==null?"":sc.getJob() %></div>
          </a>
          <a class="weui_cell">
	          <div class="weui_cell_bd weui_cell_primary">
	            <p>地址</p>
	          </div>
	          <div class="weui_cell_ft"><%=sc.getCity()==null?"":sc.getCity() %></div>
          </a>
        </div>
        <%if(isManager){ %>
        <div class="weui_cells weui_cells_access">
          <div class="weui_cell">
	          <div class="weui_cell_bd weui_cell_primary">
	            <p>转让圈主</p>
	          </div>
	          <div class="weui_cell_ft"></div>
          </div>
        </div>
        <%} %>
        <div class="weui_cells"></div>
        <div class="bd spacing">
          <a href="javascript:;" class="weui_btn weui_btn_warn" id="btnCIQuit">退出圈子</a>
        </div>
    </div>

<script type="text/html" id="ciqr">
<div class="page" style="background-color:#4A4A4A;width:100%;height:100%;padding-top:50px">
  <div style="border-radius: 6px;background-color:#ffffff;width:80%;margin-right:auto;margin-left:auto;padding-top:10px;padding-bottom:10px">
    <table style="margin-top:10px;margin-left:10px">
      <tr>
        <td rowspan="2"><img src="<%=sc.getImagedata() %>" style="width:60px;height:60px;margin-right:10px;"></td>
        <td style="padding-top:10px"><strong><%=sc.getCname() %></strong></td>
      </tr>
      <tr>
        <td><span class="help-block" ><%=sc.getExtAttr("TypeName") %></span></td>
      </tr>
    </table>
    <p class="text-center">
		<img src="<%=WechatCommons.Url_ShowQr + sc.getQrticket()%>" style="width:220px;height:220px;padding-top:10px"/>
		<br/>
		扫码加入此圈，参与聚会，分享照片
		<br/>
		<span class="help-block" style="font-size:10px;">当前二维码将于<%=sc.getExtAttr("QRExpiryDate") %>失效</span>
	</p>
  </div>
  <br/>
  <div class="bd" style="width:80%;margin-right:auto;margin-left:auto;">
    <a href="javascript:ciShare();" class="weui_btn weui_btn_primary">邀请</a>
  </div>
</div>
</script>
    

<script language="javascript">

  var shareCIMsg = {
	    title: '邀请您加入<%=sc.getCname()%>', // 分享标题
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
	
  
var myInfo = null;  
$(document).ready(function(){
  wx.onMenuShareAppMessage(shareCIMsg);
  wx.onMenuShareTimeline(shareCIMsg);

  $("#btnParty").click(function(){
    window.location = "<%=request.getContextPath()%>/party/NewParty.jsp?cId=<%=cId%>&cName=<%=sc.getCname() %>";
  }); 
  
  $("#aMember").click(function(){
    if($("#cMember").html() == "")
    	$("#cMember").load("<%=request.getContextPath()%>/circle/MemberInfo.jsp?cId=<%=cId%>");
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
    
    //MemberInfo.jsp中是否查询过用户信息，mInfo是其中的变量
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
  
  $("#btnCIQuit").click(function(){
    showDialogConfirm("确定退出当前圈子？",function(){
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
    });
  }); 


<%if(isManager){ %>  
  $("#ciImg").click(function(){
    wx.chooseImage({
	    count: 1, // 默认9
	    sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
	    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
	    success: function (res) {
	        var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
	        jQuery(function(){
	          $.each( localIds, function(i, n){
	              $("#ciImg").attr("src",n);
	              wx.uploadImage({
					    localId: n, // 需要上传的图片的本地ID，由chooseImage接口获得
					    isShowProgressTips: 1, // 默认为1，显示进度提示
					    success: function (res) {
					        var serverId = res.serverId; // 返回图片的服务器端ID
					        ciDealImg(serverId);
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

function ciShare(){
    wx.onMenuShareAppMessage(shareCIMsg);
    wx.onMenuShareTimeline(shareCIMsg);
    showDialogInfo("请点击右上角的三个小点，分享到朋友或朋友圈，邀请朋友加入圈子！");
}

function ciDealImg(serverId){
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
				      shareCIMsg.imgUrl = shareCIMsg.imgUrl + r;
				      //shareCIMsg.link = shareCIMsg.link + r;
					  wx.onMenuShareAppMessage(shareCIMsg);
					  wx.onMenuShareTimeline(shareCIMsg);
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