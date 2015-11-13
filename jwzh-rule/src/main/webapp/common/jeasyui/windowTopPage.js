// 顶层页面公共JS
// author : redstorm
// create time : 2014-05-08
//
// 1、顶层页面字典管理（combotree 的数据不能用层顶页面加载，因为多个副本之间会有影响，树形字典的数据加载到层顶页面只能用于取得字典名称）
// 2、顶层页面弹出窗口

var publicDictArray = new Array(); // 字典管理数组
var publicWindowArray = new Array(); // 窗口管理数组

// 加载字典数据
// url      加载的字典地址
// isReload 是否重新加载（默认为false）
function getPublicDict(url, isReload) {
	if ("undefined" != typeof url && url != null && url != "") {
		if ("undefined" == typeof isReload) {
			isReload = false;
		}
		if (!isReload) {
			var dictData = publicDictArray[url];
			if (dictData != null) {
				return dictData;
			}
		}
		
		$.ajax({
			url: url,
			cache: false,
			type: "GET",
			async: false, 
			dataType: "json",
			success: function(data) {
				publicDictArray[url] = data;
			},

			error: function() {
				alert("顶层页面字典加载错误：\n\n" + url);
			}
		});
		
		return publicDictArray[url];
	}
	return null;
}

// 移除一个已加载的字典数据
// url 加载的字典地址
function removePublicDict(url) {
	if ("undefined" != typeof url && url != null && url != "") {
		for (var item in publicDictArray) {
			if (item == url) {
				delete publicDictArray[item];
			}
		}
	}
}

// 根据字典代码取得字典名称
// url      加载的字典地址
// dictID   字典代码（多个时用逗号分隔）
// isReload 是否重新加载（默认为false）
function getDictName(url, dictID, isReload) {
	var dictName = "";
	if ("undefined" != typeof dictID && dictID != null && dictID != "") {
		var data = getPublicDict(url, isReload);
		if (data != null && data.length > 0) {
			var nameArray = [];
			var dictIDArray = dictID.split(",");
			var searchID = "";
			for (var j = 0; j < dictIDArray.length; j++) {
				searchID = dictIDArray[j];
				if (searchID != "") {
					for (var i = 0; i < data.length; i++) {
						if (data[i].id == searchID) {
							nameArray.push(data[i].text);
							break;
						}
						else if (data[i].children) {
							searchChildren(data[i].children, searchID, nameArray);
						}
					}
				}
			}
			dictName = nameArray.join(",");
		}
	}
	return dictName;
}

// 树形字典的递归搜索
function searchChildren(node, searchID, nameArray) {
	for (var i = 0; i < node.length; i++) {
		if (node[i].id == searchID) {
			nameArray.push(node[i].text);
			return true;
		}
		else if (node[i].children) {
			if (searchChildren(node[i].children, searchID, nameArray)) {
				return true;
			}
		}
	}
	return false;
}

