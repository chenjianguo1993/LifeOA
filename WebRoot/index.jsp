<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!doctype html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="Keywords" content="尚讯信息技术有限公司">
<meta name="description" content="尚讯信息技术有限公司">
<meta http-equiv="Content-Language" content="zh-cn">
<meta name="robots" content="all">
<meta name="author" content="shangcent">
<meta name="Copyright" content="shangcent">
<title>家庭办公系统-首页</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/index/index.css"
	type="text/css">
<link href="${pageContext.request.contextPath}/css/index/global.css"
	type="text/css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/index/home.css"
	type="text/css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/index/my_nav.css"
	type="text/css" rel="stylesheet">
<!-- 注册表单样式 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/register/style.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/css/smoothness/jquery.ui.css"
	type="text/css" />
<!-- 注册表单样式 -->
<script src="${pageContext.request.contextPath}/js/jquery.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/nav.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/index/jquery-ws.js"
	type="text/javascript"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery.validate.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery.form.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery.ui.js"></script>
<script src="${pageContext.request.contextPath }/js/reg.js"
	type="text/javascript"></script>
<script type="text/javascript">
	var banner_currId = 0;
	var imgCount;
	var timeout;
	var interval = 2500;
	$(function() {
		imgCount = $(".h_banner .imgs li").length;
		$(".h_banner .imgs li:gt(0)").hide();
		$(".h_banner .num li:eq(0)").addClass("curr");
		timeout = setTimeout("banner_change(1)", interval);
		$(".h_banner .num li").click(function() {
			cid = parseInt($(this).attr("id"));
			clearTimeout(timeout);
			banner_change(cid);
		});
	});
	function banner_change(currId) {
		banner_currId = currId;
		$(".h_banner .imgs li:visible").fadeOut(1500);
		$(".h_banner .imgs li").eq(currId).fadeIn(1500);
		$(".h_banner .num li").removeClass("curr");
		$(".h_banner .num li").eq(currId).addClass("curr");
		banner_currId++;
		if (banner_currId >= imgCount)
			banner_currId = 0;
		timeout = setTimeout("banner_change(banner_currId)", interval);
	}
</script>
</head>
<body>
	<div id="shang_z">
		<!-- 导航条 -->
		<%@ include file="/public/nav.jsp"%>
		<!-- 提示 -->
		<div id="tips"
			style="display:none;width:200px;height:100px;position: absolute;margin-left: 400px;margin-top: 350px;"></div>
		<div class="h_banner">
			<ul class="imgs">
				<li style="display: none;"><a href="#"><img height="338"
						alt="Wondershare PDF Editor"
						src="${pageContext.request.contextPath}/images/index/1.jpg"
						width="1000" border="0"> </a>
				</li>
				<li style="display: none;"><a href="#"><img height="338"
						alt="Wondershare Video Editor for Windows"
						src="${pageContext.request.contextPath}/images/index/3.jpg"
						width="1000" border="0"> </a>
				</li>
				<li style="display: none;"><a href="#"><img height="338"
						alt="Wondershare Awarded for Ecommerce Excellent"
						src="${pageContext.request.contextPath}/images/index/4.jpg"
						width="1000" border="0"> </a>
				</li>
				<li style="display: none;"><a href="#"><img height="338"
						alt="Wondershare Awarded for Ecommerce Excellent"
						src="${pageContext.request.contextPath}/images/index/5.jpg"
						width="1000" border="0"> </a>
				</li>
			</ul>
			<ul class="num">
				<li id="0" class="curr">1</li>
				<li id="1" class="">2</li>
				<li id="2" class="">3</li>
				<li id="3" class="">4</li>
			</ul>
		</div>

		<div id="wb">
			<div id="wb_z">
				<div class="wb_z01">
					<div class="wb_zz01">
						<img
							src="${pageContext.request.contextPath}/images/index/think_25.jpg">
					</div>
					<div class="wb_zz02">
						自动充值充值系统：话费、游戏、QQ产品等自动充值。支付离线托管，3级代理提成等。<br>
						支持淘宝、拍拍监控，自动发货及自动评价。
					</div>
					<div class="wb_zz03">
						<img
							src="${pageContext.request.contextPath}/images/index/think_37.jpg">
					</div>
				</div>
				<div class="wb_z02 ">
					<div class="wb_zz01">
						<img
							src="${pageContext.request.contextPath}/images/index/think_27.jpg">
					</div>
					<div class="wb_zz02">
						深圳市宝民职业技术学校：学生综合信息管理系统<br> 东莞顺达印刷厂：送货单管理系统 <br>
						东莞豪门大饭店：会员管理系统 <br> 湖南天地伟业数码科技：门票管理系统<br>
					</div>
					<div class="wb_zz03">
						<img
							src="${pageContext.request.contextPath}/images/index/think_38.jpg">
					</div>
				</div>
				<div class="wb_z03 ">
					<div class="wb_zz01">
						<img
							src="${pageContext.request.contextPath}/images/index/think_29.jpg">
					</div>
					<div class="wb_zz02">
						广东某健康科技有限公司：质辨识系统(客户端、后台管理、网站) <br> 中山华龙：网上订单管理系统<br>
					</div>
					<div class="wb_zz03">
						<img
							src="${pageContext.request.contextPath}/images/index/think_39.jpg">
					</div>
				</div>
			</div>
			<div id="wb_y">
				<div class="wb_y01 ">
					<div class="wb_zz01">
						<img
							src="${pageContext.request.contextPath}/images/index/think_31.jpg">
					</div>
					<div class="wb_zz02">
						<div class="xw">
							<div class="xw_z">新闻</div>
							<div class="xw_z_c">东莞模纹国际生产绩效管理系统</div>
						</div>
						<div class="xw">
							<div class="xw_z">新闻</div>
							<div class="xw_z_c">湖南天地伟业数码科技：门票管理系统</div>
						</div>
						<div class="xw">
							<div class="xw_z" style="background-color:#97ce28">荣誉</div>
							<div class="xw_z_r">九州软件作品获得国家版权保护中心著作权证书</div>
						</div>
					</div>
					<div class="wb_zz03">
						<img
							src="${pageContext.request.contextPath}/images/index/think_55.jpg"
							width="236" height="89">
					</div>
				</div>
			</div>
		</div>
		<div id="bottom">
			<div>
				<span>电话：15920546288&nbsp;&nbsp; QQ：<a
					href="http://wpa.qq.com/msgrd?v=3&amp;uin=535331397&amp;site=qq&amp;menu=yes"
					target="_blank"><strong>707069877</strong>
				</span></a>
			</div>
			<div>
				<span class="STYLE1">电子邮箱:&nbsp;&nbsp;<strong>707069877@qq.com</strong>
				</span>
			</div>
			<div>Copyright © 2016 - 2019 LongTeng All Rights Reserved.
				广州龙腾科技有限公司 版权所有</div>
		</div>
	</div>

</body>
</html>
