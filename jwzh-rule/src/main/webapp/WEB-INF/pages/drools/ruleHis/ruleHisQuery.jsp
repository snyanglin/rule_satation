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
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
	<div class="navbar-header">
      <a class="navbar-brand" href="<%=contextPath%>/ruleManager/ruleHisManager">已归档规则列表</a>
   </div>
   <div>
      <ul class="nav navbar-nav">
         <li><a href="<%=contextPath%>/ruleManager/ruleHisListQuery?ruleid=${ruleHis.ruleid}">规则版本列表</a></li>           
      </ul>
      <ul class="nav navbar-nav">
         <li class="active"><a href="#">规则详情</a></li>           
      </ul>
   </div>
</nav>
<div class="mainDiv">
<div class="layoutDiv">
<div class="well well-lg">

<div class="panel panel-default">
   <div class="panel-heading">规则详情</div>
	<table class="table">
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