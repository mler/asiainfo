$(function(){
	try {
		goPage(buttons);
	} catch (e) {
		goPage();
	}
	//主动触发 refresh的按钮的事件,开始第一次加载
	$('.pageBar').find('a[icon=pagination-load]').trigger('click.pagination');
});


$(document).ready(function(){
	//解决在form内的input被按回车的时候提交请求报错的问题
	$(".conditionForm").each(function(){
		$(this).append('<input type="text" value="不要删除这个input，只有在form里不只有一个input的情况下，在input里按回车才不会提交表单" style="display:none" />')
	})
})


goPage = function(buttons) {
	$('.pageBar').pagination({
		total:0,
		buttons: (buttons == undefined) ? [] : buttons,
		pageSize: 10,//每页显示的记录条数，默认为5  
        pageList: [5,10,15],//可以设置每页记录条数的列表  
        beforePageText: '第',//页数文本框前显示的汉字  
        afterPageText: '页    共 {pages} 页',
		displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		onSelectPage:function(pageNumber, pageSize){
			$(this).pagination('loading');
			var paginationDiv = $(this).parents('.paginationDiv');//容器，为了解决一个页面多个分页的问题	
			var pageBar = paginationDiv.find('.pageBar');//获取分页的bar

			//选择分页数据的时候,开始加载列表
			paginationDiv.find(".p_from").find('.p_startRow').val((pageNumber-1)*pageSize);
			paginationDiv.find(".p_from").find('.p_count').val(pageSize);
			
			var condition = paginationDiv.find(".conditionForm").serialize();
			//serialize方法会将空格替换成“+” 号 ，而 “+号被转义为：％２Ｂ，所以放心德替换回来
			condition = condition.replace(/\+/g," ");
			
			if (pageBar.attr("oldCondition") != condition) {
				paginationDiv.find(".p_from").find('.p_startRow').val(0);
			}
			
			var p_param = "";
			if(paginationDiv.find('.p_from').size() ==0){
				p_param = "pageStart="+(pageNumber-1)*pageSize+"&pageCount="+pageSize;//第一次页面的时候没有加载过分页信息。
			}else{
				p_param = paginationDiv.find('.p_from').serialize()
			}
			
			//查询使用的完整参数
			var param = p_param + "&" + condition;
			var requestMethod = "GET";
			if(paginationDiv.find(".conditionForm").attr('method') == "POST" || paginationDiv.find(".conditionForm").attr('method') == 'post'){
				requestMethod = "POST";
			}
			if(requestMethod == "POST"){
				param = convertToObject(param);
			}

			//param = serializeArray
			paginationDiv.mask("Waiting...");
			paginationDiv.find(".queryResultList").load(paginationDiv.find(".conditionForm").attr('action'), param, function(){
				paginationDiv.unmask();
				if (pageBar.attr("oldCondition") == condition) {
					pageBar.pagination({total:paginationDiv.find(".p_from").find('.p_total').val()});
				}
				else {
					pageBar.attr("oldCondition", condition);
					pageBar.pagination({total:paginationDiv.find(".p_from").find('.p_total').val(), pageNumber:1});
				}
				
			})
			$(this).pagination('loaded');
		}
	});
}

function submitForm(form){
	
	form.parents('.paginationDiv').find('.pageBar').find('a[icon=pagination-load]').trigger('click.pagination');
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
} 
