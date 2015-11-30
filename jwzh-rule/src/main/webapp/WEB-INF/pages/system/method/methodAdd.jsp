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
		var serviceid = $("#serviceid").val();
		if(serviceid ==null || serviceid== ""){
			alert("请选择服务！");
			return;
		}
		
		getParam();
		
		var paramPairs=[			
		 		new ParamPair("bz",$("#bz").val()),
		 		new ParamPair("methodname",$("#methodname").val()),
		 		new ParamPair("serviceid",serviceid),
		 		new ParamPair("methodresponse",$("#methodresponse").val()),
		 		new ParamPair("paramname",paramnameAry),
		 		new ParamPair("paramclass",paramclassAry),
		 		new ParamPair("parambz",parambzAry)
		 		
		];
		var url="<%=basePath%>methodManager/methodAdd";
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
	
	function urlChange(urlid){
		if(urlid==""){
			$("#serviceSpan").html("<select id=\"serviceid\" class=\"form-control\" ><option value=\"\">*请选择服务地址*</option></select>");
		}else{
			var paramPairs=[
			 		 		new ParamPair("urlid",urlid) 		 		
			];
			var url="<%=contextPath%>/serviceManager/getServiceSelectList";
			postToServer(paramPairs,url,function(data){ 			
				if(data)
					$("#serviceSpan").html(data);
			}); 
		}
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
         <li class="active"><a href="#">新增方法</a></li>           
      </ul>
   </div>
</nav>
<div class="mainDiv">
<div class="layoutDiv">
<div class="well well-lg">

<form  id="dataForm" name="dataForm" method="post" >
<!-- 隐藏区域 -->

<div class="panel panel-default">
   <div class="panel-heading">新增方法</div>
	<table class="table">
		<tr>
			<th>服务地址</th>
			<td>
				<select id="urlid" name="urlid" class="form-control" onchange="urlChange(this.value)">
					<option value="">*请选择服务地址*</option>
					<c:forEach items="${UrlList}" var="item" varStatus="status">
					<option value="${item.id }">${item.urlname}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<th>服务名称</th>
			<td id="serviceSpan">				
				<select id="serviceid" class="form-control" ><option value="">*请选择服务地址*</option></select>				
			</td>
		</tr>	
		<tr>
			<th>方法名称</th>
			<td>
				<input type="text" id="methodname" name="methodname" value="" maxlength="50" class="form-control" placeholder="最长50个字符" />
			</td>
		</tr>
		<tr>
			<th>返回结果</th>
			<td>
				<input type="text" id="methodresponse" name="methodresponse" value="" maxlength="100" class="form-control" placeholder="最长100个字符或50个汉字" />
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
<div class="panel panel-default">
   <div class="panel-heading">方法参数 <button type="button" class="btn btn-sm btn-success" onclick="addParam()">添加</button></div>
	<table class="table" id="paramTable">
		<tr><th>参数名称</th><th>参数类型</th><th>参数说明</th><th>操作</th></tr>
	</table>	
</div>

<div align="center">
	<button type="button" class="btn btn-success" onclick="save()">保 存</button>	
</div>
</form>

<div class="Hint">
<p>1.请先选择“服务地址”和“服务名称”。</p>
<p>2.“方法名称”不可重复，会和“服务地址”、“服务名称”拼接成请求的地址。</p>
<p>3.“返回结果”请描述清楚返回的结果和类型，如果比较多，可在备注中补充说明。</p>
</div>

</div>
</div>
</div>
</body>
</html>