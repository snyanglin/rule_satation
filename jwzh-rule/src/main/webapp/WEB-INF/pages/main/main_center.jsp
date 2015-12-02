<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/include.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
function startup(){
	getNum("<%=basePath%>founderRule/getUrlNum","urlTd");
	getNum("<%=basePath%>founderRule/getServiceNum","serviceTd");
	getNum("<%=basePath%>founderRule/getMethodNum","methodTd");
	getNum("<%=basePath%>founderRule/getGroupNum","groupTd");
	getNum("<%=basePath%>founderRule/getRuleNum","ruleTd");
}

function getNum(url,id){		 		
		
	postToServer("",url,function(data){ 			
	if(data){
		$("#"+id).html(data);			
	}else{
		$("#"+id).html("查询失败");
	}		
		 				
	});
} 	
</script>
</head>
<body onload="startup()">
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
	<div class="navbar-header">
      <a class="navbar-brand" href="#">欢迎使用方正规则平台</a>
   </div>	
</nav>
<div class="mainDiv">
<div class="layoutDiv">
<div class="well well-lg">

<div class="panel panel-default">
   <div class="panel-heading">平台统计</div>
	<table class="table">
	<tr><th>统计类型</th><th>统计结果</th></tr>	
	<tr>
		<td>已注册应用服务器</td>
		<td id="urlTd">查询中，请稍后……</td>
	</tr>
	<tr>
		<td>已注册服务</td>
		<td id="serviceTd">查询中，请稍后……</td>
	</tr>
	<tr>
		<td>已注册方法</td>
		<td id="methodTd">查询中，请稍后……</td>
	</tr>	
	<tr>
		<td>规则分组</td>
		<td id="groupTd">查询中，请稍后……</td>
	</tr>
	<tr>
		<td>已有规则</td>
		<td id="ruleTd">查询中，请稍后……</td>
	</tr>
	</table>	
</div>
	

</div>
</div>
</div>
</body>
</html>