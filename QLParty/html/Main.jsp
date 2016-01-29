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

    <!-- 通用组件 -->
    <div class="weui_toptips weui_warn js_tooltips" id="tooltip"></div>
    <!--BEGIN dialog1-->
    <div class="weui_dialog_confirm" id="dialogConfirm" style="display: none;">
        <div class="weui_mask"></div>
        <div class="weui_dialog">
            <div class="weui_dialog_hd"><strong class="weui_dialog_title">操作确认</strong></div>
            <div class="weui_dialog_bd"></div>
            <div class="weui_dialog_ft">
                <a href="javascript:;" class="weui_btn_dialog default">取消</a>
                <a href="javascript:;" class="weui_btn_dialog primary">确定</a>
            </div>
        </div>
    </div>
    <!--END dialog1-->
    <!--BEGIN dialog2-->
    <div class="weui_dialog_alert" id="dialogInfo" style="display: none;">
        <div class="weui_mask"></div>
        <div class="weui_dialog">
            <div class="weui_dialog_hd"><strong class="weui_dialog_title">提示信息</strong></div>
            <div class="weui_dialog_bd"></div>
            <div class="weui_dialog_ft">
                <a href="javascript:;" class="weui_btn_dialog primary">确定</a>
            </div>
        </div>
    </div>
    <!--END dialog2-->
    
    <div id="toast" style="display: none;">
        <div class="weui_mask_transparent"></div>
        <div class="weui_toast">
            <i class="weui_icon_toast"></i>
            <p class="weui_toast_content">处理成功</p>
        </div>
    </div>
    
    <div id="actionSheet_wrap">
        <div class="weui_mask_transition" id="mask"></div>
        <div class="weui_actionsheet" id="weui_actionsheet">
            <div class="page-header-group">
              <div class="left" id="as_name"></div>
              <div class="right"><a href='javascript:;' class="weui_btn weui_btn_mini weui_btn_plain_primary" id="btnASSave">保存</a></div>
            </div>
            <div class="bd">
              <div class="weui_cells" id="as_area"></div>
            </div>
            <div class="weui_actionsheet_action">
                <div class="weui_actionsheet_cell" id="actionsheet_cancel"><div class="weui_icon_cancel"></div></div>
            </div>
        </div>
    </div>
  
<div class="btn3 clearfix">
    <div class="menu">
        <div class="bt-name"><a href="javascript:;">聚会</a></div>
        <div class="sanjiao"></div>
        <div class="new-sub">
            <ul >
                <li><a href="javascript:;" xname="aLink" data-id="#pn" data-url="/party/NewParty.jsp">创建聚会</a></li>
                <li><a href="javascript:;" xname="aLink" data-id="#pc" data-url="/party/CurrentParty.jsp">当前聚会</a></li>
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
            <li><a href="javascript:;" xname="aLink" data-id="#cn" data-url="/circle/NewCircle.jsp">创建圈子</a></li>
            <li><a href="javascript:;" xname="aLink" data-id="#cl" data-url="/circle/CircleList.jsp">我的圈子</a></li>
        </ul>
        <div class="tiggle"></div>
        <div class="innertiggle"></div>
    </div>
    </div>

    <div class="menu">
        <div class="bt-name"><a href="javascript:;">帮助</a></div>
    </div><!--menu-->
</div>
  </body>
</html>

<script type="text/javascript">

wx.ready(function(){
  wx.hideOptionMenu();
  wx.showMenuItems({
    menuList: ['menuItem:share:appMessage','menuItem:share:timeline'] // 要显示的菜单项，所有menu项见附录3
  });

});
wx.error(function(res){
//    alert(res.errMsg);
});

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

</script>
<script src="./js/Main.js" language="JavaScript"></script>