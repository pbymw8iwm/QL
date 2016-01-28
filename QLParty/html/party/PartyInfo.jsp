<%@page import="com.ql.party.ivalues.IQPartyValue"%>
<%@page import="com.ql.party.wechat.WechatOpImpl"%>
<%@page import="com.ai.appframe2.common.SessionManager"%>
<%@page import="com.ql.wechat.WechatCommons"%>
<%@page import="com.ql.party.web.PartyAction"%>
<%@page import="com.ai.appframe2.web.HttpUtil"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<%
long partyId = HttpUtil.getAsLong(request, "partyId");
IQPartyValue party = PartyAction.getParty(partyId,true);
if(party == null){
%>  
<h3>聚会可能已经被删除</h3>
<%
  return;
}
long userId = SessionManager.getUser().getID();
boolean isManager = userId == party.getCreater();
%>
  <div class="page-header-group">
    <span class="left">聚会信息</span>
    <a class="right" id="btnPIShare"><span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span></a>
  </div>
  <div class="bd">
    <div class="weui_cells <%if(isManager){ %>weui_cells_access<%}%>">
       <a class="weui_cell">
	      <div class="weui_cell_bd weui_cell_primary">
	         <p>主题</p>
	      </div>
	      <div class="weui_cell_ft"><%=party.getTheme() %></div>
       </a>
       <a class="weui_cell">
	      <div class="weui_cell_bd weui_cell_primary">
	         <p>开始</p>
	      </div>
	      <div class="weui_cell_ft"><%=party.getExtAttr("Start")%></div>
       </a>
       <a class="weui_cell">
	      <div class="weui_cell_bd weui_cell_primary">
	         <p>结束</p>
	      </div>
	      <div class="weui_cell_ft"><%=party.getExtAttr("End") %></div>
       </a>
       <a class="weui_cell">
	      <div class="weui_cell_bd weui_cell_primary">
	         <p>地点</p>
	      </div>
	      <div class="weui_cell_ft"><%=party.getGatheringplace() %></div>
       </a>
       <a class="weui_cell">
	      <div class="weui_cell_bd weui_cell_primary">
	         <p>备注</p>
	      </div>
	      <div class="weui_cell_ft"><%=party.getRemarks().replaceAll("\n", "<br/>") %></div>
       </a>
    </div>
    
    <div class="weui_cells">
       <a class="weui_cell weui_cells_access">
	      <div class="weui_cell_bd weui_cell_primary">
	         <p>我</p>
	      </div>
	      <div class="weui_cell_ft"><%=party.getExtAttr("SelfStateName") %>(<%=party.getExtAttr("SelfCount") %>人)</div>
       </a>
    </div>
    
    <div class="weui_cells">
       <a class="weui_cell" href='javascript:gotoPage("/party/Photo.jsp?partyId=<%=party.getPartyid()%>&cId=<%=party.getCid()%>&manager=<%=party.getCreater()%>","#pp");'>
	      <div class="weui_cell_bd weui_cell_primary">
	         <p>时光留驻</p>
	      </div>
	      <div class="weui_cell_ft"><span class="glyphicon glyphicon-picture" aria-hidden="true"></span></div>
       </a>
    </div>
  </div>

<script language="javascript">

  var sharePartyMsg = {
	    title: '聚会啦，快来参加吧', // 分享标题
	    desc: '<%=party.getTheme()%>', // 分享描述
	    link: '<%=WechatCommons.getUrlView(WechatOpImpl.Type_JoinParty+partyId)%>', // 分享链接
	    imgUrl: '<%=party.getImagedata()%>', // 分享图标
	    type: 'link', // 分享类型,music、video或link，不填默认为link
	    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
	    success: function () { 
	        // 用户确认分享后执行的回调函数
	    },
	    cancel: function () { 
	        // 用户取消分享后执行的回调函数
	    }
	};

var partyInfo = {};
var selfInfo = {};

var lastPartyInfo = {
  Theme : "<%=party.getTheme()%>",
  StartTime : "<%=party.getStarttime()%>",
  EndTime: "<%=party.getEndtime()%>",
  GatheringPlace : "<%=party.getGatheringplace()%>",
  Remarks : ""
};

var lastSelfInfo = {
  State : "<%=party.getExtAttr("SelfState")%>",
  PCount : "<%=party.getExtAttr("SelfCount")%>"
};
  
