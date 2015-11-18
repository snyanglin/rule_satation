<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/include.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
 	
</script>
</head>
<body>
<div class="mainDiv">
<div class="layoutDiv">


			<table class="listTab">
			<tr><th>服务名</th><th>规则分组</th><th>规则文件名称</th><th>版本号</th><th>操作</th></tr>
			<c:forEach items="${List}" var="item" varStatus="status">
				<tr>
				<td><c:out value="${item.servicename }" /></td>
				<td><c:out value="${item.groupname }" /></td>
				<td><c:out value="${item.rulefilename }" /></td>
				<td><c:out value="${item.version }" /></td>	
				<td><a href="<%=contextPath%>/ruleManager/ruleHisQuery?version=${item.version}">详情</a></td>
				</tr>
			</c:forEach>
			</table>
		
</div>
</div>
</body>
</html>