<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>对不起，你访问的页面不存在</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/commons/2048/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/commons/2048/game.js"></script>
<link href="${pageContext.request.contextPath}/commons/2048/style.css" rel="stylesheet" type="text/css" />
<script language="javascript">
$(function(){
	$("#gameArea").G2048(4,4);
});
</script>
</head>

<body>
<p align="center"><font color='blue' size="6">对不起，你访问的页面不存在<br/>玩2048游戏放松下...</font></p>
<div id="gameArea" class="GameArea">

</div>
<table align="center">
<tr><td align="left">
			玩法：<br/>
			1.用键盘上下左右键控制数字走向<br/>
			2.当点击了一个方向时，格子中的数字会全部往那个方向移动，直到不能再移动，如果有相同的数字则会合并<br/>
			3.当格子中不再有可移动和可合并的数字时，游戏结束<br/>
</td></tr>

</table>
</body>
</html>

<script>
  function _isIeLow() {
    var ie = navigator.userAgent.match(/msie (\d+\.\d+)/i);
    if (ie) {
      return parseInt(ie[1]) < 9;
    }
    return false;
  }

  if (_isIeLow()) {
    window.location.href = "error-low.html"+location.search;
  }


</script>
 