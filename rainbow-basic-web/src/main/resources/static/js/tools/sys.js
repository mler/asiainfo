function resetFormValue(formId) {
	$(":input", $("#" + formId)).each(function(i) {
		this.value = "";
	});
}

function resetForm(formId) {
	$('#' + formId)[0].reset();
}

saveForm = function(formId) {
	var ret = true;
	$("body").mask("Waiting...");

	$("input,select", $("#" + formId)).each(function(i) {
		if ($(this).attr("notnull") && $(this).attr("notnull") == "true") {
			if ($(this).val() == null || $(this).val() == "") {
				alert("*标记不能为空！");
				$(this).focus();
				ret = false;
				$("body").unmask();
				return false;
			}
			if (!checkFileType(this)) {
				ret = false;
				$("body").unmask();
				return false;
			}
		}
		if (!checkDataType(this)) {
			ret = false;
			$("body").unmask();
			return false;
		}
	});
	if (ret) {
		$("#" + formId).submit();
		// $("body").unmask();
		/*
		 * if(mask){ mask(); }
		 */
	}
}



checkDataType = function(ele) {
	var dataType = $(ele).attr("dataType");
	if (!dataType)
		return true;
	var re;
	if (dataType.toUpperCase() == "EMAIL") {
		re = /^[a-zA-Z_0-9]+@[a-zA-Z_0-9.\-]+$/;
		if (!re.test($(ele).val())) {
			alert("该输入正确的Email地址!");
			$(ele).focus();
			return false;
		} else {
			return true;
		}
	}

	if (dataType.toUpperCase() == "MOBILEPHONE") {
		re = /^((13|15|18)\d{9})$/;
		if (!re.test($(ele).val())) {
			alert("该输入正确的11位移动手机号码!");
			$(ele).focus();
			return false;
		} else {
			return true;
		}
	}

	if (dataType.toUpperCase() == "PHONE") {
		re = /^([0]?\d{2,3})?[-]?\d{5,8}([-]\d+)?$/;
		if (!re.test($(ele).val())) {
			alert("该输入正确的固定电话号码!");
			$(ele).focus();
			return false;
		} else {
			return true;
		}
	}

	if (dataType.toUpperCase() == "ICCID") {
		re = /^((898601)\d{13})$/;
		if (!re.test($(ele).val())) {
			alert("该输入正确的ICCID!");
			$(ele).focus();
			return false;
		} else {
			return true;
		}
	}

	// 数字型
	if (dataType.toUpperCase() == "NUMBER") {
		if (isNaN(this.trim())) {
			alert("请输入正确的数值(如:1.23)!");
			$(ele).focus();
			return false;
		}
	}

	if (dataType.toUpperCase() == "FLOAT") {
		re = /^(\d+((\.\d+)|(\d*)))$/;
		if (!re.test($(ele).val())) {
			alert("该输入正确的数值!");
			$(ele).focus();
			return false;
		} else {
			return true;
		}
	}
}

checkFileType = function(ele) {
	var allowFileTypes = $(ele).attr("fileType");
	if (!allowFileTypes)
		return true;
	allowFileTypes = allowFileTypes + ',';
	var fileType = $(ele).val().substr($(ele).val().lastIndexOf('.') + 1,
			$(ele).val().length)
			+ ',';
	if (allowFileTypes.indexOf(fileType) == -1) {
		alert("附件格式不正确！支持的上传格式为：" + allowFileTypes);
		$(ele).focus();
		return false;
	} else {
		return true;
	}
}

switchKeyBoxes = function(eleId, checked, eleName) {
	var theForm = $("#" + eleId);
	$("input:checkbox[name='" + eleName + "']", theForm).attr("checked",
			checked);
}

checkSelect = function(eleName, theFormId) {
	return $("input:checked", $("#" + theFormId)).length;
}

function popWin(options) {

	$('#popDiv').remove();
	// $(".pop-upLine").remove();
	if ($('#popDiv').length == 0) {

		var width = options.width - 14;// 386

		var height = options.height - 38;// 262
		$("#messageContext").empty();
		$("#messageContext").append(
				'<div id="popDiv"><iframe id="popIframe" frameborder="0" style="width:'
						+ width + 'px;height:' + height
						+ 'px;" ></iframe></div>')
	}

	if (options['top']) {
		$('#popDiv').css('position', options.top)
	}
	
	$('#messageModal').css("z-index", "1500");
	$("#messageModal .modal-title").html(options.title);

	$('#popIframe').attr("src", options.href);

	// options.href = null;
	$('#popDiv').data('onPopClose', options.onPopClose);
	// $('#popDiv').window(options)
	//showWait($("body"));
	$('#messageModal').modal({
		backdrop : "static",
	});
	$('#messageModal').on('hidden.bs.modal', function() {
		$("#messageContext").empty();
	});
}

