<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>计算</title>
	
    <meta http-equiv="keywords" content="">
    <meta http-equiv="description" content="">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <%@ include file="/CommonHead.jsp"%>
  </head>
  
  <body style="padding-top: 5px;">
    <div class="container">
	  <div class="row">
		<div class="col-md-4"></div>
		<div class="col-md-4">
		  <div class="list-group">
			  <a href="#" class="list-group-item" xname="a" xt="1">
			    <h4 class="list-group-item-heading"><span id="s1" class="glyphicon glyphicon-ok"></span>&nbsp;&nbsp;10以内加减法</h4>
    			<p class="list-group-item-text">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label><input type="checkbox" id="r1"> 结果在10以内</label></p>
			  </a>
			  <a href="#" class="list-group-item" xname="a" xt="2">
			    <h4 class="list-group-item-heading"><span id="s2" class="glyphicon glyphicon-ok sr-only"></span>&nbsp;&nbsp;100以内加减法</h4>
    			<p class="list-group-item-text">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label><input type="checkbox" id="r2"> 结果在100以内</label></p>
    		  </a>
			  <a href="#" class="list-group-item" xname="a" xt="3">
			    <h4 class="list-group-item-heading"><span id="s3" class="glyphicon glyphicon-ok sr-only"></span>&nbsp;&nbsp;100以内加减乘除</h4>
    			<p class="list-group-item-text">&nbsp;</p>
    		  </a>
			  <a href="#" class="list-group-item" xname="a" xt="4">
			    <h4 class="list-group-item-heading"><span id="s4" class="glyphicon glyphicon-ok sr-only"></span>&nbsp;&nbsp;快速乘法</h4>
    			<p class="list-group-item-text">&nbsp;</p>
    		  </a>
		  </div>
		  <form class="form-horizontal">
		  <table class="table">
		      <tr>
		        <td style="width:60px"><b>练习</b></td>
		        <td style="width:100px"><label><input type="radio" name="optionsRadios" id="optionsRadios1" value="1" checked>  定时</label></td>
		        <td class="form-group"><div class="col-xs-8"><input type="number" class="form-control" id="time" xname="number" value="60"></div>秒</td>
		      </tr>
		      <tr>
		        <td></td>
		        <td><label><input type="radio" name="optionsRadios" id="optionsRadios2" value="2"> 定量</label></td>
		        <td class="form-group"><div class="col-xs-8"><input type="number" class="form-control" id="count" xname="number" value="20"></div>题</td>
		      </tr>
		      <tr>
		        <td><b>竞赛</b></td>
		        <td><label><input type="radio" name="optionsRadios" id="optionsRadios3" value="3" class="disabled" disabled>  同场竞技</label></td>
		        <td></td>
		      </tr>
		      <tr>
		        <td></td>
		        <td><label><input type="radio" name="optionsRadios" id="optionsRadios4" value="4" class="disabled" disabled> 排名赛</label></td>
		        <td></td>
		      </tr>		      
		  </table>
		  </form>
		  <p class="text-center"><button type="button" id="btnStart">开始</button></p>
		</div>
		<div class="col-md-4"></div>
	  </div>
	</div>
  </body>
</html>

<script>
var curItem = "1";
$(document).ready(function(){

  //只能输入数字
  $("[xname='number']").keyup(function(){     
        var tmptxt=$(this).val();     
        $(this).val(tmptxt.replace(/\D|^0/g,''));     
    }).bind("paste",function(){     
        var tmptxt=$(this).val();     
        $(this).val(tmptxt.replace(/\D|^0/g,''));     
    }).css("ime-mode", "disabled");
  

  $("[xname='a']").click(function(){
    var newItem = $(this).attr("xt");
    if(curItem == newItem)
      return;
    if(curItem != "")
      $("#s"+curItem).addClass("sr-only");
    $("#s"+newItem).removeClass("sr-only");
    curItem = newItem;
  });
  
  $("#btnStart").click(function(){
    var m = $("input[name='optionsRadios']:checked").val();
    var r = 0;
    var time = -1;
    var count = -1;
    if(m == 1)
      time = $("#time").val();
    else if(m == 2)
      count = $("#count").val();
    if(curItem == "1" && $("#r1:checked").val() == "on")
      r = 1;
    else if(curItem == "2" && $("#r2:checked").val() == "on")
      r = 1;
    var param = "m1="+curItem+"&m2="+m+"&time="+time+"&count="+count+"&r="+r;
    //alert(param);
    window.location = "TestPapers.jsp?"+param;
  });
});
</script>