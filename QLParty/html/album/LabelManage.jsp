<%@ page contentType="text/html;charset=UTF-8"%>

<%
String[][] labels = new String[][]{
         //{"节日","春节","元旦","元宵","清明","五一","端午","中秋","国庆","七夕","情人节","圣诞","感恩节"},
         {"人物","家人","同学","同事","朋友"},
         {"城市"},
         {"地点"},
         {"事件"},
         {"其它"}//,
        // {"时间","2016.","2015.","2014.","2013.","2012.","2011.","其它"}
         };
%> 
<style type="text/css">
      .aOP {font-size: 18px;color: #04BE02; margin-left:20px;}
      .aOPConfirm {font-size: 14px;color: #04BE02; margin-left:20px;}
      .toDel{text-decoration: line-through; color: gray;}
</style>
<div class="page-header-border">标签管理</div>
<div class="bd">

            <%for(String[] tmps : labels){
		     %>
        <div class="weui_panel">
		     <div class="weui_panel_hd"><%=tmps[0] %>
		       <a class="aOP aAdd" data-title="<%=tmps[0] %>" data-parent="<%=tmps[0] %>">+</a><a class="aOP aDel" data-parent="<%=tmps[0] %>">-</a>
		       <a class="aOPConfirm aToDel sr-only" data-parent="<%=tmps[0] %>">删除</a><a class="aOPConfirm aCancelDel sr-only" data-parent="<%=tmps[0] %>">取消</a>
		     </div>
             <div class="weui_panel_bd">
                <div class="weui_media_box weui_media_text itemGroup" id="div<%=tmps[0] %>">
                    <%for(int i=1;i<tmps.length;i++){ %>
				    <div class="labelItem lItem" data-parent="<%=tmps[0] %>"><%=tmps[i] %></div>
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
	  <div class="weui_cell_bd weui_cell_primary" id="ci_area">
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
   		var $parent = $("#div"+title);
   		for(var i =0;i<news.length;i++)
   		  $parent.append('<div class="labelItem lItem" data-parent="'+title+'">'+news[i]+'</div>');
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