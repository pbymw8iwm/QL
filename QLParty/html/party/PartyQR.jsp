<%@page import="com.ql.party.ivalues.IQPartyValue"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.ql.party.web.PartyAction"%>
<%@page import="com.ai.appframe2.web.HttpUtil"%>
<%@page import="com.ql.wechat.WechatCommons"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<%
Long partyId = HttpUtil.getAsLong(request, "partyId");
IQPartyValue party = PartyAction.getParty(partyId,false);
if(party == null){
%>  
<body><h3>聚会可能已经被删除</h3></body>
<%
  return;
}
%> 
  <head>
    <title>邀请您参加聚会</title>
	
    <meta http-equiv="keywords" content="聚会助手  社交圈">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <%@ include file="/CommonHead.jsp"%>
  </head>
  <body>
<div style="background-color:#5bc0de;width:100%;height:100%;padding-top:50px">
  <div style="border-radius: 6px;background-color:#ffffff;width:80%;margin-right:auto;margin-left:auto;padding-top:10px;padding-bottom:10px">
		  <h3 class="text-center"><%=party.getTheme() %></h3>
		    <p class="text-center">
				<span class="help-block"><%=party.getCname() %>&nbsp;&nbsp;-&nbsp;&nbsp;<%=party.getUsername() %></span>
				<strong><%=party.getExtAttr("PTime") %></strong>
				<br/>
				<strong><%=party.getGatheringplace() %></strong>
				<br/><br/>
				<button type="button" class="btn btn-link" id="btnInfo" data-toggle="modal" data-target="#infoModal">查看参与情况</button>
			</p>
		<p class="text-center">
		      参加聚会请长按识别下图二维码
		  <img src="<%=WechatCommons.Url_ShowQr + party.getQrticket()%>" class="img-rounded" width="200" height="200"/>
		  <br/>
		      扫码参加聚会，分享精彩时刻
		<br/>
		<span class="help-block" style="font-size:10px;">当前二维码将于<%=party.getExtAttr("QRExpiryDate").toString() %>失效</span>
		</p>
		</div>
	</div>
	
	<!--BEGIN actionSheet-->
    <div id="actionSheet_wrap">
        <div class="weui_mask_transition" id="mask"></div>
        <div class="weui_actionsheet" id="weui_actionsheet">
            <div id="divInfo"></div>
            <div class="weui_actionsheet_action">
                <div class="weui_actionsheet_cell" id="actionsheet_cancel"><div class="weui_icon_cancel"></div></div>
            </div>
        </div>
    </div>
    <!--END actionSheet-->
  </body>
</html>

<script language="javascript">
$(document).ready(function(){
  $("#btnInfo").click(function(){
    var div = $("#divInfo");
    if(div.html() == "")
      div.load("<%=request.getContextPath()%>/party/MemberInfo.jsp?partyId=<%=partyId%>");
    var mask = $('#mask');
                    var weuiActionsheet = $('#weui_actionsheet');
                    weuiActionsheet.addClass('weui_actionsheet_toggle');
                    mask.show().addClass('weui_fade_toggle').click(function () {
                        hideActionSheet(weuiActionsheet, mask);
                    });
                    $('#actionsheet_cancel').click(function () {
                        hideActionSheet(weuiActionsheet, mask);
                    });
                    weuiActionsheet.unbind('transitionend').unbind('webkitTransitionEnd');

                    function hideActionSheet(weuiActionsheet, mask) {
                        weuiActionsheet.removeClass('weui_actionsheet_toggle');
                        mask.removeClass('weui_fade_toggle');
                        weuiActionsheet.on('transitionend', function () {
                            mask.hide();
                        }).on('webkitTransitionEnd', function () {
                            mask.hide();
                        })
                    }
  });
});
</script>