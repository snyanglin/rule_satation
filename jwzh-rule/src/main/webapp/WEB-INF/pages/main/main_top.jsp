<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/include.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
body{
	width:100%; 
	margin:0; 
	padding:0;
	background:url(../images/top_bg.png);		
	background-size: 80px; 	
}

.title{	
	cursor:pointer;
	font-size:25px;
	text-align:left;
	padding-left:10px;
}
</style>
</head>
<body>
<table width="100%" height="100%" border="0">
<tr>
<td width="125px">
<p class="title" onclick="window.parent.frames['main_center'].location='<%=contextPath%>/forward/main|main_center'">规则平台</p>
</td>
<td>&nbsp;</td>
<td align="right">
	<div href="#" onclick="window.top.location='<%=basePath%>founderRule/logout'" style="margin-right:20px;cursor:pointer;">
		<img alt="退出" src="<%=basePath%>/images/logout.png" width="60px" />
	</div>
</td>
</tr>
</table>	
</body>
</html>