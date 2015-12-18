<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/include.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>导入导出</title>
<script type="text/javascript">
var groupSize=${fn:length(GroupList)};
function shGroup(index,obj){
	if(obj.innerHTML=="收起"){
		obj.innerHTML="展开";
		$("#group_"+index).hide();
	}else{
		obj.innerHTML="收起";
		$("#group_"+index).show();
	}
}

function groupCheck(obj,index){
	if(obj.checked){
		
		$("input[name=ruleFile_"+index+"]").prop("checked", true);			
	}else{
		$("input[name=ruleFile_"+index+"]").prop("checked", false);
	}
}

function importRule(obj){
	$(obj).prop("disabled",true);
	
	if(groupSize){
		var groupname;
		var fileNameAry;
		for(var index=0;index<groupSize;index++){//循环获取分组
			
			fileNameAry=$("input[name=ruleFile_"+index+"]");//分组下的规则文件
			if(fileNameAry){//该分组下有规则文件
				for(var i=0;i<fileNameAry.length;i++){
					if(fileNameAry[i].checked){//选中要导出的文件
						importRuleFile(index,i,fileNameAry[i].value);
					}
					
				}
			}
		}
	}
	$(obj).prop("disabled",false);
}

function importRuleFile(groupIndex,ruleIndex,ruleFileName){
	var groupname = $("#groupname_"+groupIndex).val();//分组名
	var filePath = $("#filePath_"+groupIndex+"_"+ruleIndex).val();//规则文件内容
	var paramPairs=[
	 				new ParamPair("groupname",groupname),
	 		 		new ParamPair("ruleFileName",ruleFileName),
	 		 		new ParamPair("filePath",filePath)
	 		];
	
	var url="<%=basePath%>ruleExOrIm/ruleImport";
	
	$("#statusText_"+groupIndex+"_"+ruleIndex).html("导入中……");
	postToServer(paramPairs,url,function(data){ 			
		if(data){
			if(data.resStatus == '0'){
				$("#statusText_"+groupIndex+"_"+ruleIndex).html("导入成功");
			}else{
				$("#statusText_"+groupIndex+"_"+ruleIndex).html("导入失败");
			}
		}else{
			$("#statusText_"+groupIndex+"_"+ruleIndex).html("导入失败");
		}		
	});
}
</script>
</head>
<body>
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
	<div class="navbar-header">
      <a class="navbar-brand" href="#">规则导入</a>
   </div>	
</nav>
<div class="mainDiv">
<div class="layoutDiv">
<!-- 导航 -->

<!-- 主体 -->
<div class="well well-lg">

<form action="<%=basePath%>ruleExOrIm/ruleExport"  id="dataForm" name="dataForm" method="post">

<c:forEach items="${GroupList}" var="item" varStatus="status">
<span id="resListTab">
<div class="panel panel-default">
   <div class="panel-heading">
   		<table width="100%" >
   		<tr>
   			<td width="10%"><input type="checkbox" checked="checked" onclick="groupCheck(this,'${status.index }')" /></td> 
			<td width="80%" align="center"><b>规则分组：</b><c:out value="${item.groupname }" /></td>
			<td align="right"><a href="#" onclick="shGroup('${status.index}',this)">收起</a></td>
   		</tr>
   		</table>
   		<input type="hidden" id="groupname_${status.index}" value="${item.groupname }" />
   
   </div>
	<table class="table" id="group_${status.index}">	
		<tr><th width="10%">选择</th><th width="30%">规则文件名称</th><th width="30%">备注</th><th width="30%">状态</th></tr>
		
		<c:forEach items="${item.ruleFileList}" var="item2" varStatus="status2">
		<tr>
			<td>
				<input type="checkbox" id="ruleFile_${status.index}_${status2.index}" name="ruleFile_${status.index}" checked="checked" value="${item2.rulefilename}"/>
				<input type="hidden" id="filePath_${status.index}_${status2.index}" value="${item2.content}"/>
				<textarea id="ruleFileContent_${status.index}_${status2.index}" style="display:none">
					${item2.content }
				</textarea>
			</td>
			<td>${item2.rulefilename }</td>
			<td>${item2.bz }</td>
			<td id="statusText_${status.index}_${status2.index}"></td>
		</tr>
		</c:forEach>
	</table>
</div>
</span>
</c:forEach>


<div align="center">
	<button type="button" class="btn btn-success" onclick="importRule(this)">导入</button>
</div>

</form>
</div>
</div>
</div>
</body>
</html>