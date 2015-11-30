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
	
	var urlid=$("#urlid").val();
	var serviceid = $("#serviceid").val();
	if(urlid != "ALL"){
		paramPairs[paramPairs.length] = new ParamPair("urlid",urlid);
		if( serviceid !="ALL"){
			paramPairs[paramPairs.length] = new ParamPair("serviceid",serviceid);
		}
	}
	
	
	var url="<%=contextPath%>/methodManager/getMethodManagerList";
	postToServer(paramPairs,url,function(data){ 			
		if(data)
			$("#resListTab").html(data);
	}); 		
			
}

function edit(id){
	window.location.href="<%=contextPath%>/methodManager/methodEditPre?id="+id;
}

function doIt(){
	currentIndex=0;	
	onPageChange(1);
}

function urlChange(urlid){
	if(urlid=="ALL"){
		$("#serviceSpan").html("<select id=\"serviceid\" class=\"form-control\" ><option value=\"ALL\">*全部*</option></select>");
	}else{
		var paramPairs=[
		 		 		new ParamPair("urlid",urlid),
		 		 		new ParamPair("queryType","0")
		];
		var url="<%=contextPath%>/serviceManager/getServiceSelectList";
		postToServer(paramPairs,url,function(data){ 			
			if(data)
				$("#serviceSpan").html(data);
		}); 
	}
}
</script>
</head>
<body onload="doIt()">
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
	<div class="navbar-header">
      <a class="navbar-brand" href="<%=contextPath%>/methodManager/methodManager">方法列表</a>
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
			<select id="urlid" name="urlid" class="form-control" onchange="urlChange(this.value)">
				<option value="ALL">*全部*</option>
				<c:forEach items="${UrlList}" var="item" varStatus="status">
				<option value="${item.id }">${item.urlname}</option>
				</c:forEach>
			</select>
			</div>
		</td>
		<td>
			<div class="input-group">			
			<span class="input-group-addon">服务</span>
			<span id="serviceSpan">
				<select id="serviceid" class="form-control" ><option value="ALL">*全部*</option></select>
			</span>
			</div>
		</td>
		
	</tr>
</table>
</div>

<div align="center">
	<button type="button" class="btn btn-default" onclick="doIt()">查 询</button>	
	
	<button type="button" class="btn btn-default" onclick="location.href='<%=contextPath%>/methodManager/methodAddPre'" >新 增</button>
</div>
<br />

<span id="resListTab">
<div class="panel panel-default">
   <div class="panel-heading">查询结果</div>
	<table class="table">
	<tr><th>方法名称</th><th>返回结果</th><th>方法说明</th><th>操作</th></tr>
	
	</table>
</div>
</span>

</form>
</div>
</div>
</div>
</body>
</html>