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
	
	var urlname=$("#urlname_old").val();
	if(urlname == "")
		urlname=$("#urlname").val();
	
	var url=$("#url_old").val();
	if(url == "")
		url=$("#url").val();
	
	var paramPairs=[
	 				new ParamPair("pageIndex",pageNum),	 		 		
	 		 		new ParamPair("url",url),
	 		 		new ParamPair("urlname",urlname)	 		 		
	];
	var url="<%=contextPath%>/urlManager/getUrlManagerList";
	postToServer(paramPairs,url,function(data){ 			
		if(data)
			$("#resListTab").html(data);
	}); 		
			
}

function editUrl(id){
	window.location.href="<%=contextPath%>/urlManager/urlEditPre?id="+id;
}

function doIt(){
	currentIndex=0;
	$("#urlname_old").val("");
	$("#url_old").val("");
	onPageChange(1);
}

function validateUrl(url){		 		
	var paramPairs=[			
	 		new ParamPair("url",url)		 		
	];
	var url="<%=basePath%>urlManager/urlValidate";
	postToServer(paramPairs,url,function(data){ 			
	if(data){
		if(data.resStatus == '0'){				
			alert("地址可用");								
		}else{
			alert("地址验证失败："+data.errorMsg);				
		}				
	}else{
		alert("地址验证失败："+data);				
	}		
		 				
	});
} 	
</script>
</head>
<body onload="doIt()">
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
	<div class="navbar-header">
      <a class="navbar-brand" href="<%=contextPath%>/urlManager/urlManager">地址列表</a>
   </div>	
</nav>
<div class="mainDiv">
<div class="layoutDiv">
<div class="well well-lg">

<form  id="dataForm" name="dataForm" method="post" >
<input type="hidden" name="urlname_old" id="urlname_old" value="" />
<input type="hidden" name="url_old" id="url_old" value="" />

<div class="panel panel-default">
   <div class="panel-heading">查询条件</div>
	<table class="table table-bordered">
	<tr>		
		<td width="50%">
			<div class="input-group">			
			<span class="input-group-addon">地址名称</span>			
			<input type="text" id="urlname" name="urlname" value="" maxlength="100" class="form-control" />
			</div>
		</td>
		<td>
			<div class="input-group">			
			<span class="input-group-addon">URL</span>			
			<input type="text" id="url" name="url" value="" maxlength="50" class="form-control" />
			</div>
		</td>
		
	</tr>
</table>
</div>

<div align="center">
	<button type="button" class="btn btn-default" onclick="doIt()">查 询</button>	
	
	<button type="button" class="btn btn-default" onclick="location.href='<%=contextPath%>/urlManager/urlAddPre'" >新 增</button>
</div>
<br />

<span id="resListTab">
<div class="panel panel-default">
   <div class="panel-heading">查询结果</div>
	<table class="table">
	<tr><th>地址名称</th><th>URL</th><th>备注</th><th>操作</th></tr>
	
	</table>
</div>
</span>

</form>
</div>
</div>
</div>
</body>
</html>