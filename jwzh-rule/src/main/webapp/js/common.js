
/**
 * 参数对象
 */

window.ParamPair = (function(){
	function ParamPair(key, valueObj){        
        this.name = key;
        this.value = valueObj;
    }
	
	return ParamPair
})();

/**
 * 公共ajax post方法 同步
 * paramPairs:ParamPairs数组
 * url:请求URL
 * calback:返回处理参数
 */
function postToServer(paramPairs,url,calback){
	var dataStr="";
	//参数处理
	for(var i=0;i<paramPairs.length;i++){
		if(dataStr!="")
			dataStr+="&";
		dataStr+=paramPairs[i].name+"="+paramPairs[i].value;
	}
	
	if(calback==null)
		calback=postToServerCalback;
	
	$.ajax({
		async:true,
		type:"POST",
		data:dataStr,
		dataType:"json",
		url:url,
		success:calback,
		error: function(data){
			if(data){
				if(data.status==200){
					calback(data.responseText);
				}else if(data.status==409){
					alert("用户操作超时,请重新登录！");
					window.top.location.href=contextPath+"/founderRule/index";
				}else if(data.statusText){
					alert(data.statusText);
				}else{
					alert(data);
				}
				
			}else{
				alert("post to server error!");
			}
			
		}
	});
}

/**
 * 公共ajax post方法 异步
 * paramPairs:ParamPairs数组
 * url:请求URL
 * calback:返回处理参数
 */
function postToServerAsync(paramPairs,url,calback){
	var dataStr="";
	//参数处理
	for(var i=0;i<paramPairs.length;i++){
		if(dataStr!="")
			dataStr+="&";
		dataStr+=paramPairs[i].name+"="+paramPairs[i].value;
	}
	
	if(calback==null)
		calback=postToServerCalback;
	
	$.ajax({
		async:false,
		type:"POST",
		data:dataStr,
		dataType:"json",
		url:url,
		success:calback,
		error: function(data){
			if(data){
				if(data.status==200){
					calback(data.responseText);
				}else if(data.status==409){
					alert("用户操作超时,请重新登录！");
					window.top.location.href=contextPath+"/founderRule/index";
				}else if(data.statusText){
					alert(data.statusText);
				}else{
					alert(data);
				}
				
			}else{
				alert("post to server error!");
			}
			
		}
	});
}

function postToServerCalback(data){
	
}