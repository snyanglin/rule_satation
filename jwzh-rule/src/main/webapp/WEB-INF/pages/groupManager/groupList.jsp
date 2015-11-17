<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/commonInclude.jsp"%>
<%@ page import="com.founder.framework.config.SystemConfig"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>分组管理</title>
		<script type="text/javascript" src="<%=contextPath%>/js/groupManager/groupList.js"></script>
	</head>
	<body>
		<table id="dg" style="width: 300px"></table>
		
		<div id="dg_tools">
			<a href="#" class="easyui-linkbutton" onclick="GroupList.doAdd()" data-options="iconCls:'icon-add'">新增 </a>
		</div>
		
		<div id="win_add" />
	</body>
</html>