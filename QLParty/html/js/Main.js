//公众号js相关
wx.ready(function(){
  wx.hideOptionMenu();
  wx.showMenuItems({
    menuList: ['menuItem:share:appMessage','menuItem:share:timeline'] // 要显示的菜单项，所有menu项见附录3
  });

});
wx.error(function(res){
//    alert(res.errMsg);
});

//界面切换相关
var _isGo = false;
var _pageStack = [];

function gotoPage(url,id){
	//如果是当前页面，原来不处理，现在就当是刷新
	for(var i=_pageStack.length-1;i>=0;i--){
		  if(_pageStack[i].id == id && _pageStack[i].div != null){
			  _pageStack[i].div.remove();
			  _pageStack[i].div = null;
			  break;
		  }
	  }
	  var div;
	  if(url != null){
		  div = $("<div class='page'></div>");
		  div.load(_gModuleName+url);
	  }
	  else{
		  div = $($(id).html());
	  }
	  div.addClass("slideIn");
    $("#container").append(div);
    if(location.hash == id){
    	_pageStack[_pageStack.length-1].div = div;
    }
    else{
    	location.hash = id;
		_isGo = true;
	    _pageStack.push({id:id,div:div});	
    }
    toTop();
}

$(window).on('hashchange', function (e) {
    var _isBack = !_isGo;
    _isGo = false;
    if (!_isBack) {
        return;
    }
    //后退
    if(_pageStack.length > 0){
      var p = _pageStack.pop();
      if(p.div != null)
        p.div.addClass("slideOut").on('animationend', function () {
                p.div.remove();
            }).on('webkitAnimationEnd', function () {
            	p.div.remove();
            });
    }
    toTop();
});

function toTop(){
	document.documentElement.scrollTop = document.body.scrollTop =0;
}
