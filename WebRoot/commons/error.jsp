<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ page import="org.apache.commons.logging.LogFactory" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="content">
	<%
		//Exception from JSP didn't log yet ,should log it here.
		String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
		LogFactory.getLog(requestUri).error(exception.getMessage(), exception);
	%>

	<h3>System Runtime Error: <br><%=exception.getMessage()%>
	</h3>
	<font color='red'>${message }</font>
	<br>


	<br>

	<p><a href="#" ><!-- 获取详细的错误信息. --></a></p>

	<div id="detail_error_msg" style="">
		<pre><%exception.printStackTrace(new java.io.PrintWriter(out));%></pre>
	</div>
</div>
