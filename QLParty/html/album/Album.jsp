<%@ page contentType="text/html;charset=UTF-8"%>

    <style type="text/css">
	  dl { width:100%;margin:0;}
	  #dlSelected dd {float:left;text-align:center ;margin:5px;font-size: 16px;}
	</style>

<%@ include file="/album/_label.jsp"%>	
<div style="border-bottom: 2px solid #eee;margin-top:10px;"></div>

  <select class="weui_select" style="height:25px;margin-top:10px;">
    <option value="1">分组：年</option>
    <option value="2">分组：月</option>
    <option value="3">分组：日</option>
    <option value="4">不分组</option>
  </select>

<dl id="dlSelected">
</dl>

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
    
		<div class="navbar-fixed-bottom text-right" style="margin-right: 10px;">
		   <img src="<%=request.getContextPath() %>/images/add.jpg" id="btnUpload" class="img-circle" width="40" height="40"/>
		</div>
		
<script type="text/javascript">
var PhotoIds = [];

$(document).ready(function(){

  $(".labelItem").click(function(){
    var label = $(this).text();
    var found = false;
    $("#dlSelected .labelSlt span").each(function(index,s){
      if($(this).text() == label){
        found = true;
        return;
      }  
    });
    if(found == true)
      return;
    var $newLabel = $("<dd class='labelSlt'><span class='label label-success'>"+label+"</span></dd>");
    $("#dlSelected").append($newLabel);
  }); 

  $("#dlSelected").on("click",".labelSlt",function(){
    $(this).remove();
  }); 
  
  $("#btnUpload").click(function(){
    wx.chooseImage({
	    count: 9, // 默认9
	    sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有  
	    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
	    success: function (res) {
	        $('#loadingToast').show();
	        var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
	        //uploadImg(localIds);
	        //tmp
	        dealImg();
	    }
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
					        PhotoIds.push(res.serverId); // 返回图片的服务器端ID
					        uploadImg(localIds);
					    }
				  });
  }
  else{
      dealImg();
  }
}

function dealImg(){
    $('#loadingToast').hide();
    //设置标签
    gotoPage("/album/LabelSet.jsp","#LS");
    return;
    //上传
	var datas = PhotoIds.join(",");
	$.ajax({ 
				type: "post", 
				async: false,
				processData: false,
				//url: _gModuleName+"/business/com.ql.party.web.PartyAction?action=addPartyPhoto&partyId=",
				data: datas, 
				contentType: "text/html; charset=UTF-8",
				success: function(data, textStatus){
				  if(textStatus == "success"){
				    if(data.flag == true){
				      //showToast();
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
		