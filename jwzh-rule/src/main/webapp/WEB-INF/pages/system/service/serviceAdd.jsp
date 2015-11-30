<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/include.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function save(){		 		
		var paramPairs=[			
		 		new ParamPair("bz",$("#bz").val()),
		 		new ParamPair("servicename",$("#servicename").val()),
		 		new ParamPair("urlid",$("#urlid").val())		 		
		];
		var url="<%=basePath%>serviceManager/serviceAdd";
		postToServer(paramPairs,url,function(data){ 			
		if(data){
			if(data.resStatus == '0'){				
				alert("服务已保存");				
				window.location.href="<%=contextPath%>/serviceManager/serviceManager";				
			}else{
				alert("服务保存失败："+data.errorMsg);					
			}				
		}else{
			alert("服务保存失败："+data);				
		}		
			 				
		});
	} 	
</script>
</head>
<body>
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
	<div class="navbar-header">
      <a class="navbar-brand" href="<%=contextPath%>/serviceManager/serviceManager">服务列表</a>
   </div>
   <div>
      <ul class="nav navbar-nav">
         <li class="active"><a href="#">新增服务</a></li>           
      </ul>
   </div>
</nav>
<div class="mainDiv">
<div class="layoutDiv">
<div class="well well-lg">

<form  id="dataForm" name="dataForm" method="post" >
<!-- 隐藏区域 -->

<div class="panel panel-default">
   <div class="panel-heading">新增服务</div>
	<table class="table">
		<tr>
			<th>服务地址</th>
			<td>
				<select id="urlid" name="urlid" class="form-control">
				<c:forEach items="${UrlList}" var="item" varStatus="status">
				<option value="${item.id }">${item.urlname}</option>
			</c:forEach>
								
			</select>
			</td>
		</tr>
		<tr>
			<th>服务名称</th>
			<td>
				<input type="text" id="servicename" name="servicename" value="" maxlength="50"  class="form-control" placeholder="最长50个字符" />
			</td>
		</tr>		
		<tr>
			<th>备注</th>
			<td>
				<input type="text" id="bz" name="bz" value="" maxlength="100" class="form-control" placeholder="最长100个字符或50个汉字" />
			</td>
		</tr>
	</table>	
</div>

<div align="center">
	<button type="button" class="btn btn-success" onclick="save()">新 增</button>	
</div>
</form>

<div class="Hint">
<p>1.如果没有“服务地址”可选，请在“地址管理”中添加地址。</p>
<p>2.“服务名称”不可重复,会和“地址”中的“URL”拼接成请求地址。</p>
</div>

</div>
</div>
</div>
</body>
</html>