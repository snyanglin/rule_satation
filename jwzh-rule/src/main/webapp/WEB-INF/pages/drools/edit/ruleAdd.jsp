<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/include.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function saveRule(){		 		
		var paramPairs=[			
		 		new ParamPair("bz",$("#bz").val()),
		 		new ParamPair("rulefilename",$("#rulefilename").val()),
		 		new ParamPair("groupid",$("#groupid").val())		 		
		];
		var url="<%=basePath%>ruleManager/ruleAdd";
		postToServer(paramPairs,url,function(data){ 			
		if(data){
			if(data.resStatus == '0'){				
				alert("规则已保存");				
				window.location.href="<%=basePath%>ruleManager/ruleManager";				
			}else{
				alert("规则保存失败："+data.errorMsg);					
			}				
		}else{
			alert("规则保存失败："+data);				
		}		
			 				
		});
	} 	
</script>
</head>
<body>
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
	<div class="navbar-header">
      <a class="navbar-brand" href="<%=contextPath%>/ruleManager/ruleManager">规则列表</a>
   </div>
   <div>
      <ul class="nav navbar-nav">
         <li class="active"><a href="#">规则新增</a></li>           
      </ul>
   </div>
</nav>
<div class="mainDiv">
<div class="layoutDiv">
<div class="well well-lg">

<form action="<%=basePath%>ruleManager/ruleAdd"  id="dataForm" name="dataForm" method="post" >
<!-- 隐藏区域 -->

<div class="panel panel-default">
   <div class="panel-heading">新增规则</div>
	<table class="table">
		<tr>
			<th>规则分组</th>
			<td>
				<select id="groupid" name="groupid" class="form-control" >
					<option value="20151109145800">重点人员</option>
				</select>
			</td>
		</tr>
		<tr>
			<th>规则文件名称</th>
			<td>
				<input type="text" id="rulefilename" name="rulefilename" value="" maxlength="50"  class="form-control"  />
			</td>
		</tr>
		<tr>
			<th>备注</th>
			<td>
				<input type="text" id="bz" name="bz" value="" maxlength="100" class="form-control"  />
			</td>
		</tr>
	</table>	
</div>

<div align="center">
	<button type="button" class="btn btn-default" onclick="saveRule()">新 增</button>	
</div>
</form>

</div>
</div>
</div>
</body>
</html>