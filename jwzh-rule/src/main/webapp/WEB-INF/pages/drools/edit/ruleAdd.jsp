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
<form action="<%=basePath%>ruleManager/ruleAdd"  id="dataForm" name="dataForm" method="post" >
<!-- 隐藏区域 -->


	<table class="queryTab">
		<tr>
			<th>规则分组</th>
			<td>
				<select id="groupid" name="groupid">
					<option value="20151109145800">重点人员</option>
				</select>
			</td>
		</tr>
		<tr>
			<th>规则文件名称</th>
			<td>
				<input type="text" id="rulefilename" name="rulefilename" value="" maxlength="50" />
			</td>
		</tr>
		<tr>
			<th>备注</th>
			<td>
				<input type="text" id="bz" name="bz" value="" maxlength="100" />
			</td>
		</tr>
	</table>	
	<div class="queryButtonDiv">	
		<input type="submit" value="新增" id="addButton" />								
	</div>
</form>

</div>
</div>
</body>
</html>