
if(typeof GroupList == "undefined" || !GroupList){
	var GroupList = {};
};

//
$(function(){
	GroupList.initGrid();
});

/**
 * 初始化表格
 */
GroupList.initGrid = function(){
	$('#dg').datagrid({    
	    url:contextPath+'/groupManager/list',
	    pagination:true,//显示分页  
	    pageSize:getAutoPageSize(105),
		pageList:[getAutoPageSize(105),
		getAutoPageSize(105) * 2],
		singleSelect:true,
		fitColumns:false,
		nowrap:true,
	    columns:[[    
	        {field:'groupname',title:'名称',width:100},
	        {field:'createTime',title:'接口',width:100},
	        {field:'updateTime',title:'接口',width:100},   
	        {field:'process',title:'操作',width:100,formatter:GroupList.operationFormatter}    
	    ]]    
	});
}

GroupList.operationFormatter = function(value,row,index){
	return '&nbsp;<a class="link" href="javascript:javascript:void(0)" onclick="GroupList.doEdit('+index+')">编辑</a>&nbsp;'+
	   '&nbsp;<a class="link" href="javascript:javascript:void(0)" onclick="GroupList.doCancel('+index+')">注销</a>&nbsp;';
}

/**
 * 点击编辑按钮
 */
GroupList.doEdit = function(index){
	alert(index);
}

/点击注销按钮
GroupList.doCancel = function(index){
	alert(index);
}

