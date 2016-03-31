<%@ page contentType="text/html;charset=UTF-8"%>

<div class="page-header-border">设置标签</div>
<div class="bd">
<%@ include file="/album/_label.jsp"%>	

	<style type="text/css">
	  dl { width:100%;margin:0;}
	  #labelSelected dd {float:left;text-align:center ;margin:10px;font-size: 20px;}
	</style>
	
<hr style="margin-top:7px;">

<dl id="labelSelected">
</dl>

		<div class="navbar-fixed-bottom text-center">
		   <button type="button" class="btn btn-success" id="btnLFinish">完成</button>&nbsp;&nbsp;&nbsp;&nbsp;
		   <button type="button" class="btn btn-default" id="btnLCancel">取消</button>
		</div>
</div>

<script type="text/javascript">

$(document).ready(function(){

  $(".labelItem").click(function(){
    if($("#labelSelected").length == 0)
      return;
    var label = $(this).text();
    var found = false;
    $("#labelSelected .labelSlt span").each(function(index,s){
      if($(this).text() == label){
        found = true;
        return;
      }  
    });
    if(found == true)
      return;
    var $newLabel = $("<dd class='labelSlt'><a class='weui_btn weui_btn_mini weui_btn_plain_primary'>"+label+"</a></dd>");
    $("#labelSelected").append($newLabel);
  }); 

  $("#labelSelected").on("click",".labelSlt",function(){
    $(this).remove();
  }); 
  
  $("#btnLCancel").click(function(){
    history.back();
  });

});

</script>
