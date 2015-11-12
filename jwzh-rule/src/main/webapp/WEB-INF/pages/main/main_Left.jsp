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
</head>
<body>
<h2>系统管理</h2><br/>
<a href="<%=contextPath%>/ruleSys/serviceManager" target="main_center">服务管理</a><br><br>
<a href="<%=contextPath%>/ruleSys/groupManager" target="main_center">分组管理</a><br><br>
<h2>规则管理</h2><br>
<a href="<%=contextPath%>/ruleManager/ruleListQuery" target="main_center">规则查询</a><br><br>
<a href="<%=contextPath%>/ruleManager/ruleManager" target="main_center">规则编缉</a><br><br>
<a href="<%=contextPath%>/ruleManager/ruleHisManager" target="main_center">已归档规则</a>
</body>
</html>