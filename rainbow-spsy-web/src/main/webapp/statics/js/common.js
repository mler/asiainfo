function resetFormValue(formId) {
    $(":input", $("#" + formId)).each(function(i){
    	this.value = "";
    });
}

function switchKeyBoxes (eleId,checked,eleName) {
	var theForm = $("#" + eleId);
	$("input:checkbox[name='" + eleName +"']", theForm).prop("checked",checked);
}

function checkSelect(eleName, theFormId) {
	return $("input:checked", $("#" + theFormId)).length;
}

function checkPhone(phone){
	var re= /^((13|15|18)\d{9})$/;
	if (!re.test(phone)) {
		re = /^([0]?\d{2,3})?[-]?\d{5,8}([-]\d+)?$/;
		if (!re.test(phone)) {
		   return false;
		}else{
		   return true;
		}
	}else {
		return true;
	}
}

function checkDataType(ele) {
	var dataType = $(ele).attr("dataType");
	if(dataType == "INTEGER") {
		if(isNaN(this.trim())) {
			alert("请输入正确的数值(如:1,2,3)!");
			$(ele).focus();
			return false;
		}
    }
  
	if(dataType == "MOBILEPHONE") {
		re= /^((13|15|18)\d{9})$/;
		if (!re.test($(ele).val())) {
			alert("该输入正确的11位移动手机号码!");
			$(ele).focus();
			return false;
		}
		else {
			return true;
		}
	}
	
	if (dataType == "PHONE") {
		re = /^([0]?\d{2,3})?[-]?\d{5,8}([-]\d+)?$/;
		if (!re.test($(ele).val())) {
			alert("该输入正确的固定电话号码!");
			$(ele).focus();
			return false;
		} else {
			return true;
		}
	}
}

