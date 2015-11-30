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
		if(!confirm("您确定要删除该地址么？")){
			return;
		}
		
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
         <li class="active"><a href="#">地址修改</a></li>           
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
   <div class="panel-heading">地址修改</div>
	<table class="table">
		<tr>
			<th>地址名称</th>
			<td>
				<input type="text" id="urlname" name="urlname"  maxlength="100"  class="form-control" value="${entity.urlname }" placeholder="最长100个字符或50个汉字" />
			</td>
		</tr>
		<tr>
			<th>URL</th>
			<td>
				<input type="text" id="url" name="url"  maxlength="50"  class="form-control" value="${entity.url }" placeholder="最长50个字符"  />
			</td>
		</tr>
		<tr>
			<th>备注</th>
			<td>
				<input type="text" id="bz" name="bz"  maxlength="100" class="form-control" value="${entity.bz }" placeholder="最长100个字符或50个汉字"  />
			</td>
		</tr>
	</table>	
</div>

<div align="center">
	<button type="button" class="btn btn-success" onclick="save()">保 存</button>
	&nbsp;
	<button type="button" class="btn btn-danger" onclick="del()">删 除</button>	
</div>
</form>

<div class="Hint">
<p>1.“地址名称”不是地址的唯一标识，但是为了能方便区分，所以也不可重复。</p>
<p>2.“URL”是服务的根地址，如“http://localhost:9080/jwzh-rule”。</p>
<p>3.“删除”后不可再找回，所以删除前请确认不再使用再删除。</p>
<p>4.被“服务”占用的地址是不能删除的，如果要删除，请先删除占用的服务。</p>
</div>

</div>
</div>
</div>
</body>
</html>