// 动态弹出一个窗口
// isCache      是否缓存页面（默认为false不缓存）
// windowID     窗口的ID（isCache=true，windowID确保在同一个页面中唯一；isCache=false，windowID可以不指定；）
// openURL      打开的URL地址
// paramArray   传入的参数数组（作为打开页面的doInit()方法的参数）
// dataOptions  jquery.window中的data-options定义参数
function openWindow(isCache, windowID, openURL, paramArray, dataOptions) {
	if ("undefined" == typeof isCache || isCache == null || isCache == "") {
		isCache = false;
	}
	if ("undefined" == typeof paramArray || paramArray == null) {
		paramArray = [];
	}
	var windowObject;
	if (isCache) { // 缓存页面
		if (windowID == null || windowID == "") {
			$.messager.alert('页面错误','打开窗口openWindow()方法中：<br><br>参数 windowID 不能为空！','error');
			return;
		}
		windowObject = publicWindowArray[windowID];
		if (windowObject == null) {
			var hasBind = false;
			var iframe;
			if ( $("#" + windowID).length == 0 ) {
				$("body").append("<div id='"+ windowID +"' style='z-index:300;'></div>");
				iframe = $("<iframe id='"+ windowID +"_iframe'></iframe>") 
					.attr('height', '100%') 
					.attr('width', '100%') 
					.attr('marginheight', '0') 
					.attr('marginwidth', '0') 
					.attr('frameborder','0')
					.attr('scrolling','auto');
				$('#' + windowID).append(iframe);
				hasBind = true;
			}
			windowObject = $("#" + windowID).dialog(dataOptions);
			windowObject.dialog({
				onBeforeClose: function() { // 关闭之前判断是否正在提交中
					var curWindowObject = this;
					if (openWindowButtonHasDisabled(curWindowObject)) {
						return false;
					}
					return true;
				},
				onBeforeDestroy: function() { // 卸载时回收内存
					var iframeObject = iframe.get(0).contentWindow;
					iframeObject.document.write("");
					iframeObject.close();
					if ($.isFunction(window.CollectGarbage)) { 
						window.CollectGarbage(); 
					}
				}
			});
			var opts = windowObject.dialog('options');
			paramArray['windowID'] = windowID;
			paramArray['datagrid_ID'] = dataOptions.datagrid_ID;
			if (hasBind) {
				var frameOnLoad = function() {
					var _this = this; // this 是iframe对象
					var iframeObject = iframe.get(0).contentWindow;
					if (iframeObject.doInit && typeof(iframeObject.doInit) == 'function') { // 执行初始化方法  doInit
						iframeObject.doInit(paramArray);
					}	
				}
				iframe.bind('load', frameOnLoad);
				iframe.attr('src', openURL);
			}
			windowObject.dialog('open');
			publicWindowArray[windowID] = windowObject;
		}
		else {
			windowObject.dialog('open');
		}
	}
	else {
		if ("undefined" == typeof windowID || windowID == null || windowID == "") {
			var myTime = (new Date()).getTime();
			windowID = "win_" + myTime;
		}
		$("body").append("<div id='"+ windowID +"' style='z-index:300;'></div>");
		var iframe = $("<iframe id='"+ windowID +"_iframe'></iframe>") 
				.attr('height', '100%') 
				.attr('width', '100%') 
				.attr('marginheight', '0') 
				.attr('marginwidth', '0') 
				.attr('frameborder','0')
				.attr('scrolling','auto');
		$('#' + windowID).append(iframe);
		windowObject = $("#" + windowID).dialog(dataOptions);
		windowObject.dialog({
			onBeforeClose: function() { // 关闭之前判断是否正在提交中
				var curWindowObject = this;
				if (openWindowButtonHasDisabled(curWindowObject)) {
					return false;
				}
				return true;
			},
			onClose: function() { // 关闭时销毁
				$(this).dialog('destroy');
			},
			onBeforeDestroy: function() { // 卸载时回收内存
				var iframeObject = iframe.get(0).contentWindow;
				iframeObject.document.write("");
				iframeObject.close();
				if ($.isFunction(window.CollectGarbage)) { 
					window.CollectGarbage(); 
				}
			}
		});
		var opts = windowObject.dialog('options');
		paramArray['windowID'] = windowID;
		paramArray['datagrid_ID'] = dataOptions.datagrid_ID;
		var frameOnLoad = function() {
			var _this = this; // this 是iframe对象
			var iframeObject = iframe.get(0).contentWindow;
			if (iframeObject.doInit && typeof(iframeObject.doInit) == 'function') { // 执行初始化方法  doInit
				iframeObject.doInit(paramArray);
			}	
		}
		iframe.bind('load', frameOnLoad);
		openURL = openURL + (openURL.indexOf('?') != -1 ? '&' : '?') + "time="+(new Date()).getTime();
		iframe.attr('src', openURL);
		windowObject.dialog('open');
	}
}

// 销毁顶层页面的缓存窗口
// windowID     窗口的ID（多个时用逗号分隔）
function destroyWindow(windowID) {
	if ("undefined" != typeof windowID && windowID != null && windowID != "") {
		var tempArray = windowID.split(",");
		for (var i = 0; i < tempArray.length; i++) {
			var tempWindowID = tempArray[i];
			var windowObject = publicWindowArray[tempWindowID];
			if (windowObject != null) {
				windowObject.panel('destroy');
				delete publicWindowArray[tempWindowID];
			}
		}
	}
}

