<%@page import="com.ql.party.web.PartyAction"%>
<%@page import="com.ql.ivalues.ICfStaticDataValue"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>创建圈子</title>
	
    <meta http-equiv="keywords" content="聚会助手  社交圈">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <%@ include file="/CommonHead.jsp"%>
  </head>

<%
ICfStaticDataValue[] sdatas = PartyAction.getStaticDatas("CircleType");
%>  
  <body>
    <div class="container">
		<div class="page-header">
		  	<h3>创建圈子</h3>
			</div>
		  <form role="form" class="form-horizontal" id="frmInfo" name="frmInfo">
			   <div class="form-group">
			      <label for="CName" class="col-xs-2 control-label">圈名</label>
			      <div class="col-xs-10"><input type="text" maxlength="10" class="form-control" id="CName" name="CName" placeholder="请输入圈名"></div>
			   </div>
			   <div class="form-group">
			      <label for="CType" class="col-xs-2 control-label">分类</label>
			      <div class="col-xs-10">
			        		 <select class="form-control" id="CType" name="CType">
			        		 <%for(ICfStaticDataValue sd : sdatas){ %>
						         <option value="<%=sd.getCodevalue() %>"><%=sd.getCodename() %></option>
						     <%} %>
						      </select>
			      </div>
			   </div>
			   <p class="text-right"><button type="button" class="btn btn-default" id="btnSave">创建</button></p>
			</form>
	</div>
  </body>
</html>
<script language="javascript">
$(document).ready(function(){
  $("#btnSave").click(function(){
	    if($('#CName').val() == ""){
	      $('#CName').focus();
	      return;
	    }
	    var datas = JSON.stringify($('#frmInfo').serializeObject());
	    $.ajax({ 
				type: "post", 
				async: false,
				processData: false,
				url: _gModuleName+"/business/com.ql.party.web.PartyAction?action=createCircle",
				data: datas, 
				contentType: "text/html; charset=UTF-8",
				success: function(data, textStatus){
				  if(textStatus == "success"){
				    if(data.flag == true){
				    	window.location = _gModuleName+"/circle/CircleInfo.jsp?cId="+data.msg;
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
  
  window.location = _gModuleName+"/circle/CircleInfo.jsp?cId=11";
});
</script>