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
					<c:forEach items="${GroupList}" var="item" varStatus="status">
					<option value="<c:out value="${item.id }" />"><c:out value="${item.groupname }" /></option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<th>规则文件名称</th>
			<td>
				<input type="text" id="rulefilename" name="rulefilename" value="" maxlength="50"  class="form-control" placeholder="最长50个字符"  />
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
	<button type="button" class="btn btn-success" onclick="saveRule()">保 存</button>	
</div>
</form>

<div class="Hint">
<p>1.“规则文件名称”不可重复。</p>
<p>1.如果没有“规则分组”可选择，请先在“分组管理”中添加分组。</p>
</div>

</div>
</div>
</div>
</body>
</html>