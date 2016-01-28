
<%@ page contentType="text/html;charset=UTF-8"%>
		<div class="page-group">
    	  <div class="weui_cell">
	          <%if(cId <= 0){ %><div class="weui_cell_hd"><img src="<%=party.getImagedata() %>" class="img-circle" style="width:50px;margin-right:8px;display:block"/></div><%} %>
	          <div class="weui_cell_bd weui_cell_primary">
	            <p><a href='javascript:gotoPage("/party/PartyInfo.jsp?partyId=<%=party.getPartyid()%>","#pi");'><%=party.getTheme() %></a></p>
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
	            <p><%=party.getExtAttr("PTime") %></p>
	          </div>
          </div>
          <div class="weui_cell">
	          <div class="weui_cell_bd weui_cell_primary">
	            <p><%=party.getGatheringplace() %></p>
	          </div>
          </div>
          <a class="weui_cell" href='javascript:gotoPage("/party/Photo.jsp?partyId=<%=party.getPartyid()%>&cId=<%=party.getCid()%>&manager=<%=party.getCreater()%>","#pp");'>
              <div class="weui_cell_bd weui_cell_primary">
	            <p>时光留驻</p>
	          </div>
	          <div class="weui_cell_ft">
		          <span class="glyphicon glyphicon-picture" aria-hidden="true"></span>
	          </div>
          </a>
        </div>