<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/menu.css"></link>

</head>

<body >
	<div  align="center">
	<h1 align="center" class="menu">登录界面</h1>
	<form id="form1"  align="center" name="loginform" method="post" action="<%=basePath%>founderRule/login">
		<table width="260" border="1" align="center">
		  <tr>
			<td width="64">用户：</td>
			<td width="180"><input type="text" name="userName" "/></td>
		  </tr>
		  <tr>
			<td>密码：</td>
			<td><input type="password" name="passWord" "/></td>
		  </tr>
		  <tr>
			<td> </td>
			<td>
			  <input type="submit" name="Submit2" " value="登录" />
			  <input type="reset" name="Submit" value="重置" />
			</td>
		  </tr>
		</table>
	</form>
	</div>
</body>


</html>