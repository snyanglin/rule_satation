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
<script type="text/javascript" >

</script>
</head>
<body onload="init()">
<ul>
<li class="menuf">系统管理</li>
<li>
	<ul>
		<li class="menu" onclick="menuClick('<%=contextPath%>/urlManager/urlManager')">
			地址管理
		</li>
		<li class="menu" onclick="menuClick('<%=contextPath%>/serviceManager/serviceManager')">
			服务管理
		</li>
		<li class="menu" onclick="menuClick('<%=contextPath%>/methodManager/methodManager')">
			方法管理
		</li>
		<li class="menu" onclick="menuClick('<%=contextPath%>/groupManager/groupManager')">
			分组管理
		</li>
	</ul>
</li>
<li class="menuf">规则管理</li>
<li>
	<ul>
		<li class="menu" onclick="menuClick('<%=contextPath%>/ruleManager/ruleListQuery')">
			规则查询
		</li>
		<li class="menu" onclick="menuClick('<%=contextPath%>/ruleManager/ruleManager')">
			规则编缉
		</li>
		<li class="menu" onclick="menuClick('<%=contextPath%>/ruleManager/ruleHisManager')">
			已归档规则
		</li>
	</ul>
</li>
<li class="menuf">导入导出</li>
<li>
	<ul>
		<li class="menu" onclick="menuClick('<%=contextPath%>/ruleExOrIm/ruleExportPre')">
			规则导出
		</li>
		<li class="menu" onclick="menuClick('<%=contextPath%>/ruleExOrIm/ruleImportPre')">
			规则导入
		</li>		
	</ul>
</li>
</ul>
</body>
</html>
