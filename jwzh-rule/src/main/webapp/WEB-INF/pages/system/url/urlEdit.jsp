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
				new ParamPair("id",$("#id").val()),
		 		new ParamPair("bz",$("#bz").val()),
		 		new ParamPair("urlname",$("#urlname").val()),
		 		new ParamPair("url",$("#url").val())		 		
		];
		var url="<%=basePath%>urlManager/urlEdit";
		postToServer(paramPairs,url,function(data){ 			
		if(data){
			if(data.resStatus == '0'){				
				alert("地址已保存");				
				window.location.href="<%=contextPath%>/urlManager/urlManager";				
			}else{
				alert("地址保存失败："+data.errorMsg);					
			}				
		}else{
			alert("地址保存失败："+data);				
		}		
			 				
		});
	} 	
	
	function del(){		 		
		var paramPairs=[
				new ParamPair("id",$("#id").val())		 		
		];
		var url="<%=basePath%>urlManager/urlDelete";
		postToServer(paramPairs,url,function(data){ 			
		if(data){
			if(data.resStatus == '0'){				
				alert("地址已删除");				
				window.location.href="<%=contextPath%>/urlManager/urlManager";				
			}else{
				alert("地址删除失败："+data.errorMsg);					
			}				
		}else{
			alert("地址删除失败："+data);				
		}		
			 				
		});
	} 	
</script>
</head>
<body>
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
	<div class="navbar-header">
      <a class="navbar-brand" href="<%=contextPath%>/urlManager/urlManager">地址列表</a>
   </div>
   <div>
      <ul class="nav navbar-nav">
         <li class="active"><a href="#">规则地址</a></li>           
      </ul>
   </div>
</nav>
<div class="mainDiv">
<div class="layoutDiv">
<div class="well well-lg">

<form  id="dataForm" name="dataForm" method="post" >
<!-- 隐藏区域 -->
<input type="hidden" id="id" name="id" value="${entity.id }" />

<div class="panel panel-default">
   <div class="panel-heading">新增地址</div>
	<table class="table">
		<tr>
			<th>地址名称</th>
			<td>
				<input type="text" id="urlname" name="urlname"  maxlength="100"  class="form-control" value="${entity.urlname }" />
			</td>
		</tr>
		<tr>
			<th>URL</th>
			<td>
				<input type="text" id="url" name="url"  maxlength="50"  class="form-control" value="${entity.url }"  />
			</td>
		</tr>
		<tr>
			<th>备注</th>
			<td>
				<input type="text" id="bz" name="bz"  maxlength="100" class="form-control" value="${entity.bz }"  />
			</td>
		</tr>
	</table>	
</div>

<div align="center">
	<button type="button" class="btn btn-default" onclick="save()">保存</button>
	
	<button type="button" class="btn btn-default" onclick="del()">删除</button>	
</div>
</form>

</div>
</div>
</div>
</body>
</html>