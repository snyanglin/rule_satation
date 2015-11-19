<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/include.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/css/edit.css"></link>
<script type="text/javascript">
	var saveNum=0;//需要保存的数量

 	function saveRule(index,id){		 		
 		var content = $("#content_"+index).val();
 		content = content.replace(/\+/g,'%2B');//处理"+"号
		
 		var paramPairs=[
 				new ParamPair("content",content),
 		 		new ParamPair("paramstr",$("#paramstr_"+index).val()),
 		 		new ParamPair("rulename",$("#rulename_"+index).val()),
 		 		new ParamPair("rulefilename",$("#rulefilename").val()),
 		 		new ParamPair("groupid",$("#groupid").val()),
 		 		new ParamPair("id",id)
 		];
 		var url="<%=basePath%>ruleManager/ruleEdit";
 		postToServer(paramPairs,url,function(data){ 			
			if(data){
				if(data.resStatus == '0'){
					hideSaveButton(index);
					alert("规则已保存");
				}else{
					alert("规则保存失败："+data.errorMsg);					
				}				
			}else{
				alert("规则保存失败："+data);				
			}		
 			 				
 		});
 	}
	function saveRuleCancel(){
 		$("#addTab").hide();
		$("#releaseButton").show();
		$("#delButton").show();
		$("#addButton").show();
		$("#content_add").val(""); 	
		$("#rulename_add").val("");
	}
 	
 	function deleteRule(index,id){ 	
 		if(confirm("您确定要删除此规则么？")){
	 		$("#id").val(id);
	 		document.dataForm.action="<%=basePath%>ruleManager/ruleDelete";
	 		document.dataForm.submit();
 		}
 	}
 	
 	function shRule(id,obj){
 		if(obj.innerHTML=="收起"){
 			obj.innerHTML="展开";
 			$("#shtr_"+id).hide();
 			if($("#shtr2_"+id))
 				$("#shtr2_"+id).hide();
 		}else{
 			obj.innerHTML="收起";
 			$("#shtr_"+id).show();
 			if($("#shtr2_"+id))
 				$("#shtr2_"+id).show();
 		}
 	}
 	
 	function shAdd(obj){
		$("#addTab").show();	
		$("#releaseButton").hide();
		$("#delButton").hide();
		$("#addButton").hide();
 	}
 	
 	function releaseRule(){
 		if(saveNum>0){
 			if(!confirm("您还有未保存的规则，您确认要发布么？")){
 				return;
 			}
 		}
 		
 		var paramPairs=[
 			new ParamPair("rulefilename",$("#rulefilename").val()) 		 		 		
 		];
 		var url="<%=basePath%>ruleManager/ruleRelease";
 		postToServer(paramPairs,url,function(data){ 			
 					if(data){
 						if(data.resStatus == '0'){
 							alert("规则已发布");
 							window.location.reload();
 						}else{
 							alert("规则发布失败："+data.errorMsg);					
 						}				
 					}else{
 						alert("规则发布失败："+data);				
 					}		
 		 			 				
 		 		});
 		 		 		
 	}
 	
 	function testRule(index,rulename){
 		$("#ruleRes_"+index).val("验证中……"); 		
 		var rulefilename=$("#rulefilename").val(); 		
 		var paramStr=$("#paramstr_"+index).val();
 		
 		var paramPairs=[
 				new ParamPair("ruleFileName",rulefilename),
 				new ParamPair("ruleName",rulename),
 				new ParamPair("paramStr",paramStr)
 		];
 		var url="<%= basePath%>ruleManager/testRule";
 		postToServer(paramPairs,url,function(data){
			var res="验证失败！\r\n";
			if(data){
				if(data.resStatus == '0'){
					res="验证成功！\r\n";
					$("#testTd_"+index).attr("bgcolor","green");
				}else{
					$("#testTd_"+index).attr("bgcolor","red");
				}
				res += data.response;
			}else{
				res += data;					
			}
			$("#ruleRes_"+index).val(res);	
		});
 		
 	}
 	
 	function showSaveButton(index){
 		$("#saveButton_"+index).show();
 		saveNum++;
 	}
 	
 	function hideSaveButton(index){
 		$("#saveButton_"+index).hide();
 		saveNum--;
 	}
 	
 	function ruleFile(){
 		if(confirm("归档后规则将不可用，您确定要归档么？")){
 			document.dataForm.action="<%=basePath%>ruleManager/ruleFile";
 	 		document.dataForm.submit();
 		}
 	}
</script>
</head>
<body>
<div class="mainDiv">
<div class="layoutDiv">
<div class="well well-lg">

<form action="<%=basePath%>ruleManager/ruleEdit"  id="dataForm" name="dataForm" method="post" >
<!-- 隐藏区域 -->
<input type="hidden" name="id" id="id" value="" />
<input type="hidden" name="content" id="content" value="" />
<input type="hidden" name="paramstr" id="paramstr" value="" />
<input type="hidden" name="rulename" id="rulename" value="" />
<input type="hidden" name="rulefilename" id="rulefilename" value="${ruleHead.rulefilename}" />
<input type="hidden" name="groupid" id="groupid" value="${ruleHead.groupid}" />

