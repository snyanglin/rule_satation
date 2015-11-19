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
<div class="well well-lg">

<form action="<%=basePath%>ruleManager/ruleManager"  id="dataForm" name="dataForm" method="post" >

<div class="panel panel-default">
   <div class="panel-heading">查询条件</div>
	<table class="table table-bordered">
	<tr>		
		<td width="50%">
			<div class="input-group">			
			<span class="input-group-addon">规则分组</span>	
			<select id="groupid" name="groupid" class="form-control">
				<option value="20151109145800">重点人员</option>
			</select>
			</div>
		</td>		
		<td>
			<div class="input-group">			
			<span class="input-group-addon">规则文件名称</span>			
			<input type="text" id="rulefilename" name="rulefilename" value="" maxlength="50" class="form-control" />
			</div>
		</td>
		
	</tr>
</table>
</div>

<div align="center">
	<button type="submit" class="btn btn-default">查 询</button>	
	
	<button type="button" class="btn btn-default" onclick="location.href='<%=contextPath%>/ruleManager/ruleAddPre'" >新 增</button>
</div>
<br />

<div class="panel panel-default">
   <div class="panel-heading">查询结果</div>
	<table class="table">
	<tr><th>规则分组</th><th>规则文件名称</th><th>备注</th><th>操作</th></tr>
	<c:forEach items="${List}" var="item" varStatus="status">
		<tr><td><c:out value="${item.groupname }" /></td>
		<td><c:out value="${item.rulefilename }" /></td>
		<td><c:out value="${item.bz }" /></td>
		<td><a href="<%=contextPath%>/ruleManager/ruleEditPre?rulefilename=${item.rulefilename}">编辑</a></td></tr>
	</c:forEach>
	</table>
</div>

</form>
</div>
</div>
</div>
</body>
</html>