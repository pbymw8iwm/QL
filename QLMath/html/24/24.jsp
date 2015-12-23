<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>24点</title>
	
    <meta http-equiv="keywords" content="24点">
    <meta http-equiv="description" content="24点">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <%@ include file="/CommonHead.jsp"%>
	<script src="24.js" language="JavaScript"></script>
  </head>
  
  <body>
    <div class="container">
	    <div class="page-header">
		  <h3>24点 - <span id="title"></span>
		    &nbsp;&nbsp;&nbsp;&nbsp;
		    <small><span id="sTime">计时：</span><span id="sTimeUp">0</span></small>
		  </h3>
		</div>
		
		<div>
		  <button type="button" class="btn btn-default btn-lg" id="btn1" xname="btnNum" xt="1"></button>
		  <button type="button" class="btn btn-default btn-lg" id="btn2" xname="btnNum" xt="1"></button>
		  <button type="button" class="btn btn-default btn-lg" id="btn3" xname="btnNum" xt="1"></button>
		  <button type="button" class="btn btn-default btn-lg" id="btn4" xname="btnNum" xt="1"></button>
		</div>
		<br/>
		<div>
		  <button type="button" class="btn btn-default" xname="btnOp" xt="3">+</button>
		  <button type="button" class="btn btn-default" xname="btnOp" xt="3">-</button>
		  <button type="button" class="btn btn-default" xname="btnOp" xt="3">*</button>
		  <button type="button" class="btn btn-default" xname="btnOp" xt="3">/</button>
		  <button type="button" class="btn btn-link" id="btnDel">清除</button>
		</div>
		<br/>
		<table id="tblProcess" class="table">
		  <tr></tr>
		</table>
		<br/>
		<div class="panel panel-default sr-only" id="divSR">
		  <div class="panel-heading">参考答案</div>
		  <div class="panel-body" style="font-size:150%" id="divCR"></div>
		</div>
		<br/>
		<div>
		  <button type="button" class="btn btn-link" xname="btnShow">参考答案</button>
		  <button type="button" class="btn btn-default" id="btnNext">下一题</button>
	      <button type="button" class="btn btn-default sr-only" id="btnRestart">再来一次</button> 
		  <button type="button" class="btn btn-link" xname="btnShow">本题无解</button>
		</div>
	</div>
  </body>
</html>

<script>

</script>