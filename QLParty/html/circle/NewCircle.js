
$(document).ready(function(){
  var circleMediaId = null;
  $("#btnImgNC").click(function(){
    wx.chooseImage({
	    count: 1, // 默认9
	    sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
	    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
	    success: function (res) {
	        var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
	        jQuery(function(){
	          $.each( localIds, function(i, n){
	              $("#cImgNC").attr("src",n);
	              $("#cImgNC").removeClass("sr-only");
	              wx.uploadImage({
					    localId: n, // 需要上传的图片的本地ID，由chooseImage接口获得
					    isShowProgressTips: 1, // 默认为1，显示进度提示
					    success: function (res) {
					    	circleMediaId = res.serverId; // 返回图片的服务器端ID
					    }
				  });
	            });
	        });
	    }
	});
  });

  $("#btnNewCircle").click(function(){
	    var $cName = $('#frmCircle input[name="CName"]');
	    if($cName.val() == ""){
	      showTooltips("请输入圈名");
	      $cName.focus();
	      return;
	    }
	    if(circleMediaId == null){
	      showTooltips("请上传头像");
	      $("#btnImgNC").click();
	      return;
	    }
	    var datas = JSON.stringify($('#frmCircle').serializeObject());
	    $.ajax({ 
				type: "post", 
				async: false,
				processData: false,
				url: _gModuleName+"/business/com.ql.party.web.PartyAction?action=createCircle&mediaId="+circleMediaId,
				data: datas, 
				contentType: "text/html; charset=UTF-8",
				success: function(data, textStatus){
				  if(textStatus == "success"){
				    if(data.flag == true){
				    	showToast();
				    	gotoPage("/circle/CircleInfo.jsp?cId="+data.msg,"#ci");
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
});