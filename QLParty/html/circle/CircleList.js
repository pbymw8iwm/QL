
$(document).ready(function(){
  $("[xname='clShare']").click(function(){
    var cId = $(this).data("id");
    var cName = $("#clA"+cId).text();
    var img = $("#clImg"+cId).attr("src");
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
	    title: '邀请您加入'+cName, // 分享标题
	    desc: '加入圈子，参与聚会，分享照片', // 分享描述
	    link: cLink, // 分享链接
	    imgUrl: img, // 分享图标
	};
    wx.onMenuShareAppMessage(shareMsg);
    wx.onMenuShareTimeline(shareMsg);
    
    showDialogInfo("请点击右上角的三个小点，分享到朋友或朋友圈，邀请朋友加入圈子！");
  }); 
});