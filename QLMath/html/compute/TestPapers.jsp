<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title></title>
	
    <meta http-equiv="keywords" content="">
    <meta http-equiv="description" content="">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <%@ include file="/CommonHead.jsp"%>
	<script src="Compute.js" language="JavaScript"></script>
  </head>
  
  <body>
    <div class="container">
	    <div class="page-header">
		  <h3><span id="title"></span>
		    &nbsp;&nbsp;&nbsp;&nbsp;
		    <small id="sTime">倒计时：<span id="sTimeDown"></span></small>
		    <small id="sCount"><span id="sNowCount"></span> / <span id="sTotalCount"></span>&nbsp;&nbsp;&nbsp;&nbsp;计时：<span id="sTimeUp">0</span></small>
		  </h3>
		</div>
	  <p id="pStart" class="text-center" style="font-size:50px;color:red">准备</p>	
	  <table class="table sr-only" id="tbl">
	    <tr></tr>
	  </table>
	  <table id="tblKeyboard" class="table center-block sr-only" style="width:100px">
	    <tr>
	      <td><button type="button" class="btn btn-default btn-lg" xname="btnNum">1</button></td>
	      <td><button type="button" class="btn btn-default btn-lg" xname="btnNum">2</button></td>
	      <td><button type="button" class="btn btn-default btn-lg" xname="btnNum">3</button></td>
	    </tr>
	    <tr>
	      <td><button type="button" class="btn btn-default btn-lg" xname="btnNum">4</button></td>
	      <td><button type="button" class="btn btn-default btn-lg" xname="btnNum">5</button></td>
	      <td><button type="button" class="btn btn-default btn-lg" xname="btnNum">6</button></td>
	    </tr>
	    <tr>
	      <td><button type="button" class="btn btn-default btn-lg" xname="btnNum">7</button></td>
	      <td><button type="button" class="btn btn-default btn-lg" xname="btnNum">8</button></td>
	      <td><button type="button" class="btn btn-default btn-lg" xname="btnNum">9</button></td>
	    </tr>
	    <tr>
	      <td></td>
	      <td><button type="button" class="btn btn-default btn-lg" xname="btnNum">0</button></td>
	      <td><button type="button" class="btn btn-default btn-lg" id="btnDel">C</button></td>
	    </tr>
	  </table>
	  <p class="text-right"><button type="button" id="btnNext" class="sr-only">下一题</button></p>
	  <p class="text-right"><button type="button" id="btnF" class="sr-only">完成</button></p>
	  <p class="text-center" style="font-size:30px;color:green" id="pResult"></p>
	  <p class="text-center sr-only" id="pRestart">
	    <button type="button" class="btn btn-default" id="btnRestart">再来一次</button> 
	    <button type="button" class="btn btn-default" id="btnBack">返&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;回</button></p>
	</div>
  </body>
</html>

<script>

</script>