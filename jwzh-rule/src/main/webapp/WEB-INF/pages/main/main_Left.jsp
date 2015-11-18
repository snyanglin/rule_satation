<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.founder.framework.config.SystemConfig"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/css/menu.css"></link>
<script type="text/javascript" src="<%=contextPath%>/js/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/js/menu.js"></script>
</head>
<body onload="init()">
<ul>
<li class="menuf">系统管理</li>
<li>
	<ul>
<li class="menu">
<a href="<%=contextPath%>/ruleSys/serviceManager" target="main_center">服务管理</a>
</li>
<li class="menu">
<a href="<%=contextPath%>/groupManager/groupManager" target="main_center">分组管理</a>
</li>
	</ul>
</li>
<li class="menuf">规则管理</li>
<li>
	<ul>
<li class="menu">
<a href="<%=contextPath%>/ruleManager/ruleListQuery" target="main_center">规则查询</a>
</li>
<li class="menu">
<a href="<%=contextPath%>/ruleManager/ruleManager" target="main_center">规则编缉</a>
</li>
<li class="menu">
<a href="<%=contextPath%>/ruleManager/ruleHisManager" target="main_center">已归档规则</a>
</li>
</div>
</body>
</html>