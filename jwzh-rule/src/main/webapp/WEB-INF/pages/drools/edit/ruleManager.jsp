<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/commonInclude.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
 	function editRule(ruleId){
 		
 	}
</script>
</head>
<body>
<table>
	<tr>
		<th>规则分组</th>
		<td>
			<select>
				<option>重点人员</option>
			</select>
		</td>
		<th>规则名称</th>
		<td>
			<input type="text" /><input type="button" value="查询" /><input type="button"  value="新增" />
		</td>
		
	</tr>
</table>
<hr>
<table>
<tr><th>规则分组</th><th>规则名称</th><th>备注</th><th>操作</th></tr>
<c:forEach items="${List}" var="item" varStatus="status">
	<tr><td><c:out value="${item.groupname }" /></td>
	<td><c:out value="${item.rulename }" /></td>
	<td><c:out value="${item.bz }" /></td>
	<td><a href="<%=contextPath%>/ruleManager/ruleEditPre?ruleName=${item.rulename}">编辑</a></td></tr>
</c:forEach>
</table>
</body>
</html>