// 树形字典多选对话框
// isCache         是否缓存页面（默认为false不缓存）
// windowID        窗口的ID（isCache=true，windowID确保在同一个页面中唯一；isCache=false，windowID可以不指定；）
// parentWindow    调用页面的window对象
// dictName        字典对应的json文件
// dictInputID     字典值存放的ID
// dictShowID      字典显示框的ID
// onlyLeaf        是否只能选叶子结点（默认为true）
// allExcludeChild 父结点选中了就不包括子结点（默认为false，只有当onlyLeaf为false时才生效）
// dataFilter      数据过滤正则表达式
// onOkMethod      对话中点击确认后执行原页面中的方法（如：“xzqh_onOk”）
function dict_multiSelectTree(isCache, windowID, parentWindow, dictName, dictInputID, dictShowID, onlyLeaf, allExcludeChild, dataFilter, onOkMethod) {
	if (isCache) {
		if (windowID == null || windowID == "") {
			$.messager.alert('页面错误','字典选择dict_multiSelectTree()方法：<br><br>参数 windowID 不能为空！','error');
			return;
		}
	}
	if (!windowID) {
		var myTime = (new Date()).getTime();
		windowID = "win_" + myTime;
	}
	var openURL = contextPath + "/forward/commonDictMultiTree";
	if ("undefined" == typeof onlyLeaf || onlyLeaf == null) {
		onlyLeaf = true;
	}
	if ("undefined" == typeof allExcludeChild || allExcludeChild == null) {
		allExcludeChild = false;
	}
	if ("undefined" == typeof dataFilter || dataFilter == null) {
		dataFilter = "";
	}
	if ("undefined" == typeof onOkMethod || onOkMethod == null) {
		onOkMethod = "";
	}
	var paramArray = [];
	paramArray['parentWindow'] = parentWindow;
	paramArray['dictName'] = dictName;
	paramArray['dictInputID'] = dictInputID;
	paramArray['dictShowID'] = dictShowID;
	paramArray['onlyLeaf'] = onlyLeaf;
	paramArray['allExcludeChild'] = allExcludeChild;
	paramArray['dataFilter'] = dataFilter;
	paramArray['onOkMethod'] = onOkMethod;
	var dataOptions = {
		title: '&nbsp;字典选择',
		url: dictName, 
		onlyLeaf: onlyLeaf, 
		dataFilter: dataFilter,
		width: 800,   
	    height: 400,  
		collapsible: false, 
		minimizable: false, 
		maximizable: false,
		closable: true,
	    closed: false,    
	    cache: false,
	    inline: false,
		resizable: false, 
	    modal: true};
	dataOptions.buttons = [
		{
			text: '确定',
			iconCls: 'icon-ok',
			handler: function() {
				var iframeObject = window.frames[windowID + '_iframe'];
				if (iframeObject.contentWindow) {
					iframeObject = iframeObject.contentWindow;
				}
				var formObject = iframeObject.$('form').first();
				if (formObject) {
					if (formObject.form('validate')) { // 表单的验证
						if (iframeObject.beforeSubmit && typeof(iframeObject.beforeSubmit) == 'function') { // 执行自定义方法 beforeSubmit
							if (iframeObject.beforeSubmit() == false) {
								return false;
							}
						}	
						iframeObject.ok_execute();
						$('#' + windowID).dialog('close');
						if (onOkMethod != null && onOkMethod != "") {
							try {
								var parentWinObject = parentWindow;
								if (parentWinObject.contentWindow) {
									parentWinObject = parentWinObject.contentWindow;
								}
								eval("parentWinObject." + onOkMethod + "()");
							}
							catch (err) {
								$.messager.alert('页面错误', "执行事件 "+ onOkMethod + " 有错误发生：<br/><br/>错误名称: " + err.name + "<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;错误行号:" + (err.number & 0xFFFF ) + "<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;错误信息:" + err.message, 'error'); 
							}
						}
					}
				}
			}
		},
		{
			text: '关闭',
			iconCls: 'icon-cancel',
			handler: function() {
				$('#' + windowID).dialog('close');
			}
		}
	];
	openWindow(isCache, windowID, openURL, paramArray, dataOptions);
}

