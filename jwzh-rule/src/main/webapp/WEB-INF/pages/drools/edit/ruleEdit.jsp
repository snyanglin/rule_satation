<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/commonInclude.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
 	function saveRule(index,id){
 		$("#content").val($("#content_"+index).val());
 		$("#response").val($("#response_"+index).val());
 		$("#id").val(id);
 		document.dataForm.submit();
 	}
 	
 	function deleteRule(index,id){ 		
 		$("#id").val(id);
 		document.dataForm.action="<%=basePath%>ruleManager/ruleDelete";
 		document.dataForm.submit();
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
 		if(obj.value=="新增"){
 			obj.value="取消"
 			$("#addTab").show();	
 			$("#releaseButton").hide();
 			$("#delButton").hide();
 		}else{
 			obj.value="新增"
 	 		$("#addTab").hide();
 			$("#releaseButton").show();
 			$("#delButton").show();
 			$("#content_add").val("");
 			$("#response_add").val("");
 		}
 		
 	}
 	
 	function releaseRule(){
 		document.dataForm.action="<%=basePath%>ruleManager/ruleRelease";
 		document.dataForm.submit();
 	}
</script>
</head>
<body>
<form action="<%=basePath%>ruleManager/ruleEdit"  id="dataForm" name="dataForm" method="post" >
<!-- 隐藏区域 -->
<input type="hidden" name="id" id="id" value="" />
<input type="hidden" name="content" id="content" value="" />
<input type="hidden" name="response" id="response" value="" />
<input type="hidden" name="rulename" id="rulename" value="${ruleHead.rulename}" />
<input type="hidden" name="groupid" id="groupid" value="${ruleHead.groupid}" />

<table width="90%">	
	<tr>
		<td align="center">
			<table border="0" width="90%">
				<tr>
					<td align="center">
						<table border="1" width="950px">
						<tr>
							<td colspan="2">
								<table width="100%">
									<td align="left">
										<input type="button" value="保存"  onclick="saveRule('head',${ruleHead.id})"/>
										
										 验证</td>
									<td align="right"><a href="#" onclick="shRule('head',this)">收起</a></td>
								</table>
							</td>
						</tr>
						<tr id="shtr_head">					
							<td align="center">规则头</td>		
							<td width="800px">
								<textarea rows="10" cols="100" id="content_head" name="content_head">${ruleHead.content}</textarea>
							</td>
						</tr>
						
						</table>
					</td>
				</tr>
			</table>
		</td>		
	</tr>
	
	<tr>
		<td align="center">
			
			<c:forEach items="${List}" var="item" varStatus="status">
				
						<table border="1" width="950px">
						<tr>
							<td colspan="2">
								<table width="100%">
									<td align="left">
										<input type="button" value="保存"  onclick="saveRule(${status.index},${item.id})"/>
										验证
										<input type="button" value=" 删除"  onclick="deleteRule(${status.index},${item.id})"/>
									</td>
									<td align="right"><a href="#" onclick="shRule('${status.index}',this)">收起</a></td>
								</table>
							</td>
						</tr>
						<tr id="shtr_${status.index}" <c:if test="${status.index != 0}" >style="display:none"</c:if>>
							<td align="center">规则</td>
							<td width="800px">
								<textarea rows="20" cols="100" id="content_${status.index}" name="content_${status.index}">${item.content}</textarea>
							</td>
						</tr>
						<tr id="shtr2_${status.index}" <c:if test="${status.index != 0}" >style="display:none"</c:if>>
							<td  align="center">返回</td>
							<td>
								<textarea rows="5" cols="100" id="response_${status.index}" name="response_${status.index}">${item.response}</textarea>
							</td>
						</tr>
						
						</table>
					
			</c:forEach>
				<table border="1" width="950px" style="display:none" id="addTab">
						<tr>
							<td colspan="2">
								<table width="100%">
									<td align="left">
										<input type="button" value="确定"  onclick="saveRule('add','add')"/>
										验证
										 </td>
									<td align="right"><a href="#" onclick="shRule('add',this)">收起</a></td>
								</table>
							</td>
						</tr>
						<tr id="shtr_add">
							<td align="center">规则</td>
							<td width="800px">
								<textarea rows="20" cols="100" id="content_add" name="content_add"></textarea>
							</td>
						</tr>
						<tr id="shtr2_add">
							<td  align="center">返回</td>
							<td>
								<textarea rows="5" cols="100" id="response_add" name="response_add"></textarea>
							</td>
						</tr>
						
						</table>
		</td>
	</tr>
	<tr>
		<td align="center">
			
				<input type="button" value="新增" id="addButton" onclick="shAdd(this)"/>				
				<input type="button" value="发布" id="releaseButton" onclick="releaseRule()"/>
				<input type="button" value="归档" id="delButton" onclick=""/>
			
		</td>
	</tr>
</table>
</form>
</body>
</html>