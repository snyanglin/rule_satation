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
 		$("#paramstr").val($("#paramstr_"+index).val());
 		$("#rulename").val($("#rulename_"+index).val());
 		$("#id").val(id);
 		document.dataForm.submit();
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
 		}
 		
 	}
 	
 	function releaseRule(){
 		document.dataForm.action="<%=basePath%>ruleManager/ruleRelease";
 		document.dataForm.submit();
 	}
 	
 	function testRule(index,rulename){
 		$("#ruleRes_"+index).val("验证中……"); 		
 		var rulefilename=$("#rulefilename").val(); 		
 		var paramStr=$("#paramstr_"+index).val();
 		$.ajax({
			async:true,
			type:"POST",
			data:"ruleFileName="+rulefilename+"&ruleName="+rulename+"&paramStr="+paramStr,
			dataType:"json",
			url:"<%= basePath%>ruleManager/testRule",
			success:function(data){
				var res="验证失败！\r\n";
				if(data){
					if(data.resStatus == '0'){
						res="验证成功！\r\n";
						$("#title_"+index).attr("bgcolor","green");
					}else{
						$("#title_"+index).attr("bgcolor","red");
					}
					res += data.response;
				}else{
					res += data;					
				}
				$("#ruleRes_"+index).val(res);	
			}
		});
 		
 	}
 	
 	function showSaveButton(index){
 		$("#saveButton_"+index).show();
 	}
</script>
</head>
<body>
<form action="<%=basePath%>ruleManager/ruleEdit"  id="dataForm" name="dataForm" method="post" >
<!-- 隐藏区域 -->
<input type="hidden" name="id" id="id" value="" />
<input type="hidden" name="content" id="content" value="" />
<input type="hidden" name="paramstr" id="paramstr" value="" />
<input type="hidden" name="rulename" id="rulename" value="" />
<input type="hidden" name="rulefilename" id="rulefilename" value="${ruleHead.rulefilename}" />
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
									</td>
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
							<td id="title_${status.index}" colspan="2" <c:if test="${item.status == '1'}" >bgcolor="red"</c:if><c:if test="${item.status == '3'}" >bgcolor="green"</c:if>>
								<table width="100%" border="0">
									<td align="left" width="33%">
										<input type="button" value="保存" id="saveButton_${status.index}" style="display:none" onclick="saveRule(${status.index},${item.id})"/>
										
										<input type="button" value=" 删除"  onclick="deleteRule(${status.index},${item.id})"/>
									</td>
									<td align="center" width="33%">
										<input type="hidden" id="rulename_${status.index}" name="rulename_${status.index}" value="${item.rulename}" />
										${item.rulename}
									</td>
									<td align="right">
										<a href="#" onclick="shRule('${status.index}',this)"><c:if test="${status.index == 0}" >收起</c:if><c:if test="${status.index != 0}" >展开</c:if></a>
									</td>
								</table>
							</td>
						</tr>
						<tr id="shtr_${status.index}" <c:if test="${status.index != 0}" >style="display:none"</c:if>>
							<td align="center">规则</td>
							<td width="800px">
								<textarea rows="20" cols="100" id="content_${status.index}" name="content_${status.index}" onchange="showSaveButton(${status.index})">${item.content}</textarea>
							</td>
						</tr>
						<tr id="shtr2_${status.index}" <c:if test="${status.index != 0}" >style="display:none"</c:if>>
							<td  align="center">
								<input type="button" value="验证"  onclick="testRule(${status.index},'${item.rulename}')"/>
							</td>
							<td>
								<textarea rows="5" cols="48" id="paramstr_${status.index}" name="paramstr_${status.index}"  align="left">${item.paramstr}</textarea>
										
								<textarea rows="5" cols="48" id="ruleRes_${status.index}" name="ruleRes_${status.index}"  align="right"></textarea>										
							</td>
						</tr>
						
						</table>
					
			</c:forEach>
				<table border="1" width="950px" style="display:none" id="addTab">
						<tr>
							<td colspan="2">
								<table width="100%">
									<td align="left" width="33%">
										<input type="button" value="确定"  onclick="saveRule('add','add')"/>									
									</td>
									<td align="center" width="33%">
										<input type="text" id="rulename_add" name="rulename_add" value="" />
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