// 动态弹出一个窗口（带有保存与退出按钮）
// isCache         是否缓存页面（默认为false不缓存）
// windowID        窗口的ID（isCache=true，windowID确保在同一个页面中唯一；isCache=false，windowID可以不指定；）
// parentWindow    调用页面的window对象
// paramArray      传入打开页面的参数（如：{document:document, param1: 'test1', param2: 2}）
// dataOptions     jquery.window中的data-options定义参数
// submitConfirm   提交确认提示信息（为空则不出现确认框）
// onSubmitSuccess 对话中提交成功后执行的方法点击确认后执行原页面中的方法（如：“xzqh_onSubmitSuccess”，该方法的参数会传入弹出页面所有已提交的数据）
// oldPageObject   执行onSubmitSuccess方法时返回原页面的参数（如：原页面的某个动太对象{oldObject:this}）
function openWindowWithSave(isCache, windowID, parentWindow, paramArray, dataOptions, submitConfirm, onSubmitSuccess, oldPageObject) {
	if (!dataOptions.url) {
		topMessagerAlert('', '弹出层缺少 url 参数！');
		return;
	}
	if (!dataOptions.title) {
		dataOptions.title = '';
	}
	dataOptions.title = '&nbsp;' + dataOptions.title;
	if (!dataOptions.width) {
		dataOptions.width = 850;
	}
	if (!dataOptions.height) {
		dataOptions.height = 420;
	}
	if (!windowID) {
		var myTime = (new Date()).getTime();
		windowID = "win_" + myTime;
	}
	dataOptions.collapsible = dataOptions.collapsible ? dataOptions.collapsible : false;
	dataOptions.minimizable = dataOptions.minimizable ? dataOptions.minimizable : false;
	dataOptions.maximizable = dataOptions.maximizable ? dataOptions.maximizable : false; //是否最大化图标
	dataOptions.closable = true;
	dataOptions.closed = false;   
	dataOptions.cache = false;
	dataOptions.inline = false;
	dataOptions.resizable = dataOptions.maximizable ? dataOptions.maximizable : false; 
	dataOptions.modal = true;
	dataOptions.buttons = [
		{
			text: '保存',
			iconCls: 'icon-save',
			handler: function() {
				var bottonObject = this;
				if (buttonDisabled(bottonObject) == false) {
					return false;
				}
				var iframeObject = window.frames[windowID + '_iframe'];
				if (iframeObject.contentWindow) {
					iframeObject = iframeObject.contentWindow;
				}
				var formObject = iframeObject.$('form').first();
				if (formObject) {
					checkComboIsClosed(iframeObject, formObject); // 提交之前强行校验下拉框的是否关闭
					if (formObject.form('validate')) { // 表单的验证
						if (iframeObject.beforeSubmit && typeof(iframeObject.beforeSubmit) == 'function') { // 执行自定义方法 beforeSubmit
							if (iframeObject.beforeSubmit() == false) {
								buttonEnabled(bottonObject);
								return false;
							}
						}
						if (submitConfirm) {
							topMessager.confirm('', submitConfirm, function(r) {
								if (r) {
									formObject.form('submit',{
										dataType : 'json',
										onSubmit: function() {
										},
										success: function(result) {
											buttonEnabled(bottonObject);
											if (result && result.indexOf('serverError="500"') != -1) { // 服务端跳转到500页面
												if (iframeObject.isUploadFilePage && iframeObject.isUploadFilePage == "1") {
													$.messager.alert('系统信息', '上传文件失败！<br/><br/>可能超过最大上传文件大小限制！', 'error');
												}
												else {
													$.messager.alert('系统信息', '保存数据失败！<br/><br/>服务端出现致命错误！', 'error');
												}
												return;
											}
											result = parseReturn(result);
											var isDoSubmitResult = true;
											if (result.status == 'success') { // 返回成功后执行的方法
												if (iframeObject.afterSubmit && typeof(iframeObject.afterSubmit) == 'function') { // 执行自定义方法afterSubmit
													iframeObject.afterSubmit(result);
												}
												if (onSubmitSuccess != null && onSubmitSuccess != "") {
													var submitData = iframeObject.getFormData(formObject[0]);
													for (var item in result) {
														submitData[item] = result[item];
													}
													try {
														doSubmitResult(result, windowID, null);
														isDoSubmitResult = false;
														var parentWinObject = parentWindow;
														if (parentWinObject.contentWindow) {
															parentWinObject = parentWinObject.contentWindow;
														}
														eval("parentWinObject." + onSubmitSuccess + "(oldPageObject, submitData)");
													}
													catch (err) {
														$.messager.alert('页面错误', "执行事件 "+ onSubmitSuccess + " 有错误发生：<br/><br/>错误名称: " + err.name + "<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;错误行号:" + (err.number & 0xFFFF ) + "<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;错误信息:" + err.message, 'error'); 
													}
												}
												if (iframeObject.successCloseWindow && typeof(iframeObject.successCloseWindow) == 'function') { // 执行自定义方法successCloseWindow，返回true关闭窗口
													if (!iframeObject.successCloseWindow(result)) {
														doSubmitResult(result, null, null);
														return;
													}
												}
											}
											if (isDoSubmitResult) {
												doSubmitResult(result, windowID, null);
											}
										}
									});
								}
								else {
									buttonEnabled(bottonObject);
								}
							}); 
						}
						else {
							formObject.form('submit',{
								dataType : 'json',
								onSubmit: function() {
								},
								success: function(result) {
									buttonEnabled(bottonObject);
									if (result && result.indexOf('serverError="500"') != -1) { // 服务端跳转到500页面
										if (iframeObject.isUploadFilePage && iframeObject.isUploadFilePage == "1") {
											$.messager.alert('系统信息', '上传文件失败！<br/><br/>可能超过最大上传文件大小限制！', 'error');
										}
										else {
											$.messager.alert('系统信息', '保存数据失败！<br/><br/>服务端出现致命错误！', 'error');
										}
										return;
									}
									result = parseReturn(result);
									var isDoSubmitResult = true;
									if (result.status == 'success') { // 返回成功后执行的方法
										if (iframeObject.afterSubmit && typeof(iframeObject.afterSubmit) == 'function') { // 执行自定义方法afterSubmit
											iframeObject.afterSubmit(result);
										}
										if (onSubmitSuccess != null && onSubmitSuccess != "") {
											var submitData = iframeObject.getFormData(formObject[0]);
											for (var item in result) {
												submitData[item] = result[item];
											}
											try {
												doSubmitResult(result, windowID, null);
												isDoSubmitResult = false;
												var parentWinObject = parentWindow;
												if (parentWinObject.contentWindow) {
													parentWinObject = parentWinObject.contentWindow;
												}
												eval("parentWinObject." + onSubmitSuccess + "(oldPageObject, submitData)");
											}
											catch (err) {
												$.messager.alert('页面错误', "执行事件 "+ onSubmitSuccess + " 有错误发生：<br/><br/>错误名称: " + err.name + "<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;错误行号:" + (err.number & 0xFFFF ) + "<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;错误信息:" + err.message, 'error'); 
											}
										}
										if (iframeObject.successCloseWindow && typeof(iframeObject.successCloseWindow) == 'function') { // 执行自定义方法successCloseWindow，返回true关闭窗口
											if (!iframeObject.successCloseWindow(result)) {
												doSubmitResult(result, null, null);
												return;
											}
										}
									}
									if (isDoSubmitResult) {
										doSubmitResult(result, windowID, null);
									}
								}
							});
						}
					}
					else {
						buttonEnabled(bottonObject);
						if (iframeObject.validateError && typeof(iframeObject.validateError) == 'function') { // 执行自定义方法validateError，验校错误时执行
							iframeObject.validateError();
						}
					}
				}
			}
		},
		{
			text: '关闭',
			iconCls: 'icon-cancel',
			handler: function() {
				$('#' + windowID).dialog('close');
			}
		}
	];
	openWindow(isCache, windowID, dataOptions.url, paramArray, dataOptions);
}

