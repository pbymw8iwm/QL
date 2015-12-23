
var intervalId = null;
var nowTime = 0;
var isCountDown = false;
var A,B,C,D;
var xt = "3";
var curTr = null;
var totalCount = 0;
var lastResult = null;

$(document).ready(function(){
  var type = getParam('type');
  if(type == "1"){
    $("#title").text("自由训练");
  }
  else if(type == "2"){
    $("#title").text("一分钟速算");
    $("#sTime").text("倒计时：");
    $("#sTimeUp").text("60");
    nowTime = 60;
    isCountDown = true;
  }
  else if(type == "3"){
    $("#title").text("排名赛");
  }
  
  $("#btnRestart").click(function(){
	  $("#btnRestart").addClass("sr-only");
	  $("#btnNext").removeClass("sr-only");
	  $("#sTimeUp").text("60");
	  nowTime = 60;
	  totalCount = 0;
	  $("#btnNext").click();
	  intervalId = setInterval("timeCountDown()",1000);
  });
  
  //下一题  
  $("#btnNext").click(function(){
	    if(isCountDown == false){
	    	if(intervalId != null)
	            clearInterval(intervalId);
	          $("#sTimeUp").text("0");
	    }
        
    	$("#divSR").addClass("sr-only");
        
		A = getRandomNum(1,10);
		B = getRandomNum(1,10);
		C = getRandomNum(1,10);
		D = getRandomNum(1,10);
		
		/*
		//不重复
		while(B == A){
		  B = getRandomNum(1,10);
		}
		C = getRandomNum(1,10);
		while(C == A || C == B){
		  C = getRandomNum(1,10);
		}
		D = getRandomNum(1,10);
		while(D == A || D == B || D == C){
		  D = getRandomNum(1,10);
		}
		*/
		
		$("#btn1").text(A); 
		$("#btn2").text(B); 
		$("#btn3").text(C); 
		$("#btn4").text(D); 
  
        $("#btnDel").click();
        $(this).blur();
        $("[xname='btnOp']").removeClass("disabled");
        $("#btnDel").removeClass("disabled");
        
        if(isCountDown == false){
            nowTime = 0;
            intervalId = setInterval("timeCountUp()",1000);
        }
  });
  
  //运算符
  $("[xname='btnOp']").click(function(){
    var t = $(this).attr("xt");
    if(t == xt && lastResult == null)
      return;
    if(lastResult != null)
    	lastResult.click();
    xt = t;  
    var td = curTr.find("[xname='tdExp']");
    td.text(td.text() + $(this).text() + " ");
  });
  
  //数字
  $("body").on("click","[xname='btnNum']",function(){
    var t = $(this).attr("xt");
    if(t == xt)
      return;
    xt = t;  
    
    $(this).addClass("disabled");
    
    if(curTr == null){
      var newTr = "<tr>"
        +"<td style='font-size:150%;width:30%' xname='tdExp'>"+$(this).text()+" </td>"
        +"<td style='font-size:150%;width:30%' xname='tdR'><button type='button' class='btn btn-default sr-only' xname='btnNum' xt='1'></button></td>"
        +"<td style='font-size:150%;color:red' xname='tdI'></td></tr>";
      $("#tblProcess tr:last").after(newTr);
      curTr = $("#tblProcess tr:last");
      lastResult = null;
    }
    else{
      var td = curTr.find("[xname='tdExp']");
      td.text(td.text() + $(this).text() + " ");
      var value = td.text().replace(/[ ]/g,"");
      var tmp = eval(value);
      if(parseInt(tmp) == tmp)
        value = tmp;
      
      if($("[xname='btnNum'].disabled").length == 6){
    	  lastResult = null;
	      var tdR = curTr.find("[xname='tdR']");
	      tdR.text(value);
	      var tdI = curTr.find("[xname='tdI']");
	      if(value == 24){
	        tdI.text("正确！");
	        if(isCountDown == true){
	        	totalCount++;
	        	setTimeout(function () { 
	        		$("#btnNext").click();
	            }, 800);
	        }
	        else{
			    clearInterval(intervalId);
			    intervalId = null;
	        }	
	      }
	      else
	        tdI.text("错误！");	        
      }
      else{
	      var btnNum = curTr.find("[xname='btnNum']");
	      btnNum.text(value);
	      btnNum.removeClass("sr-only");
	      curTr = null;
	      lastResult = btnNum;
      }
      xt = "3";
    }
    
  });
  
  //清除
  $("#btnDel").click(function(){
    xt = "3";
    curTr = null;
    $("#tblProcess tr").empty();
    $("[xname='btnNum']").removeClass("disabled");
  });
  
  //参考答案
  $("[xname='btnShow']").click(function(){
    if(deal() == false)
      $("#divCR").text("无解");
    $("#divSR").removeClass("sr-only");
  });
  
  $("#btnNext").click();
  
  if(nowTime > 0)
  	intervalId = setInterval("timeCountDown()",1000);
});

