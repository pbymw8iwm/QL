<%@page import="com.ql.party.web.PartyAction"%>
<%@page import="com.ql.party.ivalues.ISocialCircleValue"%>
<%@page import="com.ai.appframe2.web.HttpUtil"%>
<%@page import="com.ai.appframe2.common.SessionManager"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>创建聚会</title>
	
    <meta http-equiv="keywords" content="聚会助手  社交圈">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <%@ include file="/CommonHead.jsp"%>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/moment-with-locales.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap-datetimepicker.min.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/js/bootstrap-datetimepicker.min.css" />
  </head>

<%
long userId = SessionManager.getUser().getID();
long cId = HttpUtil.getAsLong(request, "cId");
String cName = null;
ISocialCircleValue[] scs = null;
if(cId > 0)
  cName = HttpUtil.getAsString(request, "cName");
else
  scs = PartyAction.getSocialCircles(false);
%>  
  <body>
    <div class="container">
		<div class="page-header"><h3>创建聚会</h3></div>
		<form role="form" class="form-horizontal" id="frmInfo" name="frmInfo">
			   <div class="form-group">
			      <label for="CId" class="col-xs-2 control-label">圈子</label>
			      <div class="col-xs-10">
			        <select class="form-control" id="CId" name="CId">
			          <%if(cId > 0){ %>
			            <option value="<%=cId %>"><%=cName %></option>
			          <%} else{
			              for(ISocialCircleValue sc : scs){ %>
						    <option value="<%=sc.getCid() %>"><%=sc.getCname() %></option>
					  <%  } 
					    }%>
					</select>
			      </div>
			   </div>
			   <div class="form-group">
			      <label for="Theme" class="col-xs-2 control-label">主题</label>
			      <div class="col-xs-10"><input type="text" maxlength="20" class="form-control" id="Theme" name="Theme" placeholder="请输入聚会主题"></div>
			   </div>
			   <div class="form-group">
			      <label for="StartTime" class="col-xs-2 control-label">开始</label>
			      <div class="col-xs-10"><input type="text" class="form-control" id="StartTime" name="StartTime" placeholder="请选择聚会开始时间" readonly></div>
			   </div>
			   <div class="form-group">
			      <label for="EndTime" class="col-xs-2 control-label">结束</label>
			      <div class="col-xs-10"><input type="text" class="form-control" id="EndTime" name="EndTime" placeholder="请选择聚会结束时间(可空)" readonly></div>
			   </div>
			   <div class="form-group">
			      <label for="GatheringPlace" class="col-xs-2 control-label">地点</label>
			      <div class="col-xs-10"><textarea row="3" class="form-control" id="GatheringPlace" name="GatheringPlace" placeholder="请输入聚会地点"></textarea></div>
			   </div>
			   <div class="form-group">
			     <label for="Remarks" class="col-xs-2 control-label">备注</label>
			     <div class="col-xs-10"><textarea row="10" class="form-control" id="Remarks" name="Remarks" placeholder="请输入备注说明"></textarea></div>
			   </div>
			   <p class="text-right"><button type="button" class="btn btn-default" id="btnSave">创建</button></p>
			</form>
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

$(document).ready(function(){
        if($('#CId').val() == ""){
	      if(confirm("需要先创建圈子\n是否前往创建？")){
	        window.location = _gModuleName+"/circle/NewCircle.jsp";
	      }
	    }
	    
//window.location = _gModuleName+"/party/PartyInfo.jsp?partyId=1";

  $("#StartTime").datetimepicker(dtp);
  $("#EndTime").datetimepicker(dtp);
  $("#StartTime").on("dp.change", function (e) {
    $("#EndTime").data("DateTimePicker").minDate(e.date);
  });
  $("#EndTime").on("dp.change", function (e) {
    $("#StartTime").data("DateTimePicker").maxDate(e.date);
  });
  
  $("#btnSave").click(function(){
        if($('#CId').val() == ""){
	      if(confirm("需要先创建圈子\n是否前往创建？")){
	        window.location = _gModuleName+"/circle/NewCircle.jsp";
	      }
	      return;
	    }
	    if($("#Theme").val() == ""){
	      $("#Theme").focus();
	      return;
	    }
	    if($("#StartTime").val() == ""){
	      $("#StartTime").focus();
	      return;
	    }
	    if($("#StartTime").val().length == 16)
	      $("#StartTime").val($("#StartTime").val()+":00");
	    if($("#EndTime").val() != ""
	        && $("#EndTime").val().length == 16)
	      $("#EndTime").val($("#EndTime").val()+":00");
	    var datas = JSON.stringify($('#frmInfo').serializeObject());
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
				        alert("聚会创建成功\n请点击右上角的三个小点，分享到朋友或朋友圈，邀请朋友参加聚会！");
				    	window.location = _gModuleName+"/party/PartyInfo.jsp?partyId="+data.msg;
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
</script>