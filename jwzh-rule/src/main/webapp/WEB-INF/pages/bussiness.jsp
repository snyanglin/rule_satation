<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<title>操作异常</title>
</head>
<body>
 
<div style="margin:50px auto;width:600px;height:400px;text-align:center;">
	<img alt="" src="<%= basePath%>images/buss.png" style="vertical-align:middle">
	<ul style="font-size:14px;color:#666;line-height:23px;text-align:left;">
		<!--  <li>您也可以<a href="<%= basePath%>">点击此处</a>返回系统主页。</li>-->
		<li>${message}</li>
	</ul>
</div>
</body>
</html>