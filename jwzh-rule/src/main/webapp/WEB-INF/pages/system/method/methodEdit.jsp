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
		
		getParam();
		
		var paramPairs=[
				new ParamPair("id",$("#id").val()),
		 		new ParamPair("bz",$("#bz").val()),
		 		new ParamPair("methodname",$("#methodname").val()),
		 		new ParamPair("methodresponse",$("#methodresponse").val()),
		 		new ParamPair("paramname",paramnameAry),
		 		new ParamPair("paramclass",paramclassAry),
		 		new ParamPair("parambz",parambzAry)
		];
		var url="<%=basePath%>methodManager/methodEdit";
		postToServer(paramPairs,url,function(data){ 			
		if(data){
			if(data.resStatus == '0'){				
				alert("方法已保存");				
				window.location.href="<%=contextPath%>/methodManager/methodManager";				
			}else{
				alert("方法保存失败："+data.errorMsg);					
			}				
		}else{
			alert("方法保存失败："+data);				
		}		
			 				
		});
	} 	
	
	function del(){		 		
		var paramPairs=[
				new ParamPair("id",$("#id").val())		 		
		];
		var url="<%=basePath%>methodManager/methodDelete";
		postToServer(paramPairs,url,function(data){ 			
		if(data){
			if(data.resStatus == '0'){				
				alert("方法已删除");				
				window.location.href="<%=contextPath%>/methodManager/methodManager";				
			}else{
				alert("方法删除失败："+data.errorMsg);					
			}				
		}else{
			alert("方法删除失败："+data);				
		}		
			 				
		});
	} 	
</script>
</head>
<body>
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
	<div class="navbar-header">
      <a class="navbar-brand" href="<%=contextPath%>/methodManager/methodManager">方法列表</a>
   </div>
   <div>
      <ul class="nav navbar-nav">
         <li class="active"><a href="#">方法修改</a></li>           
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
   <div class="panel-heading">方法修改</div>
	<table class="table">
		<tr>
			<th>服务地址</th>
			<td>
				<input type="text" id="urlname" name="urlname" class="form-control"  value="${entity.urlname }" readonly="readonly" />
			</td>
		</tr>
		<tr>
			<th>服务名称</th>
			<td>
				<input type="text" id="servicename" name="servicename" class="form-control"  value="${entity.servicename }"  readonly="readonly"  />
			</td>
		</tr>	
		<tr>
			<th>方法名称</th>
			<td>
				<input type="text" id="methodname" name="methodname" value="${entity.methodname }" maxlength="50" class="form-control"  />
			</td>
		</tr>
		<tr>
			<th>返回结果</th>
			<td>
				<input type="text" id="methodresponse" name="methodresponse" value="${entity.methodresponse }" maxlength="100" class="form-control"  />
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

<div class="panel panel-default">
   <div class="panel-heading">方法参数 <a href="#" onclick="addParam()">ADD</a></div>
	<table class="table" id="paramTable">
		<tr><th>参数名称</th><th>参数类型</th><th>参数说明</th><th>操作</th></tr>
		<c:forEach items="${List}" var="item" varStatus="status">
			<tr><td><c:out value="${item.paramname }" />
				<input type="hidden"name="paramname" value="<c:out value="${item.paramname }" />" />
			</td>
			<td><c:out value="${item.paramclass }" />
				<input type="hidden"name="paramclass" value="<c:out value="${item.paramclass }" />" />
			</td>		
			<td><c:out value="${item.bz }" />
				<input type="hidden"name="parambz" value="<c:out value="${item.bz }" />" />
			</td>
			<td>
				<button type="button" class="btn btn-default" onclick="delParam(this)">删除</button>		
			</td></tr>
		</c:forEach>
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