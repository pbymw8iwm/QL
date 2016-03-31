
$(document).ready(function(){
  $CId = $('#frmParty input[name="CId"]');
  $Theme = $('#frmParty input[name="Theme"]');
  $StartTime = $('#frmParty input[name="StartTime"]');
  $EndTime = $('#frmParty input[name="EndTime"]');

  if($CId.val() == undefined || $CId.val() == ""){
        showDialogConfirm("需要先创建圈子<br/>是否前往创建？",function(){
        		gotoPage("/party/circle/NewCircle.jsp","#cn");
        });
  }

  $("#btnNewParty").click(function(){
        if($CId.val() == undefined || $CId.val() == ""){
	      showDialogConfirm("需要先创建圈子<br/>是否前往创建？",function(){
        		gotoPage("/party/circle/NewCircle.jsp","#cn");
          });
	      return;
	    }
	    if($Theme.val() == ""){
	      showTooltips("请输入聚会主题");
	      $Theme.focus();
	      return;
	    }
	    if($StartTime.val() == ""){
	      showTooltips("请输入聚会开始时间");
	      $StartTime.focus();
	      return;
	    }

	    var datas = JSON.stringify($('#frmParty').serializeObject());
	    $.ajax({ 
				type: "post", 
				async: false,
				processData: false,
				url: _gModuleName+"/business/com.ql.party.web.PartyAction?action=createParty",
				data: datas, 
				contentType: "text/html; charset=UTF-8",
				success: function(data, textStatus){
				  if(textStatus == "success"){
				    if(data.flag == true){
				        showDialogInfo("聚会创建成功<br/>请点击右上角的三个小点，分享到朋友或朋友圈，邀请朋友参加聚会！");
				    	gotoPage("/party/party/PartyInfo.jsp?partyId="+data.msg,"#pi");
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