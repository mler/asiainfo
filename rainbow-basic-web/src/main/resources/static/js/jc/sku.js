/**
 * 展现详情
 * @param skuBarcode
 */
function showDetail(skuBarcode){
	//$("body").mask("努力加载中...");
	var admin = $.trim($("#admin").val());
	var url = base + '/sku/showDetail?skuBarcode=' + skuBarcode + '&admin=' + admin;
	$("#sku_detail").load(url, function(){
	//	$("body").unmask();
		$("#sku_detail").show();
	});
}

/**
 * 修改sku
 * @param skuBarcode
 * @param optType
 */
function doWithSku(optType){
	var url = base + '/sku/update?optType=' + optType;
	var data = $("#sku_detail_form").serialize();
	$.ajax({
	    type: 'post',
	    url: url,
	    data: data,
	    success:function(d){
	    	var dataObj=eval("("+d+")");;
			if(dataObj.flag){
				if(optType == 2){
					alert("删除成功");
				}else if(optType == 0){
					alert("修改成功");
				}else{
					alert("审批通过");
				}
			}else{
				alert(dataObj.msg);			
			}
		}
	});
	
}

function querySku(){
	var skuBarcode= $.trim($("#skuBarcode").val());
	var skuName = $.trim($("#skuName").val());
	var createName = $.trim($("#createEmpCode").val());
	var status = $("#status").val();
	var admin = $.trim($("#admin").val());
	if(admin == '' || admin == undefined || admin == null){
		location.href = base + '/sku/list?skuBarcode=' + skuBarcode + '&skuName=' + skuName + '&createEmpCode=' + createName + '&status=' + status;
	}else{
		location.href = base + '/sku/list?skuBarcode=' + skuBarcode + '&skuName=' + skuName + '&createEmpCode=' + createName + '&status=' + status + '&admin=' + admin;
	}
}