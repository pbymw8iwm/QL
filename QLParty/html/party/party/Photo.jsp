<%@page import="com.ql.party.ivalues.IPartyPhotoValue"%>
<%@page import="com.ai.appframe2.common.SessionManager"%>
<%@page import="com.ql.party.web.PartyAction"%>
<%@page import="com.ai.appframe2.web.HttpUtil"%>
<%@ page contentType="text/html;charset=UTF-8"%>


<%
long partyId = HttpUtil.getAsLong(request, "partyId");
long cId = HttpUtil.getAsLong(request, "cId");
long manager = HttpUtil.getAsLong(request, "manager");
String partyName = HttpUtil.getAsString(request, "partyName");
long userId = SessionManager.getUser().getID();
boolean isM = userId == manager;
IPartyPhotoValue[] phs = PartyAction.getPhotoes(partyId);
int total = phs.length;
int rows = total/4;
if(total%4 > 0)
  rows += 1;
int height = rows * 100;
%>  
  
<style type="text/css">
.photo {}
.photo dl { width:100%;height:<%=height%>px;border:0px;margin:0;font-size:14px;}
.photo dl dd {width:25%; height:100px; border:0px; float:left;text-align:center ;padding-top: 7px;}
</style>

	    <div class="page-header-group">
		    <span class="left"><%=partyName %></span>
		    <div class="right"><button type="button" class="btn btn-link" id="btnPPAdd"><span class="glyphicon glyphicon-plus" aria-hidden="true">添加</span></button>
			   <button type="button" class="btn btn-link" id="btnPPDel"><span class="glyphicon glyphicon-minus" aria-hidden="true">删除</span></button>
	        </div>
	    </div>
    <div class="bd">
	    <div class="weui_cells">
	      <div class="weui_cell">
		   <div class="weui_cell_bd weui_cell_primary photo">
		     <dl>
			<%for(IPartyPhotoValue ph:phs){ %>
			  <dd>
			    <img src="<%=ph.getPhotodata() %>-200" data-src="<%=ph.getPhotodata() %>" class="img-rounded" width="70" height="70" xname="iPhoto"/>
			    <br/>
			    <%if(isM || userId == ph.getUserid()){ %>
			    <input type="checkbox" data-id="<%=ph.getPhotoid()%>" data-user="<%=ph.getUserid()%>" class="sr-only">
			    <%} %>
			  </dd>
			<%} %>
			</dl>
		   </div>
	      </div>
	     </div>
    </div>
		
		
		<!-- loading toast -->
    <div id="loadingToast" class="weui_loading_toast" style="display:none;">
        <div class="weui_mask_transparent"></div>
        <div class="weui_toast">
            <div class="weui_loading">
                <div class="weui_loading_leaf weui_loading_leaf_0"></div>
                <div class="weui_loading_leaf weui_loading_leaf_1"></div>
                <div class="weui_loading_leaf weui_loading_leaf_2"></div>
                <div class="weui_loading_leaf weui_loading_leaf_3"></div>
                <div class="weui_loading_leaf weui_loading_leaf_4"></div>
                <div class="weui_loading_leaf weui_loading_leaf_5"></div>
                <div class="weui_loading_leaf weui_loading_leaf_6"></div>
                <div class="weui_loading_leaf weui_loading_leaf_7"></div>
                <div class="weui_loading_leaf weui_loading_leaf_8"></div>
                <div class="weui_loading_leaf weui_loading_leaf_9"></div>
                <div class="weui_loading_leaf weui_loading_leaf_10"></div>
                <div class="weui_loading_leaf weui_loading_leaf_11"></div>
            </div>
            <p class="weui_toast_content">上传中</p>
        </div>
    </div>
		<div class="navbar-fixed-bottom text-center sr-only" style="margin-bottom: 50px;" id="divPPDel">
		   <a class="weui_btn weui_btn_mini weui_btn_warn" id="btnPPSave">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;
		   <a class="weui_btn weui_btn_mini weui_btn_default" id="btnPPCancel">取消</a>
		</div>


<script language="javascript">

var ppMediaIds = [];  
$(document).ready(function(){
  var imgArray = [];
  $("[xname='iPhoto']").each(function(index, el) {
     var itemSrc = $(this).data("src");
     imgArray.push(itemSrc);
  });

  $("[xname='iPhoto']").click(function(){
    wx.previewImage({
	    current: $(this).data("src"), // 当前显示图片的http链接
	    urls: imgArray // 需要预览的图片http链接列表
	});
  });

  $("#btnPPAdd").click(function(){
    wx.chooseImage({
	    count: 9, // 默认9
	    sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有  
	    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
	    success: function (res) {
	        $('#loadingToast').show();
	        var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
	        uploadImg(localIds);
	    }
	});
  }); 
  $("#btnPPDel").click(function(){
    $(".photo :checkbox").removeClass("sr-only");
    $("#divPPDel").removeClass("sr-only");
    $("#btnPPAdd").addClass("sr-only");
    $("#btnPPDel").addClass("sr-only");
  }); 
  $("#btnPPCancel").click(function(){
    $(".photo :checkbox").addClass("sr-only");
    $("#divPPDel").addClass("sr-only");
    $("#btnPPAdd").removeClass("sr-only");
    $("#btnPPDel").removeClass("sr-only");
  }); 
  $("#btnPPSave").click(function(){
    var ids = [];
    var users = [];
	$(".photo :checked").each(function(){
      ids.push($(this).data("id"));
      users.push($(this).data("user"));
    });
    if(ids.length == 0){
      showDialogInfo("请选择要删除的照片<br/><br/>聚会创建者可删除任意照片<br/>普通圈友只可删除自己上传的照片");
      return;
    }
    showDialogConfirm("确定删除所选照片？",function(){
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
				      showToast();
				      gotoPage("/party/party/Photo.jsp?partyId=<%=partyId%>&cId=<%=cId%>&manager=<%=manager%>&partyName=<%=partyName %>","#pp");
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
});

function uploadImg(localIds){
  var localId = localIds.pop();
  if(localId != null){
      wx.uploadImage({
					    localId: localId, // 需要上传的图片的本地ID，由chooseImage接口获得
					    isShowProgressTips: 0, // 默认为1，显示进度提示
					    success: function (res) {
					        ppMediaIds.push(res.serverId); // 返回图片的服务器端ID
					        uploadImg(localIds);
					    }
				  });
  }
  else{
      dealImg();
  }
}

function dealImg(){
	var datas = ppMediaIds.join(",");
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
				      showToast();
				      gotoPage("/party/party/Photo.jsp?partyId=<%=partyId%>&cId=<%=cId%>&manager=<%=manager%>&partyName=<%=partyName %>","#pp");
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