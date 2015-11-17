<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/commonInclude.jsp"%>
<%@ page import="com.founder.framework.config.SystemConfig"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>新增分组</title>
		<script type="text/javascript" src="<%=contextPath%>/js/groupManager/groupAdd.js"></script>
	</head>
	<body>
		<form id="dataForm" method="post">
			<div>   
		        <label for="groupname">分组名称:</label>   
		        <input class="easyui-validatebox" type="text" name="groupname" data-options="required:true" />   
		    </div>   
		    <div>   
		        <label for="serviceid">服务ID:</label>   
		        <input class="easyui-validatebox" type="text" name="serviceid" data-options="" />   
		    </div>
		</form>
	</body>
</html>