//注册表单的js
//这是jquery里的,是当文档载入完毕就执行,的意思
$(function(){
	
	//这个就是当文档加载完成,就执行
	$('#search_button').button({
		icons:{
			primary:'ui-icon-search',
			
		}
	});
	//旋转进度条
	$('#loading').dialog({//获取加载对话框的div，并且设置注册对话框的提交按钮信息
		autoOpen:false,
		modal:true,
		width:200,
		height:50,
		resizable:false,
		draggable:false,
		closeOnEscape:false,
	}).parent().parent().find('.ui-widget-header').hide();
	//让注册表单出现
	$('#register #reg_a').click(function(){
		$('#reg').dialog('open');
		return false;
	});
	$('#reg').dialog({
		autoOpen:false,
		modal:true,//遮罩
		resizable:false,
		width:370,
		height:380,
		closeText:'关闭',
		buttons:{
			'提交':function(){
				$(this).submit();
			}
		
		}
	}).buttonset().validate({
		submitHandler:function(form){
			$(form).ajaxSubmit({
				url:'/LifeOA/common/loginAndReg_register.action',//异步提交到后台进行校验
				type:'POST',
				dataType:'text',
				beforeSubmit:function(){
					$('#reg').dialog('widget').find('button').eq(1).button('disable');
					$('#loading').dialog('open');
				},
				success:function(responseText,statusText){
					data = eval("("+responseText+")");//解析后台返回的json数据
					if(data.status=="true"){//如果都通过校验，设置相应的表单显示信息，如输入框后面显示绿色勾
						$('#loading').css('background','url(img/success.gif) no-repeat 20px center').html(data.message+"···");
						setTimeout(function(){
							$('#loading').dialog('close');
							$('#reg').dialog('close');
							$('#loading').css('background','url(img/loading.gif) no-repeat 20px center').html('数据正在提交中···');
							$('#reg').resetForm();
							$('#reg span.star').html('*').removeClass('reg_succ');
							$('#reg').dialog('widget').find('button').eq(1).button('enable');
						},1200);
						window.location.href="/LifeOA/common/index_toIndex.action";//转发到首页
					}else if(data.status=="false"){//如果校验失败
						$(".reg_error").empty();//第一步先致空错误提示框
						$('#loading').dialog('close');//关闭提交加载框
						$('#reg').dialog('widget').find('button').eq(1).button('enable');
						var username = data.username;
						var password = data.password;
						var password1 = data.password1;
						var email = data.email;
						var birthday = data.birthday;
						var num = 0;
						if(typeof(username) !="undefined"){
							var lio = $("<li></li>").appendTo($('.reg_error'));
							var labelo = $("<label class='error' style='display:inline'></label>").appendTo(lio);
							labelo.attr("for","username");
							labelo.html(data.username);
							num++;
						}
						if(typeof(password) !="undefined"){
							var lio = $("<li></li>").appendTo($('.reg_error'));
							var labelo = $("<label class='error' style='display:inline'></label>").appendTo(lio);
							labelo.attr("for","password");
							labelo.html(data.password);
							num++;
						}
						if(typeof(password1) !="undefined"){
							var lio = $("<li></li>").appendTo($('.reg_error'));
							var labelo = $("<label class='error' style='display:inline'></label>").appendTo(lio);
							labelo.attr("for","password1");
							labelo.html(data.password1);
							num++;
						}
						if(typeof(email) !="undefined"){
							var lio = $("<li></li>").appendTo($('.reg_error'));
							var labelo = $("<label class='error' style='display:inline'></label>").appendTo(lio);
							labelo.attr("for","email");
							labelo.html(data.email);
							num++;
						}
						if(typeof(birthday) !="undefined"){
							var lio = $("<li></li>").appendTo($('.reg_error'));
							var labelo = $("<label class='error' style='display:inline'></label>").appendTo(lio);
							labelo.attr("for","birthday");
							labelo.html(data.birthday);
							num++;
						}
						$('#reg').css('height',258+num*20);
						$('.reg_error').css("display","block");
						$('.reg_error li').css("display","block");
					}
					
				},
				error:function(){
					$('#reg').dialog('widget').find('button').eq(1).button('enable');
				}
				
			});
		},
		showErrors:function(errorMap,errorList){
			var errors = this.numberOfInvalids();
			if(errors>0){
				$('#reg').css('height',258+errors*20);
				this.defaultShowErrors();
			}else{
				$('#reg').css('height',258);
				this.defaultShowErrors();
			}
		},
		highlight:function(element,errorClass){
			$(element).css('border','1px solid #630');
			$(element).parent().find('span').html('*').removeClass('reg_succ').addClass('star');
		},
		unhighlight:function(element,errorClass){
			$(element).css('border','1px solid #ccc');
			$(element).parent().find('span').html('&nbsp').addClass('reg_succ');
		},
		errorLabelContainer:'ol.reg_error',
		wrapper:'li',
		rules:{
			username:{//用户名长度是4-25
				required:true,
				
				rangelength:[4,25],
			},
			password:{
				required:true,
				rangelength:[6,16],
			},
			password1:{
				required:true,
				rangelength:[6,16],
				equalTo:"#password",
			},
			email:{
				required:true,
				email:true,
			}
			
		},
		messages:{
			username:{
				required:'请输入用户名',
				rangelength:'用户名必须是{0}到{1}位字符',
			},
			password:{
				required:'请输入密码',
				rangelength:'密码必须是{0}-{1}位字符',
			},
			password1: {
				required: "请输入确认密码",
				rangelength:'密码必须是{0}-{1}位字符',
				equalTo: "两次输入密码不一致不一致"
			},
			email:{
				required:'邮箱不能为空',
				email: "请输入正确的email地址"
			}
		},
	});
/*	
	$('#reg input[title]').tooltip({
		position:{
			my:'left+5 center',
			at:'right center'
		
		},
		tooltipClass:'tooltip_font',
			
	});
	*/
	$('#birthday').datepicker({
		dateFormat:'yy-mm-dd',
		changeMonth:true,
		changeYear:true,
		showButtonPanel:true,
		yearRange:'1950:2020',
		showOn:'both',
		buttonImage:'/LifeOA/img/calendar.gif',
		buttonImageOnly:true,
		
	});
	/*邮箱自动完成*/
	$('#email').autocomplete({
		autoFocus:true,
		delay:0,
		source:function(request,response){
			var hosts=['qq.com','126.com','163.com','sina.com.cn','google.com','yahu.com','sohu.com','gmail.com','hotmail.com'],
			term=request.term,
			name=term,
			result=[],
			host='',
			index = term.indexOf('@');
			//首先分几种情况，第一种是还没有输入@的，第二种是已经输了@的，第二种还有两种可能，一种是数据源有的，另一种是没有的
			//还有一种是，已经符合上面第二种的，但是数据源没有匹配到该类型，则返回输入的数据
			name=index>-1?term.slice(0,index):term;
			host=term.slice(index+1);
			
			//第一种，如果是没有输入@的，直接返回数据源
			var findResult=[];
			if(index<0){
				findResult=hosts;
			}else{
			
				//第二种是，如果输入了@的，host不为空的，直接查找含有host的数据源，否则返回所有数据源
				
				findResult=(host=='')?hosts:$.grep(hosts,function(value,index){
						
						return value.indexOf(host)>-1;
					});
				if(findResult==''){
					findResult.push(host);
				}
			}
			//最后在同一加上域名
			result=$.map(findResult,function(value,index){
				return name+'@'+value;
			});
			
			response(result);
			
		}
	});
});