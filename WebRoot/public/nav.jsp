<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- 导航条 -->
<div id="top">
	<div id="shang_logo">
		<div id="shang_logo_z">

			<!-- 用户未登陆时 -->
			<c:if test="${userInfo==null}">
				<div id="none_login">
					<!-- 登陆文本 -->
					<div id="login">
						<a class="none_login_a" style="text-decoration:none;"
							href="${pageContext.request.contextPath}/common/loginAndReg_toLogin.action">登陆&nbsp</a>
					</div>
					<!-- 注册文本 -->
					<div id="register">
						<a id="reg_a" style="text-decoration:none;" class="none_login_a"
							href="#">注册</a>
					</div>
				</div>
			</c:if>
			<!-- 用户登陆时 -->
			<c:if test="${userInfo!=null}">
				<div id="have_login">
					<!-- 用户头像 -->
					<div id="headimage">
						<img id="user_headimage" alt=""
							src="${pageContext.request.contextPath}${userInfo.image}">
					</div>
					<!-- 用户名字 -->
					<div id="username">
						<a class="have_login_a" href=""
							style="text-decoration:none;color:#4191CE">${userInfo.userName}</a>
					</div>
					<c:if test="${user.usertype=='医生'}">
						<c:set var="usertype" value="doctor" />
					</c:if>
					<c:if test="${user.usertype=='护士'}">
						<c:set var="usertype" value="nurse" />
					</c:if>
					<!-- 个人中心 -->
					<div id="personal_center">
						<a class="have_login_a"
							href="${pageContext.request.contextPath }/servlet/PersonalCenter_Servlet?usertype=${usertype}"
							style="text-decoration:none;color:#4191CE">个人中心</a>
					</div>
					<!-- 用户类型 -->
					<div id="usertype"
						style="float:left;height:53px;font-size: 18px;margin-top: 20px;color: #459ADD;font-weight: bold">
						类型:${user.usertype}</div>
					<!-- 退出登陆 -->
					<div id="logout"
						style="float:left;height:53px;font-size: 18px;margin-left:10px;margin-top: 20px;color: #459ADD;font-weight: bold">
						<a style="text-decoration:none;"
							href="${pageContext.request.contextPath}/servlet/Logout_Servlet">退出</a>
					</div>
				</div>
			</c:if>
		</div>
	</div>
	<div id="shang_nav">
		<ul>
			<li style="padding-left:30px;"><a
				href="${pageContext.request.contextPath}/servlet/IndexUIServlet"><div
						class="dh">首页</div> </a></li>
			<li class="dh"><a onclick="outpatient()">房屋管理</a></li>
			<li class="dh"><a onclick="doctordepartment()">膳食统计</a></li>
			<li class="dh"><a
				href="${pageContext.request.contextPath}/servlet/DrugHouseManagerServlet">旅行郊游</a>
			</li>
			<li class="dh"><a
				href="${pageContext.request.contextPath}/servlet/DrugRepositoryServlet">医疗卫生</a>
			</li>
			<li class="dh"><a
				href="${pageContext.request.contextPath}/servlet/FincialSearchServlet">财务收支</a>
			</li>
			<li class="dh"><a onclick="systemmanager()">聊天交友</a></li>
		</ul>
	</div>
</div>
<!-- 注册表单 -->
<form style="display:none" title="会员注册" id="reg" method="post">
	<ol class="reg_error">

	</ol>
	<p>
		<label for="username" style=""> &nbsp;&nbsp;&nbsp;用户名:</label> <input type="text" class="text"
			name="userName" id="username"></input><span class="star">*</span>
	</p>
	<p>
		<label for="password"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;密 码:</label> <input type="password"
			class="text" name="userPassword" id="password"></input><span class="star">*</span>
	</p>
	<p>
		<label for="password1">确认密码:</label> <input type="password"
			class="text" name="password1" id="password1"></input><span
			class="star">*</span>
	</p>
	<p>
		<label for="email">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;邮 箱:</label> <input type="text" class="text"
			name="email" id="email"></input><span class="star">*</span>
	</p>
	<!--<p>
			<label>性　别：</label>
			<input type="radio" class="sex" checked="checked" name="sex" value="male" id="male"><label for="male">男</label></input>
			<input type="radio" class="sex" name="sex" value="female" id="female"><label for="female">女</label></input>
		</p>-->
	<p>
		<label for="birthday">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;生 日:</label> <input type="text" name="birthday"
			class="text" readonly="readonly" id="birthday"></input>
	</p>
</form>
<!-- 旋转进度条 -->
<div id="loading" style="display:none">数据正在提交中···</div>
