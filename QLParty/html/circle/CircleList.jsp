<%@page import="com.ql.party.web.PartyAction"%>
<%@page import="com.ql.party.ivalues.ISocialCircleValue"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<%
ISocialCircleValue[] scs = PartyAction.getSocialCircles(true);
%>  

    <div class="container">
      <div class="page-header-border">我的圈子</div>
	  <%for(ISocialCircleValue sc : scs){ %>
	    <div class="page-group">
	  	  <table>
		    <tr>
		      <td rowspan="2"><img id="clImg<%=sc.getCid() %>" src="<%=sc.getImagedata() %>" class="img-thumbnail" width="80" height="80"/></td>
		      <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
		      <td><h4><a href='javascript:gotoPage("/circle/CircleInfo.jsp?cId=<%=sc.getCid()%>","#ci");' id="clA<%=sc.getCid()%>"><%=sc.getCname() %></a></h4></td>
		    </tr>
		    <tr>
		      <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
		      <td><%=sc.getExtAttr("TypeName") %>
		  	&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:gotoPage("/circle/MemberInfo.jsp?cId=<%=sc.getCid()%>","#cm");'><%=sc.getExtAttr("MemberCount") %>个成员</a>
		  	&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:gotoPage("/party/PartyList.jsp?cId=<%=sc.getCid()%>","#pl");'><%=sc.getExtAttr("PartyCount") %>个聚会</a>
		  	&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:;' xname="clShare" data-id="<%=sc.getCid()%>"><span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span></a></td>
		    </tr>
		  </table>
		</div>
	  <%} %>
	  
	</div>

<script src="<%=request.getContextPath()%>/circle/CircleList.js" language="JavaScript"></script>