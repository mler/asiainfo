/**
A jQuery plugin for search hints

Author: Lorenzo Cioni - https://github.com/lorecioni
*/

(function($) {
	$.fn.autocomplete = function(params) {
		
		//Selections
		var currentSelection = -1;
		var currentProposals = [];
		
		//Default parameters
		params = $.extend({
			hints: [],
			placeholder: '请输入投诉单位',
			width: 200,
			height: 16,
			showButton: false,
			buttonText: 'Search',
            typeValue:'',
            url:'',
			onSubmit: function(text){},
			onBlur: function(){}
		}, params);

		//Build messagess
		this.each(function() {
			//Container
			var searchContainer = $('<div></div>')
				.addClass('autocomplete-container');
				//.css('height', params.height * 2);
				
			//Text input		
			var input = $('<input type="text" CORP_NAME="true" name="complaintUnit" autocomplete="off" >')
				.attr('placeholder', params.placeholder)
				.addClass('col-sm-12')
				.css({
					//'width' : params.width,
					'height' : params.height
				});
            var msgBox=$('<span style="display: none">没有从库里找到对应的企业信息</span>');
			if(params.showButton){
				input.css('border-radius', '3px 0 0 3px');
			}

			//Proposals
			var proposals = $('<div></div>')
				.addClass('proposal-box')
				.css('width', params.width + 18)
				.css('top', input.height() + 0);
			var proposalList = $('<ul></ul>')
				.addClass('proposal-list');

			proposals.append(proposalList);
			
			input.keydown(function(e) {
				switch(e.which) {
					case 38: // Up arrow
					e.preventDefault();
					$('ul.proposal-list li').removeClass('selected');
					if((currentSelection - 1) >= 0){
						currentSelection--;
						$( "ul.proposal-list li:eq(" + currentSelection + ")" )
							.addClass('selected');
					} else {
						currentSelection = -1;
					}
					break;
					case 40: // Down arrow
					e.preventDefault();
					if((currentSelection + 1) < currentProposals.length){
						$('ul.proposal-list li').removeClass('selected');
						currentSelection++;
						$( "ul.proposal-list li:eq(" + currentSelection + ")" )
							.addClass('selected');
					}
					break;
					case 13: // Enter
						if(currentSelection > -1){
							var text = $( "ul.proposal-list li:eq(" + currentSelection + ")" ).html();
							var id=$("ul.proposal-list li:eq(" + currentSelection + ")" ).attr('id');
							input.attr('key',id);
							input.val(text);
						}
						currentSelection = -1;
						proposalList.empty();
						params.onSubmit(input.attr('key'));
                        params.hints=[];
						break;
					case 27: // Esc button
						currentSelection = -1;
						proposalList.empty();
						input.val('');
                        params.hints=[];
						break;
				}
			});
				
			input.bind("change paste keyup", function(e){
				if(e.which != 13 && e.which != 27 
						&& e.which != 38 && e.which != 40){				
					currentProposals = [];
					currentSelection = -1;
					proposalList.empty();
					if(input.val() != ''){
						var word =input.val() + ".*";
						proposalList.empty();
                        if(params.hints.length==0){
                            $.ajax({
                                type: "POST",
                                async: true,
                                url: params.url,
                                data: "enterpriseName="+input.val()+'&enterpriseBusinessType='+$("#enterpriseBusinessType").val(),
                                success: function(msg) {
                                    if(msg.data==null){
                                        msgBox.css('display','block');
                                    }else{
                                        params.hints= msg.data;
                                        msgBox.css('display','none');
                                    }
                                }
                            });
                        }
						for(var test in params.hints){
							if(params.hints[test].match(word)){
								var arrs=params.hints[test].split(';')
								currentProposals.push(arrs[0]);	
								var element = $('<li></li>')
									.html(arrs[0])
									.addClass('proposal').attr('id',arrs[1])
									.click(function(){
										input.val($(this).html());
										proposalList.empty();
										params.onSubmit(input.attr('key'));
									})
									.mouseenter(function() {
										$(this).addClass('selected');
									})
									.mouseleave(function() {
										$(this).removeClass('selected');
									});
								proposalList.append(element);
							}
						}
					}else{
                        params.hints=[];
                    }
				}
			});
			
			input.blur(function(e){
				currentSelection = -1;
				//proposalList.empty();
				params.onBlur();
			});
			searchContainer.append(input);
            searchContainer.append(msgBox);
			searchContainer.append(proposals);
			
			if(params.showButton){
				//Search button
				var button = $('<div></div>')
					.addClass('autocomplete-button')
					.html(params.buttonText)
					.css({
						'height': params.height + 2,
						'line-height': params.height + 2 + 'px'
					})
					.click(function(){
						proposalList.empty();
						params.onSubmit(input.attr('key'));
					});
				searchContainer.append(button);	
			}
	
			$(this).append(searchContainer);	
			
			if(params.showButton){
				//Width fix
				searchContainer.css('width', params.width + button.width() + 50);
			}
		});

		return this;
	};

})(jQuery);