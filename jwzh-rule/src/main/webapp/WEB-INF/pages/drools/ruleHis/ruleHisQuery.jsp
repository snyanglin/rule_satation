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
<div class="well well-lg">

<div class="panel panel-default">
   <div class="panel-heading">规则详情</div>
	<table class="table">
				<tr>
					<th>服务名：</th>
					<td>${ruleHis.servicename}</td>
				</tr>
				<tr>
					<th>服务地址：</th>
					<td>${ruleHis.serviceurl}</td>
				</tr>
				<tr>
					<th>服务方法：</th>
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
					<td colspan="2">
						<pre class="pre-scrollable">${ruleHis.content}</pre>						
					</td>
				</tr>
			</table>
		</td>		
	</tr>
	</table>
</div>

</div>
</div>
</div>
</body>
</html>