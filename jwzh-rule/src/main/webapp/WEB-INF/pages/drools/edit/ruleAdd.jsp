<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/commonInclude.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
 	
</script>
</head>
<body>
<form action="<%=basePath%>ruleManager/ruleAdd"  id="dataForm" name="dataForm" method="post" >
<!-- 隐藏区域 -->

<table width="90%">	
	<tr>
		<td align="center">
			<table border="0" width="90%">
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
		</td>
	</tr>
	<tr>
		<td align="center">
			<input type="submit" value="新增" id="addButton" />								
		</td>
	</tr>
</table>
</form>
</body>
</html>