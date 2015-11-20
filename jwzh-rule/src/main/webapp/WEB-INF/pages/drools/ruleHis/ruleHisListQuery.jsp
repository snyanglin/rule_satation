<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/include.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
var currentIndex=0;
function onPageChange(pageNum){
	//防止死循环
	if(pageNum==currentIndex)
		return;
	currentIndex=pageNum;
	
	var ruleid='${Entity.ruleid}';	
	
	var paramPairs=[
					new ParamPair("pageIndex",pageNum),
	 				new ParamPair("ruleid",ruleid)	 		 		
	];
	var url="<%=contextPath%>/ruleManager/getRuleHisList";
	postToServer(paramPairs,url,function(data){ 			
		if(data)
			$("#resListTab").html(data);
	}); 		
	
	
	//document.dataForm.submit();
}

function detail(version){
	window.location.href="<%=contextPath%>/ruleManager/ruleHisQuery?version="+version;
}
</script>
</head>
<body onload="onPageChange(1)">
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
	<div class="navbar-header">
      <a class="navbar-brand" href="<%=contextPath%>/ruleManager/ruleHisManager">已归档规则列表</a>
   </div>
   <div>
      <ul class="nav navbar-nav">
         <li class="active"><a href="#">规则版本列表</a></li>           
      </ul>
   </div>
</nav>
<div class="mainDiv">
<div class="layoutDiv">
<div class="well well-lg">

<span id="resListTab">
<div class="panel panel-default">
   <div class="panel-heading">规则版本列表</div>   	
	<table class="table">				
			<tr><th>服务名</th><th>规则分组</th><th>规则文件名称</th><th>版本号</th><th>操作</th></tr>			
	</table>
</div>
</span>
		
</div>
</div>
</div>
</body>
</html>