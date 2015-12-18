<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/include.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>导入导出</title>
<script type="text/javascript">
	function doIt(){
		if($("#zipFile").val()==""){
			alert("请选择导入的ZIP文件");
			return false;
		}else{
			document.dataForm.submit();
		}
	}
</script>
</head>
<body>
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
	<div class="navbar-header">
      <a class="navbar-brand" href="#">规则导入</a>
   </div>	
</nav>
<div class="mainDiv">
<div class="layoutDiv">
<!-- 导航 -->

<!-- 主体 -->
<div class="well well-lg">

<form action="<%=basePath%>ruleExOrIm/ruleImportView"  id="dataForm" name="dataForm" method="post" enctype="multipart/form-data">

<div class="panel panel-default">
   <div class="panel-heading">导入选项</div>
	<table class="table table-bordered">
	<tr>		
		<td width="50%">
			
		      <input type="file" id="zipFile" name="zipFile" />
		</td>
		<td>
		      <button type="button" class="btn btn-success" onclick="doIt()">预览</button>
		</td>
	</tr>
	</table>
</div>

<div class="panel panel-default">
   <div class="panel-heading">*导入说明（<font color="red">请仔细阅读</font>）</div>
	<table class="table table-bordered">
	<tr>		
		<td>
			<p>1.导入前先备份当前规则平台的所有规则，因为导入必须清空原有的分组和规则。</p>
			<p>2.导入文件必须是规则平台导出的ZIP压缩包，否则识别不了规则文件内容。</p>
			<p>3.文件大小不能超过1M。</p>
		</td>
	</tr>
	</table>
</div>


<div align="center">
		
</div>

<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" 
   aria-labelledby="myModalLabel" aria-hidden="false">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
         	<button type="button" class="close" 
               data-dismiss="modal" aria-hidden="true">
                  &times;
            </button>
            <h4 class="modal-title" id="myModalLabel">规则导出进度</h4>
         </div>
         <div class="modal-body">
         	<br/>
            	<div id="showDiv" align="left">
            		<table class="table">	
						<tr>
							<td>
							规则分组导出完成
							</td>
						</tr>
						<tr>
							<td>
							规则分组导出完成
							</td>
						</tr>
					</table>
            	</div>
            <br/>
         </div>       
      </div><!-- /.modal-content -->
   </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</form>
</div>
</div>
</div>
</body>
</html>