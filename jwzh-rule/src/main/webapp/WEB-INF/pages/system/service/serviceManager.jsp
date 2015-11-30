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
	
	var urlid=$("#urlid").val();
	if(urlid == "")
		return;
	
	var paramPairs=[
	 				new ParamPair("pageIndex",pageNum),	 		 		
	 		 		new ParamPair("urlid",urlid) 		 		
	];
	var url="<%=contextPath%>/serviceManager/getServiceManagerList";
	postToServer(paramPairs,url,function(data){ 			
		if(data)
			$("#resListTab").html(data);
	}); 		
			
}

function editUrl(id){
	window.location.href="<%=contextPath%>/serviceManager/serviceEditPre?id="+id;
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
      <a class="navbar-brand" href="<%=contextPath%>/serviceManager/serviceManager">服务列表</a>
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
			<span class="input-group-addon">服务地址</span>
			<select id="urlid" name="urlid" class="form-control" onchange="doIt()">
				<c:forEach items="${UrlList}" var="item" varStatus="status">
				<option value="${item.id }">${item.urlname}</option>
			</c:forEach>
								
			</select>
			</div>
		</td>
		<td>
			<button type="button" class="btn btn-success" onclick="location.href='<%=contextPath%>/serviceManager/serviceAddPre'" >新 增</button>
		</td>
		
	</tr>
</table>
</div>
<br />

<span id="resListTab">
<div class="panel panel-default">
   <div class="panel-heading">查询结果</div>
	<table class="table">
	<tr><th>服务名称</th><th>服务说明</th><th>操作</th></tr>
	
	</table>
</div>
</span>

</form>

<div class="Hint">
<p>1.切换“服务地址”可自动查询该地址下的所有服务。</p>
<p>2.如果没有“服务地址”可选，请在“地址管理”中添加地址。</p>
</div>

</div>
</div>
</div>
</body>
</html>