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
	
	var groupid=$("#groupid_old").val();
	if(groupid == "")
		groupid=$("#groupid").val();
	
	var rulefilename=$("#rulefilename_old").val();
	if(rulefilename == "")
		rulefilename=$("#rulefilename").val();
	
	var paramPairs=[ 
	                
	 				new ParamPair("pageIndex",pageNum),	 		 		
	 		 		new ParamPair("rulefilename",rulefilename),
	 		 		new ParamPair("groupid",groupid)	 		 		
	];
	var url="<%=contextPath%>/ruleManager/getRuleManagerList";
	postToServer(paramPairs,url,function(data){ 			
		if(data)
			$("#resListTab").html(data);
	}); 		
			
}

function editRule(rulefilename){
	window.location.href="<%=contextPath%>/ruleManager/ruleEditPre?rulefilename="+rulefilename;
	
}

function doIt(){
	currentIndex=0;
	$("#rulefilename_old").val("");
	$("#groupid_old").val("");
	onPageChange(1);
}
</script>
</head>
<body onload="doIt()">
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
	<div class="navbar-header">
      <a class="navbar-brand" href="<%=contextPath%>/ruleManager/ruleManager">规则列表</a>
   </div>	
</nav>
<div class="mainDiv">
<div class="layoutDiv">
<div class="well well-lg">

<form action="<%=basePath%>ruleManager/ruleManager"  id="dataForm" name="dataForm" method="post" >
<input type="hidden" name="groupid_old" id="groupid_old" value="" />
<input type="hidden" name="rulefilename_old" id="rulefilename_old" value="" />

<div class="panel panel-default">
   <div class="panel-heading">查询条件</div>
	<table class="table table-bordered">
	<tr>		
		<td width="50%">
			<div class="input-group">			
			<span class="input-group-addon">规则分组</span>	
			<select id="groupid" name="groupid" class="form-control">
				<c:forEach items="${GroupList}" var="item" varStatus="status">
				<option value="<c:out value="${item.id }" />"><c:out value="${item.groupname}" /></option>
				</c:forEach>
			</select>
			</div>
		</td>		
		
		<td>
			<div class="input-group">			
			<span class="input-group-addon">规则文件名称</span>			
			<input type="text" id="rulefilename" name="rulefilename" value="" maxlength="50" class="form-control" placeholder="最长50个字符" />
			</div>
		</td>
		
	</tr>
</table>
</div>

<div align="center">
	<button type="button" class="btn btn-info" onclick="doIt()">查 询</button>	
	
	<button type="button" class="btn btn-success" onclick="location.href='<%=contextPath%>/ruleManager/ruleAddPre'" >新 增</button>
</div>
<br />

<span id="resListTab">
<div class="panel panel-default">
   <div class="panel-heading">查询结果</div>
	<table class="table">
	<tr><th>规则分组</th><th>规则文件名称</th><th>备注</th><th>操作</th></tr>
	
	</table>
</div>
</span>

</form>
</div>
</div>
</div>
</body>
</html>