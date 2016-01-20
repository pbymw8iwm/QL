<%@page import="com.ql.party.ivalues.IQPartyValue"%>
<%@page import="com.ql.party.wechat.WechatOpImpl"%>
<%@page import="com.ai.appframe2.common.SessionManager"%>
<%@page import="com.ql.wechat.WechatCommons"%>
<%@page import="com.ql.party.web.PartyAction"%>
<%@page import="com.ai.appframe2.web.HttpUtil"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>聚会信息</title>
	
    <meta http-equiv="keywords" content="聚会助手  社交圈">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <%@ include file="/CommonHead.jsp"%>
    <%@ include file="/WechatJsHead.jsp"%>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/moment-with-locales.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap-datetimepicker.min.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/js/bootstrap-datetimepicker.min.css" />
  </head>

<%
long partyId = HttpUtil.getAsLong(request, "partyId");
IQPartyValue party = PartyAction.getParty(partyId,true);
if(party == null){
%>  
<body><h3>聚会可能已经被删除</h3></body>
<%
  return;
}
long userId = SessionManager.getUser().getID();
String userName = SessionManager.getUser().getName();
String td;
boolean isPartyEditable = userId == party.getCreater();
if(isPartyEditable)
  td = "<td width=\"10\">&gt;</td>";
else
  td = "";
%>
  <body>
    <div>
	    <div class="table-responsive">
		  <table class="table">
		    <tr <%if(isPartyEditable){ %>data-toggle="modal" data-target="#mTheme"<%} %>>
		      <td width="60">主题</td>
		      <td align="right" id="tdTheme"><%=party.getTheme() %></td>
		      <%=td %>
		    </tr>
		    <tr <%if(isPartyEditable){ %>data-toggle="modal" data-target="#mTime"<%} %>>
		      <td>开始</td>
		      <td align="right" id="tdStartTime"><%=party.getExtAttr("Start") %></td>
		      <%=td %>
		    </tr>
		    <tr <%if(isPartyEditable){ %>data-toggle="modal" data-target="#mTime"<%} %>>
		      <td>结束</td>
		      <td align="right" id="tdEndTime"><%=party.getExtAttr("End") %></td>
		      <%=td %>
		    </tr>
		    <tr <%if(isPartyEditable){ %>data-toggle="modal" data-target="#mPlace"<%} %>>
		      <td>地点</td>
		      <td align="right" id="tdPlace"><%=party.getGatheringplace() %></td>
		      <%=td %>
		    </tr>
		    <tr <%if(isPartyEditable){ %>data-toggle="modal" data-target="#mRemarks"<%} %>>
		      <td>备注</td>
		      <td align="right" id="tdRemarks"><%=party.getRemarks() %></td>
		      <%=td %>
		    </tr>
		  </table>
		  <br/>
		  <table class="table">
		    <tr data-toggle="modal" data-target="#mSelf">
		      <td width="60">我</td>
		      <td align="right" id="tdState"><%=party.getExtAttr("SelfStateName") %></td>
		      <td width="10">&gt;</td>
		    </tr>
		    <tr data-toggle="modal" data-target="#mSelf">
		      <td>人数</td>
		      <td align="right" id="tdCount"><%=party.getExtAttr("SelfCount") %></td>
		      <td>&gt;</td>
		    </tr>
		  </table>
		</div>
		<div class="text-center">
			<a href="<%=request.getContextPath()%>/party/Photo.jsp?partyId=<%=party.getPartyid()%>&cId=<%=party.getCid()%>&manager=<%=party.getCreater()%>">
	   		  <span class="glyphicon glyphicon-picture" aria-hidden="true" style="padding-top: 2px;"></span>时光留驻
	   		</a>
   		</div>
		<div class="text-right"><button type="button" class="btn btn-link" id="btnShare"><span class="glyphicon glyphicon-share-alt" aria-hidden="true">邀请</span></button></div>
		<%@ include file="/party/_MemberInfo.jsp"%>
   		      
		<%if(isPartyEditable){ %>
		<div class="modal fade" id="mTheme" tabindex="-1" role="dialog" aria-labelledby="mThemeLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		        <h4 class="modal-title" id="mThemeLabel">主题</h4>
		      </div>
		      <div class="modal-body">
				<input type="text" maxlength="20" class="form-control" id="Theme" name="Theme" value="<%=party.getTheme()%>">
			  </div>
			    <div class="modal-footer">
			        <button type="button" class="btn btn-sm btn-success" id="btnTheme">确定</button>
			        <button type="button" class="btn btn-sm btn-default" data-dismiss="modal">取消</button>
			    </div>
		    </div>
		  </div>
		</div>
		
		<div class="modal fade" id="mTime" tabindex="-1" role="dialog" aria-labelledby="mTimeLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		        <h4 class="modal-title" id="mTimeLabel">时间</h4>
		      </div>
		      <div class="modal-body">
		        <form role="form" class="form-horizontal">
				<div class="form-group">
			      <label for="StartTime" class="col-xs-3 control-label">开始</label>
			      <div class="col-xs-9"><input type="text" class="form-control" id="StartTime" name="StartTime" readonly></div>
			    </div>
			    <div class="form-group">
			      <label for="EndTime" class="col-xs-3 control-label">结束</label>
			      <div class="col-xs-9"><input type="text" class="form-control" id="EndTime" name="EndTime" readonly></div>
			    </div>
			    </form>
			  </div>
			    <div class="modal-footer">
			        <button type="button" class="btn btn-sm btn-success" id="btnTime">确定</button>
			        <button type="button" class="btn btn-sm btn-default" data-dismiss="modal">取消</button>
			    </div>
		    </div>
		  </div>
		</div>
		
		<div class="modal fade" id="mPlace" tabindex="-1" role="dialog" aria-labelledby="mPlaceLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		        <h4 class="modal-title" id="mPlaceLabel">地点</h4>
		      </div>
		      <div class="modal-body">
				<textarea row="3" class="form-control" id="GatheringPlace" name="GatheringPlace"><%=party.getGatheringplace() %></textarea>
			  </div>
			    <div class="modal-footer">
			        <button type="button" class="btn btn-sm btn-success" id="btnPlace">确定</button>
			        <button type="button" class="btn btn-sm btn-default" data-dismiss="modal">取消</button>
			    </div>
		    </div>
		  </div>
		</div>
		
		<div class="modal fade" id="mRemarks" tabindex="-1" role="dialog" aria-labelledby="mRemarksLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		        <h4 class="modal-title" id="mRemarksLabel">备注</h4>
		      </div>
		      <div class="modal-body">
				<textarea row="3" class="form-control" id="Remarks" name="Remarks"><%=party.getRemarks() %></textarea>
			  </div>
			    <div class="modal-footer">
			        <button type="button" class="btn btn-sm btn-success" id="btnRemarks">确定</button>
			        <button type="button" class="btn btn-sm btn-default" data-dismiss="modal">取消</button>
			    </div>
		    </div>
		  </div>
		</div>
		<%} %>
		
		<div class="modal fade" id="mSelf" tabindex="-1" role="dialog" aria-labelledby="mSelfLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		        <h4 class="modal-title" id="mSelfLabel">个人情况</h4>
		      </div>
		      <div class="modal-body">
		        <form role="form" class="form-horizontal">
				<div class="form-group">
			      <label for="PState" class="col-xs-3 control-label">状态</label>
			      <div class="col-xs-9">
			        <select class="form-control" id="PState" name="PState" value="<%=party.getExtAttr("SelfState") %>">
			          <option value="1">参加</option>
			          <option value="2">待定</option>
			          <option value="0">不参加</option>
					</select>
			      </div>
			    </div>
			    <div class="form-group">
			      <label for="PCount" class="col-xs-3 control-label">人数</label>
			      <div class="col-xs-9">
			        <input type="number" class="form-control" id="PCount" name="PCount" value="<%=party.getExtAttr("SelfCount") %>"
			          onkeyup="this.value=this.value.replace(/\D/g,'1')" onafterpaste="this.value=this.value.replace(/\D/g,'1')">
			      </div>
			    </div>
			    </form>
			  </div>
			    <div class="modal-footer">
			        <button type="button" class="btn btn-sm btn-success" id="btnSelf">确定</button>
			        <button type="button" class="btn btn-sm btn-default" data-dismiss="modal">取消</button>
			    </div>
		    </div>
		  </div>
		</div>
	</div>
  </body>
