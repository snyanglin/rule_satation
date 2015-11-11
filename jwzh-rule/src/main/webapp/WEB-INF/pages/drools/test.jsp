<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/commonInclude.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function testRule(){
		document.dataForm.submit();
	}
</script>
</head>
<body>
<form action="<%=basePath%>founderRule/executeRule"  id="dataForm" name="dataForm" method="post" >
<!-- 隐藏区域 -->
<input type="hidden" name="ruleFileName" id="ruleFileName" value="MESSAGE_ZDRY" />
<input type="hidden" name="ruleName" id="ruleName" value="LGSQ" />
<input type="hidden" name="paramStr" id="paramStr" value="{zdrygllx=02, p2=p2, p1=p1}" />
<input type="hidden" name="globalParamMap" id="globalParamMap" value="" />

<table>
	<tr>
		<th>规则</th><th>测试</th><th>结果</th>
	</tr>
	<tr>
		<td>MESSAGE_ZDRY</td>
		<td>
			<input type="button" value="test"  onclick="testRule()"/>
		</td>
		<td>
			${ruleRes }
		</td>
	</tr>
</table>
</form>
</body>
</html>