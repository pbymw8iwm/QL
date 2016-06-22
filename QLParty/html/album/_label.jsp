<%@page import="com.ql.album.ivalues.IAlbumLabelValue"%>
<%@page import="com.ql.album.web.AlbumAction"%>
<%@page import="com.ql.ivalues.ICfStaticDataValue"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<%
ICfStaticDataValue[] groups = AlbumAction.getLabelGroups();
IAlbumLabelValue[] labels = AlbumAction.getLabels();
%> 
<style type="text/css">
      .labelGroup {white-space:nowrap;overflow-x:auto;font-size: 16px;margin-top:5px;}
	  .labelTitle {display:inline;text-align:center;margin:10px;color:green;}
	  .labelItem {display:inline;text-align:center;margin:10px;}
	</style>
<div style="padding-right:10px;">	
    <%
      long lastGroup = -1;
      String groupName = null;
      for(IAlbumLabelValue label : labels){
        if(label.getGroupid() != lastGroup){
          for(ICfStaticDataValue group : groups){
            if(Long.parseLong(group.getCodevalue()) == label.getGroupid()){
              groupName = group.getCodename();
              break;
            }
          }
          if(lastGroup != -1){
     %>
     </div>
     <%
          }
          lastGroup = label.getGroupid();
     %>
     <div class="labelGroup">
	    <div class="labelTitle"><%=groupName %></div>
     <%
        }
     %>
	    <div class="labelItem"><%=label.getLabelname() %></div>
    <%} %>
    </div>
  <div class="text-right"><span class="glyphicon glyphicon-cog lconfig" aria-hidden="true"></span></div>
</div>
<div style="border-bottom: 2px solid #eee;"></div>

<script type="text/javascript">
$(document).ready(function(){
  $(".lconfig").click(function(){
    //alert(1);  多次 TODO
    gotoPage("/album/LabelManage.jsp","#LM");
  });
});
</script>