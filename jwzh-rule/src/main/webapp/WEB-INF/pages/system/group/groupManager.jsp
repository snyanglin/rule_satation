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
	
	var paramPairs=[
	 				new ParamPair("pageIndex",pageNum)	
	 		 				 		
	];
	
	var groupname=$("#groupname").val();	
	if(groupname != ""){
		paramPairs[paramPairs.length] = new ParamPair("groupname",groupname);		
	}
	
	
	var url="<%=contextPath%>/groupManager/getGroupManagerList";
	postToServer(paramPairs,url,function(data){ 			
		if(data)
			$("#resListTab").html(data);
	}); 		
			
}

function edit(id){
	window.location.href="<%=contextPath%>/groupManager/groupEditPre?id="+id;
}

function doIt(){
	currentIndex=0;	
	onPageChange(1);
}
</script>
</head>
<body onload="doIt()">
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
	<div class="navbar-header">
      <a class="navbar-brand" href="<%=contextPath%>/groupManager/groupManager">分组列表</a>
   </div>	
</nav>
<div class="mainDiv">
<div class="layoutDiv">
<div class="well well-lg">

<form  id="dataForm" name="dataForm" method="post" >

<div class="panel panel-default">
   <div class="panel-heading">查询条件</div>
	<table class="table table-bordered">
	<tr>		
		<td width="50%">
			<div class="input-group">			
			<span class="input-group-addon">分组名称</span>			
			<input type="text" id="groupname" name="groupname" value="" maxlength="100" class="form-control" />
			</div>
		</td>
		<td>
			<button type="button" class="btn btn-default" onclick="doIt()">查 询</button>	
			<button type="button" class="btn btn-default" onclick="location.href='<%=contextPath%>/groupManager/groupAddPre'" >新 增</button>
		</td>
		
	</tr>
</table>
</div>

<br />

<span id="resListTab">
<div class="panel panel-default">
   <div class="panel-heading">查询结果</div>
	<table class="table">
	<tr><th>分组名称</th><th>备注</th><th>操作</th></tr>
	
	</table>
</div>
</span>

</form>
</div>
</div>
</div>
</body>
</html>