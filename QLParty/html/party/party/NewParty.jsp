<%@page import="com.ql.party.web.PartyAction"%>
<%@page import="com.ql.party.ivalues.ISocialCircleValue"%>
<%@page import="com.ai.appframe2.web.HttpUtil"%>
<%@page import="com.ai.appframe2.common.SessionManager"%>
<%@ page contentType="text/html;charset=UTF-8"%>

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
    <div class="page-header">创建聚会</div>
    <div class="bd">
		<form role="form" class="form-horizontal" id="frmParty" name="frmParty">
		  <div class="weui_cells weui_cells_access">
		      <div class="weui_cell weui_cell_select weui_select_after">
	            <div class="weui_cell_hd"><label class="weui_label">圈子</label></div>
	            <div class="weui_cell_bd weui_cell_primary">
	               <select class="weui_select" name="CId" id="CId">
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
	            <div class="weui_cell">
	                <div class="weui_cell_hd"><label class="weui_label">主题</label></div>
	                <div class="weui_cell_bd weui_cell_primary">
	                    <input class="weui_input" type="text" maxlength="20" name="Theme" placeholder="请输入聚会主题"/>
	                </div>
	            </div>
	            <div class="weui_cell">
	                <div class="weui_cell_hd"><label class="weui_label">开始</label></div>
	                <div class="weui_cell_bd weui_cell_primary">
	                    <input class="weui_input" type="datetime-local" name="StartTime" placeholder="请选择聚会开始时间"/>
	                </div>
	                <div class="weui_cell_ft"></div>
	            </div>
	            <div class="weui_cell">
	                <div class="weui_cell_hd"><label class="weui_label">结束</label></div>
	                <div class="weui_cell_bd weui_cell_primary">
	                    <input class="weui_input" type="datetime-local" name="EndTime" placeholder="请选择聚会结束时间(可空)"/>
	                </div>
	                <div class="weui_cell_ft"></div>
	            </div>
	            <div class="weui_cell">
	                <div class="weui_cell_hd"><label class="weui_label">地点</label></div>
	                <div class="weui_cell_bd weui_cell_primary">
	                    <input class="weui_input" type="text" name="GatheringPlace" placeholder="请输入聚会地点"/>
	                </div>
	            </div>
	            <div class="weui_cell">
	                <div class="weui_cell_bd weui_cell_primary">
	                    <textarea class="weui_textarea" name="Remarks" placeholder="请输入备注说明" rows="3"></textarea>
	                </div>
	            </div>
		  </div>
			   <div class="weui_cells"></div>
	        <div class="bd spacing">
			   <a href="javascript:;" class="weui_btn weui_btn_primary" id="btnNewParty">创建</a>
			</div>
			</form>
	</div>

<script src="<%=request.getContextPath()%>/party/party/NewParty.js" language="JavaScript"></script>