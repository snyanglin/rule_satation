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
	var url="<%=basePath%>ruleManager/getRuleHisManagerList";
	postToServer(paramPairs,url,function(data){ 			
		if(data)
			$("#resListTab").html(data);
	}); 		
			
}

function detail(ruleid){
	window.location.href="<%=contextPath%>/ruleManager/ruleHisListQuery?ruleid="+ruleid;
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
      <a class="navbar-brand" href="<%=contextPath%>/ruleManager/ruleHisManager">已归档规则列表</a>
   </div>	
</nav>
<div class="mainDiv">
<div class="layoutDiv">
<div class="well well-lg">

<form action="<%=basePath%>ruleManager/ruleHisManager"  id="dataForm" name="dataForm" method="post" >
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
				<option value="<c:out value="${item.groupid }" />"><c:out value="${item.groupname }" /></option>
				</c:forEach>
			</select>
			</div>
		</td>		
		<td>
			<div class="input-group">			
			<span class="input-group-addon">规则文件名称</span>	
			<input type="text" id="rulefilename" name="rulefilename" value="" maxlength="50" class="form-control" />	
			</div>		
		</td>
		
	</tr>
	</table>
</div>

<div align="center">
	<button type="button" class="btn btn-info" onclick="doIt()">查 询</button>	
</div>
<br />

<span id="resListTab">
<div class="panel panel-default">
   <div class="panel-heading">查询结果</div>   
   <table class="table" >
	<tr><th>规则分组</th><th>规则文件名称</th><th>操作</th></tr>
	
	</table>
   
</div>
</span>

</form>

<div class="Hint">
<p>1.查询条件中的“规则分组”是按分组id来查的，所以如果某个分组改过组名，可能会出现两个相同id不同组名，但是查询结果相同的情况。</p>
<p>2.“查看”可列出选择的规则文件的历史版本。</p>
</div>

</div>
</div>
</div>
</body>
</html>