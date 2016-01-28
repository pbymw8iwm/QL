<%@page import="com.ai.appframe2.common.SessionManager"%>
<%@page import="com.ql.party.web.PartyAction"%>
<%@page import="com.ql.ivalues.ICfStaticDataValue"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<%
long userId = SessionManager.getUser().getID();
ICfStaticDataValue[] sdatas = PartyAction.getStaticDatas("CircleType");
%>  
		<div class="page-header">创建圈子</div>
    	<div class="bd">
		  <form role="form" class="form-horizontal" id="frmCircle" name="frmCircle">
	    	<div class="weui_cells weui_cells_access">
	            <div class="weui_cell">
	                <div class="weui_cell_hd"><label class="weui_label">圈名</label></div>
	                <div class="weui_cell_bd weui_cell_primary">
	                    <input class="weui_input" maxlength="10" type="text" name="CName" placeholder="请输入圈名"/>
	                </div>
	            </div>
	            <div class="weui_cell weui_cell_select weui_select_after">
	                <div class="weui_cell_hd"><label class="weui_label">分类</label></div>
	                <div class="weui_cell_bd weui_cell_primary">
	                     <select class="weui_select" name="CType">
			        		 <%for(ICfStaticDataValue sd : sdatas){ %>
						         <option value="<%=sd.getCodevalue() %>"><%=sd.getCodename() %></option>
						     <%} %>
						      </select>
	                </div>
	            </div>
	            <a class="weui_cell" id="btnImgNC">
	                <div class="weui_cell_hd"><label class="weui_label">头像</label></div>
	                <div class="weui_cell_bd weui_cell_primary">
			            <img id="cImgNC" src="" class="sr-only" width="100px" height="100px"/>
	                </div>
	                <div class="weui_cell_ft">上传</div>
	            </a>
	        </div>
        	<div class="weui_cells"></div>
	        <div class="bd spacing">
			   <a href="javascript:;" class="weui_btn weui_btn_primary" id="btnNewCircle">创建</a>
			</div>
			</form>
		</div>

<script src="<%=request.getContextPath()%>/circle/NewCircle.js" language="JavaScript"></script>