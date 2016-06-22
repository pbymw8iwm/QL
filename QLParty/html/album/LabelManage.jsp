<%@page import="com.ql.album.ivalues.IAlbumLabelValue"%>
<%@page import="com.ql.album.web.AlbumAction"%>
<%@page import="com.ql.ivalues.ICfStaticDataValue"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<%
ICfStaticDataValue[] groups = AlbumAction.getLabelGroups();
IAlbumLabelValue[] labels = AlbumAction.getLabels();
%> 
<style type="text/css">
      .aOP {font-size: 18px;color: #04BE02; margin-left:20px;}
      .aOPConfirm {font-size: 14px;color: #04BE02; margin-left:20px;}
      .toDel{text-decoration: line-through; color: gray;}
</style>
<div class="page-header-border">标签管理</div>
<div class="bd">

            <%for(ICfStaticDataValue group : groups){
                if(group.getState() == 1)
                  continue;
                long groupId = Long.parseLong(group.getCodevalue());
		     %>
        <div class="weui_panel">
		     <div class="weui_panel_hd"><%=group.getCodename() %>
		       <a class="aOP aAdd" data-title="<%=group.getCodename() %>" data-parent="<%=groupId %>">+</a><a class="aOP aDel" data-parent="<%=groupId %>">-</a>
		       <a class="aOPConfirm aToDel sr-only" data-parent="<%=groupId %>">删除</a><a class="aOPConfirm aCancelDel sr-only" data-parent="<%=groupId %>">取消</a>
		     </div>
             <div class="weui_panel_bd">
                <div class="weui_media_box weui_media_text itemGroup" id="div<%=groupId %>">
                    <%for(IAlbumLabelValue label : labels){
                        if(label.getGroupid() != groupId)
                          continue;
                     %>
				    <div class="labelItem lItem" data-parent="<%=groupId %>"><%=label.getLabelname() %></div>
				    <%} %>
                </div>
            </div>
        </div>
		    <%} %>
</div>

<script type="text/html" id="lmas">
<div class="weui_cells">
  <form role="form" class="form-horizontal" id="frmLM" name="frmLM">
    <div class="weui_cell">
	  <div class="weui_cell_bd weui_cell_primary">
		<input class='weui_input' type='text' name='txtLMnew' id='txtLMnew' placeholder="多个标签请用逗号(,)分隔"/>
      </div>
	</div>
  </form>
</div>
</script>    

<script type="text/javascript">
var ToDelete = null;
$(document).ready(function(){
  $(".aAdd").click(function(){
    var title = $(this).data("title");
    var groupId = $(this).data("parent");
    $ActionSheetName.text(title);
    var $asAreaSub = $($("#lmas").html());
    $ActionSheetArea.append($asAreaSub);
    var $txtNew = $("#txtLMnew");
    $txtNew.focus();
    showActionSheet($asAreaSub,function(){
        var value = $txtNew.val();
   		if(value != undefined && value == ""){
   		  $txtNew.focus();
   		  return;
   		}
   		var news = value.split(/[,，]/);
   		var $parent = $("#div"+groupId);
   		for(var i =0;i<news.length;i++)
   		  $parent.append('<div class="labelItem lItem" data-parent="'+groupId+'">'+news[i]+'</div>');
   		hideActionSheet();
   		$txtNew.val("");
    });
  });
  
  //TODO 键盘上的前往按钮会使页面跳转，怎么处理呢？
  $(".aAdd").keypress(function(e){
  });
  
  $(".aDel").click(function(){
    if(ToDelete != null){
      $(".aOP[data-parent='"+ToDelete+"']").each(function (){$(this).removeClass("sr-only");});
      $(".aOPConfirm[data-parent='"+ToDelete+"']").each(function (){$(this).addClass("sr-only");});
      $(".toDel[data-parent='"+ToDelete+"']").each(function (){$(this).removeClass("toDel");});
    }
    var parent = $(this).data("parent");
    $(".aOP[data-parent='"+parent+"']").each(function (){$(this).addClass("sr-only");});
    $(".aOPConfirm[data-parent='"+parent+"']").each(function (){$(this).removeClass("sr-only");});
    ToDelete = parent;
  });
  
  $(".aToDel").click(function(){
    var parent = $(this).data("parent");
    $(".aOP[data-parent='"+parent+"']").each(function (){$(this).removeClass("sr-only");});
    $(".aOPConfirm[data-parent='"+parent+"']").each(function (){$(this).addClass("sr-only");});
    $(".toDel[data-parent='"+parent+"']").each(function (){$(this).remove();});
    ToDelete = null;
  });
  
  $(".aCancelDel").click(function(){
    var parent = $(this).data("parent");
    $(".aOP[data-parent='"+parent+"']").each(function (){$(this).removeClass("sr-only");});
    $(".aOPConfirm[data-parent='"+parent+"']").each(function (){$(this).addClass("sr-only");});
    $(".toDel[data-parent='"+parent+"']").each(function (){$(this).removeClass("toDel");});
    ToDelete = null;
  });
  
  $(".itemGroup").on("click",".lItem", function(){
    var parent = $(this).data("parent");
    if(parent == ToDelete){
      if($(this).hasClass("toDel") == false)
        $(this).addClass("toDel");
      else
        $(this).removeClass("toDel");
    }
  });
  
  
});
</script>