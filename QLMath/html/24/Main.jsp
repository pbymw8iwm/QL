<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>24点</title>
	
    <meta http-equiv="keywords" content="">
    <meta http-equiv="description" content="">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <%@ include file="/CommonHead.jsp"%>
  </head>
  
  <body style="padding-top: 100px;">
    <div class="container" style="width:50%">
	  <p class="text-center">
	    <button type="button" class="btn btn-info btn-lg btn-block" xname="btn" xid="1">自由训练</button>
	    <br/>
	    <button type="button" class="btn btn-info btn-lg btn-block" xname="btn" xid="2">一分钟速算</button>
	<!--     <br/>
	    <button type="button" class="btn btn-info btn-lg btn-block disabled" xname="btn" xid="3" disabled>排名赛</button>
	    <br/>
	     -->
	  </p>
	</div>
  </body>
</html>

<script>

$(document).ready(function(){

  $("[xname='btn']").click(function(){
    window.location = "24.jsp?type="+$(this).attr("xid");
  });
  
});
</script>