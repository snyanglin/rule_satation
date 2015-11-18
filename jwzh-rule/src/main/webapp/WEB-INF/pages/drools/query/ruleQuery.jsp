<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/include.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/css/edit.css"></link>
<script type="text/javascript">
 	
</script>
</head>
<body>
<div class="mainDiv">
<div class="layoutDiv">

			<table class="queryTab">
				<tr>
					<th>规则文件名：</th>
					<td>${ruleObj.rulefilename}</td>
				</tr>
				<tr>
					<th>创建时间：</th>
					<td>${ruleObj.createtime}</td>
				</tr>				
				<tr>
					<td align="center" colspan="2">
						<textarea rows="30" cols="100" readonly="readonly">${ruleObj.content}</textarea>
					</td>
				</tr>
			</table>
		
</div>
</div>
</body>
</html>