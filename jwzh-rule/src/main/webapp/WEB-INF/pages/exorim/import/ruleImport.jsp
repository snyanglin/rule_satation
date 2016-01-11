<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/include.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>导入导出</title>
<script type="text/javascript">
var groupSize=${fn:length(GroupList)};

var errorNum=0;//失败数
var importNum=0;//正在导入的规则数
var importFile="";//成功导入的规则文件

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
	if(!confirm("您即将清空原有规则，导入新的规则，您确认要导入么？"))
		return;
	
	errorNum=0;//失败数
	importNum=0;//正在导入的规则数
	importFile="";//成功导入的规则文件
	$("#importBtn").text("导入中……");	
	$(obj).prop("disabled",true);
	$("#releaseBtn").prop("disabled",true);
	
	if(groupSize){
		clearRule();
		var groupname;
		var fileNameAry;
		for(var index=0;index<groupSize;index++){//循环获取分组
			
			fileNameAry=$("input[name=ruleFile_"+index+"]");//分组下的规则文件
			if(fileNameAry){//该分组下有规则文件
				for(var i=0;i<fileNameAry.length;i++){
					if(fileNameAry[i].checked){//选中要导出的文件
						importNum++;//正在导入的规则数
						importRuleFile(index,i,fileNameAry[i].value);
					}
					
				}
			}
		}
	}
	
}

function importRuleFile(groupIndex,ruleIndex,ruleFileName){
	var groupname = $("#groupname_"+groupIndex).val();//分组名
	var groupbz   = $("#groupbz_"+groupIndex).val();//分组备注
	var ruleFileContent = $("#ruleFileContent_"+groupIndex+"_"+ruleIndex).val();//规则文件内容
	ruleFileContent=ruleFileContent.replace(/\+/g, "%2B");
	var paramPairs=[
	 				new ParamPair("groupname",groupname),
	 				new ParamPair("groupbz",groupbz),
	 		 		new ParamPair("ruleFileName",ruleFileName),
	 		 		new ParamPair("content",ruleFileContent)
	 		];
	
	var url="<%=basePath%>ruleExOrIm/ruleImport";
	
	$("#statusText_"+groupIndex+"_"+ruleIndex).html("导入中……");
	postToServer(paramPairs,url,function(data){ 			
		if(data){
			if(data.resStatus == '0'){
				$("#statusText_"+groupIndex+"_"+ruleIndex).html("导入成功");
				if(groupname!="sysconfig")
					importFile+=ruleFileName+",";
			}else{
				$("#statusText_"+groupIndex+"_"+ruleIndex).html("导入失败");
				errorNum++;
			}
		}else{
			$("#statusText_"+groupIndex+"_"+ruleIndex).html("导入失败");
		}	
		importNum--;//导入完成
		if(importNum==0){
			$("#importBtn").text("重新导入");	
			$("#importBtn").prop("disabled",false);
			$("#releaseBtn").prop("disabled",false);
		}
	});
}

function clearRule(){
	var url="<%=basePath%>ruleExOrIm/clrearRule";
	
	postToServerAsync(null,url,function(data){ 			
		if(data){
			
		}else{
			
		}		
	});
}

function releaseRule(obj){
	if(errorNum>0){
		if(!confirm("有"+errorNum+"条规则导入失败，您确认要发布所有已导入规则么？")){
			return;
		}
	}
	
	if(importNum>0){
		alert("还有规则正在导入，请等待所有规则导入完成后再发布");
		return;
	}
	if(importFile=="")
		return;
	
	alert(importFile);
}
</script>
</head>
<body>
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
	<div class="navbar-header">
      <a class="navbar-brand" href="<%=contextPath%>/ruleExOrIm/ruleImportPre">规则包选择</a>
   </div>
   <div>
      <ul class="nav navbar-nav">
         <li class="active"><a href="#">规则导入</a></li>           
      </ul>
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
			<td width="80%" align="center"><b>规则分组：</b><c:out value="${item.groupname }" />（<c:out value="${item.bz }" />）</td>
			<td align="right"><a href="#" onclick="shGroup('${status.index}',this)">收起</a></td>
   		</tr>
   		</table>
   		<input type="hidden" id="groupname_${status.index}" value="${item.groupname }" />
   		<input type="hidden" id="groupbz_${status.index}" value="${item.bz }" />
   
   </div>
	<table class="table" id="group_${status.index}">	
		<tr><th width="10%">选择</th><th width="30%">规则文件名称</th><th width="30%">备注</th><th width="30%">状态</th></tr>
		
		<c:forEach items="${item.ruleFileList}" var="item2" varStatus="status2">
		<tr>
			<td>
				<input type="checkbox" id="ruleFile_${status.index}_${status2.index}" name="ruleFile_${status.index}" checked="checked" value="${item2.rulefilename}"/>
				<input type="hidden" id="ruleFileContent_${status.index}_${status2.index}" value="${item2.content }" />				
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
	<button type="button" class="btn btn-success" id="importBtn" onclick="importRule(this)">导入</button>
	<button type="button" class="btn btn-success" id="releaseBtn" onclick="releaseRule(this)" disabled="disabled">发布</button>
</div>

</form>
</div>
</div>
</div>
</body>
</html>