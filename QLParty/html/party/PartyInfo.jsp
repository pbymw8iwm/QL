<%@page import="com.ql.party.ivalues.IQPartyValue"%>
<%@page import="com.ql.party.wechat.WechatOpImpl"%>
<%@page import="com.ai.appframe2.common.SessionManager"%>
<%@page import="com.ql.wechat.WechatCommons"%>
<%@page import="com.ql.party.web.PartyAction"%>
<%@page import="com.ai.appframe2.web.HttpUtil"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<%
long partyId = HttpUtil.getAsLong(request, "partyId");
IQPartyValue party = PartyAction.getParty(partyId,true);
if(party == null){
%>  
<h4>聚会可能已经被删除</h4>
<%
  return;
}
long userId = SessionManager.getUser().getID();
boolean isManager = userId == party.getCreater();
%>
  <div class="page-header-group">
    <span class="left">聚会信息 - <%=party.getCname() %></span>
    <a class="right" id="btnPIShare"><span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span></a>
  </div>
  <div class="bd">
    <div class="weui_cells <%if(isManager){ %>weui_cells_access<%}%>">
       <a class="weui_cell" xname="pi">
	      <div class="weui_cell_bd weui_cell_primary">
	         <p class="pi_title">主题</p>
	      </div>
	      <div class="weui_cell_ft pi_value" data-type="text" data-name="Theme"><%=party.getTheme() %></div>
       </a>
       <a class="weui_cell" xname="pi">
	      <div class="weui_cell_bd weui_cell_primary">
	         <p class="pi_title">开始</p>
	      </div>
	      <div class="weui_cell_ft pi_value" data-type="datetime-local" data-name="StartTime"><%=party.getExtAttr("Start")%></div>
       </a>
       <a class="weui_cell" xname="pi">
	      <div class="weui_cell_bd weui_cell_primary">
	         <p class="pi_title">结束</p>
	      </div>
	      <div class="weui_cell_ft pi_value" data-type="datetime-local" data-name="EndTime"><%=party.getExtAttr("End") %></div>
       </a>
       <a class="weui_cell" xname="pi">
	      <div class="weui_cell_bd weui_cell_primary">
	         <p class="pi_title">地点</p>
	      </div>
	      <div class="weui_cell_ft pi_value" data-type="text" data-name="GatheringPlace"><%=party.getGatheringplace() %></div>
       </a>
       <a class="weui_cell" xname="pi">
	      <div class="weui_cell_bd weui_cell_primary">
	         <p class="pi_title">备注</p>
	      </div>
	      <div class="weui_cell_ft pi_value" data-type="text" data-name="Remarks"><%=party.getRemarks().replaceAll("\n", " ") %></div>
       </a>
    </div>
    
    <div class="weui_cells">
       <a class="weui_cell" href='javascript:gotoPage("/party/MemberInfo.jsp?partyId=<%=party.getPartyid()%>&partyName=<%=party.getTheme() %>","#pm");'>
              <div class="weui_cell_bd weui_cell_primary">
	            <p>出席者</p>
	          </div>
	          <div class="weui_cell_ft">
		          <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
	          </div>
          </a>
       <a class="weui_cell weui_cells_access" xname="piss">
	      <div class="weui_cell_bd weui_cell_primary">
	         <p>我</p>
	      </div>
	      <div class="weui_cell_ft"><span id="piSSN"><%=party.getExtAttr("SelfStateName") %></span>(<span id="piSC"><%=party.getExtAttr("SelfCount") %></span>人)</div>
       </a>
    </div>
    
    <div class="weui_cells">
       <a class="weui_cell" href='javascript:gotoPage("/party/Photo.jsp?partyId=<%=party.getPartyid()%>&cId=<%=party.getCid()%>&manager=<%=party.getCreater()%>&partyName=<%=party.getTheme() %>","#pp");'>
	      <div class="weui_cell_bd weui_cell_primary">
	         <p>相册</p>
	      </div>
	      <div class="weui_cell_ft"><span class="glyphicon glyphicon-picture" aria-hidden="true"></span></div>
       </a>
    </div>
  </div>
  
<script type="text/html" id="pias">
<div class="weui_cells">
  <form role="form" class="form-horizontal" id="frmPI" name="frmPI">
				   <div class="sr-only">
				     <input type="text" maxlength="10" class="form-control" name="PartyId" value="<%=partyId%>"/>
				   </div>
                  <div class="weui_cell">
	                <div class="weui_cell_bd weui_cell_primary" id="pi_area"></div>
	              </div>
  </form>
</div>
</script>   

<script type="text/html" id="sias">
<div class="weui_cells weui_cells_radio">
            <label class="weui_cell weui_check_label">
                <div class="weui_cell_bd weui_cell_primary">
                    <p>参加</p>
                </div>
                <div class="weui_cell_ft">
                    <input type="radio" class="weui_check" name="piSState" value="1" data-name="参加">
                    <span class="weui_icon_checked"></span>
                </div>
            </label>
            <label class="weui_cell weui_check_label">
                <div class="weui_cell_bd weui_cell_primary">
                    <p>待定</p>
                </div>
                <div class="weui_cell_ft">
                    <input type="radio" class="weui_check" name="piSState" value="2" data-name="待定">
                    <span class="weui_icon_checked"></span>
                </div>
            </label>
            <label class="weui_cell weui_check_label">
                <div class="weui_cell_bd weui_cell_primary">
                    <p>不参加</p>
                </div>
                <div class="weui_cell_ft">
                    <input type="radio" class="weui_check" name="piSState" value="0" data-name="不参加">
                    <span class="weui_icon_checked"></span>
                </div>
            </label>
        </div>
