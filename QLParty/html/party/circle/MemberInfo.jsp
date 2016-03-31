
<%@page import="com.ai.appframe2.common.SessionManager"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.ql.party.web.PartyAction"%>
<%@page import="com.ql.party.ivalues.ICircleMemberValue"%>
<%@page import="com.ai.appframe2.web.HttpUtil"%>

<%@ page contentType="text/html;charset=UTF-8"%>
<%
long cId = HttpUtil.getAsLong(request, "cId");
long mId = HttpUtil.getAsLong(request, "mId");
String cName = HttpUtil.getAsString(request, "cName");
long type = HttpUtil.getAsLong(request, "type");
ICircleMemberValue[] cms = PartyAction.getCircleMembers(cId);
long userId = 0;
if(SessionManager.getUser() != null)
  userId = SessionManager.getUser().getID();
boolean isManager = userId == mId;
String title = null;
if(type == 2)
  title = "转让圈主";
else
  title = "圈友";
%>
<style type="text/css">
.cmb {}
</style>

<div class="page-header-group">
		    <span class="left"><%=cName %> - 圈友</span>
		    <div class="right">
		      <%if(type == 2){ %>
		      <button type="button" class="btn btn-link" id="btnCMSet"><span class="glyphicon glyphicon-user" aria-hidden="true">设置</span></button>
		      <%}else if(isManager){ %>
		      <button type="button" class="btn btn-link" id="btnCMDel"><span class="glyphicon glyphicon-minus" aria-hidden="true">删除</span></button>
		      <%} %>
	        </div>
	    </div>
    <div class="bd">
	    <div class="weui_cells cmb">
	      <%for(int i=0;i<cms.length;i++){%>
	          <div class="weui_cell">
	              <div class="weui_cell_hd">
	                <%if(type == 2){ %>
	                <input type="radio" name="cm" data-name="<%=cms[i].getUsername() %>" value="<%=cms[i].getUserid()%>" <%if(userId == cms[i].getUserid()){ %> disabled <%} %>>
	                <%}else if(isManager){ %><input type="checkbox" class="sr-only" data-id="<%=cms[i].getUserid()%>" <%if(userId == cms[i].getUserid()){ %> disabled <%} %>><%} %>
	                <img id="cImg" src="<%=cms[i].getExtAttr("ImageData") %>" style="width:60px;height:60px;margin-right:10px;">
	              </div>
		          <div class="weui_cell_bd weui_cell_primary">
		            <p><%=cms[i].getUsername() %><%if(mId == cms[i].getUserid()){ %><small class="glyphicon glyphicon-user txt-info" style="padding-left:5px"/><%} %></p>
		          </div>
		          <div class="weui_cell_ft">
		            <small><%if(StringUtils.isNotBlank(cms[i].getPhone())){%><%=cms[i].getPhone() %><br/><%} %>
		                <%if(StringUtils.isNotBlank(cms[i].getJob())){%><%=cms[i].getJob() %><br/><%} %>
		                <%if(StringUtils.isNotBlank(cms[i].getCity())){%><%=cms[i].getCity() %><%} %></small>
		          </div>
	          </div>
	      <%}%>
	    </div>
    </div>
		<div class="navbar-fixed-bottom text-center sr-only" style="margin-bottom: 50px;" id="divCMDel">
		   <a class="weui_btn weui_btn_mini weui_btn_warn" id="btnCMSave">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;
		   <a class="weui_btn weui_btn_mini weui_btn_default" id="btnCMCancel">取消</a>
		</div>

<p class="text-right" style="padding-top:10px;padding-right:5px;">圈友：<%=cms.length %>人</p>

<%if(isManager){ %>
<script language="javascript">

$(document).ready(function(){
  $("#btnCMDel").click(function(){
    $(".cmb :checkbox").removeClass("sr-only");
    $("#divCMDel").removeClass("sr-only");
    $("#btnCMDel").addClass("sr-only");
  }); 
  $("#btnCMCancel").click(function(){
    $(".cmb :checkbox").addClass("sr-only");
    $("#divCMDel").addClass("sr-only");
    $("#btnCMDel").removeClass("sr-only");
  }); 
  
  $("#btnCMSave").click(function(){
    var ids = [];
	$(".cmb :checked").each(function(){
      ids.push($(this).data("id"));
    });
    if(ids.length == 0){
      showDialogInfo("请选择要删除的圈友");
      return;
    }
    showDialogConfirm("确定删除所选圈友？",function(){
        var datas = ids.join(",");
	    $.ajax({ 
				type: "post", 
				async: false,
				processData: false,
				url: _gModuleName+"/business/com.ql.party.web.PartyAction?action=kickoutCircle&cId=<%=cId%>&cName=<%=cName %>",
				data: datas, 
				contentType: "text/html; charset=UTF-8",
				success: function(data, textStatus){
				  if(textStatus == "success"){
				    if(data.flag == true){
				      showToast();
				      gotoPage("/party/circle/MemberInfo.jsp?cId=<%=cId%>&cName=<%=cName %>&mId=<%=mId %>","#cm");
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
  
  $("#btnCMSet").click(function(){
    var $user = $('input:radio[name=cm]:checked');

    if($user == null || $user.val() == undefined){
      showDialogInfo("请选择圈友");
      return;
    }
    var name = $user.data("name");
    showDialogConfirm("确定将圈主权利转让给"+name+"？",function(){
	    $.ajax({ 
				type: "post", 
				async: false,
				processData: false,
				url: _gModuleName+"/business/com.ql.party.web.PartyAction?action=changeMaster&cId=<%=cId%>&cName=<%=cName %>&userId="+$user.val(),
				contentType: "text/html; charset=UTF-8",
				success: function(data, textStatus){
				  if(textStatus == "success"){
				    if(data.flag == true){
				      showToast();
				      gotoPage("/party/circle/CircleInfo.jsp?cId=<%=cId%>","#ci");
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
</script>
<%}%>