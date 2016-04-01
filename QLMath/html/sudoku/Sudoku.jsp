<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>数独</title>
	
    <meta http-equiv="keywords" content="数独">
    <meta http-equiv="description" content="数独">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <%@ include file="/CommonHead.jsp"%>
	<script src="Sudoku.js" language="JavaScript"></script>
    
    <style type="text/css">
	    #content { 
		    border:1px solid #ddd; 
		    height:364px; 
		    width:100%;
		    margin:0 auto;
		} 
	    #content dl { width:100%;border:1px solid #ddd;margin:0;font-size:18px;}
	    #content dl dd {width:11.1%; height:40px; border:1px solid #ddd; float:left;text-align:center ;padding-top: 7px;}
	    #content .b{background-color:lightyellow;}
	    #content .c{color:red;}
	    #content .same{background-color:lightgray;}
	    #content .current{background-color:lightblue;}
	    
	    .info { 
		    height:25px; 
		    width:100%;
		    margin:auto;
		} 
	    .info dl { width:100%;border:0;margin:0;font-size:14px;}
	    .info dl dd {width:11.1%; height:30px; border:0; float:left;text-align:center;}
	    
	    #answerContent { 
		    border:1px solid #ddd; 
		    height:274px; 
		    width:274px;
		    margin:0 auto;
		} 
	    #answerContent dl { width:100%;border:1px solid #ddd;margin:0;font-size:12px;}
	    #answerContent dl dd {width:11.1%; height:30px; border:1px solid #ddd; float:left;text-align:center ;padding-top: 5px;}
	    #answerContent .b{background-color:lightyellow;}
	    #answerContent .c{color:red;}
	    
	    table {border:1px}
	</style>
  </head>
  
  <body>
    <div class="container">
	    <div class="page-header">
		  <h3><span id="title"></span>
		    &nbsp;&nbsp;&nbsp;&nbsp;
		    <small>最高纪录：<span id="sRecord">无</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计时：<span id="sTimeUp">0</span></small>
		  </h3>
		</div>
		<div id="content">
		  <dl id="sdk">
			<dd i=1></dd>
			<dd i=2></dd>
			<dd i=3></dd>
			<dd i=4 class="b"></dd>
			<dd i=5 class="b"></dd>
			<dd i=6 class="b"></dd>
			<dd i=7></dd>
			<dd i=8></dd>
			<dd i=9></dd>
			
			<dd i=10></dd>
			<dd i=11></dd>
			<dd i=12></dd>
			<dd i=13 class="b"></dd>
			<dd i=14 class="b"></dd>
			<dd i=15 class="b"></dd>
			<dd i=16></dd>
			<dd i=17></dd>
			<dd i=18></dd>
			
			<dd i=19></dd>
			<dd i=20></dd>
			<dd i=21></dd>
			<dd i=22 class="b"></dd>
			<dd i=23 class="b"></dd>
			<dd i=24 class="b"></dd>
			<dd i=25></dd>
			<dd i=26></dd>
			<dd i=27></dd>
			
			<dd i=28 class="b"></dd>
			<dd i=29 class="b"></dd>
			<dd i=30 class="b"></dd>
			<dd i=31></dd>
			<dd i=32></dd>
			<dd i=33></dd>
			<dd i=34 class="b"></dd>
			<dd i=35 class="b"></dd>
			<dd i=36 class="b"></dd>
			
			<dd i=37 class="b"></dd>
			<dd i=38 class="b"></dd>
			<dd i=39 class="b"></dd>
			<dd i=40></dd>
			<dd i=41></dd>
			<dd i=42></dd>
			<dd i=43 class="b"></dd>
			<dd i=44 class="b"></dd>
			<dd i=45 class="b"></dd>
			
			<dd i=46 class="b"></dd>
			<dd i=47 class="b"></dd>
			<dd i=48 class="b"></dd>
			<dd i=49></dd>
			<dd i=50></dd>
			<dd i=51></dd>
			<dd i=52 class="b"></dd>
			<dd i=53 class="b"></dd>
			<dd i=54 class="b"></dd>
			
			<dd i=55></dd>
			<dd i=56></dd>
			<dd i=57></dd>
			<dd i=58 class="b"></dd>
			<dd i=59 class="b"></dd>
			<dd i=60 class="b"></dd>
			<dd i=61></dd>
			<dd i=62></dd>
			<dd i=63></dd>
			
			<dd i=64></dd>
			<dd i=65></dd>
			<dd i=66></dd>
			<dd i=67 class="b"></dd>
			<dd i=68 class="b"></dd>
			<dd i=69 class="b"></dd>
			<dd i=70></dd>
			<dd i=71></dd>
			<dd i=72></dd>
			
			<dd i=73></dd>
			<dd i=74></dd>
			<dd i=75></dd>
			<dd i=76 class="b"></dd>
			<dd i=77 class="b"></dd>
			<dd i=78 class="b"></dd>
			<dd i=79></dd>
			<dd i=80></dd>
			<dd i=81></dd>
		  </dl>
		</div>
		<br/>
		<div id="op" class="info">
		  <dl>
		    <dd><button type="button" class="btn btn-default" xname="btnNum">1</button></dd>
		    <dd><button type="button" class="btn btn-default" xname="btnNum">2</button></dd>
		    <dd><button type="button" class="btn btn-default" xname="btnNum">3</button></dd>
		    <dd><button type="button" class="btn btn-default" xname="btnNum">4</button></dd>
		    <dd><button type="button" class="btn btn-default" xname="btnNum">5</button></dd>
		    <dd><button type="button" class="btn btn-default" xname="btnNum">6</button></dd>
		    <dd><button type="button" class="btn btn-default" xname="btnNum">7</button></dd>
		    <dd><button type="button" class="btn btn-default" xname="btnNum">8</button></dd>
		    <dd><button type="button" class="btn btn-default" xname="btnNum">9</button></dd>
		  </dl>
		</div>
		<br/>
		<div id="info" class="info">
		  <dl>
		    <dd><span id="s1"></span></dd>
		    <dd><span id="s2"></span></dd>
		    <dd><span id="s3"></span></dd>
		    <dd><span id="s4"></span></dd>
		    <dd><span id="s5"></span></dd>
		    <dd><span id="s6"></span></dd>
		    <dd><span id="s7"></span></dd>
		    <dd><span id="s8"></span></dd>
		    <dd><span id="s9"></span></dd>
		  </dl>
		</div>
		<p class="text-center">
		  <button type="button" class="btn btn-link" id="btnBack">回退</button>
		  <button type="button" class="btn btn-link" id="btnClear">清空</button>
		  <button type="button" class="btn btn-default" id="btnNext">下一题</button>
		  <button type="button" class="btn btn-link" data-toggle="modal" data-target="#modalAnswer">参考答案</button>
		</p>
		
		<div class="modal fade" id="modalAnswer" tabindex="-1" role="dialog" aria-labelledby="divLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		        <h4 class="modal-title" id="divLabel">参考答案</h4>
		      </div>
		      <div class="modal-body" id="divBody">
		      	<div id="answerContent">
				  <dl id="dAnswer">
					<dd a=1></dd>
					<dd a=2></dd>
					<dd a=3></dd>
					<dd a=4 class="b"></dd>
					<dd a=5 class="b"></dd>
					<dd a=6 class="b"></dd>
					<dd a=7></dd>
					<dd a=8></dd>
					<dd a=9></dd>
					
					<dd a=10></dd>
					<dd a=11></dd>
					<dd a=12></dd>
					<dd a=13 class="b"></dd>
					<dd a=14 class="b"></dd>
					<dd a=15 class="b"></dd>
					<dd a=16></dd>
					<dd a=17></dd>
					<dd a=18></dd>
					
					<dd a=19></dd>
					<dd a=20></dd>
					<dd a=21></dd>
					<dd a=22 class="b"></dd>
					<dd a=23 class="b"></dd>
					<dd a=24 class="b"></dd>
					<dd a=25></dd>
					<dd a=26></dd>
					<dd a=27></dd>
					
					<dd a=28 class="b"></dd>
					<dd a=29 class="b"></dd>
					<dd a=30 class="b"></dd>
					<dd a=31></dd>
					<dd a=32></dd>
					<dd a=33></dd>
					<dd a=34 class="b"></dd>
					<dd a=35 class="b"></dd>
					<dd a=36 class="b"></dd>
					
					<dd a=37 class="b"></dd>
					<dd a=38 class="b"></dd>
					<dd a=39 class="b"></dd>
					<dd a=40></dd>
					<dd a=41></dd>
					<dd a=42></dd>
					<dd a=43 class="b"></dd>
					<dd a=44 class="b"></dd>
					<dd a=45 class="b"></dd>
					
					<dd a=46 class="b"></dd>
					<dd a=47 class="b"></dd>
					<dd a=48 class="b"></dd>
					<dd a=49></dd>
					<dd a=50></dd>
					<dd a=51></dd>
					<dd a=52 class="b"></dd>
					<dd a=53 class="b"></dd>
					<dd a=54 class="b"></dd>
					
					<dd a=55></dd>
					<dd a=56></dd>
					<dd a=57></dd>
					<dd a=58 class="b"></dd>
					<dd a=59 class="b"></dd>
					<dd a=60 class="b"></dd>
					<dd a=61></dd>
					<dd a=62></dd>
					<dd a=63></dd>
					
					<dd a=64></dd>
					<dd a=65></dd>
					<dd a=66></dd>
					<dd a=67 class="b"></dd>
					<dd a=68 class="b"></dd>
					<dd a=69 class="b"></dd>
					<dd a=70></dd>
					<dd a=71></dd>
					<dd a=72></dd>
					
					<dd a=73></dd>
					<dd a=74></dd>
					<dd a=75></dd>
					<dd a=76 class="b"></dd>
					<dd a=77 class="b"></dd>
					<dd a=78 class="b"></dd>
					<dd a=79></dd>
					<dd a=80></dd>
					<dd a=81></dd>
				  </dl>
				</div>
		      </div>
		    </div>
		  </div>
		</div>
	</div>
  </body>
</html>

<script>

</script>