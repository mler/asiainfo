/**
 * Created by core on 16/1/28.
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
function delParam(qryformId, url) {
    if (confirm("确认删除？")) {
        $.ajax({
            type: "POST",
            url: url,
            success: function (data) {
                if (data.success) {
                    if (data.success) {
                        alert(data.message);
                        $("#pageNumber").val(0);
                        submitForm(qryformId);
                    } else if (data == "logout") {
                        window.location.href = base + '/sys/main';
                    } else {
                        alert(data.message);
                    }
                }
            }
        });
    }
}
function deleteParam(formId, paramIds, qryformId, url) {
    if (checkSelect(paramIds, formId) == 0) {
        alert("请至少选择一条操作记录");
        return false;
    }
    //var checkedList = new Array();
    //$("input[type='checkbox']:checked").each(function() {
    //    checkedList.push($(this).val());
    //});
    if (confirm("确认删除？")) {
        $.ajax({
            type: "POST",
            url: url,
            data: $("#" + formId).serialize(), //{'delitems':checkedList.toString()},
            dataType: "json",
            success: function (data) {
                if (data.success) {
                    if (data.success) {
                        alert(data.message);
                        submitForm(qryformId);
                    } else if (data == "logout") {
                        window.location.href = base + '/sys/main';
                    } else {
                        alert(data.message);
                    }
                }
            }
        });
    }
}
switchKeyBoxes = function (eleId, checked, eleName) {
    var theForm = $("#" + eleId);
    $("input:checkbox[name='" + eleName + "']", theForm).attr("checked", checked);
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

function changeStatu(formId, url, value, listIds) {
    if (confirm("确认操作?")) {
        $.ajax({
            type: "POST",
            url: url,
            data: listIds + "=" + value,
            success: function (data) {
                if (data.success) {
                    alert(data.message);
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
function saveInfo(pformId, formId, cancelId) {
    var ret = true;
    $("input,select", $("#" + formId)).each(function (i) {
        var message = $(this).parent().prev().text().trim();
        if ($(this).attr("notnull") && $(this).attr("notnull") == "true") {
            if ($(this).val() == null || $(this).val() == "") {
                alert(message.substring(0, message.length - 1) + "不能为空！");
                $(this).focus();
                ret = false;
                return false;
            }
        }
        if ($(this).attr("PLAT") && $(this).attr("PLAT") == "true") {
            if ($(this).val() == null || $(this).val() == "" || !$(this).val().match(/^\d.*$/)) {
                alert(" 请选择平台！");
                $(this).focus();
                ret = false;
                return false;
            }
        }
    });
    if (ret) {
        var pathurl = $("#" + formId).attr("action");
        jQuery.ajax({
            type: "post",
            url: pathurl,
            data: $("#" + formId).serialize(),
            success: function (msg) {
                if (msg.success) {
                    alert(msg.message);
                    $("#" + cancelId).click();
                    submitForm(pformId);
                } else if (msg == "logout") {
                    window.location.href = base + '/sys/main';
                } else {
                    alert(msg.message);
                }
            }
        });
    }
}
var setOptions = function (data, target) {
    var temp_html = "<option value='0'>父节点</option>";
    if (target == "menuPid") {
        $.each(data, function (n, value) {
            temp_html += "<option value='" + value.menuId + "'>" + value.menuName + "</option>";
        });
    } else if (target == "rolePid") {
        $.each(data, function (n, value) {
            temp_html += "<option value='" + value.roleId + "'>" + value.roleName + "</option>";
        });
    } else if (target == "deptPid") {
        $.each(data, function (n, value) {
            temp_html += "<option value='" + value.deptId + "'>" + value.deptName + "</option>";
        });
    }

    $("#" + target).html(temp_html);
};
function goEditMenu(path, role_id, flag) {
    //取消原先选中的
    $("input[type='checkbox'][name='roleIds']").each(function () {
        $(this).attr("checked", false);
    });

    //选中修改的那条
    var checkboxs = $("input[type='checkbox'][name='roleIds'][value=" + role_id + "]");
    $.each(checkboxs, function () {
        this.checked = "checked";
    });

    delmenuid = "";
    addmenuid = "";

    //生成菜单树
    var url = path + "/sys/role/roleToMenu?roleId=" + role_id + "&operflag=" + flag;
    normalModal('myModal', 'editContext', url, '角色权限')
}
function showHideView(e, objname) {
    var obj = $("#" + objname);
    if (obj.css('display') == "none") {
        obj.slideToggle('fast');
        $(e).parent().removeClass("plusm").addClass("minus");
    } else {
        obj.slideToggle('fast');
        $(e).parent().removeClass("minus").addClass("plusm");
    }
}
function checkFaNode(obj, menuPid, flag, checkflag) {
    // $(obj).checked = checkflag;
    if (flag == 1) {
        var checkboxs = $("input[type='checkbox'][id='menuids'][value=" + menuPid + "]");
        $.each(checkboxs, function () {
            this.checked = "checked";
            if (checkflag) {
                addmenuid = addmenuid + $(this).val() + ",";
            }
        });
    } else {
        var namemenu = "menu" + menuPid;
        var checkboxs = $("input[type='checkbox'][name=" + namemenu + "]");
        $.each(checkboxs, function () {
            this.checked = checkflag;
            if (checkflag) {
                addmenuid = addmenuid + $(this).val() + ",";
            } else {
                delmenuid = delmenuid + $(this).val() + ",";
            }
        });
    }
    if (checkflag) {
        addmenuid = addmenuid + $(obj).val() + ",";
    } else {
        delmenuid = delmenuid + $(obj).val() + ",";
    }
}
function validateNull(value) {

    if (value == undefined) {

        return false;
    } else {
        if ($.trim(value) == "") {

            return false;
        }
        return true;
    }
}
function checkName(){
    if($("#userLoginName").val()==""){
        $("#Uspan").html("<font color='red'>登录用户名不能为空！</font>")
        return false;
    }
}
function checkUname(path) {
    if ($("#userName").val() == "") {
        $("#uspan").html("<font color='red'>用户名不能为空！</font>")
        return false;
    }
    var username = $("#userName").val();
    $.ajax({

        type: "GET",
        url: path,
        data: "username=" + username,
        success: function (data) {
            if (data.success) {
                $("#uspan").html("<font color='green'>该用户名可以使用</font>")

            } else {
                $("#uspan").html("<font color='red'>该用户名已经被占用！</font>")
            }
        }
    });
}
function checkSubmitEmail() {
    if ($("#email").val() == "") {
        $("#espan").html("<font color='red'>邮箱地址不能为空！</font>");
        //alert("邮箱不能为空!")
        //$("#email").focus();
        return false;
    }
    if (!$("#email").val().match( /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/)) {
        //alert("邮箱格式不正确");
        $("#espan").html("<font color='red'>邮箱格式不正确！</font>");
        //$("#email").focus();
        return false;
    }
    else {
       $("#espan").html("<font color='green'>该邮箱可以使用</font>");
     return true;
    }
    return true;
}
function checkMobileNum(){
    if ($("#mobileNum").val() == "") {
        //alert("手机号码不能为空！");
        $("#mpspan").html("<font color='red'>手机号码不能为空！</font>");
        //$("#mobilePhone").focus();
        return false;
    }if (!$("#mobileNum").val().match(/^0{0,1}(13[4-9]|15[7-9]|15[0-2]|18[7-8])[0-9]{8}$/)) {
        //alert("手机号码格式不正确！");
        $("#mpspan").html("<font color='red'>手机号码格式不正确！请重新输入！</font>");
        //$("#mobilePhone").focus();
        return false;
    }else{
        $("#mpspan").html("<font color='green'>该号码可以使用</font>");
        return true;
    }
    return true;
}
function checkSubmitMobile() {
    if ($("#mobilePhone").val() == "") {
        //alert("手机号码不能为空！");
        $("#mspan").html("<font color='red'>手机号码不能为空！</font>");
        //$("#mobilePhone").focus();
        return false;
    }


    if (!$("#mobilePhone").val().match(/^0{0,1}(13[4-9]|15[7-9]|15[0-2]|18[7-8])[0-9]{8}$/)) {
        //alert("手机号码格式不正确！");
        $("#mspan").html("<font color='red'>手机号码格式不正确！请重新输入！</font>");
        //$("#mobilePhone").focus();
        return false;
    }else{
        $("#mspan").html("<font color='green'>该号码可以使用</font>");
        return true;
    }

}
function checkPwd(){
$('#loginPwd').keyup(function(e) {
    var strongRegex = new RegExp("^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$", "g");
    var mediumRegex = new RegExp("^(?=.{7,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$", "g");
    var enoughRegex = new RegExp("(?=.{6,}).*", "g");
    if (false == enoughRegex.test($(this).val())) {
        $('#passstrength').html("<font color='red'>密码长度太小</font>");
    } else if (strongRegex.test($(this).val())) {
        $('#passstrength').className = 'ok';
        $('#passstrength').html("<font color='green'>密码强度很高!</font>");
    } else if (mediumRegex.test($(this).val())) {
        $('#passstrength').className = 'alert';
        $('#passstrength').html("<font color='#adff2f'>密码强度适中!</font>");
    } else {
        $('#passstrength').className = 'error';
        $('#passstrength').html("<font color='red'>密码强度很弱!</font>");
    }
    return true;
});}