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
		      <td rowspan="2"><img id="clImg<%=sc.getCid() %>" src="<%=sc.getImagedata() %>" class="img-thumbnail" style="width:80px;height:80px;margin-right:10px"/></td>
		      <td><h4><a href='javascript:gotoPage("/party/circle/CircleInfo.jsp?cId=<%=sc.getCid()%>","#ci");' id="clA<%=sc.getCid()%>"><strong class="txt-primary"><%=sc.getCname() %></strong></a></h4></td>
		    </tr>
		    <tr>
		      <td><%=sc.getExtAttr("TypeName") %>
		  	<a style="margin-left:8px" href='javascript:gotoPage("/party/circle/MemberInfo.jsp?cId=<%=sc.getCid()%>&cName=<%=sc.getCname() %>&mId=<%=sc.getCreater() %>","#cm");'><%=sc.getExtAttr("MemberCount") %>个成员</a>
		  	<a style="margin-left:8px" href='javascript:gotoPage("/party/party/PartyList.jsp?cId=<%=sc.getCid()%>&cName=<%=sc.getCname() %>","#pl");'><%=sc.getExtAttr("PartyCount") %>个聚会</a>
		  	<a style="margin-left:8px" href='javascript:;' xname="clShare" data-id="<%=sc.getCid()%>"><span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span></a></td>
		    </tr>
		  </table>
		</div>
	  <%} %>
	  
	</div>

<script src="<%=request.getContextPath()%>/party/circle/CircleList.js" language="JavaScript"></script>