
<%@page import="com.ql.party.web.PartyAction"%>
<%@page import="com.ql.party.ivalues.ICircleMemberValue"%>
<%@page import="com.ai.appframe2.web.HttpUtil"%>

<%@ page contentType="text/html;charset=UTF-8"%>
<%
long cId = HttpUtil.getAsLong(request, "cId");
long isSub = HttpUtil.getAsLong(request, "t");
ICircleMemberValue[] cms = PartyAction.getCircleMembers(cId);
int rows = cms.length/4;
if(cms.length%4 > 0)
  rows += 1;
int height = rows * 100;
%>
<style type="text/css">
dl { width:100%;height:<%=height%>px;border:0px;margin:0;font-size:14px;}
dl dd {width:25%; height:100px; border:0px; float:left;text-align:center ;padding-top: 7px;}
</style>

<dl>
<%
for(int i=0;i<cms.length;i++){
%>
  <dd <%if(isSub == 0){ %> xname="mi" xid="<%=i%>" data-toggle="modal" data-target="#infoModal"<%} %>>
    <img src="<%=cms[i].getExtAttr("ImageData") %>" class="img-rounded" width="60" height="60"/>
    <br/><span id="s<%=cms[i].getUserid()%>"><%=cms[i].getUsername() %></span>
  </dd>
<%}%>
</dl>
<%if(isSub == 0){ %>
		<div class="modal fade" id="infoModal" tabindex="-1" role="dialog" aria-labelledby="infoLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		        <h4 class="modal-title" id="infoLabel"><img src="" id="sImg" class="img-rounded" width="60" height="60"></img>&nbsp;&nbsp;&nbsp;&nbsp;<span id="sName"></span></h4>
		      </div>
		      <div class="modal-body">
				<table class="table">
				  <tr><td width="60px">电话：</td><td><span id="sPhone"></span></td></tr>
				  <tr><td>工作：</td><td><span id="sJob"></span></td></tr>
				  <tr><td>地址：</td><td><span id="sCity"></span></td></tr>
				</table>
			  </div>
		    </div>
		  </div>
		</div>
<%} %>
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