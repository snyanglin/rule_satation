<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>规则平台</title>
<script type="text/javascript" src="<%=basePath%>/js/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/bootstrap/js/bootstrap.min.js"></script>

<link rel="stylesheet" type="text/css" href="<%=basePath%>/bootstrap/css/bootstrap.min.css"></link>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/main.css"></link>

<style type="text/css">

.foot{
	height: 60px;
	margin-bottom:0px;
}
.copyright{
	margin:0 auto;
	vertical-align: middle;
	width: 200px;
}
.copyright span{
	margin:0 auto;
	font-size: 8pt;
}
</style>
</head>

<body >
<div class="mainDiv">
<div class="layoutDiv">
<div class="well well-lg">
	
	<form id="form1"  align="center" name="loginform" method="post" action="<%=basePath%>founderRule/login">
		<h1 align="center" class="menu">方正规则平台</h1>
		<br/><br/><br/>
		<div align="center">
		<div style="width:300px">
			<div>
				<input type="text" id="userName" name="userName" value="" maxlength="50"  class="form-control" placeholder="请输入用户名"  />
			</div>
			<br/>
			<div>	
				<input type="passWord" id="passWord" name="passWord" value="" maxlength="50"  class="form-control" placeholder="请输入密码"  />
			</div>
			 <br/>
			<div class="btn-group btn-group-lg">
				<input class="btn btn-success" type="submit" name="submitBtn" value="登 录" />	
			</div>
		</div>
		</div>
	</form>
	<br /><br /><br />
	<div class="foot">
		<div class="copyright"><span>Copyright©2015  方正国际 版权所有 </span></div>
	</div>
</div>
</div>
</div>
	
</body>


</html>