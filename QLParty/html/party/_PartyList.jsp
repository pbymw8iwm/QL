<%@page import="com.ai.appframe2.common.SessionManager"%>
<%@page import="com.ql.party.ivalues.IQPartyValue"%>
<%@page import="com.ql.party.web.PartyAction"%>
<%@page import="com.ai.appframe2.web.HttpUtil"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<style type="text/css">
a:hover,
a:focus {  
  text-decoration: none;
}
</style>
<%
long cId = HttpUtil.getAsLong(request, "cId");
IQPartyValue[] partys = PartyAction.getPartys(cId);
boolean hasData = false;
%>  
<div class="container">
	  <div class="page-header">
        <h3>参与的</h3>
      </div>
      <%for(IQPartyValue party : partys){ 
          if(party.getExtAttr("PState") == null)
            continue;
          hasData = true;
      %>
      <%@ include file="/party/_PartyInfo.jsp"%>
      <%} %>
      <%if(hasData == false){ %>
      无
      <%} %>
	  <div class="page-header">
        <h3>未参与</h3>
      </div>
      <%
        hasData = false;
        for(IQPartyValue party : partys){ 
          if(party.getExtAttr("PState") != null)
            continue;
          hasData = true;
      %>
      <%@ include file="/party/_PartyInfo.jsp"%>
      <%} %>
      <%if(hasData == false){ %>
      无
      <%} %>
</div>
<script language="javascript">

wx.ready(function(){
  wx.hideOptionMenu();
  wx.showMenuItems({
    menuList: ['menuItem:share:appMessage','menuItem:share:timeline'] // 要显示的菜单项，所有menu项见附录3
  });

});
wx.error(function(res){
//    alert(res.errMsg);
});
  
$(document).ready(function(){
  $("[xname='btnShare']").click(function(){
    var pId = $(this).attr("xid");
    var theme = $(this).attr("theme");
    var img = $(this).attr("img");
    var cLink = "";
    $.ajax({ 
                cache: false,
				async: false,
				type: "get", 
				url: _gModuleName+"/business/com.ql.party.web.PartyAction?action=getPartyShareLink&pId="+pId,
				contentType: "text/html; charset=UTF-8",
				success: function(data, textStatus){
				  if(textStatus == "success"){
				    if(data.flag == true){
				    	cLink = data.msg;
				    }
				    else
				      alert(data.msg);
				  }
	      },
	      error:function(httpRequest,errType,ex ){
	        alert(ex);
	      }
		});
    var shareMsg = {
	    title: '<%=SessionManager.getUser().getName()%>邀您参加聚会', // 分享标题
	    desc: theme, // 分享描述
	    link: cLink, // 分享链接
	    imgUrl: img, // 分享图标
	};
    wx.onMenuShareAppMessage(shareMsg);
    wx.onMenuShareTimeline(shareMsg);
    
    alert("请点击右上角的三个小点，分享到朋友或朋友圈，邀请朋友参加聚会！");
  }); 
});
</script>