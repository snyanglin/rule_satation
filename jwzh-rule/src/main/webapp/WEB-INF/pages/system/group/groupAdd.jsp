<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/include.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="<%=contextPath%>/js/methodParameter.js"></script>
<script type="text/javascript">
	

	function save(){		 		
		
		
		var paramPairs=[			
		 		new ParamPair("bz",$("#bz").val()),
		 		new ParamPair("groupname",$("#groupname").val())		 		
		];
		var url="<%=basePath%>groupManager/groupAdd";
		postToServer(paramPairs,url,function(data){ 			
		if(data){
			if(data.resStatus == '0'){				
				alert("分组已保存");				
				window.location.href="<%=contextPath%>/groupManager/groupManager";				
			}else{
				alert("分组保存失败："+data.errorMsg);					
			}				
		}else{
			alert("分组保存失败："+data);				
		}		
			 				
		});
	} 	
</script>
</head>
<body>
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
	<div class="navbar-header">
      <a class="navbar-brand" href="<%=contextPath%>/groupManager/groupManager">分组列表</a>
   </div>
   <div>
      <ul class="nav navbar-nav">
         <li class="active"><a href="#">新增分组</a></li>           
      </ul>
   </div>
</nav>
<div class="mainDiv">
<div class="layoutDiv">
<div class="well well-lg">

<form  id="dataForm" name="dataForm" method="post" >
<!-- 隐藏区域 -->

<div class="panel panel-default">
   <div class="panel-heading">新增分组</div>
	<table class="table">
		
		<tr>
			<th>分组名称</th>
			<td>
				<input type="text" id="groupname" name="groupname" value="" maxlength="100" class="form-control"  />
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
	<button type="button" class="btn btn-default" onclick="save()">保 存</button>	
</div>
</form>

</div>
</div>
</div>
</body>
</html>