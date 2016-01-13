<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/include.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/css/edit.css"></link>
<script type="text/javascript">
	var saveNum=0;//需要保存的数量
	var color_NotValidate = "#febc81";//未验证或者验证失败的颜色 
	var color_NotRelease = 	"#d0d7d2";//未发布活验证通过的颜色
	
 	function saveRule(index,id){		 		
 		var content = $("#content_"+index).val();
 		content = content.replace(/\+/g,'%2B');//处理"+"号
 		
 		var paramstr =$("#paramstr_"+index).val();
 		var rulename = $("#rulename_"+index).val();
 		var bz = $("#bz_"+index).val();
		
 		var paramPairs=[
 				new ParamPair("content",content),
 		 		new ParamPair("rulefilename",$("#rulefilename").val()),
 		 		new ParamPair("groupid",$("#groupid").val()),
 		 		new ParamPair("id",id)
 		];
 		
 		if(paramstr!=undefined && paramstr != ""){
 			paramPairs[paramPairs.length]=new ParamPair("paramstr",paramstr);
 		}
 		
 		if(rulename!=undefined && rulename != ""){
 			paramPairs[paramPairs.length]=new ParamPair("rulename",rulename);
 		}
 		
 		if(bz!=undefined && bz != ""){
 			paramPairs[paramPairs.length]=new ParamPair("bz",bz);
 		}
 		
 		var url="<%=basePath%>ruleManager/ruleEdit";
 		postToServer(paramPairs,url,function(data){ 			
			if(data){
				if(data.resStatus == '0'){
					hideSaveButton(index);
					alert("规则已保存");
					if(index=="add"){
						window.location.href="<%=basePath%>ruleManager/ruleEditPre?rulefilename="+$("#rulefilename").val();
					}else{
						$("#title_"+index).css("background-color",color_NotValidate);
						shTest(index,"validate");
					}
				}else{
					alert("规则保存失败："+data.errorMsg);					
				}				
			}else{
				alert("规则保存失败："+data);				
			}		
 			 				
 		});
 	}
	function saveRuleCancel(){
 		$("#addTab").hide();
		$("#releaseButton").show();
		$("#delButton").show();
		$("#addButton").show();		
	}
 	
 	function deleteRule(index,id){ 	
 		if(confirm("您确定要删除此规则么？")){
	 		$("#id").val(id);
	 		document.dataForm.action="<%=basePath%>ruleManager/ruleDelete";
	 		document.dataForm.submit();
 		}
 	}
 	
 	function shRule(id,obj){
 		if(obj.innerHTML=="收起"){
 			obj.innerHTML="展开";
 			$("#shtr_"+id).hide();
 			if($("#shtr2_"+id))
 				$("#shtr2_"+id).hide();
 		}else{
 			obj.innerHTML="收起";
 			$("#shtr_"+id).show();
 			if($("#shtr2_"+id))
 				$("#shtr2_"+id).show();
 		}
 	}
 	
 	function shAdd(obj){
		$("#addTab").show();	
		$("#releaseButton").hide();
		$("#delButton").hide();
		$("#addButton").hide();
		
		$("#content_add").val("when\r\n\truleBean:RuleBean(ruleName == \"\");//规则说明\r\n\tparaMap:Map();\r\nthen\r\n\truleBean.setResStatus(0);//返回成功\r\n\truleBean.setResponse(\"\");//返回结果\r\n"); 	
		$("#rulename_add").val("");
 	}
 	
 	function shTest(index,shType){
 		if(shType=="test"){//测试按钮
 			$("#validateButton_"+index).hide();	
 			$("#testButton_"+index).show();	
 		}else{
 			$("#validateButton_"+index).show();	
 			$("#testButton_"+index).hide();	
 		}
 	}
 	
 	function releaseRule(){
 		if(saveNum>0){
 			alert("您还有未保存的规则，请先保存"); 			
 		}
 		
 		var paramPairs=[
 			new ParamPair("rulefilename",$("#rulefilename").val()) 		 		 		
 		];
 		var url="<%=basePath%>ruleManager/ruleRelease";
 		postToServer(paramPairs,url,function(data){ 			
 					if(data){
 						if(data.resStatus == '0'){
 							alert("规则已发布"); 					
 							window.location.href="<%=basePath%>ruleManager/ruleEditPre?rulefilename="+$("#rulefilename").val();
 						}else{
 							alert("规则发布失败："+data.errorMsg);					
 						}				
 					}else{
 						alert("规则发布失败："+data);				
 					}		
 		 			 				
 		 		});
 		 		 		
 	}
 	
 	//验证
 	function validateRule(index,rulename){
 		$("#ruleRes_"+index).val("验证中……"); 		
 		var rulefilename=$("#rulefilename").val(); 		 		
 		
 		var paramPairs=[
 				new ParamPair("ruleFileName",rulefilename),
 				new ParamPair("ruleName",rulename)
 		];
 		var url="<%= basePath%>ruleManager/validateRule";
 		postToServer(paramPairs,url,function(data){
			var res="验证失败！\r\n";
			if(data){
				if(data.resStatus == '0'){
					res="验证成功！\r\n";
					$("#title_"+index).css("background-color",color_NotRelease);
					shTest(index,"test");
				}else{
					$("#title_"+index).css("background-color",color_NotValidate);
					res += data.response;
				}
				
			}else{
				res += data;					
			}
			$("#ruleRes_"+index).val(res);	
		});
 		
 	}
 	
 	//测试
 	function testRule(index,rulename){
 		$("#ruleRes_"+index).val("验证中……"); 		
 		var rulefilename=$("#rulefilename").val(); 		
 		var paramStr=$("#paramstr_"+index).val();
 		
 		var paramPairs=[
 				new ParamPair("ruleFileName",rulefilename),
 				new ParamPair("ruleName",rulename),
 				new ParamPair("paramStr",paramStr)
 		];
 		var url="<%= basePath%>ruleManager/testRule";
 		postToServer(paramPairs,url,function(data){
			var res="测试结果：失败！\r\n";
			if(data){
				if(data.resStatus == '0'){
					res="测试结果：成功！\r\n";
					savaParaStr(index,rulename);
					$("#ruleRes_"+index).css("background-color",color_NotRelease);
				}else{
					$("#ruleRes_"+index).css("background-color",color_NotValidate);
				}
				res += "返回信息："+data.response;
			}else{
				res += "返回信息："+data;					
			}
			$("#ruleRes_"+index).val(res);	
		});
 		
 	}
 	
 	function savaParaStr(index,rulename){
 		var rulefilename=$("#rulefilename").val(); 		
 		var paramStr=$("#paramstr_"+index).val();
 		var paramPairs=[
 		 				new ParamPair("ruleFileName",rulefilename),
 		 				new ParamPair("ruleName",rulename),
 		 				new ParamPair("paramStr",paramStr)
 		 		];
 		var url="<%= basePath%>ruleManager/saveParamStr";
 		postToServer(paramPairs,url,function(data){
			if(data){
				if(data.resStatus == '0'){
					alert("参数保存成功！");
					$("#ruleRes_"+index).css("background-color",color_NotRelease);
				}else{
					$("#ruleRes_"+index).css("background-color",color_NotValidate);
				}
			}
		});
 		
 		
 	}
 	function showSaveButton(index){
 		$("#saveButton_"+index).show();
 		saveNum++;
 	}
 	
 	function hideSaveButton(index){
 		$("#saveButton_"+index).hide();
 		saveNum--;
 	}
 	
 	function ruleFile(){
 		if(confirm("归档后规则将不可用，您确定要归档么？")){
 			document.dataForm.action="<%=basePath%>ruleManager/ruleFile";
 	 		document.dataForm.submit();
 		}
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
         <li class="active"><a href="#">规则编辑</a></li>           
      </ul>
   </div>
</nav>
<div class="mainDiv">
<div class="layoutDiv">
<div class="well well-lg">

<form action="<%=basePath%>ruleManager/ruleEdit"  id="dataForm" name="dataForm" method="post" >
<!-- 隐藏区域 -->
<input type="hidden" name="id" id="id" value="" />
<input type="hidden" name="content" id="content" value="" />
<input type="hidden" name="paramstr" id="paramstr" value="" />
<input type="hidden" name="rulename" id="rulename" value="" />
<input type="hidden" name="rulefilename" id="rulefilename" value="${ruleHead.rulefilename}" />
<input type="hidden" name="groupid" id="groupid" value="${ruleHead.groupid}" />

<table width="100%">	
	<tr>
		<td align="right">
			<div style="background:#febc81;height:12px;width:150px;font-size:10px" >未验证或者验证失败</div><br/>
			<div style="background:#d0d7d2;height:12px;width:150px;font-size:10px" >未发布或者验证通过</div><br/>
		</td>
	</tr>
	<tr>
		<td>
			<div class="panel panel-default">
   				<div class="panel-heading">
   					<table width="100%">	
						<tr>
							<td align="left" width="30%">
   								<button type="button" class="btn btn-success btn-sm" onclick="saveRule('head','${ruleHead.id}')" style="display:none;" id="saveButton_head" >保存</button>  						
							</td>
							<td align="center">
								<div class="input-group">			
								<span class="input-group-addon">备注</span>	
								 <input type="text" id="bz_head" name="bz_head" class="form-control" onchange="showSaveButton('head')" value="${ruleHead.bz}" />
								</div>
							</td>
							<td align="right" width="30%">
								<a href="#" onclick="shRule('head',this)">展开</a>
							</td>
						</tr>
					</table>
   				</div>
				<table class="table table-bordered">				
				<tr id="shtr_head" style="display:none">										
					<td align="center">
						<textarea rows="8" cols="100" id="content_head" name="content_head" onchange="showSaveButton('head')">${ruleHead.content}</textarea>
					</td>
				</tr>
				</table>
			</div>		
		</td>		
	</tr>
	
	
			
			<c:forEach items="${List}" var="item" varStatus="status">
			<tr>
			<td>
				<div class="panel panel-default">
   					<div class="panel-heading" id="title_${status.index}" <c:if test="${item.status == '1' }">style="background-color:#febc81"</c:if><c:if test="${item.status == '3' }">style="background-color:#d0d7d2"</c:if>>
   						<table width="100%">	
						<tr>
							<td align="left" width="30%">
   								<button type="button" class="btn btn-success btn-sm" id="saveButton_${status.index}" style="display:none;" onclick="saveRule(${status.index},'${item.id}')" >保存</button>
   								<button type="button" class="btn btn-danger btn-sm" onclick="deleteRule(${status.index},'${item.id}')" >删除</button>   						
							</td>
							<td align="center">
								<input type="hidden" id="rulename_${status.index}" name="rulename_${status.index}" value="${item.rulename}" />
								${item.rulename}
							</td>
							<td align="right" width="30%">
								<a href="#" onclick="shRule('${status.index}',this)">展开</a>
							</td>
						</tr>
						</table>
					</div>
					
					<table class="table table-bordered">						
						<tr id="shtr_${status.index}" style="display:none">							
							<td colspan="2" align="center">
								<textarea rows="20" cols="100" id="content_${status.index}" name="content_${status.index}" onchange="showSaveButton(${status.index})">${item.content}</textarea>
							</td>
						</tr>
						<tr id="shtr2_${status.index}" style="display:none">
							<td  align="center" id="testTd_${status.index}">
								
									<input type="button" value="验证" id="validateButton_${status.index}" onclick="validateRule(${status.index},'${item.rulename}')" class="btn btn-default" <c:if test="${item.status == '3' || item.status == '0' }">style="display:none"</c:if> />
																
									<input type="button" value="测试" id="testButton_${status.index}" onclick="testRule(${status.index},'${item.rulename}')" class="btn btn-default" <c:if test="${item.status == '1' }">style="display:none"</c:if> />																
								
							</td>
							<td>
								<div style="width:49%;border-right:1px solid #bebde3;float:left">
								<textarea rows="5" cols="48" id="paramstr_${status.index}" name="paramstr_${status.index}">${item.paramstr}</textarea>
								</div>	
								<div class="rightDiv" style="width:49%;float:right">	
								<textarea rows="5" cols="48" id="ruleRes_${status.index}" name="ruleRes_${status.index}" readonly="readonly">验证/测试结果</textarea>	
								</div>									
							</td>
						</tr>
						
					</table>
					</div>
			</td>
			</tr>
			</c:forEach>
			<tr>
			<td style="display:none" id="addTab">
				<div class="panel panel-default">
   					<div class="panel-heading" id="title_${status.index}">
   						<table width="100%">	
						<tr>
							<td align="left" width="30%">
   								<button type="button" class="btn btn-success btn-sm" onclick="saveRule('add','add')" >保存</button>
   								<button type="button" class="btn btn-default btn-sm" onclick="saveRuleCancel()" >取消</button>   								  						
							</td>
							<td align="center">
								<div class="input-group">			
								<span class="input-group-addon">请输入规则名</span>	
								 <input type="text" id="rulename_add" name="rulename_add" class="form-control" value="" />
								</div>
							</td>
							<td align="right" width="30%">
								<a href="#" onclick="shRule('add',this)">收起</a>
							</td>
						</tr>
						</table>
   					</div>
						<table class="table table-bordered">				
						
						<tr id="shtr_add">							
							<td align="center">
								<textarea rows="20" cols="100" id="content_add" name="content_add" ></textarea>
							</td>
						</tr>
						</table>
			</div>
		</td>
	</tr>
	
</table>

<div align="center">
	<button type="button" class="btn btn-success" id="addButton" onclick="shAdd(this)">新 增</button>
	<button type="button" class="btn btn-warning" id="releaseButton" onclick="releaseRule()">发 布</button>
	<button type="button" class="btn btn-danger" id="delButton" onclick="ruleFile()">归 档</button>	
</div>

</form>

<div class="Hint">
<p>1.“新增”是在当前规则文件中添加新的子规则。</p>
<p>2.“发布”是将当前规则格式化并生成规则文件，规则马上生效，并在历史版本中生成历史版本。</p>
<p>3.“归档”即删除，但是会在历史版本中生成历史版本。</p>
</div>

</div>
</div>
</div>
</body>
</html>