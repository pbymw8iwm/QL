
var _isGo = false;
var _pageStack = [];
$("[xname='aLink']").click(function(){
  gotoPage($(this).data("url"),$(this).data("id"));
});

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

function showTooltips(text){
	var tooltips = $("#tooltip");
	tooltips.text(text);
    if (tooltips.css('display') != 'none') {
        return;
    }
    tooltips.show();
    setTimeout(function () {
        tooltips.hide();
    }, 2000);
}

function showDialogConfirm(info,func){
	var $dialog = $('#dialogConfirm');
	//$dialog.find(".weui_dialog_title").text(title);
	$dialog.find(".weui_dialog_bd").html(info);
    $dialog.show();
    $dialog.find('.primary').one('click', func);
    $dialog.find('.weui_btn_dialog').one('click', function () {
        $dialog.hide();
    });
}

function showDialogInfo(info){
	var $dialog = $('#dialogInfo');
	//$dialog.find(".weui_dialog_title").text(title);
	$dialog.find(".weui_dialog_bd").html(info);
    $dialog.show();
    $dialog.find('.weui_btn_dialog').one('click', function () {
        $dialog.hide();
    });
}

function toTop(){
	document.documentElement.scrollTop = document.body.scrollTop =0;
}

function init(){
	var hash = location.hash;
	var url = null;
	if(hash == "#pi")
		url = "/party/PartyInfo.jsp?partyId="+getParam("partyId");
	else if(hash == "#ci")
		url = "/circle/CircleInfo.jsp?cId="+getParam("cId");
	else{
		url = "/party/CurrentParty.jsp";
		hash = "#pc";
		location.hash = hash;
	}
	if(url != null)
		gotoPage(url,hash);
		

	//$("#container").load(_gModuleName+"/circle/CircleInfo.jsp?cId=11");
}

init();