function closePop(param) {
	$('#popDiv').data('onPopClose')(param);
	// $("#popDiv").find("#closeBtn").trigger("click");
	$("#messageModal").find(".close").trigger("click");
	// $('#popDiv').window('close');
}

openDeptTree = function(path, cur_id, inputElementId,descElementId) {
	/**$("#msg").load(path + "/sys/dept/getDeptTree.do", null, function(){
		$('#basic-modal-content').modal();
	});**/
	
	if(inputElementId == undefined )
		inputElementId = "deptTree";
	if(descElementId == undefined)
		descElementId = "deptTreeName";
	
	var url = path + "/sys/dept/getDeptTree.do?cur_deptType=user";
	if (cur_id) {
		url = url + "&cur_deptId=" + cur_id;
	}
	 //window.open(url, '_blank', '');	
	 
	 
	popWin({
		title:'选择组织机构',
		href:url,
		width:400,
		height:300,
		draggable: false,
		onPopClose:function(param){
			document.getElementById(inputElementId).value = param.deptId;
			document.getElementById(descElementId).value = param.deptName;
			$("#popDiv").find("#closeBtn").trigger("click");
		}
	});
}

helpDept = function (path, id,inputElementId,descElementId) {
	var url = path + "/sys/initDeptHelper.do";
	
	if(inputElementId == undefined )
		inputElementId = "deptTree";
	if(descElementId == undefined)
		descElementId = "deptTreeName";
	
	popWin({
		title:'组织机构小助手',
		href:url,
		width:700,
		height:560,
		shadow: false,
		draggable: false,
		closed: false,
		modal: true,
		onPopClose:function(param){
			document.getElementById(inputElementId).value = param.deptId;
			document.getElementById(descElementId).value = param.deptName
		}
	});
}

openMenuTree = function(path, id, inputElementId,descElementId) {
	var url = path + "/sys/menu/getMenuTree.do";
	if (id) {
		url = url + "?cur_menuId=" + id;
	}
	 //window.open(url, '_blank', '');	
	 
	if(inputElementId == undefined )
		inputElementId = "menuTree";
	if(descElementId == undefined)
		descElementId = "menuTreeName";
	
	popWin({
		title:'选择菜单',
		href:url,
		width:500,
		height:400,
		draggable: false,
		maximizable:false,
		minimizable:false,
		collapsible:false,
		onPopClose:function(param){
			document.getElementById("menuTree").value = param.menuId;
			document.getElementById("menuTreeName").value = param.menuName
		}
	});
}

openRoleUserTree = function(path, id, name) {
	var url = path + "/sys/role/getRoleUserTree.do";
	if (id) {
		url = url + "?cur_roleId=" + id;
	}
	 //window.open(url, '_blank', '');	
	 
	 
	popWin({
		title:'选择管理权限',
		href:url,
		width:450,
		height:350,
		draggable: false,
		maximizable:false,
		minimizable:false,
		collapsible:false,
		onPopClose:function(param){
			document.getElementById("roleTree").value = param.roleId;
			document.getElementById("roleTreeName").value = param.roleName;

		}
	});
}

openRoleTree = function(path, id, name) {
	var url = path + "/sys/role/getRoleTree.do";
	if (id) {
		url = url + "?cur_roleId=" + id;
	}
	 //window.open(url, '_blank', '');	
	  
	popWin({
		title:'选择管理权限',
		href:url,
		width:450,
		height:350,
		draggable: false,
		maximizable:false,
		minimizable:false,
		collapsible:false,
		onPopClose:function(param){
			document.getElementById("roleTree").value = param.roleId;
			document.getElementById("roleTreeName").value = param.roleName;
			$("#popDiv").find("#closeBtn").trigger("click");

		}
	});
}


/**
 * 普通model 浮动框
 */
function normalModal(modelId, contentId, url, title) {
	showWait($("body"));
	if (title) {
		$("#" + modelId + " .modal-title").html(title);
	}
	$("#" + contentId).load(url, function() {
		closeWait($("body"));
	});

	$("#" + modelId).modal({
		backdrop : "static"
	});
	$("#" + modelId).on('hidden.bs.modal', function() {
		$("#" + contentId).empty();
	});
}

/**
 * 
 * @param modelId
 * @param contentId
 * @param url
 * @param title
 */
