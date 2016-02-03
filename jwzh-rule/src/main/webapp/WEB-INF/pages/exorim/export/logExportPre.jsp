<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/include.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>导出规则</title>
<script type="text/javascript">
	var bz= 0;
	function onCompare(){
		if(bz == 0){
			$("#ruleRes").val("准备中……"); 		
			$.ajax({
				type: "GET",
				
				url:"<%= basePath%>ruleExOrIm/logPrepare",
				dataType: "json",
				async:true,
				success: function(data) {
					if(data == 'true'){
							$("#exportButton").html("开始导出");
							$("#ruleRes").val("规则日志准备成功！");					 
					}else{
						$("#ruleRes").val("规则日志准备失败！");
					}
			
	 
				}
			});
		bz =1;
		}else{
			<%-- $.ajax({
				type: "GET",
				
				url:"<%= basePath%>ruleExOrIm/logPrepare",
				dataType: "json",
				async:true,
				success: function(data) {
					if(data == 'true'){
							$("#exportButton").html("开始导出");
							$("#ruleRes").val("规则日志准备成功！");					 
					}else{
						$("#ruleRes").val("规则日志准备失败！");
					}
			
	 
				}
			}); --%>
			$("#exportButton").html("准备导出");
			$("#ruleRes").val("导出规则文件日志。注：导出的日志为最近10条");
			download();
			bz =0;
			alert("nihao");
			
		}
				
	}
	
	function download(){
		location.href="<%=basePath%>download/rules.zip";
	}
	
	

	
</script>
</head>
<body>
<div navbar-header>
   <div >导入选项</div>
	<table >
	<tr>		
		<td>
		      <button type="button" id ="exportButton" onclick="onCompare()" >${name}</button>
		</td>
		<td>
		      <textarea rows="5" cols="48" id="ruleRes" name="ruleRes" readonly="readonly">导出规则文件日志。注：导出的日志为最近10条</textarea>
		</td>
	</tr>
	</table>
</div>
		
</body>
</html>