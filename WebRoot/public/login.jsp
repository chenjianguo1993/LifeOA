<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>家庭办公-登陆</title>
<%@ include file="/public/common.jspf" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login/style.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login/body.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login/login.css" />
 <script type="text/javascript" src="${pageContext.request.contextPath}/js/login/login.js"></script>
</head>
<body>
	<div class="container">
		<section id="content">
		<form id="loginForm" onsubmit="return login()" method="post">
			<h1>会员登录</h1>
			<div>
				<input type="text" name="username" placeholder="用户名" required="" id="username" />
			</div>
			<div>
				<input type="password" name="password" placeholder="密码" required="" id="password" />
			</div>
			<div class="login_error_tips" >
				<span class="help-block u-errormessage" id="js-server-helpinfo">&nbsp;</span>
			</div>
			<div>
				<!-- <input type="submit" value="Log in" /> -->
				<input type="submit" value="登录" class="btn btn-primary"
					id="js-btn-login" /> <a href="#">忘记密码?</a>
				<!-- <a href="#">Register</a> -->
			</div>
		</form>
		<!-- form -->
		<div class="button">
			<span class="help-block u-errormessage" id="js-server-helpinfo">&nbsp;</span>
			<a href="#">下载网盘</a>
		</div>
		<!-- button --> </section>
		<!-- content -->
	</div>
	<!-- container -->
</body>
</html>