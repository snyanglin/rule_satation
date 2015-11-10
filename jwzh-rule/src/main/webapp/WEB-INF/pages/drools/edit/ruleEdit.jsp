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
 		$("#id").val(id);
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

<table width="90%">	
	<tr>
		<td>
			<table border="0" width="90%">
				<tr>
					<td align="center">
						<table border="1" width="950px">
						<tr>					
							<td align="center">规则头</td>		
							<td width="800px">
								<textarea rows="10" cols="100" id="content_head" name="content_head">import ……</textarea>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<table width="100%">
									<td align="left">保存 验证 删除</td>
									<td align="right">收起</td>
								</table>
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
							<td align="center">规则</td>
							<td width="800px">
								<textarea rows="20" cols="100" id="content_${status.index}" name="content_${status.index}">
									${item.content}
								</textarea>
							</td>
						</tr>
						<tr>
							<td  align="center">返回</td>
							<td>
								<textarea rows="5" cols="100" id="response_${status.index}" name="response_${status.index}"></textarea>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<table width="100%">
									<td align="left">
										<input type="button" value="保存"  onclick="saveRule(${status.index},${item.id})"/>
										验证
										 删除</td>
									<td align="right">收起</td>
								</table>
							</td>
						</tr>
						</table>
					
			</c:forEach>
			
		</td>
	</tr>
</table>
</form>
</body>
</html>