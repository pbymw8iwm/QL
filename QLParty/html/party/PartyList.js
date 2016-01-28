
$(document).ready(function(){
  $("[xname='piShare']").click(function(){
    var pId = $(this).data("id");
    var theme = $(this).data("theme");
    var img = $(this).data("img");
    var cLink = "";
    $.ajax({ 
                cache: false,
				async: false,
				type: "get", 
				url: _gModuleName+"/business/com.ql.party.web.PartyAction?action=getPartyShareLink&pId="+pId,
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
	    title: '聚会啦，快来参加吧', // 分享标题
	    desc: theme, // 分享描述
	    link: cLink, // 分享链接
	    imgUrl: img, // 分享图标
	};
    wx.onMenuShareAppMessage(shareMsg);
    wx.onMenuShareTimeline(shareMsg);
    
    showDialogInfo("请点击右上角的三个小点，分享到朋友或朋友圈，邀请朋友参加聚会！");
  }); 
});