<div class="weui_cells">
            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">人数</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                  <input class="weui_input" maxlength="10" type="tel" name="piSCount"/>
                </div>
            </div>
        </div>
</script>   

<script language="javascript">

  var sharePartyMsg = {
	    title: '聚会啦，快来参加吧', // 分享标题
	    desc: '<%=party.getTheme()%>', // 分享描述
	    link: '<%=WechatCommons.getUrlView(WechatOpImpl.Type_JoinParty+partyId)%>', // 分享链接
	    imgUrl: '<%=party.getImagedata()%>', // 分享图标
	    type: 'link', // 分享类型,music、video或link，不填默认为link
	    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
	    success: function () { 
	        // 用户确认分享后执行的回调函数
	    },
	    cancel: function () { 
	        // 用户取消分享后执行的回调函数
	    }
	};
  
$(document).ready(function(){
  wx.onMenuShareAppMessage(sharePartyMsg);
  wx.onMenuShareTimeline(sharePartyMsg);
    
  $("#btnPIShare").click(function(){
    wx.onMenuShareAppMessage(sharePartyMsg);
    wx.onMenuShareTimeline(sharePartyMsg);
    showDialogInfo("请点击右上角的三个小点，分享到朋友或朋友圈，邀请朋友参加聚会！");
  }); 
  
  $("[xname='pi']").click(function(){
    $piObj = $(this).find(".pi_value");
    var title = $(this).find(".pi_title").text();
    var value = $piObj.text();
    var name = $piObj.data("name");
    var type = $piObj.data("type");

    $piObjEdit = $("<input class='weui_input' type='"+type+"' name='"+name+"' value='"+value+"'/>");
    var $asAreaSub = $($("#pias").html());
    $("#as_name").text(title);
    $("#as_area").append($asAreaSub);
    $("#pi_area").append($piObjEdit);
    $piObjEdit.focus();
    showActionSheet($asAreaSub, function(){
   		var value = $('#frmPI input[name=Theme]').val();
   		if(value != undefined && value == ""){
   		  $('#frmPI input[name=Theme]').focus();
   		  return;
   		}
   		value = $('#frmPI input[name=StartTime]').val();
   		if(value != undefined && value == ""){
   		  $('#frmPI input[name=StartTime]').focus();
   		  return;
   		}
	    var datas = JSON.stringify($('#frmPI').serializeObject());
	    if(datas == "{}")
	      return;
	    $.ajax({ 
				type: "post", 
				async: false,
				processData: false,
				url: _gModuleName+"/business/com.ql.party.web.PartyAction?action=saveParty",
				data: datas, 
				contentType: "text/html; charset=UTF-8",
				success: function(data, textStatus){
				  if(textStatus == "success"){
				    if(data.flag == true){
				      $piObj.text($piObjEdit.val());
				      hideActionSheet();
				    }
				    else
				      alert(data.msg);
				  }
	      },
	      error:function(httpRequest,errType,ex ){
	        alert(ex);
	      }
	  }); 
	});
  });
  
  $("[xname='piss']").click(function(){
    var state = $("#piSSN").text();
    var count = $("#piSC").text();

    var $asAreaSub = $($("#sias").html());
    $("#as_name").text("我的参与情况");
    $("#as_area").append($asAreaSub);
    
    var radioIndex = 2;
    if(state == "参加")
      radioIndex = 0;
    else if(state == "待定")
      radioIndex = 1;
    $("input[name=piSState]:eq("+radioIndex+")").attr("checked",'checked');
    $("input[name=piSCount]").val(count);
    
    showActionSheet($asAreaSub, function(){
   		state = $('input[name=piSState]:checked').val();
   		count = parseInt($('input[name=piSCount]').val());
   		if(state != "不参加" && (count == NaN || count <= 0))
   		  count = 1;
	    $.ajax({ 
				type: "post", 
				async: false,
				processData: false,
				url: _gModuleName+"/business/com.ql.party.web.PartyAction?action=setPartyMember&partyId=<%=partyId%>&state="+state+"&count="+count,
				contentType: "text/html; charset=UTF-8",
				success: function(data, textStatus){
				  if(textStatus == "success"){
				    if(data.flag == true){
				      $("#piSSN").text($('input[name=piSState]:checked').data("name"));
				      $("#piSC").text(count);
				      hideActionSheet();
				    }
				    else
				      alert(data.msg);
				  }
	      },
	      error:function(httpRequest,errType,ex ){
	        alert(ex);
	      }
	  }); 
	});
  });
});
    
function save() { 
  if(isChanged){
    $.ajax({ 
				type: "post", 
				async: true,
				processData: false,
				url: _gModuleName+"/business/com.ql.party.web.PartyAction?action=setPartyMember&partyId=<%=partyId%>&state="+selfInfo.State+"&count="+selfInfo.PCount,
				contentType: "text/html; charset=UTF-8",
				success: function(data, textStatus){
			    },
			    error:function(httpRequest,errType,ex ){
			    }
	}); 
  }
}
</script>