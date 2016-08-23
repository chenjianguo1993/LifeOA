

//系统管理
function systemmanager()
{
	$.post("/HISWeb/servlet/Index_System_ManagerUIServlet",
			  function( data ) 
			  {
				//alert(data);
		 		if(data == "noneprivilege")
		 		{
		 			$("#tips").css("display","inline");
		 			$("#tips").css("border-radius","20px");
		 			$("#tips").css("width","200px");
		 			$("#tips").css("height","100px");
		 			$("#tips").css("fontSize","18px");
		 			$("#tips").css("border","1px");
		 			$("#tips").css("fontWeight","blod");
		 			$("#tips").css("background","red");
		 			$("#tips").text("对不起,您没有访问的该该板块权限");
					$("#tips").fadeIn(2000);
					$("#tips").fadeOut(5000);
		 		}else{
		 			window.location.href="/HISWeb/servlet/Index_System_ManagerServlet";
		 		}
			 	
			  }); 
	return false;
	
}


//医生站
function doctordepartment()
{
	$.post("/HISWeb/servlet/PersonalCenter_Servlet",
			function( data ) 
			{
//		alert(data);
//		alert("222");
		if(data == "noneprivilege")
		{
			$("#tips").css("display","inline");
			$("#tips").css("border-radius","20px");
			$("#tips").css("width","200px");
			$("#tips").css("height","100px");
			$("#tips").css("fontSize","18px");
			$("#tips").css("border","1px");
			$("#tips").css("fontWeight","blod");
			$("#tips").css("color","#F5561C");
			$("#tips").css("background","green");
			$("#tips").text("对不起,您没有访问医生站板块权限");
			$("#tips").fadeIn(2000);
			$("#tips").fadeOut(5000);
		}else{
			window.location.href="/HISWeb/servlet/PersonalCenter_UIServlet";
		}
		
			}); 
	return false;
	
}
//门诊管理
function outpatient()
{
	$.post("/HISWeb/servlet/PersonalCenter_Servlet",
			function( data ) 
			{
//		alert(data);
//		alert("222");
		if(data == "noneprivilege")
		{
			$("#tips").css("display","inline");
			$("#tips").css("border-radius","20px");
			$("#tips").css("width","200px");
			$("#tips").css("height","100px");
			$("#tips").css("fontSize","18px");
			$("#tips").css("border","1px");
			$("#tips").css("fontWeight","blod");
			$("#tips").css("color","#F5561C");
			$("#tips").css("background","green");
			$("#tips").text("对不起,您没有访问门诊管理板块权限");
			$("#tips").fadeIn(2000);
			$("#tips").fadeOut(5000);
		}else{//拥有权限进入门诊管理权限，接下来在servlet访问分针页面
			window.location.href="/HISWeb/servlet/OutpatientUIServlet";
		}
		
			}); 
	return false;
	
}