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
<table width="90%">	
	<tr>
		<td align="center">
			<table border="1" width="500">
				<tr>
					<th width="40%" align="right">服务名：</th>
					<td>${ruleHis.servicename}</td>
				</tr>
				<tr>
					<th width="40%" align="right">服务地址：</th>
					<td>${ruleHis.serviceurl}</td>
				</tr>
				<tr>
					<th width="40%" align="right">服务方法：</th>
					<td>${ruleHis.servicemethod}</td>
				</tr>
				<tr>
					<th align="right">规则分组：</th>
					<td>${ruleHis.groupname}</td>
				</tr>
				<tr>
					<th align="right">规则文件名：</th>
					<td>${ruleHis.rulefilename}</td>
				</tr>
				<tr>
					<th align="right">版本号：</th>
					<td>${ruleHis.version}</td>
				</tr>				
				<tr>
					<td align="center" colspan="2">
						<textarea rows="30" cols="100" readonly="readonly">${ruleHis.content}</textarea>
					</td>
				</tr>
			</table>
		</td>		
	</tr>
</table>

</body>
</html>