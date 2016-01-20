<%
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", -10);
%>

<%@page import="com.ql.wechat.WechatUtils"%>
<%@page import="com.ql.wechat.WechatCommons"%>

<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"> </script>

<%
String[] s = WechatUtils.getJsSignature(request);
%>

<script language="javascript">

wx.config({
      debug: false,
      appId: '<%=WechatCommons.AppId%>',
      timestamp: <%=s[0] %>,
      nonceStr: '<%=s[1] %>',
      signature: '<%=s[2] %>',
      jsApiList: [
        'hideOptionMenu',
        'showMenuItems',
        'onMenuShareAppMessage',
        'onMenuShareTimeline',
        'chooseImage',
        'uploadImage',
        'previewImage'
      ]
  });
 
</script>