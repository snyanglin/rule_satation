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
<table width="90%">	
	<tr>
		<td align="center">
			<table border="1" width="500">
				<tr>
					<th width="40%" align="right">规则文件名：</th>
					<td>${ruleObj.rulefilename}</td>
				</tr>
				<tr>
					<th align="right">创建时间：</th>
					<td>${ruleObj.createtime}</td>
				</tr>				
				<tr>
					<td align="center" colspan="2">
						<textarea rows="30" cols="100" readonly="readonly">${ruleObj.content}</textarea>
					</td>
				</tr>
			</table>
		</td>		
	</tr>
</table>

</body>
</html>