// 动态弹出一个窗口（只有退出按钮）
// isCache         是否缓存页面（默认为false不缓存）
// windowID        窗口的ID（isCache=true，windowID确保在同一个页面中唯一；isCache=false，windowID可以不指定；）
// parentWindow    调用页面的window对象
// paramArray      传入打开页面的参数（如：{document:document, param1: 'test1', param2: 2}）
// dataOptions     jquery.window中的data-options定义参数
function openWindowNoSave(isCache, windowID, parentWindow, paramArray, dataOptions) {
	if (!dataOptions.url) {
		topMessagerAlert('', '弹出层缺少 url 参数！');
		return;
	}
	if (!dataOptions.title) {
		dataOptions.title = '';
	}
	dataOptions.title = '&nbsp;' + dataOptions.title;
	if (!dataOptions.width) {
		dataOptions.width = 850;
	}
	if (!dataOptions.height) {
		dataOptions.height = 420;
	}
	if (!windowID) {
		var myTime = (new Date()).getTime();
		windowID = "win_" + myTime;
	}
	dataOptions.collapsible = dataOptions.collapsible ? dataOptions.collapsible : false;
	dataOptions.minimizable = dataOptions.minimizable ? dataOptions.minimizable : false;
	dataOptions.maximizable = dataOptions.maximizable ? dataOptions.maximizable : false; // 是否最大化图标
	dataOptions.closable = true;
	dataOptions.closed = false;   
	dataOptions.cache = false;
	dataOptions.inline = false;
	dataOptions.modal = true;
	dataOptions.buttons = [
		{
			text: '关闭',
			iconCls: 'icon-cancel',
			handler: function() {
				$('#' + windowID).dialog('close');
			}
		}
	];
	openWindow(isCache, windowID, dataOptions.url, paramArray, dataOptions);
}