<table width="100%">	
	<tr>
		<td>
			<div class="panel panel-default">
   				<div class="panel-heading">
   					<table width="100%">	
						<tr>
							<td align="left" width="50%">
   								<button type="button" class="btn btn-info btn-sm" onclick="saveRule('head',${ruleHead.id})" style="display:none;" id="saveButton_head" >保存</button>  						
							</td>
							
							<td align="right">
								<a href="#" onclick="shRule('head',this)">收起</a>
							</td>
						</tr>
						</table>
   				</div>
				<table class="table table-bordered">				
				<tr id="shtr_head">										
					<td align="center">
						<textarea rows="8" cols="100" id="content_head" name="content_head" onchange="showSaveButton('head')">${ruleHead.content}</textarea>
					</td>
				</tr>
				</table>
			</div>		
		</td>		
	</tr>
	
	
			
			<c:forEach items="${List}" var="item" varStatus="status">
			<tr>
			<td>
				<div class="panel panel-default">
   					<div class="panel-heading" id="title_${status.index}">
   						<table width="100%">	
						<tr>
							<td align="left" width="30%">
   								<button type="button" class="btn btn-info btn-sm" id="saveButton_${status.index}" style="display:none;" onclick="saveRule(${status.index},${item.id})" >保存</button>
   								<button type="button" class="btn btn-info btn-sm" onclick="deleteRule(${status.index},${item.id})" >删除</button>   						
							</td>
							<td align="center">
								<input type="hidden" id="rulename_${status.index}" name="rulename_${status.index}" value="${item.rulename}" />
								${item.rulename}
							</td>
							<td align="right" width="30%">
								<a href="#" onclick="shRule('${status.index}',this)"><c:if test="${status.index == 0}" >收起</c:if><c:if test="${status.index != 0}" >展开</c:if></a>
							</td>
						</tr>
						</table>
					</div>
					
					<table class="table table-bordered">						
						<tr id="shtr_${status.index}" <c:if test="${status.index != 0}" >style="display:none"</c:if>>							
							<td colspan="2" align="center">
								<textarea rows="20" cols="100" id="content_${status.index}" name="content_${status.index}" onchange="showSaveButton(${status.index})">${item.content}</textarea>
							</td>
						</tr>
						<tr id="shtr2_${status.index}" <c:if test="${status.index != 0}" >style="display:none"</c:if>>
							<td  align="center" id="testTd_${status.index}">
								<input type="button" value="测试"  onclick="testRule(${status.index},'${item.rulename}')" class="btn btn-default" style="width:50px" />
							</td>
							<td>
								<div style="width:49%;border-right:1px solid #bebde3;float:left">
								<textarea rows="5" cols="48" id="paramstr_${status.index}" name="paramstr_${status.index}">${item.paramstr}</textarea>
								</div>	
								<div class="rightDiv" style="width:49%;float:right">	
								<textarea rows="5" cols="48" id="ruleRes_${status.index}" name="ruleRes_${status.index}" readonly="readonly">验证结果</textarea>	
								</div>									
							</td>
						</tr>
						
					</table>
					</div>
			</td>
			</tr>
			</c:forEach>
			<tr>
			<td style="display:none" id="addTab">
				<div class="panel panel-default">
   					<div class="panel-heading" id="title_${status.index}">
   						<table width="100%">	
						<tr>
							<td align="left" width="30%">
   								<button type="button" class="btn btn-info btn-sm" onclick="saveRule('add','add')" >保存</button>
   								<button type="button" class="btn btn-default btn-sm" onclick="saveRuleCancel()" >取消</button>   								  						
							</td>
							<td align="center">
								<div class="input-group">			
								<span class="input-group-addon">请输入规则名</span>	
								 <input type="text" id="rulename_add" name="rulename_add" class="form-control" value="" />
								</div>
							</td>
							<td align="right" width="30%">
								<a href="#" onclick="shRule('add',this)">收起</a></a>
							</td>
						</tr>
						</table>
   					</div>
						<table class="table table-bordered">				
						
						<tr id="shtr_add">							
							<td align="center">
								<textarea rows="20" cols="100" id="content_add" name="content_add" ></textarea>
							</td>
						</tr>
						</table>
			</div>
		</td>
	</tr>
	
</table>

<div align="center">
	<button type="button" class="btn btn-default" id="addButton" onclick="shAdd(this)">新 增</button>
	<button type="button" class="btn btn-default" id="releaseButton" onclick="releaseRule()">发 布</button>
	<button type="button" class="btn btn-default" id="delButton" onclick="ruleFile()">归 档</button>	
</div>

</form>

</div>
</div>
</div>
</body>
</html>