$(document).ready(function(){
  wx.onMenuShareAppMessage(sharePartyMsg);
  wx.onMenuShareTimeline(sharePartyMsg);
  
  //给tr增加一个click事件以在ios上触发点击，否则编辑的modal出不来
  $("tr").click(function(){});
  
  $("#btnPIShare").click(function(){
    wx.onMenuShareAppMessage(sharePartyMsg);
    wx.onMenuShareTimeline(sharePartyMsg);
    showDialogInfo("请点击右上角的三个小点，分享到朋友或朋友圈，邀请朋友参加聚会！");
  }); 
    
  $("#btnTheme").click(function(){
    var value = $("#Theme").val();
    if(value != lastPartyInfo.Theme){
      lastPartyInfo.Theme = value;
      partyInfo.Theme = value;
      $("#tdTheme").text(value);
      save();
    }
    $('#mTheme').modal('hide');
  });
  
  $("#btnTime").click(function(){
    var value = $("#StartTime").val();
    if(value == ""){
      $("#StartTime").focus();
	  return;
    }
    if(value != lastPartyInfo.StartTime){
      lastPartyInfo.StartTime = value;
      partyInfo.StartTime = value+":00";
      $("#tdStartTime").text(value);
    }
    value = $("#EndTime").val();
    if(value != lastPartyInfo.EndTime){
      lastPartyInfo.EndTime = value;
      partyInfo.EndTime = value;
      if(value.length > 0)
        partyInfo.EndTime = partyInfo.EndTime + ":00";
      $("#tdEndTime").text(value);
    }
    save();
    $('#mTime').modal('hide');
  });
  
  $("#btnPlace").click(function(){
    var value = $("#GatheringPlace").val();
    if(value != lastPartyInfo.GatheringPlace){
      lastPartyInfo.GatheringPlace = value;
      partyInfo.GatheringPlace = value;
      $("#tdPlace").text(value);
      save();
    }
    $('#mPlace').modal('hide');
  });
  
  $("#btnRemarks").click(function(){
    var value = $("#Remarks").val();
    if(value != lastPartyInfo.Remarks){
      lastPartyInfo.Remarks = value;
      partyInfo.Remarks = value;
      $("#tdRemarks").text(value);
      save();
    }
    $('#mRemarks').modal('hide');
  });
  
  $("#btnSelf").click(function(){
    var changed = false;
    var value = $("#PState").val();
    if(value != lastSelfInfo.State){
      lastSelfInfo.State = value;
      selfInfo.State = value;
      $("#tdState").text($("#PState option:selected").text());
      changed = true;
    }
    selfInfo.State = value;
    
    value = $("#PCount").val();//parseInt
    if(value == "" || value < 0)
      value = 0;
    if(lastSelfInfo.State == 0)
      value = 0;
    else if(value == 0)
      value = 1;
    $("#PCount").val(value);
    if(value != lastSelfInfo.PCount){
      lastSelfInfo.PCount = value;
      selfInfo.PCount = value;
      $("#tdCount").text(value);
      changed = true;
    }
    selfInfo.PCount = value;
    if(changed)
      save();
    $('#mSelf').modal('hide');
  });
});

//直接关闭时这里不起作用
//$(window).unload(save);

function save() { 
  var isChanged = false;
  for (var prop in partyInfo){
    isChanged = true;
    partyInfo.PartyId = <%=partyId%>;
    break;
  }
  if(isChanged){
    $.ajax({ 
				type: "post", 
				async: true,
				processData: false,
				url: _gModuleName+"/business/com.ql.party.web.PartyAction?action=saveParty",
				data: JSON.stringify(partyInfo), 
				contentType: "text/html; charset=UTF-8",
				success: function(data, textStatus){
				  partyInfo = {};
			    },
			    error:function(httpRequest,errType,ex ){
			    }
	}); 
  }
  isChanged = false;
  for (var prop in selfInfo){
    isChanged = true;
    break;
  }
  if(isChanged){
    $.ajax({ 
				type: "post", 
				async: true,
				processData: false,
				url: _gModuleName+"/business/com.ql.party.web.PartyAction?action=setPartyMember&partyId=<%=partyId%>&state="+selfInfo.State+"&count="+selfInfo.PCount,
				contentType: "text/html; charset=UTF-8",
				success: function(data, textStatus){
			    },
			    error:function(httpRequest,errType,ex ){
			    }
	}); 
  }
}
</script>