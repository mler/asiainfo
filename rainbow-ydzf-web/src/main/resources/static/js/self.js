$(function(){
		//导航点击效果
		$(".main_nav .nav-pills li").click(function(){
			$(this).addClass("active").siblings().removeClass("active");
		});
		//鼠标移入显示二级菜单
  		$(".main_nav .nav-pills li").mouseover(function(){
  			var id=$(this).attr("data-target");
  			if(id!=undefined){
  				$(id).fadeIn('fast').siblings(".sec_nav").css('display','none');    //如果获取的id不为空，则让这个id对应的二级菜单显示
  			}else{
  				$(".sec_nav").each(function(){
  					$(this).css('display','none');     //若对应的id为空，则不显示
  				});
  			}
  		});
  		var delay;
  		$(".main_nav").mouseover(function(){
			clearTimeout(delay);    //防止二级菜单重复显示隐藏
		});
		$(".main_nav").mouseleave(function(){
			delay=setTimeout(function(){
				delay=$(".main_nav .sec_nav").fadeOut();    //当鼠标移出导航区域，导航隐藏
			},1000);
		});
  	});
/**
 * 模态框调用
 */
function normalModal(modelId, contentId, url, title) {

    if (title) {
        $("#" + modelId + " .modal-title").html(title);
    }
    $("#" + modelId + " #" + contentId).load(url, function () {
    });

    $("#" + modelId).modal({
        backdrop: "static"
    });
    $("#" + modelId).on('hidden.bs.modal', function () {
        $("#" + modelId + " #" + contentId).empty();
    });
}
function commit(formId,pformId,modalCancelId){
    var ret = true;
    //var data="";
    $("input,select,textarea", $("#" + formId)).each(function (i) {
        //if(i==0){
        //    data=data+$(this).attr('name')+"="+$(this).val();
        //}else{
        //    data=data+"&"+$(this).attr('name')+"="+$(this).val();
        //}

        var message = $(this).parent().prev().text().trim();
        if ($(this).attr("notnull") && $(this).attr("notnull") == "true") {
            if ($(this).val() == null || $(this).val() == "") {
                alert(message.substring(0, message.length - 1) + "不能为空！");
                $(this).focus();
                ret = false;
                return false;
            }
        }
        if ($(this).attr("CORP_NAME") && $(this).attr("CORP_NAME") == "true") {
            if ($(this).val() == null || $(this).val() == "") {
                alert( "企业不能为空！");
                $(this).focus();
                ret = false;
                return false;
            }
        }
        if ($(this).attr("CORP_ID") && $(this).attr("CORP_ID") == "true") {
            if ($(this).val() == null || $(this).val() == "") {
                alert( "企业必须为库里已有的企业！");
                ret = false;
                return false;
            }
        }
        if ($(this).attr("NUMBER") && $(this).attr("NUMBER") == "true") {
            var re= /^(\d+((\.\d+)|(\d*)))$/;
            if ($(this).val() == null || $(this).val() == "") {
                alert( "不能为空！");
                ret = false;
                return false;
            }
            if (!re.test($(this).val())) {
                alert("该输入正确的数值!");
                $(this).focus();
                ret = false;
                return false;
            }

        }
    });
    if(ret){
        jQuery.ajax({
            type: "POST",
            url: $("#"+formId).attr('action'),
            data: $("input,select,textarea", $("#" + formId)).serialize(),
            success: function (msg) {
                if (msg.success) {
                    alert(msg.message);
                    $("#"+modalCancelId).click();
                    submitForm(pformId);
                } else {
                    alert(msg.message);
                }
            }
        });
    }
}

switchKeyBoxes = function (eleId, checked, eleName) {
	alert(checked);
    var theForm = $("#" + eleId);
    $("input:checkbox[name='" + eleName + "']:not(:disabled)", theForm).prop('checked', checked);
};
checkSelect = function (eleName, theFormId) {
    return $("input[name='" + eleName + "']:checked", $("#" + theFormId)).length;
};

function changeStatus(listId, formId, parameterId, url) {
    if (checkSelect(parameterId, listId) == 0) {
        alert("请至少选择一条操作记录");
        return false;
    }
    if (confirm("确认操作?")) {
        $.ajax({
            type: "POST",
            url: url,
            data: $("#" + listId).serialize(),
            success: function (data) {
                if (data.success) {
                    alert(data.message);
                    $("#pageNumber").val(0);
                    submitForm(formId);
                } else if (data == "logout") {
                    window.location.href = base + '/sys/main';
                } else {
                    alert(data.message);
                }
            }
        });
    }
}