function errorModal(context) {

	$("#errorContext").html(context);

	$("#errorModal").modal({
		backdrop : "static"
	});
	$("#errorModal").on('hidden.bs.modal', function() {
		$("#errorContext").empty();
	
	});
}

/**
 * 通用保存方法
 * @param url 保存的URL
 * @param form 保存的对象保单元素form
 * @param formId 原查询页面的formId
 */
function goSave(url, form, formId) {
	if (confirm("确认提交吗?")) {
		var ret = true;
		showWait($("#editModal"));

		$("input,select", $(form)).each(function(i) {
			if ($(this).attr("notnull") && $(this).attr("notnull") == "true") {
				if ($(this).val() == null || $(this).val() == "") {
					alert("*标记不能为空！");
					$(this).focus();
					ret = false;
					closeWait($("#editModal"));
					return false;
				}
				if (!checkFileType(this)) {
					ret = false;
					closeWait($("#editModal"));
					return false;
				}
			}
			if (!checkDataType(this)) {
				ret = false;
				closeWait($("#editModal"));
				return false;
			}
		});

		if (ret) {
			/**
			 * 走jquery的ajax提交方式
			 */
			$.ajax({
				url : url,
				data : $(form).serialize(),
				dataType : 'json',
				type : 'post',
				success : function(data, textStatus) {
					if (data.success) {
						alert("保存成功！");
						$("#editModal .modal-header .close").click();
						$("#" + formId).find("#goQuery").trigger("click");
					} else {
						errorModal(data.msg);
					}
				},
				error : function(errors, textStatus) {
					if ("timeout" == textStatus) {
						alert("系统请求超时，请联系管理员！");
					}

				},
				complete : function(XMLHttpRequest, textStatus) {
					closeWait($("#editModal"));
					this;
				}
			});
		}
	}
}

/**
 * 通用的删除 url 操作的链接 data 参数 message 信息提醒 formId 操作的回调函数的fromId
 */
function goDel(url, data, message, formId) {
	if (confirm("确认提交吗?")) {
		showWait($("body"));
		$.ajax({
			url : url,
			data : data,
			dataType : 'json',
			type : 'get',
			success : function(data, textStatus) {
				if (data.success) {
					alert("删除成功！");
					if (formId) {
						$("#" + formId).find("#goQuery").trigger("click");
	
					} else {
						$("#goQuery").trigger("click");
					}
				} else {
					errorModal(data.message);
				}
			},
			error : function(errors, textStatus) {
				if ("timeout" == textStatus) {
					alert("系统请求超时，请联系管理员！");
				}
	
			},
			complete : function(XMLHttpRequest, textStatus) {
				closeWait($("body"));
				this;
			}
		});
	}
}

/**
 * 浮出等待条
 * @param it_ele
 * @param mes
 */
function showWait(it_ele, mes) {
	if (mes) {
		mes = "努力加载中...";
	}

	if (it_ele) {
		$(it_ele).mask(mes);
	} else {
		$("body").mask(mes);
	}

}

/**
 * 关闭等待条
 * @param it_ele
 */
function closeWait(it_ele) {
	if (it_ele) {
		$(it_ele).unmask();
	} else {
		$("body").unmask();
	}
}

function showMask() {
	$('.loaderWrap').show();
}

function hideMask() {
	$('.loaderWrap').hide();
}

//将form中的值转换为键值对。
function getFormJson(formId) {
    var o = {};
    var a = $("#"+formId).serializeArray();
    $.each(a, function () {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
}

/**
 * 
 * @param title: tab title
 * @param target: tab id
 */
function goTab(title, target, url) {
	var length = 0;
	var let1 = true;
	$("#thead").children().each(function(index,element) {
		length++;
		eurl = $($(element).children()[0]).attr("url");
		if (eurl == url) {
			$($(element).children()[0]).click();
			let1 = false;
			return;
		}
	});
	
	if (let1) {
		if (length == 8) {
			alert("已达最大tab数，请关闭其他不需要的再进行操作！");
			return;
		}
		
		$("#tc").children().removeClass("active");
		$("#tc").append("<div class='tab-pane active' id='" + target + "'></div>");
		$("#thead li").removeClass("active");
		$("#thead").append("<li class='active'><a href='#" + target + "' data-toggle='tab' url='" + url + "'>" + title + "</a><a href='#' class='cross'></a></li>");
		
		showWait($("#tc #" + target));
		$("#tc #" + target).load(url, function(data) {
			closeWait($("#tc #" + target));
		});
	}
}