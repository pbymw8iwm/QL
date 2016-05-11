<%@ page contentType="text/html;charset=UTF-8"%>

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
              <div class="right"><a href='javascript:;' class="weui_btn weui_btn_mini weui_btn_plain_primary" id="btnASSave">确定</a></div>
            </div>
            <div class="bd" id="as_area"></div>
            <div class="weui_actionsheet_action">
                <div class="weui_actionsheet_cell" id="actionsheet_cancel"><div class="weui_icon_cancel"></div></div>
            </div>
        </div>
    </div>
    
<script src="<%=request.getContextPath()%>/js/WeuiCommon.js" language="JavaScript"></script>