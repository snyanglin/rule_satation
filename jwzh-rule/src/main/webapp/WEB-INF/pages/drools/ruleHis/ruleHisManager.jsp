<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/include.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
 	function queryRule(){
 		
 	}
</script>
</head>
<body>
<div class="mainDiv">
<div class="layoutDiv">
<form action="<%=basePath%>ruleManager/ruleHisManager"  id="dataForm" name="dataForm" method="post" >

<table  class="queryTab">
	<tr>
		<th width="20%">规则分组</th>
		<td width="30%">
			<select id="groupid" name="groupid" class="inputText">
				<option value="20151109145800">重点人员</option>
			</select>
		</td>
		<th  width="20%">规则文件名称</th>
		<td>
			<input type="text" id="rulefilename" name="rulefilename" value="" maxlength="50" class="inputText" />			
		</td>
		
	</tr>
</table>

<div class="queryButtonDiv">
	<input type="submit" value="查询" />	
</div>

<table class="listTab">
<tr><th>服务名</th><th>规则分组</th><th>规则文件名称</th><th>操作</th></tr>
<c:forEach items="${List}" var="item" varStatus="status">
	<tr>
	<td><c:out value="${item.servicename }" /></td>
	<td><c:out value="${item.groupname }" /></td>
	<td><c:out value="${item.rulefilename }" /></td>	
	<td><a href="<%=contextPath%>/ruleManager/ruleHisListQuery?ruleid=${item.ruleid}">查看</a></td>
	</tr>
</c:forEach>
</table>

</form>
</div>
</div>
</body>
</html>