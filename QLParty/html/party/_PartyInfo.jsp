
<%@ page contentType="text/html;charset=UTF-8"%>

  	<table class="table">
   		<caption>
   		  <div class="row">
   		    <a href="<%=request.getContextPath()%>/party/PartyInfo.jsp?partyId=<%=party.getPartyid()%>">
   		    <div class="col-xs-10">
   		      <%if(cId <= 0){ %><img src="<%=party.getImagedata() %>" class="img-circle" width="50" height="50"/>&nbsp;&nbsp;<%} %><strong><%=party.getTheme() %></strong>
   		    </div>
   		    </a>
   		    <div class="col-xs-2">
   		      <button type="button" class="btn btn-link" xname="btnShare" xid="<%=party.getPartyid()%>" theme="<%=party.getTheme()%>" img="<%=party.getImagedata()%>">
   		        <span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span>
   		      </button>
   		    </div>
   		  </div>
   		</caption>
   		<tbody>
   		  <tr><td><%=party.getCname() %>&nbsp;&nbsp;-&nbsp;&nbsp;<%=party.getUsername() %></td></tr>
   		  <tr><td><%=party.getExtAttr("PTime") %></td></tr>
   		  <tr><td><%=party.getGatheringplace() %></td></tr>
   		  <tr><td>
   		    <a href="<%=request.getContextPath()%>/party/Photo.jsp?partyId=<%=party.getPartyid()%>&cId=<%=party.getCid()%>&manager=<%=party.getCreater()%>">
	   		  <span class="glyphicon glyphicon-picture" aria-hidden="true" style="padding-top: 2px;"></span>时光留驻
	   		</a>
		  </td></tr>
   		</tbody>
   	  </table>