// 获取 datagrid的id
function getDatagrid_ID(isToolbar, elem) {
	var datagrid_ID;
	if (isToolbar) {
		datagrid_ID = $(elem).parents('div.datagrid-toolbar').siblings('div.datagrid-view').find('table.easyui-datagrid').attr('id');
	} else {
		datagrid_ID = $(elem).parents('div.datagrid-view2').siblings('table.easyui-datagrid').attr('id');
	}
	return datagrid_ID;
}

// datagrid 查询条件（在顶层页面中弹出，而且缓存页面）
// paramArray 可以传入参数（如：{document:document, param1: 'test1', param2: 2}）
function datagridQuery(toolbarButton, windowID, paramArray, dataOptions) {
	if (!windowID) {
		topMessagerAlert('', '顶层缓存页面缺少 windowID 参数！');
		return;
	}
	if (!dataOptions.url) {
		topMessagerAlert('', '弹出层缺少 url 参数！');
		return;
	}
	var datagrid_ID = getDatagrid_ID(1, toolbarButton);
	dataOptions.datagrid_ID = datagrid_ID;
	if (!dataOptions.title) {
		dataOptions.title = '查询条件';
	}
	dataOptions.title = '&nbsp;' + dataOptions.title;
	if (!dataOptions.width) {
		dataOptions.width = 850;
	}
	if (!dataOptions.height) {
		dataOptions.height = 420;
	}
	if (!windowID) {
		windowID = "queryWindow";
	}
	dataOptions.collapsible = dataOptions.collapsible ? dataOptions.collapsible : false;
	dataOptions.minimizable = dataOptions.minimizable ? dataOptions.minimizable : false;
	dataOptions.maximizable = dataOptions.maximizable ? dataOptions.maximizable : false; //是否最大化图标
	dataOptions.closable = true;
	dataOptions.closed = false;   
	dataOptions.cache = false;
	dataOptions.inline = false;
	dataOptions.resizable = dataOptions.maximizable ? dataOptions.maximizable : false; 
	dataOptions.modal = true;
	dataOptions.buttons = [
		{
			text: '确定',
			iconCls: 'icon-ok',
			handler: function() {
				var iframeObject = window.frames[windowID + '_iframe'];
				if (iframeObject.contentWindow) {
					iframeObject = iframeObject.contentWindow;
				}
				var formObject = iframeObject.$('form').first();
				if (formObject) {
					checkComboIsClosed(iframeObject, formObject); // 提交之前强行校验下拉框的是否关闭
					if (formObject.form('validate')) { // 表单的验证
						if (iframeObject.beforeSubmit && typeof(iframeObject.beforeSubmit) == 'function') { // 执行自定义方法 beforeSubmit
							if(iframeObject.beforeSubmit() == false) {
								return false;
							}
						}
						var opts = $('#' + datagrid_ID).datagrid('options');
						var data = opts.queryParams;
						var queryData = iframeObject.getFormData(formObject[0]);
						for (var item in queryData) {
							data[item] = queryData[item];
						}
						$('#' + windowID).dialog('close');
						$('#' + datagrid_ID).datagrid('load', data);// 强行定位到第一页
					}
				}
			}
		},
		{
			text: '重置',
			iconCls: 'icon-reset',
			handler: function() {
				var iframeObject = window.frames[windowID + '_iframe'];
				if (iframeObject.contentWindow) {
					iframeObject = iframeObject.contentWindow;
				}
				var formObject = iframeObject.$('form').first();
				if (formObject) {
					formObject.form('reset');
				}
			}
		},
		{
			text: '关闭',
			iconCls: 'icon-cancel',
			handler: function() {
				$('#' + windowID).dialog('close');
			}
		}
	];
	openWindow(true, windowID, dataOptions.url, paramArray, dataOptions);
}