function timeCountUp(){
  nowTime++;
  $("#sTimeUp").text(nowTime); 
}

function timeCountDown(){
	nowTime--;
	  if(nowTime > 0)
		  $("#sTimeUp").text(nowTime); 
	  else{
	    $("#sTimeUp").text("0");
	    clearInterval(intervalId);
	    $("#btnNext").addClass("sr-only");
	    $("#btnRestart").removeClass("sr-only");
	    alert("时间到！\n您本次共完成了"+totalCount+"道题目");
	  }
}

function getRandomNum(Min,Max){ 
    var Range = Max - Min; 
    var Rand = Math.random(); 
    return(Min + Math.round(Rand * Range)); 
} 

function compare(a,b){
  if(a > b)
    return -1;
  else if(a < b)
    return 1;
  else
    return 0;
}

function deal(){
  var arr = [A,B,C,D];
  arr.sort(compare);
  
  var r = check(arr);
  if(r == true)
    return true;
  if(arr[0] != arr[1]){
    var newArr = [arr[1],arr[0],arr[2],arr[3]];
    r = check(newArr);
	if(r == true)
	  return true;
  }
  if(arr[1] != arr[2]){
    var newArr = [arr[2],arr[1],arr[0],arr[3]];
    r = check(newArr);
	if(r == true)
	  return true;
  }
  if(arr[2] != arr[3]){
    var newArr = [arr[3],arr[1],arr[2],arr[0]];
    r = check(newArr);
	if(r == true)
	  return true;
  }
  return false;
}

function check(arr){
  var r = check24(arr[0],arr[1],arr[2],arr[3]);
  if(r == true)
    return true;
  if(arr[1] != arr[2]){
	  r = check24(arr[0],arr[2],arr[1],arr[3]);
	  if(r == true)
	    return true;
  }
  r = check24(arr[0],arr[2],arr[3],arr[1]);
  if(r == true)
    return true;
  r = check24(arr[0],arr[3],arr[1],arr[2]);
  if(r == true)
    return true;
  r = check24(arr[0],arr[3],arr[2],arr[1]);
  return r;
}

function check24(a,b,c,d){
  for(var i=0;i<4;i++){
    var op1 = getOp(i);
    for(var j=0;j<4;j++){
      var op2 = getOp(j);
      var s = _check(_calculate(a,b,op1),_calculate(c,d,op2));
      if(s != ""){
        var r = "( " + a + " " + op1 + " " + b + " ) " + s + " ( " + c + " " + op2 + " " + d + " ) ";
        $("#divCR").text(r);
        return true;
      }
      s = _check(a,_calculate(_calculate(b,c,op1),d,op2));
      if(s != ""){
        var r = a + " " + s + " (( " + b + " " + op1 + " " + c + " ) " + op2 + " " + d + " ) ";
        $("#divCR").text(r);
        return true;
      }
      s = _check(a,_calculate(b,_calculate(c,d,op1),op2));
      if(s != ""){
        var r = a + " " + s + " ( " + b + " " + op2 + " ( " + c + " " + op1 + " " + d + " )) ";
        $("#divCR").text(r);
        return true;
      }
      s = _check(_calculate(_calculate(a,b,op1),c,op2),d);
      if(s != ""){
        var r = "(( " + a + " " + op1 + " " + b + " ) " + op2 + " " + c + " ) " + s + " " + d;
        $("#divCR").text(r);
        return true;
      }
      s = _check(_calculate(a,_calculate(b,c,op1),op2),d);
      if(s != ""){
        var r = "( " + a + " " + op2 + " ( " + b + " " + op1 + " " + c + " )) " + s + " " + d;
        $("#divCR").text(r);
        return true;
      }
    }
  }
  return false;
}

function getOp(opIndex){
  if(opIndex == 0)
    return "+";
  if(opIndex == 1)
    return "-";
  if(opIndex == 2)
    return "*";
  if(opIndex == 3)
    return "/";
}

function _calculate(a,b,op){
  return eval(a+op+"("+b+")");
}

function _check(a,b){
  if(a + b == 24)
    return "+";
  if(a - b == 24)
    return "-";
  if(a * b == 24)
    return "*";
  if(a / b == 24)
    return "/";
  return "";
}