var A,B,F,R,MaxP = 100,MaxR = -1;
var isPlus = true,isMulti = false;
var startCount = 1;
//计时
var timeCount = -1;
//计次
var totalCount = -1;
var nowCount = 0;
var nowTime = 0;

var intervalId;
var curRS;

var quickArray = [[2,25],[4,25],[8,25],[8,125]];

$(document).ready(function(){
  var m1 = getParam('m1');
  var m2 = getParam('m2');
  var time = getParam('time');  
  var count = getParam('count');
  var r = getParam('r');
  var title;
  
  if(m1 == "1"){
	title = "10以内加减法 - ";
    MaxP = 10;
    if(r == "1")
      MaxR = 10;
  }
  else if(m1 == "2"){
	title = "100以内加减法 - ";
    MaxP = 100;
    if(r == "1")
      MaxR = 100;
  }
  else if(m1 == "3"){
	title = "100以内加减乘除 - ";
	isMulti = true;
  }
  else if(m1 == "4"){
	title = "快速乘法 - ";
	isPlus = false;
  }
    
  if(m2 == "1"){
    if(time == "")
      time = "60";
    title += time+"秒练习";
    timeCount = parseInt(time);
  }
  else if(m2 == "2"){
    if(count == "")
      count = "20";
    title += count+"次练习";
    totalCount = parseInt(count);
  }
  else{
    title += "竞技";
    timeCount = parseInt(60);
  }
  $("#title").text(title);
  
  if(timeCount > 0){
    $("#sTimeDown").text(timeCount);
    $("#sCount").addClass("sr-only");
    nowTime = timeCount;
  }
  else{
    $("#sTime").addClass("sr-only");
    $("#sTotalCount").text(totalCount);
    $("#sNowCount").text(0);
  }
  
  $("[xname='btnNum']").click(function(){
    curRS.text(curRS.text() + $(this).text());
    if(curRS.text() == R){
      if(totalCount > 0 && nowCount >= totalCount){
        $("#btnF").click();
      }
      else{
	    $("#btnNext").click();
      }
    }
  });
  
  $("#btnDel").click(function(){
    var value = curRS.text();
    if(value.length > 0)
      curRS.text(value.substr(0,value.length-1));
  });

  $("#btnNext").click(function(){
    nowCount++;
    if(totalCount > 0){
      if(nowCount >= totalCount){
        $("#btnNext").addClass("sr-only");
        $("#btnF").removeClass("sr-only");
      }
      if(nowCount <= totalCount)
        $("#sNowCount").text(nowCount);
    }
    if(isMulti == true){
      var t = Math.random();
      if(t <= 0.25)
        plus();
      else if(t > 0.25 && t <= 0.5)  
        sub();
      else if(t > 0.5 && t <= 0.75)  
        multi();
      else
        div();        
    }
    else if(isPlus == true){
      var t = Math.random();
      if(t <= 0.5)
        plus();
      else  
        sub();
    }
    else{
      quickMulti();
    }
    
    $("#tbl tr:last").addClass("sr-only");
    var newTr = "<tr xname='tr'>"
        +"<td style='font-size:150%;width:50%' class='text-right'><var>"+A+"</var> "+F+" <var>"+B+"</var>  = </td>"
        +"<td><span xname='rs' style='font-size:150%'></span></td>"
        +"<td><span xname='tdr' class='sr-only' style='font-size:150%;color:red'>"+R+"</span></td></tr>";
    $("#tbl tr:last").after(newTr);
    curRS = $("#tbl tr:last").find("[xname='rs']");
  });
  
  $("#btnF").click(function(){
    $("#tblKeyboard").addClass("sr-only");
    if(totalCount > 0){
      $("#btnF").addClass("sr-only");
      clearInterval(intervalId);
    }
    var eCount = 0;
    $("#tbl").find("[xname='tr']").each(function () {
      var tdr = $(this).find("[xname='tdr']");
      if(totalCount <= 0 && $(this).find("[xname='rs']").text() == ""){
        nowCount--;
        $("#tbl tr:last").remove(); 
      }
      else if(tdr.text() != $(this).find("[xname='rs']").text()){
        tdr.removeClass("sr-only");
        eCount++;
      }
    });
  
    $("[xname='tr']").removeClass("sr-only");
    
    var r = 0;
    if(nowCount > 0)
      r = Math.round((nowCount - eCount)/nowCount*100);
    $("#pResult").text("您在"+nowTime+"秒内完成了"+nowCount+"道题目，准确率是：" + r + "%");
    $("#pRestart").removeClass("sr-only");
  });
  
  $("#btnRestart").click(function(){
    window.location.reload();
  });
  
  $("#btnBack").click(function(){
    window.location.href = "Main.jsp";
  });
  
  intervalId = setInterval("startCountDown()",1000);
});

function startCountDown(){
  startCount--;
  if(startCount > 0){
    $("#pStart").text(startCount);
  }
  else if(startCount == 0){
    $("#pStart").text("GO!");
  }
  else{
    clearInterval(intervalId);
    $("#pStart").addClass("sr-only");
    $("#btnNext").removeClass("sr-only");
    $("#btnNext").click();
    $("#tbl").removeClass("sr-only");
    $("#tblKeyboard").removeClass("sr-only");
    
    if(timeCount > 0)
      intervalId = setInterval("timeCountDown()",1000);
    else
      intervalId = setInterval("timeCountUp()",1000);
  }
}

function timeCountDown(){
  timeCount--;
  if(timeCount > 0)
    $("#sTimeDown").text(timeCount);
  else{
    $("#sTimeDown").text("0");
    clearInterval(intervalId);
    $("#btnNext").addClass("sr-only");
    alert("时间到！");
    $("#btnF").click();
  }
}

function timeCountUp(){
  nowTime++;
  $("#sTimeUp").text(nowTime); 
}

function getRandomNum(Min,Max){ 
    var Range = Max - Min; 
    var Rand = Math.random(); 
    return(Min + Math.round(Rand * Range)); 
} 

function plus(){
  F = "+";
  if(MaxR == -1){
    A = getRandomNum(1,MaxP);
    B = getRandomNum(0,MaxP);
    R = A + B;
  }
  else{
    R = getRandomNum(1,MaxR);
    A = getRandomNum(0,R);
    B = R - A;
  }
}

function sub(){
  F = "-";
  A = getRandomNum(1,MaxP);
  B = getRandomNum(0,A);
  R = A - B;
}

function multi(){
  F = "*";
  A = getRandomNum(1,MaxP);
  B = getRandomNum(1,10);
  R = A * B;
}

function div(){
  F = "/";
  B = getRandomNum(1,10);
  var t = Math.floor(MaxP/B);
  R = getRandomNum(1,t);
  A = R * B;
}

function quickMulti(){
  var t = getRandomNum(0,quickArray.length*3);
  if(t < quickArray.length){
	  A = quickArray[t][0];
	  B = quickArray[t][1];
  }
  else if(t < quickArray.length * 2){
    t = getRandomNum(11,20);
    A = t;
    B = t;
  }
  else{
    A = 11;
    B = getRandomNum(12,99);
  }
  R = A * B;
  F = "*";
}