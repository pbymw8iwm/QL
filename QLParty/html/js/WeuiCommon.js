function showToast(text){
	var $toast = $('#toast');
	if(text != null)
		$toast.text(text);
    if ($toast.css('display') != 'none') {
        return;
    }

    $toast.show();
    setTimeout(function () {
        $toast.hide();
    }, 2000);
}

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

var $ActionSheetName = $("#as_name");
var $ActionSheetArea = $("#as_area");
function showActionSheet(objEdit,saveFunc){
	if(saveFunc != null)
		$("#btnASSave").bind("click",saveFunc);
	var mask = $('#mask');
    var weuiActionsheet = $('#weui_actionsheet');
    weuiActionsheet.addClass('weui_actionsheet_toggle');
    mask.show().addClass('weui_fade_toggle').one('click',function () {
        _hideActionSheet(weuiActionsheet, mask);
    });
    $('#actionsheet_cancel').one('click',function () {
        _hideActionSheet(weuiActionsheet, mask);
    });
    weuiActionsheet.unbind('transitionend').unbind('webkitTransitionEnd');

	function _hideActionSheet(weuiActionsheet, mask) {
        weuiActionsheet.removeClass('weui_actionsheet_toggle');
        mask.removeClass('weui_fade_toggle');
        weuiActionsheet.on('transitionend', function () {
            mask.hide();
        }).on('webkitTransitionEnd', function () {
            mask.hide();
        });
		if(objEdit != null)
			  objEdit.remove();
		$("#btnASSave").unbind("click");
		mask.unbind("click");
        $('#actionsheet_cancel').unbind("click");
    }
}

function hideActionSheet(){
	$('#mask').click();
}
