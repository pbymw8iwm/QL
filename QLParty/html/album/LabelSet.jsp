<%@ page contentType="text/html;charset=UTF-8"%>

<div class="page-header-border">设置标签</div>
<div class="bd" id="divLS">
<%@ include file="/album/_label.jsp"%>	

	<style type="text/css">
	  dl { width:100%;margin:0;}
	  #labelSelected dd {float:left;text-align:center ;margin:10px;font-size: 20px;}
	</style>
	
<dl id="labelSelected">
</dl>

		<div class="navbar-fixed-bottom text-center">
		   <button type="button" class="btn btn-success" id="btnLFinish">完成</button>&nbsp;&nbsp;&nbsp;&nbsp;
		   <button type="button" class="btn btn-default" id="btnLCancel">取消</button>
		</div>
</div>

<script type="text/javascript">

$(document).ready(function(){

  $("#divLS .labelItem").click(function(){
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
  
  $("#btnLFinish").click(function(){
    history.back();
  });
  
  $("#btnLCancel").click(function(){
    history.back();
  });

});

</script>