</html>
<script language="javascript">

var dtp = {
	locale: 'zh-cn',
	format: "YYYY-MM-DD HH:mm",
	ignoreReadonly:true,
	showTodayButton:true,
	showClear:true,
	showClose:true,
	useCurrent: false,
	toolbarPlacement:"top"
};

  var shareMsg = {
	    title: '<%=userName%>邀您参加聚会', // 分享标题
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
	
wx.ready(function(){
  wx.hideOptionMenu();
  wx.showMenuItems({
    menuList: ['menuItem:share:appMessage','menuItem:share:timeline'] // 要显示的菜单项，所有menu项见附录3
  });

  wx.onMenuShareAppMessage(shareMsg);
  wx.onMenuShareTimeline(shareMsg);
});
wx.error(function(res){
//    alert(res.errMsg);
});

var partyInfo = {};
var selfInfo = {};

var lastPartyInfo = {
  Theme : "<%=party.getTheme()%>",
  StartTime : "<%=party.getStarttime()%>",
  EndTime: "<%=party.getEndtime()%>",
  GatheringPlace : "<%=party.getGatheringplace()%>",
  Remarks : "<%=party.getRemarks()%>"
};

var lastSelfInfo = {
  State : "<%=party.getExtAttr("SelfState")%>",
  PCount : "<%=party.getExtAttr("SelfCount")%>"
};
  
$(document).ready(function(){
  $("#StartTime").datetimepicker(dtp);
  $("#EndTime").datetimepicker(dtp);
  $("#StartTime").on("dp.change", function (e) {
    $("#EndTime").data("DateTimePicker").minDate(e.date);
  });
  $("#EndTime").on("dp.change", function (e) {
    $("#StartTime").data("DateTimePicker").maxDate(e.date);
  });
  $("#StartTime").data("DateTimePicker").defaultDate("<%=party.getExtAttr("Start") %>");
  $("#EndTime").data("DateTimePicker").defaultDate("<%=party.getExtAttr("End") %>");
  
  //给tr增加一个click事件以在ios上触发点击，否则编辑的modal出不来
  $("tr").click(function(){});
  
  $("#btnShare").click(function(){
    alert("请点击右上角的三个小点，分享到朋友或朋友圈，邀请朋友参加聚会！");
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