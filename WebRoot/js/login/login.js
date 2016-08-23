//登陆
function login(){
	$.post("/HISWeb/servlet/LoginServlet", $("#loginForm").serialize(),//这是form的 id
			  function( msg ) {
				var data = eval("("+msg+")");
					if(data.message == "login_success")
					{
						//1.登陆成功，跳转到网站首页
						window.location.href='/HISWeb/servlet/IndexUIServlet';
					}else if(data.message=="login_failure")
					{
						//将出错信息打印在登录表单下方
						$(".login_error_tips").text("您的密码有误,请重新输入！");
						$(".login_error_tips").css("fontSize","12px");
						$(".login_error_tips").css("fontWeight","bold");
						$(".login_error_tips").css("color","red");
					}else if(data.message=="none_user")
					{
						//将出错信息打印在登录表单下方
						$(".login_error_tips").text("您的用户名有误,请重新输入！");
						$(".login_error_tips").css("fontSize","12px");
						$(".login_error_tips").css("fontWeight","bold");
						$(".login_error_tips").css("color","red");
					}
			  });	
			return false;
}
