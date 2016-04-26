function qryData(form1){
			var paginationDiv = $("#" + form1).parents('.paginationDiv');//容器，为了解决一个页面多个分页的问题	
			var pageBar = paginationDiv.find('.pageBar');//获取分页的bar
		
			//选择分页数据的时候,开始加载列表
			//paginationDiv.find(".p_from").find('.p_startRow').val((pageNumber-1)*pageSize);
			//paginationDiv.find(".p_from").find('.p_count').val(10);
			var condition = $("#" + form1).serialize();
			//serialize方法会将空格替换成“+” 号 ，而 “+号被转义为：％２Ｂ，所以放心德替换回来
			condition = condition.replace(/\+/g," ");
			
			if (pageBar.attr("oldCondition") != condition) {
				paginationDiv.find(".p_from").find('.p_startRow').val(0);
			}
			
			var p_param = "";
			//p_param = "pageNumber=1"+"&pageCount=1"+"&pageSize=10";
			if($('.p_from').size() ==0){
				p_param = "pageStart=0"+"&pageCount=10"+"&pageSize=5";//第一次页面的时候没有加载过分页信息。
			}else{
				//var pageSize=$("#pageSizeSelect").val();
				//$("#pageSize").val(pageSize);
				p_param = paginationDiv.find('.p_from').serialize()

			}
			
			//查询使用的完整参数
			var param = p_param + "&" + condition;
			/*var requestMethod = "POST";
			if($(".conditionForm").attr('method') == "POST" || $(".conditionForm").attr('method') == 'post'){
				requestMethod = "POST";
			} else {
				requestMethod = "POST";
			}
			if(requestMethod == "POST"){
				param = convertToObject(param);
			}*/
			
			param = convertToObject(param);

			
			//param = serializeArray
			var path = $("#" + form1).attr('action');

			paginationDiv.find(".queryResultList").load(path, param, function(){
				try {

					if (pageBar.attr("oldCondition") == condition) {
						
					}
					else {
						pageBar.attr("oldCondition", condition);
						paginationDiv.find(".p_from").find('.p_startRow').val(0);
						//$("#pageNumber").val(0);
					}
					goPage(form1);
				}  catch(err) {
					
				}
			});
		
}
goPage = function(form1) {
		var paginationDiv = $("#" + form1).parents('.paginationDiv');//容器，为了解决一个页面多个分页的问题

		var pageNumber = parseInt(paginationDiv.find(".p_from").find(".p_number").val());
		var pageCount = paginationDiv.find(".p_from").find(".p_size").val();
		paginationDiv.find(".pager").pager({
			pagenumber : pageNumber,
			pagecount : pageCount,
			buttonClickCallback : function(pnumber) {
				paginationDiv.find(".p_from").find(".p_startRow").val(pnumber);
				paginationDiv.find(".p_from").find(".p_number").val(pageCount);
				qryData(form1)
			}
	});
};

function submitForm(form1){	
	var paginationDiv = $("#" + form1).parents('.paginationDiv');
	paginationDiv.find(".p_from").find(".p_startRow").val(0);
	qryData(form1);
}


/**
 * 将格式为： key=value&key=value 的字符串转化为js对象
 * @param {Object} params
 */
function convertToObject(params){
	
	var obj = {};
	var keyValues = params.split('&');
	for(var i=0; i<keyValues.length; i++){
		var keyVal = keyValues[i].split('=');
		obj[keyVal[0]] = decodeURIComponent(keyVal[1]?keyVal[1]:"");
	}
	return obj
}


String.prototype.replaceAll  = function(s1,s2){   
	return this.replace(new RegExp(s1,"gm"),s2);   
};
