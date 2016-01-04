<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>规则平台</title>
</head>
<body>
	<div>
		<h1 align="center">登录失败！！！</h1>
		<h3 color="red" align="center">请检查后重新输入！5后跳转到登录页面</h3>
		<meta http-equiv="Refresh" content="5;url=<%=basePath%>founderRule/index">
		<div align="center"><a href="<%=basePath%>founderRule/index">如果没有跳转，请点这里！</a></div>
		
	</div>

</body>
</html>