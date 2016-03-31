<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>聚会助手</title>
	
    <meta http-equiv="keywords" content="聚会助手  社交圈">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <%@ include file="/CommonHead.jsp"%>
    <%@ include file="/WechatJsHead.jsp"%>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/style/menu.css">
  </head>
  
  <body style="padding-bottom:45px">


<div id="container">
</div>

<div class="btn3 clearfix">
    <div class="menu">
        <div class="bt-name"><a href="javascript:;">聚会</a></div>
        <div class="sanjiao"></div>
        <div class="new-sub">
            <ul >
                <li><a href="javascript:;" xname="aLink" data-id="#pn" data-url="/party/party/NewParty.jsp">创建聚会</a></li>
                <li><a href="javascript:;" xname="aLink" data-id="#pc" data-url="/party/party/CurrentParty.jsp">当前聚会</a></li>
            </ul>
            <div class="tiggle"></div>
            <div class="innertiggle"></div>
        </div>
    </div>

    <div class="menu" >
     <div class="bt-name"><a href="javascript:;">圈子</a></div>
    <div class="sanjiao"></div>
    <div  class="new-sub">
        <ul>
            <li><a href="javascript:;" xname="aLink" data-id="#cn" data-url="/party/circle/NewCircle.jsp">创建圈子</a></li>
            <li><a href="javascript:;" xname="aLink" data-id="#cl" data-url="/party/circle/CircleList.jsp">我的圈子</a></li>
        </ul>
        <div class="tiggle"></div>
        <div class="innertiggle"></div>
    </div>
    </div>

    <div class="menu">
        <div class="bt-name"><a href="javascript:;">帮助</a></div>
    </div><!--menu-->
</div>
    <%@ include file="/WeuiCommon.jsp"%>
  </body>
</html>

<script src="<%=request.getContextPath()%>/js/Main.js" language="JavaScript"></script>
<script type="text/javascript">
//下部菜单栏
//ios上好像不起作用
$(document).click(function(){
  $(".menu").removeClass("cura"); 
  $(".menu").children(".new-sub").slideUp("fast");
});

//弹出垂直菜单
$(".menu").click(function(e) {
    if ($(this).hasClass("cura")) {
        $(this).children(".new-sub").hide(); //当前菜单下的二级菜单隐藏
        $(".menu").removeClass("cura"); //同一级的菜单项
    } else {
        $(".menu").removeClass("cura"); //移除所有的样式
        $(this).addClass("cura"); //给当前菜单添加特定样式
        $(".menu").children(".new-sub").slideUp("fast"); //隐藏所有的二级菜单
        $(this).children(".new-sub").slideDown("fast"); //展示当前的二级菜单
    }
    return false;
});

//
$("[xname='aLink']").click(function(){
  gotoPage($(this).data("url"),$(this).data("id"));
});

function init(){
	var hash = location.hash;
	var url = null;
	if(hash == "#pi" && getParam("partyId") != null)
		url = "/party/party/PartyInfo.jsp?partyId="+getParam("partyId");
	else if(hash == "#ci" && getParam("cId") != null)
		url = "/party/circle/CircleInfo.jsp?cId="+getParam("cId");
	else{
		url = "/party/party/CurrentParty.jsp";
		hash = "#pc";
		location.hash = hash;
	}
	if(url != null)
		gotoPage(url,hash);
		

	//$("#container").load(_gModuleName+"/party/circle/CircleInfo.jsp?cId=11");
}

init();

</script>