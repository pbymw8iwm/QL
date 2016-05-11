<%@ page contentType="text/html;charset=UTF-8"%>

<%
String[][] labels = new String[][]{
         {"节日","春节","元旦","元宵","清明","五一","端午","中秋","国庆","七夕","情人节","圣诞","感恩节"},
         {"人物","家人","同学","同事","朋友"},
         {"城市"},
         {"地点"},
         {"事件"},
         {"其它"},
         {"时间","2016.","2015.","2014.","2013.","2012.","2011.","其它"}
         };
%> 
<style type="text/css">
      .labelGroup {white-space:nowrap;overflow-x:auto;font-size: 16px;margin-top:5px;}
	  .labelTitle {display:inline;text-align:center;margin:10px;color:green;}
	  .labelItem {display:inline;text-align:center;margin:10px;}
	</style>
<div style="padding-right:10px;">	
    <%for(String[] tmps : labels){
        if(tmps.length <= 1)
          continue;
     %>
    <div class="labelGroup">
	    <div class="labelTitle"><%=tmps[0] %></div>
	    <%for(int i=1;i<tmps.length;i++){ %>
	    <div class="labelItem"><%=tmps[i] %></div>
	    <%} %>
    </div>
    <%} %>
  <div class="text-right"><span class="glyphicon glyphicon-cog lconfig" aria-hidden="true"></span></div>
</div>
<div style="border-bottom: 2px solid #eee;"></div>

<script type="text/javascript">
$(document).ready(function(){
  $(".lconfig").click(function(){
    //alert(1);  多次 TODO
    gotoPage("/album/LabelManage.jsp","#LM");
  });
});
</script>