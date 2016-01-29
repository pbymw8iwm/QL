
<%@ page contentType="text/html;charset=UTF-8"%>
		<div class="weui_cells">
    	  <div class="weui_cell">
	          <div class="weui_cell_hd"><img src="<%=party.getImagedata() %>" class="img-circle" style="width:50px;margin-right:8px;display:block"/></div>
	          <div class="weui_cell_bd weui_cell_primary">
	            <p><a href='javascript:gotoPage("/party/PartyInfo.jsp?partyId=<%=party.getPartyid()%>","#pi");'><strong><%=party.getTheme() %></strong></a>
	              <small style="padding-left:5px;"><span class="glyphicon glyphicon-glass <%=stateClass %>" aria-hidden="true"></span></small>
	            </p>
	          </div>
	          <div class="weui_cell_ft">
		          <a xname="piShare" data-id="<%=party.getPartyid()%>" data-theme="<%=party.getTheme()%>" data-img="<%=party.getImagedata()%>">
	   		        <span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span>
	   		      </a>
	          </div>
          </div>
          <div class="weui_cell">
	          <div class="weui_cell_bd weui_cell_primary">
	            <p><%=party.getCname() %>&nbsp;&nbsp;-&nbsp;&nbsp;<%=party.getUsername() %></p>
	          </div>
          </div>
          <div class="weui_cell">
	          <div class="weui_cell_bd weui_cell_primary">
	            <p class="txt-info"><%=party.getExtAttr("PTime") %></p>
	          </div>
          </div>
          <div class="weui_cell">
	          <div class="weui_cell_bd weui_cell_primary">
	            <p><%=party.getGatheringplace() %></p>
	          </div>
          </div>
          <%if(party.getRemarks() != null && "".equals(party.getRemarks()) == false){ %>
          <div class="weui_cell">
	          <div class="weui_cell_bd weui_cell_primary">
	            <p><%=party.getRemarks().replaceAll("\n","<br/>") %></p>
	          </div>
          </div>
          <%} %>
          <a class="weui_cell" href='javascript:gotoPage("/party/MemberInfo.jsp?partyId=<%=party.getPartyid()%>&partyName=<%=party.getTheme() %>","#pm");'>
              <div class="weui_cell_bd weui_cell_primary">
	            <p>出席者</p>
	          </div>
	          <div class="weui_cell_ft">
		          <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
	          </div>
          </a>
          <a class="weui_cell" href='javascript:gotoPage("/party/Photo.jsp?partyId=<%=party.getPartyid()%>&cId=<%=party.getCid()%>&manager=<%=party.getCreater()%>&partyName=<%=party.getTheme() %>","#pp");'>
              <div class="weui_cell_bd weui_cell_primary">
	            <p>相册</p>
	          </div>
	          <div class="weui_cell_ft">
		          <span class="glyphicon glyphicon-picture" aria-hidden="true"></span>
	          </div>
          </a>
        </div>