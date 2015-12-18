<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/include.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>导入导出</title>
<script type="text/javascript">
	var groupSize=${fn:length(groupList)};
	var showText="<table class=\"table\">";
	var timeStr="";
	var exportNum=0;
	function shGroup(index,obj){
		if(obj.innerHTML=="收起"){
			obj.innerHTML="展开";
			$("#group_"+index).hide();
		}else{
			obj.innerHTML="收起";
			$("#group_"+index).show();
		}
	}	
	
	function groupCheck(obj,index){
		if(obj.checked){
			
			$("input[name=ruleFileAry_"+index+"]").prop("checked", true);			
		}else{
			$("input[name=ruleFileAry_"+index+"]").prop("checked", false);
		}
	}
	
	function exportRule(obj){
		$(obj).prop("disabled",true);		
		
		showText="<table class=\"table\">";
		if(groupSize){
			var groupid;
			var groupname;
			var fileNameAry;
			var fileStr;
			timeStr=getTimeStr();
			for(var index=0;index<groupSize;index++){//循环获取分组
				groupid = $("#groupid_"+index).val();//分组id
				groupname = $("#groupname_"+index).val();//分组名
				fileNameAry=$("input[name=ruleFileAry_"+index+"]");//分组下的规则文件
				fileStr="";
				if(fileNameAry){//该分组下有规则文件
					for(var i=0;i<fileNameAry.length;i++){
						if(fileNameAry[i].checked){//选中要导出的文件
							fileStr+=","+fileNameAry[i].value;
						}
						
					}
				}
				if(fileStr!=""){//有需要导出的规则
					fileStr=fileStr.substr(1);//删除最前面的','					
					exportGroup(groupid,groupname,fileStr);
				}
			}
		}
		$(obj).prop("disabled",false);	
	}
	function exportGroup(groupid,groupname,fileStr){
		$('#myModal').modal({
			keyboard: false
			})
		showText+="<tr><td id=\"text_"+groupid+"\">规则分组（"+groupname+"）导出中，请稍后……</td></tr>";
		$("#showDiv").html(showText+"</table>");	
		var paramPairs=[
		 				new ParamPair("groupid",groupid),
		 		 		new ParamPair("fileStr",fileStr),
		 		 		new ParamPair("timeStr",timeStr)
		 		];
		
		var url="<%=basePath%>ruleExOrIm/ruleExport";
		exportNum++;//调用导出前添加一个正在导出的数目
 		postToServer(paramPairs,url,function(data){ 			
			if(data){
				if(data.resStatus == '0'){
					$("#text_"+groupid).text("规则分组（"+groupname+"）导出完成");
				}else{
					$("#text_"+groupid).text("规则分组（"+groupname+"）导出失败："+data.errorMsg);
				}				
			}else{
				$("#text_"+groupid).text("规则分组（"+groupname+"）导出失败："+data);
			}		
			exportzip();//导出完成后打包
 		});
	}
	
	function exportzip(){
		//判断是否所有的分组都导出完成
		exportNum--;//调用一次说明导出完成一个
		if(exportNum>0) return;//还有未导出完成的
		//打包导出文件
		showText=$("#showDiv").html();	
		//showText=showText.substr(0,showText.indexOf("</table>"));		
		showText=showText.substr(0,showText.indexOf("</tbody>"));
		showText+="<tr><td id=\"text_zip\">规则打包中，请稍后……</td></tr>";
		$("#showDiv").html(showText+"</tbody></table>");
		var paramPairs=[
	 		 		new ParamPair("timeStr",timeStr)
	 		];
		var url="<%=basePath%>ruleExOrIm/ruleExportZip";
 		postToServer(paramPairs,url,function(data){ 			
			if(data){
				if(data.resStatus == '0'){
					$("#text_zip").html("规则打包完成：<a href=\"#\" onclick=\"download()\">下载</a>");
					$("#downButton").show();
				}else{
					$("#text_zip").text("规则打包失败："+data.errorMsg);
				}				
			}else{
				$("#text_zip").text("规规则打包失败："+data);
			}		
 		});
	}
	
	function getTimeStr(){
		var myDate = new Date();		  
		var yyyy = myDate.getFullYear();    //获取完整的年份(4位,1970-????)  
		var mm = myDate.getMonth()+1;       //获取当前月份(0-11,0代表1月)  
		if(mm.length==1) mm="0"+mm;
		var dd = myDate.getDate();        //获取当前日(1-31)  
		var hh = myDate.getHours();       //获取当前小时数(0-23)  
		var mh = myDate.getMinutes();     //获取当前分钟数(0-59)  
		var ss = myDate.getSeconds();     //获取当前秒数(0-59)  
		return ""+yyyy+mm+dd+hh+mh+ss;
	}
	
	function download(){
		location.href="<%=basePath%>download/rules.zip";
	}
</script>
</head>
<body>
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
	<div class="navbar-header">
      <a class="navbar-brand" href="#">规则导出</a>
   </div>	
</nav>
<div class="mainDiv">
<div class="layoutDiv">
<!-- 导航 -->

<!-- 主体 -->
<div class="well well-lg">

<form action="<%=basePath%>ruleExOrIm/ruleExport"  id="dataForm" name="dataForm" method="post">

<c:forEach items="${groupList}" var="item" varStatus="status">
<span id="resListTab">
<div class="panel panel-default">
   <div class="panel-heading">
   		<table width="100%" >
   		<tr>
   			<td width="10%"><input type="checkbox" checked="checked" onclick="groupCheck(this,'${status.index }')" /></td> 
			<td width="80%" align="center"><b>规则分组：</b><c:out value="${item.groupname }" /></td>
			<td align="right"><a href="#" onclick="shGroup('${status.index}',this)">收起</a></td>
   		</tr>
   		</table>
   		<input type="hidden" id="groupid_${status.index}" value="${item.id }" />
   		<input type="hidden" id="groupname_${status.index}" value="${item.groupname }" />
   
   </div>
	<table class="table" id="group_${status.index}">	
		<tr><th>选择</th><th width="45%">规则文件名称</th><th width="45%">备注</th></tr>
		
		<c:forEach items="${item.ruleFileList}" var="item2" varStatus="status2">
		<tr>
			<td>
				<input type="checkbox" id="ruleFile_${item2.version}" name="ruleFileAry_${status.index }" checked="checked" value="${item2.version}"/>
			</td>
			<td>${item2.rulefilename }</td>
			<td>${item2.bz }</td>
			
		</tr>
		</c:forEach>
	</table>
</div>
</span>
</c:forEach>


<div align="center">
	<button type="button" class="btn btn-success" onclick="exportRule(this)">导出</button>
	&nbsp;
	<button type="button" id="downButton" class="btn btn-success" onclick="download()" style="display:none">下载</button>	
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