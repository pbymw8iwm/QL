
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.ql.party.web.PartyAction"%>
<%@page import="com.ql.party.ivalues.ICircleMemberValue"%>
<%@page import="com.ai.appframe2.web.HttpUtil"%>

<%@ page contentType="text/html;charset=UTF-8"%>
<%
long cId = HttpUtil.getAsLong(request, "cId");
String cName = HttpUtil.getAsString(request, "cName");
ICircleMemberValue[] cms = PartyAction.getCircleMembers(cId);
int rows = cms.length/4;
if(cms.length%4 > 0)
  rows += 1;
int height = rows * 100;
%>
<style type="text/css">
.cmb {}
.cmb dl { width:100%;height:<%=height%>px;border:0px;margin:0;font-size:14px;}
.cmb dl dd {width:25%; height:100px; border:0px; float:left;text-align:center ;padding-top: 7px;}
</style>

<div class="page-header-group">
		    <span class="left"><%=cName %></span>
		    <div class="right">
	        </div>
	    </div>
    <div class="bd">
	    <div class="weui_cells">
	      <%for(int i=0;i<cms.length;i++){%>
	          <div class="weui_cell">
	              <div class="weui_cell_hd"><img id="cImg" src="<%=cms[i].getExtAttr("ImageData") %>" style="width:60px;height:60px;margin-right:10px;display:block"></div>
		          <div class="weui_cell_bd weui_cell_primary">
		            <p><%=cms[i].getUsername() %></p>
		          </div>
		          <div class="weui_cell_ft">
		            <small><%=StringUtils.isNotBlank(cms[i].getPhone())?cms[i].getPhone():"&nbsp;" %><br/>
		            	<%=StringUtils.isNotBlank(cms[i].getCity())?cms[i].getCity():"&nbsp;" %><br/>
		            	<%=StringUtils.isNotBlank(cms[i].getJob())?cms[i].getJob():"&nbsp;" %></small>
		          </div>
	          </div>
	      <%}%>
	    </div>
    </div>

<p class="text-right" style="padding-top:10px;padding-right:5px;">圈友：<%=cms.length %>人</p>
<script language="javascript">

var mInfo = [
<%for(int i=0;i<cms.length;i++){%>
  <%if(i > 0){%>
  ,
  <%}%>
  {
    UserId:<%=cms[i].getUserid() %>,
    Image:'<%=cms[i].getExtAttr("ImageData") %>',
    UserName:'<%=cms[i].getUsername() %>',
    Phone:'<%=cms[i].getPhone()==null?"":cms[i].getPhone() %>',
    Job:'<%=cms[i].getJob()==null?"":cms[i].getJob() %>',
    City:'<%=cms[i].getCity()==null?"":cms[i].getCity() %>'
  }
<%}%>
];

$(document).ready(function(){
  $("[xname='mi']").click(function(){
    var i = $(this).attr("xid");
    $("#sImg").attr("src",mInfo[i].Image);
    $("#sName").text(mInfo[i].UserName);
    $("#sPhone").text(mInfo[i].Phone);
    $("#sJob").text(mInfo[i].Job);
    $("#sCity").text(mInfo[i].City);
  }); 
}); 
</script>