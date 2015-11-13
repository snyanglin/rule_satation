/**
 * 业务公共方法 
 * author : redstorm 
 * create time : 2014-08-28
 */

// 初始化下拉列表（大数据的下拉列表用，如全国行政区划）
// comboID 下拉框ID
// dictUrl 对应字典的Url，如：contextPath + '/common/dict/D_BZ_XZQHLIST.js'
function initComboBox(comboID, dictUrl) {
	$('#' + comboID).combobox({
		clientLoad: true,
		validEnter: false,
		unValidClear: false,
		url: dictUrl,
		loader: function(param, success, error) {
			var opts = $(this).combobox('options');
			if (!opts.url) return false;
			if ("undefined" == typeof param.q) {
				var data = [];
				success(data);
				return;
			}
			else {
				opts.validEnter = true;
				param.q = param.q.replace(/(^[\s|　]*)|([\s|　]*$)/g, "");
				if (param.q == "") { // 清空
					var data = [];
					success(data);
					return;
				}
			}
			var str = param.q.toUpperCase();
			if (opts.isTopLoad && window.top && window.top.publicDictArray) {
				data = window.top.getPublicDict(opts.url);
				opts.loaded = true;
				var resultData = [];
				var countI = 0;
				if (data != null) {
					var dataFilter = opts.dataFilter;
					if (dataFilter == "") {
						for (var i = 0; i < data.length; i++) {
							var row = data[i];
							var returnValue = false;
							if (/[(0-9)*]/.test(str)) { 
								returnValue = row[opts.valueField].indexOf(str) == 0 || row[opts.textField].toUpperCase().indexOf(str) >= 0;
							}
							else {
								var py = row['py'];
								if (py) {
									returnValue = row[opts.valueField].indexOf(str) == 0 || row[opts.textField].toUpperCase().indexOf(str) >= 0 || row['py'].indexOf(str) >= 0;
								}
								else {
									returnValue = row[opts.valueField].indexOf(str) == 0 || row[opts.textField].toUpperCase().indexOf(str) >= 0;
								}
							}
							if (returnValue) {
								resultData.push(row);
								countI ++;
								if (countI == 10) {
									break;
								}
							}
						}
					}
					else {
						var resultData = [];
						if (dataFilter.indexOf("^") == -1 && dataFilter.indexOf("*") == -1 && dataFilter.indexOf("|") == -1 && dataFilter.indexOf("+") == -1 
								&& dataFilter.indexOf("?") == -1 && dataFilter.indexOf("$") == -1 && dataFilter.indexOf("(") == -1 && dataFilter.indexOf(")") == -1 
								&& dataFilter.indexOf("{") == -1 && dataFilter.indexOf("}") == -1 && dataFilter.indexOf("[") == -1 && dataFilter.indexOf("]") == -1 
								&& dataFilter.indexOf(".") == -1) {
							dataFilter = "^" + dataFilter;
						}
						var regExp = new RegExp(dataFilter); 
						for (var i = 0; i < data.length; i++) {
							var row = data[i];
							var v = row[opts.valueField] + '';
							if (regExp.test(v)) {
								var returnValue = false;
								if (/[(0-9)*]/.test(str)) { 
									returnValue = row[opts.valueField].indexOf(str) == 0 || row[opts.textField].toUpperCase().indexOf(str) >= 0;
								}
								else {
									var py = row['py'];
									if (py) {
										returnValue = row[opts.valueField].indexOf(str) == 0 || row[opts.textField].toUpperCase().indexOf(str) >= 0 || row['py'].indexOf(str) >= 0;
									}
									else {
										returnValue = row[opts.valueField].indexOf(str) == 0 || row[opts.textField].toUpperCase().indexOf(str) >= 0;
									}
								}
								if (returnValue) {
									resultData.push(row);
									countI ++;
									if (countI == 10) {
										break;
									}
								}
							}
						}
					}
				}
				success(resultData);
			}
			else {
				$.ajax({
					type: opts.method,
					url: opts.url,
					data: param,
					dataType: 'json',
					success: function(data) {
						opts.loaded = true;
						var resultData = [];
						var countI = 0;
						if (data != null) {
							var dataFilter = opts.dataFilter;
							if (dataFilter == "") {
								for (var i = 0; i < data.length; i++) {
									var row = data[i];
									var returnValue = false;
									if (/[(0-9)*]/.test(str)) { 
										returnValue = row[opts.valueField].indexOf(str) == 0 || row[opts.textField].toUpperCase().indexOf(str) >= 0;
									}
									else {
										var py = row['py'];
										if (py) {
											returnValue = row[opts.valueField].indexOf(str) == 0 || row[opts.textField].toUpperCase().indexOf(str) >= 0 || row['py'].indexOf(str) >= 0;
										}
										else {
											returnValue = row[opts.valueField].indexOf(str) == 0 || row[opts.textField].toUpperCase().indexOf(str) >= 0;
										}
									}
									if (returnValue) {
										resultData.push(row);
										countI ++;
										if (countI == 10) {
											break;
										}
									}
								}
							}
							else {
								var resultData = [];
								if (dataFilter.indexOf("^") == -1 && dataFilter.indexOf("*") == -1 && dataFilter.indexOf("|") == -1 && dataFilter.indexOf("+") == -1 
										&& dataFilter.indexOf("?") == -1 && dataFilter.indexOf("$") == -1 && dataFilter.indexOf("(") == -1 && dataFilter.indexOf(")") == -1 
										&& dataFilter.indexOf("{") == -1 && dataFilter.indexOf("}") == -1 && dataFilter.indexOf("[") == -1 && dataFilter.indexOf("]") == -1 
										&& dataFilter.indexOf(".") == -1) {
									dataFilter = "^" + dataFilter;
								}
								var regExp = new RegExp(dataFilter); 
								for (var i = 0; i < data.length; i++) {
									var row = data[i];
									var v = row[opts.valueField] + '';
									if (regExp.test(v)) {
										var returnValue = false;
										if (/[(0-9)*]/.test(str)) { 
											returnValue = row[opts.valueField].indexOf(str) == 0 || row[opts.textField].toUpperCase().indexOf(str) >= 0;
										}
										else {
											var py = row['py'];
											if (py) {
												returnValue = row[opts.valueField].indexOf(str) == 0 || row[opts.textField].toUpperCase().indexOf(str) >= 0 || row['py'].indexOf(str) >= 0;
											}
											else {
												returnValue = row[opts.valueField].indexOf(str) == 0 || row[opts.textField].toUpperCase().indexOf(str) >= 0;
											}
										}
										if (returnValue) {
											resultData.push(row);
											countI ++;
											if (countI == 10) {
												break;
											}
										}
									}
								}
							}
						}
						success(resultData);
					},
					error: function() {
						error.apply(this, arguments);
					}
				});
			}
		},
		filter: function(q, row) {
			return true;
		},
		loadFilter: function(data) {
			return data;
		},
		onLoadSuccess: function() {
		},
		onHidePanel: function() {
			var opts = $(this).combobox('options');
			if (!opts.validEnter) {
				return;
			}
			else {
				var clearData = false;
				var data = $(this).combobox('getData');
				if (data.length == 0) {
					clearData = true;
				}
				else {
					var panel = $(this).combo('panel');
					var itemSelected = panel.find('div.combobox-item-selected');
					if (itemSelected.length == 0) {
						clearData = true;
					}
					else {
						var oldValue = $(this).combobox("getValues");
						var valueArray = [];
						for (var i = 0; i < oldValue.length; i++) {
							var value = oldValue[i];
							if (value != null && value != "") {
								if (value.indexOf(opts.separator) != -1) {
									valueArray = valueArray.concat(value.split(opts.separator));
								}
								else {
									valueArray.push(value);
								}
							}
						}
						$(this).combobox("setValues", valueArray);						
					}
				}
				if (clearData) {
					$(this).combobox('clear');
				}
			}
		}		
	
	});
	
	$(document).ready(function() {
		var comboText = $('#' + comboID).next(".combo").children(".combo-text");
		var tempValue = comboText.val();
		$('#' + comboID).combobox("setValue", tempValue);
		comboText.bind("focus", function(e) {
			$(this).css('color', '#222222');
			if (this.value == "请输入搜索内容...") {
				this.value = "";
			}
		}).bind("blur", function() {
			if (this.value == "") {
				$(this).css('color', '#C0C0C0');
				this.value = "请输入搜索内容...";
			}
		});
	});
	
}

// 列表字典多选对话框
// isCache         是否缓存页面（默认为false不缓存）
// windowID        窗口的ID（isCache=true，windowID确保在同一个页面中唯一；isCache=false，windowID可以不指定；）
// parentWindow    调用页面的window对象
// dictName        字典对应的json文件
// dictInputID     字典值存放的ID
// dictShowID      字典显示框的ID
// dataFilter      数据过滤正则表达式
// onOkMethod      对话中点击确认后执行原页面中的方法（如：“xzqh_onOk”）
function dict_multiSelectList(isCache, windowID, parentWindow, dictName, dictInputID, dictShowID, dataFilter, onOkMethod) {
	if (isCache) {
		if (windowID == null || windowID == "") {
			$.messager.alert('页面错误','字典选择dict_multiSelectList()方法：<br><br>参数 windowID 不能为空！','error');
			return;
		}
	}
	if (!windowID) {
		var myTime = (new Date()).getTime();
		windowID = "win_" + myTime;
	}
	var openURL = contextPath + "/forward/commonDictMultiList";
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
	paramArray['dataFilter'] = dataFilter;
	paramArray['onOkMethod'] = onOkMethod;
	var dataOptions = {
		title: '&nbsp;字典选择',
		url: dictName, 
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
	    modal: true
	};
	dataOptions.buttons = [
		{
			text: '确定',
			iconCls: 'icon-ok',
			handler: function() {
				var iframeObject = window.frames[windowID + '_iframe'];
				if (iframeObject.contentWindow) {
					iframeObject = iframeObject.contentWindow;
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
	    modal: true
	};
	dataOptions.buttons = [
		{
			text: '确定',
			iconCls: 'icon-ok',
			handler: function() {
				var iframeObject = window.frames[windowID + '_iframe'];
				if (iframeObject.contentWindow) {
					iframeObject = iframeObject.contentWindow;
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

// 组织机构部门名称初始化（用于数据库只保存部门代码，初始化显示部门名称时用）
// orgCodeInputID  部门代码输入框的ID
// orgNameInputID  部门显示输入框的ID
function public_getOrgName(orgCodeInputID, orgNameInputID) {
	if (orgCodeInputID && orgNameInputID) {
		var orgCodeValue = $('#' + orgCodeInputID).val();
		if (orgCodeValue != "") {
			$.ajax({
				type: "POST",
				url: contextPath + "/orgPublicSelect/getOrgName",
				dataType: "json",
				data: "orgCodes="+orgCodeValue,
				success: function(data) {
					if (data) {
						$('#' + orgNameInputID).val(data);
					}
				}
			});				
		}
	}
}

// 组织机构部门选择（单选）
// rootOrgCode     部门树根结点代码（为空时为整个部门树）
// orgType         部门类型（=01只能选择部门；=02只能选择工作组，为空可以选择部门和工作组）
// orgLevel        部门等级过滤（多个时用逗号分隔）
// orgBizType      部门业务类型过滤（多个时用逗号分隔）
// orgCodeInputID  部门代码输入框的ID
// orgNameInputID  部门显示输入框的ID
// orgIDInputID    部门ID输入框的ID（不需要返回部门ID该参数时为''或null）
// allExcludeChild 该参数在单选中无用
// isCache         是否缓存页面（默认为false不缓存）
// windowID        窗口的ID（isCache=true，windowID确保在同一个页面中唯一；isCache=false，windowID可以不指定；）
// parentWindow    调用页面的window对象
// onOkMethod      对话中点击确认后执行原页面中的方法（如：“orgSelect_onOk”）
// dialogTitle     对话框的标题
function public_singleSelectOrg(rootOrgCode, orgType, orgLevel, orgBizType, orgCodeInputID, orgNameInputID, orgIDInputID, allExcludeChild, isCache, windowID, parentWindow, onOkMethod, dialogTitle) {
	if (isCache) {
		if (windowID == null || windowID == "") {
			$.messager.alert('页面错误','组织机构部门选择public_singleSelectOrg()方法：<br><br>参数 windowID 不能为空！','error');
			return;
		}
	}
	if (!windowID) {
		var myTime = (new Date()).getTime();
		windowID = "win_" + myTime;
	}
	if ("undefined" == typeof rootOrgCode || rootOrgCode == null) {
		rootOrgCode = "";
	}
	if ("undefined" == typeof orgType || orgType == null) {
		orgType = "";
	}
	if ("undefined" == typeof orgLevel || orgLevel == null) {
		orgLevel = "";
	}
	if ("undefined" == typeof orgBizType || orgBizType == null) {
		orgBizType = "";
	}
	if ("undefined" == typeof onOkMethod || onOkMethod == null) {
		onOkMethod = "";
	}
	if ("undefined" == typeof dialogTitle || dialogTitle == null || dialogTitle == "") {
		dialogTitle = "组织机构部门选择";
	}
	var openURL = contextPath + "/orgPublicSelect/singleSelect?rootOrgCode=" + rootOrgCode + "&orgType=" + orgType + "&orgLevel=" + orgLevel + "&orgBizType=" + orgBizType;
	var paramArray = [];
	paramArray['parentWindow'] = parentWindow;
	paramArray['orgCodeInputID'] = orgCodeInputID;
	paramArray['orgNameInputID'] = orgNameInputID;
	paramArray['orgIDInputID'] = orgIDInputID;
	paramArray['onOkMethod'] = onOkMethod;
	var dataOptions = {
		title: '&nbsp;' + dialogTitle,
		width: 800,   
	    height: 400,  
		collapsible: false, 
		minimizable: false, 
		maximizable: false,
		closable: true,
	    closed: false,    
	    cache: false,
	    inline: false,
	    modal: true
	};
	dataOptions.buttons = [
		{
			text: '确定',
			iconCls: 'icon-ok',
			handler: function() {
				var iframeObject = window.frames[windowID + '_iframe'];
				if (iframeObject.contentWindow) {
					iframeObject = iframeObject.contentWindow;
				}
				if (iframeObject.ok_execute()) {
					$('#' + windowID).dialog('close');
					if (onOkMethod != null && onOkMethod != "") {
						try {
							var parentWinObject = parentWindow;
							if (parentWinObject.contentWindow) {
								parentWinObject = parentWinObject.contentWindow;
							}
							eval("parentWinObject." + onOkMethod + "(orgCodeInputID)");
						}
						catch (err) {
							$.messager.alert('页面错误', "执行事件 "+ onOkMethod + " 有错误发生：<br/><br/>错误名称: " + err.name + "<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;错误行号:" + (err.number & 0xFFFF ) + "<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;错误信息:" + err.message, 'error'); 
						}
					}
				}
			}
		},
		{
			text: '清空',
			iconCls: 'icon-remove',
			handler: function() {
				var iframeObject = window.frames[windowID + '_iframe'];
				if (iframeObject.contentWindow) {
					iframeObject = iframeObject.contentWindow;
				}
				iframeObject.clear_execute();
				$('#' + windowID).dialog('close');
				if (onOkMethod != null && onOkMethod != "") {
					try {
						var parentWinObject = parentWindow;
						if (parentWinObject.contentWindow) {
							parentWinObject = parentWinObject.contentWindow;
						}
						eval("parentWinObject." + onOkMethod + "(orgCodeInputID)");
					}
					catch (err) {
						$.messager.alert('页面错误', "执行事件 "+ onOkMethod + " 有错误发生：<br/><br/>错误名称: " + err.name + "<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;错误行号:" + (err.number & 0xFFFF ) + "<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;错误信息:" + err.message, 'error'); 
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

// 组织机构部门选择（多选）
// rootOrgCode     部门树根结点代码（为空时为整个部门树）
// orgType         部门类型（=01只能选择部门；=02只能选择工作组，为空可以选择部门和工作组）
// orgLevel        部门等级过滤（多个时用逗号分隔）
// orgBizType      部门业务类型过滤（多个时用逗号分隔）
// orgCodeInputID  部门代码输入框的ID
// orgNameInputID  部门显示输入框的ID
// orgIDInputID    部门ID输入框的ID（不需要返回部门ID该参数时为''或null）
// allExcludeChild 父结点选中了就不包括子结点（默认为false）
// isCache         是否缓存页面（默认为false不缓存）
// windowID        窗口的ID（isCache=true，windowID确保在同一个页面中唯一；isCache=false，windowID可以不指定；）
// parentWindow    调用页面的window对象
// onOkMethod      对话中点击确认后执行原页面中的方法（如：“orgSelect_onOk”）
// dialogTitle     对话框的标题
function public_multiSelectOrg(rootOrgCode, orgType, orgLevel, orgBizType, orgCodeInputID, orgNameInputID, orgIDInputID, allExcludeChild, isCache, windowID, parentWindow, onOkMethod, dialogTitle) {
	if (isCache) {
		if (windowID == null || windowID == "") {
			$.messager.alert('页面错误','组织机构部门选择public_multiSelectOrg()方法：<br><br>参数 windowID 不能为空！','error');
			return;
		}
	}
	if (!windowID) {
		var myTime = (new Date()).getTime();
		windowID = "win_" + myTime;
	}
	if ("undefined" == typeof rootOrgCode || rootOrgCode == null) {
		rootOrgCode = "";
	}
	if ("undefined" == typeof orgType || orgType == null) {
		orgType = "";
	}
	if ("undefined" == typeof orgLevel || orgLevel == null) {
		orgLevel = "";
	}
	if ("undefined" == typeof orgBizType || orgBizType == null) {
		orgBizType = "";
	}
	if ("undefined" == typeof allExcludeChild || allExcludeChild == null) {
		allExcludeChild = false;
	}
	if ("undefined" == typeof onOkMethod || onOkMethod == null) {
		onOkMethod = "";
	}
	if ("undefined" == typeof dialogTitle || dialogTitle == null || dialogTitle == "") {
		dialogTitle = "组织机构部门选择";
	}
	var orgCodeString = "";
	var parentWinObject = parentWindow;
	if (parentWinObject.contentWindow) {
		parentWinObject = parentWinObject.contentWindow;
	}
	if (orgCodeInputID && parentWinObject.$('#' + orgCodeInputID).length > 0) {
		orgCodeString = parentWinObject.$('#' + orgCodeInputID).val();
	}
	var otherOrgCode = "";
	if (orgCodeString.length > 1900) { // IE最大支持2048个字符
		var tempString = orgCodeString.substring(0, 1900);
		var atI = tempString.lastIndexOf(",");
		otherOrgCode = orgCodeString.substr(atI + 1);
		orgCodeString = orgCodeString.substring(0, atI);
	}
	var urlParameter = "rootOrgCode=" + rootOrgCode + "&orgType=" + orgType + "&orgLevel=" + orgLevel + "&orgBizType=" + orgBizType;
	urlParameter += "&orgCodeString=" + orgCodeString;
	var openURL = contextPath + "/orgPublicSelect/multiSelect?" + urlParameter;
	var paramArray = [];
	paramArray['parentWindow'] = parentWindow;
	paramArray['orgCodeInputID'] = orgCodeInputID;
	paramArray['orgNameInputID'] = orgNameInputID;
	paramArray['orgIDInputID'] = orgIDInputID;
	paramArray['allExcludeChild'] = allExcludeChild;
	paramArray['onOkMethod'] = onOkMethod;
	paramArray['otherOrgCode'] = otherOrgCode;
	var dataOptions = {
		title: '&nbsp;' + dialogTitle,
		width: 800,   
	    height: 400,  
		collapsible: false, 
		minimizable: false, 
		maximizable: false,
		closable: true,
	    closed: false,    
	    cache: false,
	    inline: false,
	    modal: true
	};
	dataOptions.buttons = [
		{
			text: '确定',
			iconCls: 'icon-ok',
			handler: function() {
				var iframeObject = window.frames[windowID + '_iframe'];
				if (iframeObject.contentWindow) {
					iframeObject = iframeObject.contentWindow;
				}
				iframeObject.ok_execute();
				$('#' + windowID).dialog('close');
				if (onOkMethod != null && onOkMethod != "") {
					try {
						var parentWinObject = parentWindow;
						if (parentWinObject.contentWindow) {
							parentWinObject = parentWinObject.contentWindow;
						}
						eval("parentWinObject." + onOkMethod + "(orgCodeInputID)");
					}
					catch (err) {
						$.messager.alert('页面错误', "执行事件 "+ onOkMethod + " 有错误发生：<br/><br/>错误名称: " + err.name + "<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;错误行号:" + (err.number & 0xFFFF ) + "<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;错误信息:" + err.message, 'error'); 
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

// 组织机构人员选择（单选）
// rootOrgCode        部门树根结点代码（为空时为整个部门树）
// orgType            部门类型（=01只能选择部门；=02只能选择工作组，为空可以选择部门和工作组）
// orgLevel           部门等级过滤（多个时用逗号分隔）
// orgBizType         部门业务类型过滤（多个时用逗号分隔）
// userPositions      人员虚拟岗位表POSID过滤（多个时用逗号分隔）
// initFocusOrgCode   无已选择的人员时，初始定位部门代码
// userIDInputID      人员userID输入框的ID
// userNameInputID    人员显示输入框的ID
// userTableIDInputID 人员ID输入框的ID（不需要返回人员ID该参数时为''或null）
// orgCodeInputID     部门代码输入框的ID（不需要返回部门ID该参数时为''或null）
// orgNameInputID     部门显示输入框的ID（不需要返回部门ID该参数时为''或null）
// orgIDInputID       部门ID输入框的ID（不需要返回部门ID该参数时为''或null）
// isCache            是否缓存页面（默认为false不缓存）
// windowID           窗口的ID（isCache=true，windowID确保在同一个页面中唯一；isCache=false，windowID可以不指定；）
// parentWindow       调用页面的window对象
// onOkMethod         对话中点击确认后执行原页面中的方法（如：“orgUserSelect_onOk”）
// dialogTitle        对话框的标题
function public_singleSelectOrgUser(rootOrgCode, orgType, orgLevel, orgBizType, userPositions, initFocusOrgCode, userIDInputID, userNameInputID, userTableIDInputID, orgCodeInputID, orgNameInputID, orgIDInputID, isCache, windowID, parentWindow, onOkMethod, dialogTitle) {
	if (isCache) {
		if (windowID == null || windowID == "") {
			$.messager.alert('页面错误','组织机构人员选择public_singleSelectOrgUser()方法：<br><br>参数 windowID 不能为空！','error');
			return;
		}
	}
	if (!windowID) {
		var myTime = (new Date()).getTime();
		windowID = "win_" + myTime;
	}
	if ("undefined" == typeof rootOrgCode || rootOrgCode == null) {
		rootOrgCode = "";
	}
	if ("undefined" == typeof orgType || orgType == null) {
		orgType = "";
	}
	if ("undefined" == typeof orgLevel || orgLevel == null) {
		orgLevel = "";
	}
	if ("undefined" == typeof orgBizType || orgBizType == null) {
		orgBizType = "";
	}
	if ("undefined" == typeof userPositions || userPositions == null) {
		userPositions = "";
	}
	if ("undefined" == typeof initFocusOrgCode || initFocusOrgCode == null) {
		initFocusOrgCode = "";
	}
	if ("undefined" == typeof onOkMethod || onOkMethod == null) {
		onOkMethod = "";
	}
	if ("undefined" == typeof dialogTitle || dialogTitle == null || dialogTitle == "") {
		dialogTitle = "组织机构人员选择";
	}
	var urlParameter = "rootOrgCode=" + rootOrgCode + "&orgType=" + orgType + "&orgLevel=" + orgLevel + "&orgBizType=" + orgBizType + "&userPositions=" + userPositions + "&initFocusOrgCode=" + initFocusOrgCode;
	var userIdString = "";
	var parentWinObject = parentWindow;
	if (parentWinObject.contentWindow) {
		parentWinObject = parentWinObject.contentWindow;
	}
	if (userIDInputID && parentWinObject.$('#' + userIDInputID).length > 0) {
		userIdString = parentWinObject.$('#' + userIDInputID).val();
	}
	var userTableIdString = "";
	if (userTableIDInputID && parentWinObject.$('#' + userTableIDInputID).length > 0) {
		userTableIdString = parentWinObject.$('#' + userTableIDInputID).val();
	}
	urlParameter += "&userIdString=" + userIdString + "&userTableIdString=" + userTableIdString;
	var openURL = contextPath + "/orgUserPublicSelect/singleSelect?" + urlParameter;
	var paramArray = [];
	paramArray['parentWindow'] = parentWindow;
	paramArray['userIDInputID'] = userIDInputID;
	paramArray['userNameInputID'] = userNameInputID;
	paramArray['userTableIDInputID'] = userTableIDInputID;
	paramArray['orgCodeInputID'] = orgCodeInputID;
	paramArray['orgNameInputID'] = orgNameInputID;
	paramArray['orgIDInputID'] = orgIDInputID;
	paramArray['onOkMethod'] = onOkMethod;
	var dataOptions = {
		title: '&nbsp;' + dialogTitle,
		width: 800,   
	    height: 400,  
		collapsible: false, 
		minimizable: false, 
		maximizable: false,
		closable: true,
	    closed: false,    
	    cache: false,
	    inline: false,
	    modal: true
	};
	dataOptions.buttons = [
		{
			text: '确定',
			iconCls: 'icon-ok',
			handler: function() {
				var iframeObject = window.frames[windowID + '_iframe'];
				if (iframeObject.contentWindow) {
					iframeObject = iframeObject.contentWindow;
				}
				if (iframeObject.ok_execute()) {
					$('#' + windowID).dialog('close');
					if (onOkMethod != null && onOkMethod != "") {
						try {
							var parentWinObject = parentWindow;
							if (parentWinObject.contentWindow) {
								parentWinObject = parentWinObject.contentWindow;
							}
							eval("parentWinObject." + onOkMethod + "(userIDInputID)");
						}
						catch (err) {
							$.messager.alert('页面错误', "执行事件 "+ onOkMethod + " 有错误发生：<br/><br/>错误名称: " + err.name + "<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;错误行号:" + (err.number & 0xFFFF ) + "<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;错误信息:" + err.message, 'error'); 
						}
					}
				}
			}
		},
		{
			text: '清空',
			iconCls: 'icon-remove',
			handler: function() {
				var iframeObject = window.frames[windowID + '_iframe'];
				if (iframeObject.contentWindow) {
					iframeObject = iframeObject.contentWindow;
				}
				iframeObject.clear_execute();
				$('#' + windowID).dialog('close');
				if (onOkMethod != null && onOkMethod != "") {
					try {
						var parentWinObject = parentWindow;
						if (parentWinObject.contentWindow) {
							parentWinObject = parentWinObject.contentWindow;
						}
						eval("parentWinObject." + onOkMethod + "(userIDInputID)");
					}
					catch (err) {
						$.messager.alert('页面错误', "执行事件 "+ onOkMethod + " 有错误发生：<br/><br/>错误名称: " + err.name + "<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;错误行号:" + (err.number & 0xFFFF ) + "<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;错误信息:" + err.message, 'error'); 
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

// 组织机构人员选择（多选）
// rootOrgCode        部门树根结点代码（为空时为整个部门树）
// orgType            部门类型（=01只能选择部门；=02只能选择工作组，为空可以选择部门和工作组）
// orgLevel           部门等级过滤（多个时用逗号分隔）
// orgBizType         部门业务类型过滤（多个时用逗号分隔）
// userPositions      人员虚拟岗位表POSID过滤（多个时用逗号分隔）
// initFocusOrgCode   无已选择的人员时，初始定位部门代码
// userIDInputID      人员userID输入框的ID
// userNameInputID    人员显示输入框的ID
// userTableIDInputID 人员ID输入框的ID（不需要返回人员ID该参数时为''或null）
// orgCodeInputID     部门代码输入框的ID（不需要返回部门ID该参数时为''或null）
// orgNameInputID     部门显示输入框的ID（不需要返回部门ID该参数时为''或null）
// orgIDInputID       部门ID输入框的ID（不需要返回部门ID该参数时为''或null）
// isCache            是否缓存页面（默认为false不缓存）
// windowID           窗口的ID（isCache=true，windowID确保在同一个页面中唯一；isCache=false，windowID可以不指定；）
// parentWindow       调用页面的window对象
// onOkMethod         对话中点击确认后执行原页面中的方法（如：“orgUserSelect_onOk”）
// dialogTitle        对话框的标题
function public_multiSelectOrgUser(rootOrgCode, orgType, orgLevel, orgBizType, userPositions, initFocusOrgCode, userIDInputID, userNameInputID, userTableIDInputID, orgCodeInputID, orgNameInputID, orgIDInputID, isCache, windowID, parentWindow, onOkMethod, dialogTitle) {
	if (isCache) {
		if (windowID == null || windowID == "") {
			$.messager.alert('页面错误','组织机构人员选择public_multiSelectOrgUser()方法：<br><br>参数 windowID 不能为空！','error');
			return;
		}
	}
	if (!windowID) {
		var myTime = (new Date()).getTime();
		windowID = "win_" + myTime;
	}
	if ("undefined" == typeof rootOrgCode || rootOrgCode == null) {
		rootOrgCode = "";
	}
	if ("undefined" == typeof orgType || orgType == null) {
		orgType = "";
	}
	if ("undefined" == typeof orgLevel || orgLevel == null) {
		orgLevel = "";
	}
	if ("undefined" == typeof orgBizType || orgBizType == null) {
		orgBizType = "";
	}
	if ("undefined" == typeof userPositions || userPositions == null) {
		userPositions = "";
	}
	if ("undefined" == typeof initFocusOrgCode || initFocusOrgCode == null) {
		initFocusOrgCode = "";
	}
	if ("undefined" == typeof onOkMethod || onOkMethod == null) {
		onOkMethod = "";
	}
	if ("undefined" == typeof dialogTitle || dialogTitle == null || dialogTitle == "") {
		dialogTitle = "组织机构人员选择";
	}
	var urlParameter = "rootOrgCode=" + rootOrgCode + "&orgType=" + orgType + "&orgLevel=" + orgLevel + "&orgBizType=" + orgBizType + "&userPositions=" + userPositions + "&initFocusOrgCode=" + initFocusOrgCode;
	var userIdString = "";
	var parentWinObject = parentWindow;
	if (parentWinObject.contentWindow) {
		parentWinObject = parentWinObject.contentWindow;
	}
	if (userIDInputID && parentWinObject.$('#' + userIDInputID).length > 0) {
		userIdString = parentWinObject.$('#' + userIDInputID).val();
	}
	var userTableIdString = "";
	if (userTableIDInputID && parentWinObject.$('#' + userTableIDInputID).length > 0) {
		userTableIdString = parentWinObject.$('#' + userTableIDInputID).val();
	}
	urlParameter += "&userIdString=" + userIdString + "&userTableIdString=" + userTableIdString;
	var openURL = contextPath + "/orgUserPublicSelect/multiSelect?" + urlParameter;
	var paramArray = [];
	paramArray['parentWindow'] = parentWindow;
	paramArray['userIDInputID'] = userIDInputID;
	paramArray['userNameInputID'] = userNameInputID;
	paramArray['userTableIDInputID'] = userTableIDInputID;
	paramArray['orgCodeInputID'] = orgCodeInputID;
	paramArray['orgNameInputID'] = orgNameInputID;
	paramArray['orgIDInputID'] = orgIDInputID;
	paramArray['onOkMethod'] = onOkMethod;
	var dataOptions = {
		title: '&nbsp;' + dialogTitle,
		width: 800,   
	    height: 400,  
		collapsible: false, 
		minimizable: false, 
		maximizable: false,
		closable: true,
	    closed: false,    
	    cache: false,
	    inline: false,
	    modal: true
	};
	dataOptions.buttons = [
		{
			text: '确定',
			iconCls: 'icon-ok',
			handler: function() {
				var iframeObject = window.frames[windowID + '_iframe'];
				if (iframeObject.contentWindow) {
					iframeObject = iframeObject.contentWindow;
				}
				iframeObject.ok_execute();
				$('#' + windowID).dialog('close');
				if (onOkMethod != null && onOkMethod != "") {
					try {
						var parentWinObject = parentWindow;
						if (parentWinObject.contentWindow) {
							parentWinObject = parentWinObject.contentWindow;
						}
						eval("parentWinObject." + onOkMethod + "(userIDInputID)");
					}
					catch (err) {
						$.messager.alert('页面错误', "执行事件 "+ onOkMethod + " 有错误发生：<br/><br/>错误名称: " + err.name + "<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;错误行号:" + (err.number & 0xFFFF ) + "<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;错误信息:" + err.message, 'error'); 
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

// 组织机构岗位选择（单选）
// rootOrgCode        部门树根结点代码（为空时为整个部门树）
// orgType            部门类型（=01只能选择部门；=02只能选择工作组，为空可以选择部门和工作组）
// orgLevel           部门等级过滤（多个时用逗号分隔）
// orgBizType         部门业务类型过滤（多个时用逗号分隔）
// posids             人员虚拟岗位表POSID过滤（多个时用逗号分隔）
// initFocusOrgCode   无已选择的人员时，初始定位部门代码
// posIDInputID       岗位ID（实体岗位）输入框的ID
// posNameInputID     岗位人员显示输入框的ID
// orgCodeInputID     部门代码输入框的ID（不需要返回部门ID该参数时为''或null）
// orgNameInputID     部门显示输入框的ID（不需要返回部门ID该参数时为''或null）
// orgIDInputID       部门ID输入框的ID（不需要返回部门ID该参数时为''或null）
// isCache            是否缓存页面（默认为false不缓存）
// windowID           窗口的ID（isCache=true，windowID确保在同一个页面中唯一；isCache=false，windowID可以不指定；）
// parentWindow       调用页面的window对象
// onOkMethod         对话中点击确认后执行原页面中的方法（如：“orgPosSelect_onOk”）
// dialogTitle        对话框的标题
function public_singleSelectOrgPos(rootOrgCode, orgType, orgLevel, orgBizType, posids, initFocusOrgCode, posIDInputID, posNameInputID, orgCodeInputID, orgNameInputID, orgIDInputID, isCache, windowID, parentWindow, onOkMethod, dialogTitle) {
	if (isCache) {
		if (windowID == null || windowID == "") {
			$.messager.alert('页面错误','组织机构岗位选择public_singleSelectOrgPos()方法：<br><br>参数 windowID 不能为空！','error');
			return;
		}
	}
	if (!windowID) {
		var myTime = (new Date()).getTime();
		windowID = "win_" + myTime;
	}
	if ("undefined" == typeof rootOrgCode || rootOrgCode == null) {
		rootOrgCode = "";
	}
	if ("undefined" == typeof orgType || orgType == null) {
		orgType = "";
	}
	if ("undefined" == typeof orgLevel || orgLevel == null) {
		orgLevel = "";
	}
	if ("undefined" == typeof orgBizType || orgBizType == null) {
		orgBizType = "";
	}
	if ("undefined" == typeof posids || posids == null) {
		posids = "";
	}
	if ("undefined" == typeof initFocusOrgCode || initFocusOrgCode == null) {
		initFocusOrgCode = "";
	}
	if ("undefined" == typeof onOkMethod || onOkMethod == null) {
		onOkMethod = "";
	}
	if ("undefined" == typeof dialogTitle || dialogTitle == null || dialogTitle == "") {
		dialogTitle = "组织机构岗位选择";
	}
	var urlParameter = "rootOrgCode=" + rootOrgCode + "&orgType=" + orgType + "&orgLevel=" + orgLevel + "&orgBizType=" + orgBizType + "&posids=" + posids + "&initFocusOrgCode=" + initFocusOrgCode;
	var realPosIdString = "";
	if (posIDInputID && $('#' + posIDInputID).length > 0) {
		realPosIdString = $('#' + posIDInputID).val();
	}
	urlParameter += "&realPosIdString=" + realPosIdString;
	var openURL = contextPath + "/orgPosPublicSelect/singleSelect?" + urlParameter;
	var paramArray = [];
	paramArray['parentWindow'] = parentWindow;
	paramArray['posIDInputID'] = posIDInputID;
	paramArray['posNameInputID'] = posNameInputID;
	paramArray['orgCodeInputID'] = orgCodeInputID;
	paramArray['orgNameInputID'] = orgNameInputID;
	paramArray['orgIDInputID'] = orgIDInputID;
	paramArray['onOkMethod'] = onOkMethod;
	var dataOptions = {
		title: '&nbsp;' + dialogTitle,
		width: 800,   
	    height: 400,  
		collapsible: false, 
		minimizable: false, 
		maximizable: false,
		closable: true,
	    closed: false,    
	    cache: false,
	    inline: false,
	    modal: true
	};
	dataOptions.buttons = [
		{
			text: '确定',
			iconCls: 'icon-ok',
			handler: function() {
				var iframeObject = window.frames[windowID + '_iframe'];
				if (iframeObject.contentWindow) {
					iframeObject = iframeObject.contentWindow;
				}
				if (iframeObject.ok_execute()) {
					$('#' + windowID).dialog('close');
					if (onOkMethod != null && onOkMethod != "") {
						try {
							var parentWinObject = parentWindow;
							if (parentWinObject.contentWindow) {
								parentWinObject = parentWinObject.contentWindow;
							}
							eval("parentWinObject." + onOkMethod + "(posIDInputID)");
						}
						catch (err) {
							$.messager.alert('页面错误', "执行事件 "+ onOkMethod + " 有错误发生：<br/><br/>错误名称: " + err.name + "<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;错误行号:" + (err.number & 0xFFFF ) + "<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;错误信息:" + err.message, 'error'); 
						}
					}
				}
			}
		},
		{
			text: '清空',
			iconCls: 'icon-remove',
			handler: function() {
				var iframeObject = window.frames[windowID + '_iframe'];
				if (iframeObject.contentWindow) {
					iframeObject = iframeObject.contentWindow;
				}
				iframeObject.clear_execute();
				$('#' + windowID).dialog('close');
				if (onOkMethod != null && onOkMethod != "") {
					try {
						var parentWinObject = parentWindow;
						if (parentWinObject.contentWindow) {
							parentWinObject = parentWinObject.contentWindow;
						}
						eval("parentWinObject." + onOkMethod + "(posIDInputID)");
					}
					catch (err) {
						$.messager.alert('页面错误', "执行事件 "+ onOkMethod + " 有错误发生：<br/><br/>错误名称: " + err.name + "<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;错误行号:" + (err.number & 0xFFFF ) + "<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;错误信息:" + err.message, 'error'); 
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

// 组织机构岗位选择（多选）
// rootOrgCode        部门树根结点代码（为空时为整个部门树）
// orgType            部门类型（=01只能选择部门；=02只能选择工作组，为空可以选择部门和工作组）
// orgLevel           部门等级过滤（多个时用逗号分隔）
// orgBizType         部门业务类型过滤（多个时用逗号分隔）
// posids             人员虚拟岗位表POSID过滤（多个时用逗号分隔）
// initFocusOrgCode   无已选择的人员时，初始定位部门代码
// posIDInputID       岗位ID（实体岗位）输入框的ID
// posNameInputID     岗位人员显示输入框的ID
// orgCodeInputID     部门代码输入框的ID（不需要返回部门ID该参数时为''或null）
// orgNameInputID     部门显示输入框的ID（不需要返回部门ID该参数时为''或null）
// orgIDInputID       部门ID输入框的ID（不需要返回部门ID该参数时为''或null）
// isCache            是否缓存页面（默认为false不缓存）
// windowID           窗口的ID（isCache=true，windowID确保在同一个页面中唯一；isCache=false，windowID可以不指定；）
// parentWindow       调用页面的window对象
// onOkMethod         对话中点击确认后执行原页面中的方法（如：“orgPosSelect_onOk”）
// dialogTitle        对话框的标题
function public_multiSelectOrgPos(rootOrgCode, orgType, orgLevel, orgBizType, posids, initFocusOrgCode, posIDInputID, posNameInputID, orgCodeInputID, orgNameInputID, orgIDInputID, isCache, windowID, parentWindow, onOkMethod, dialogTitle) {
	if (isCache) {
		if (windowID == null || windowID == "") {
			$.messager.alert('页面错误','组织机构岗位选择public_multiSelectOrgPos()方法：<br><br>参数 windowID 不能为空！','error');
			return;
		}
	}
	if (!windowID) {
		var myTime = (new Date()).getTime();
		windowID = "win_" + myTime;
	}
	if ("undefined" == typeof rootOrgCode || rootOrgCode == null) {
		rootOrgCode = "";
	}
	if ("undefined" == typeof orgType || orgType == null) {
		orgType = "";
	}
	if ("undefined" == typeof orgLevel || orgLevel == null) {
		orgLevel = "";
	}
	if ("undefined" == typeof orgBizType || orgBizType == null) {
		orgBizType = "";
	}
	if ("undefined" == typeof posids || posids == null) {
		posids = "";
	}
	if ("undefined" == typeof initFocusOrgCode || initFocusOrgCode == null) {
		initFocusOrgCode = "";
	}
	if ("undefined" == typeof onOkMethod || onOkMethod == null) {
		onOkMethod = "";
	}
	if ("undefined" == typeof dialogTitle || dialogTitle == null || dialogTitle == "") {
		dialogTitle = "组织机构岗位选择";
	}
	var urlParameter = "rootOrgCode=" + rootOrgCode + "&orgType=" + orgType + "&orgLevel=" + orgLevel + "&orgBizType=" + orgBizType + "&posids=" + posids + "&initFocusOrgCode=" + initFocusOrgCode;
	var realPosIdString = "";
	var parentWinObject = parentWindow;
	if (parentWinObject.contentWindow) {
		parentWinObject = parentWinObject.contentWindow;
	}
	if (posIDInputID && parentWindow.$('#' + posIDInputID).length > 0) {
		realPosIdString = parentWindow.$('#' + posIDInputID).val();
	}
	urlParameter += "&realPosIdString=" + realPosIdString;
	var openURL = contextPath + "/orgPosPublicSelect/multiSelect?" + urlParameter;
	var paramArray = [];
	paramArray['parentWindow'] = parentWindow;
	paramArray['posIDInputID'] = posIDInputID;
	paramArray['posNameInputID'] = posNameInputID;
	paramArray['orgCodeInputID'] = orgCodeInputID;
	paramArray['orgNameInputID'] = orgNameInputID;
	paramArray['orgIDInputID'] = orgIDInputID;
	paramArray['onOkMethod'] = onOkMethod;
	var dataOptions = {
		title: '&nbsp;' + dialogTitle,
		width: 800,   
	    height: 400,  
		collapsible: false, 
		minimizable: false, 
		maximizable: false,
		closable: true,
	    closed: false,    
	    cache: false,
	    inline: false,
	    modal: true
	};
	dataOptions.buttons = [
		{
			text: '确定',
			iconCls: 'icon-ok',
			handler: function() {
				var iframeObject = window.frames[windowID + '_iframe'];
				if (iframeObject.contentWindow) {
					iframeObject = iframeObject.contentWindow;
				}
				iframeObject.ok_execute();
				$('#' + windowID).dialog('close');
				if (onOkMethod != null && onOkMethod != "") {
					try {
						var parentWinObject = parentWindow;
						if (parentWinObject.contentWindow) {
							parentWinObject = parentWinObject.contentWindow;
						}
						eval("parentWinObject." + onOkMethod + "(posIDInputID)");
					}
					catch (err) {
						$.messager.alert('页面错误', "执行事件 "+ onOkMethod + " 有错误发生：<br/><br/>错误名称: " + err.name + "<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;错误行号:" + (err.number & 0xFFFF ) + "<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;错误信息:" + err.message, 'error'); 
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

// 初始化二段地址选择（门楼牌号、门楼牌号详址）选择输入框
// mlphComboID 门楼牌号下拉框ID
// filterData 地址过滤参数，如：{fxjdm:'XXXX'}，如果地址过滤参数为动态的值，则传入的动态值的id（{pcsdm:'#pcsdm'}，注意id前面加“#”），其中：
// 			pcsdm   =派出所代码
// 			zrqdm   =责任区代码
// 			xzqh    =行政区划代码
// 			jlxdm   =街路巷代码
// mlphID 门楼牌号地址表ID隐藏框ID
// mlphmcID 门楼牌号名称隐藏框ID
// mlphXzComboID 门楼牌号详址下拉框ID
// returnFieldData 选择地址时回填的字段ID，如：{id:'mlphid',text:'mlphmc',dzdm:'mlphdm'}，其中：
// 			text    =地址全称
// 			dzfxjdm =地址分县局代码
// 			dzpcsdm =地址派出所代码
// 			dzzrqdm =地址责任区代码
// 			dzxzqh  =地址行政区划代码
// 			dzjlxdm =地址街路巷代码
// 			dzjlxmc =地址街路巷代名称
// 			dzzbx   =地址坐标X
// 			dzzby   =地址坐标Y
// onSelectAfterMplh 选择有效门楼牌号后执行的方法，方法传入的参数为mlphComboID
// onSelectAfterMplhXz 选择有效门楼牌号详址后执行的方法，方法传入的参数为mlphXzComboID
function initAddressSearch(mlphComboID, filterData, mlphID, mlphmcID, mlphXzComboID, returnFieldData, onSelectAfterMplh, onSelectAfterMplhXz) {
	if (mlphXzComboID) {
		$('#' + mlphXzComboID).combobox({
			validEnter: false,
			delay: 600,
			unValidClear: false,
			url: contextPath + '/dzContextSearch/searchAddressMlphXz',
			loader: function(param, success, error) {
				var opts = $(this).combobox('options');
				if (!opts.url) return false;
				if ($('#' + mlphID).val() == "") {
					var data = [];
					success(data);
					return;
				}
				if ("undefined" == typeof param.q) {
					var data = [];
					success(data);
					var comboObject = $.data(this, "combo");
					comboObject.previousValue = null;
					return;
				}
				else {
					opts.validEnter = true;
					param.q = param.q.replace(/(^[\s|　]*)|([\s|　]*$)/g, "");
					if (param.q == "") {
						var data = [];
						success(data);
						return;
					}
				}
				var submitParam = {};
				submitParam['id'] = $('#' + mlphID).val();
				submitParam['searchKey'] = param.q;
				$.ajax({
					type: opts.method,
					url: opts.url,
					data: submitParam,
					dataType: 'json',
					success: function(data) {
						opts.loaded = true;
						success(data);
					},
					error: function() {
					}
				});
			},
			onLoadSuccess: function() {
			},
			filter: function(q, row) {
				return true;
			},
			formatter: function(row) {
				var opts = $(this).combobox('options');
				if (opts.maxValueLength == 0) { // 自动调整宽度与高度
					var data = $(this).combobox('getData');
					var optionTextMaxLen = 0; // text的最大长度
					for (var i = 0; i < data.length; i++){
						var tempRow = data[i];
						var sLen = getGBLength(tempRow[opts.textField]);
						if (sLen > optionTextMaxLen) {
							optionTextMaxLen = sLen;
						}
					}
					opts.maxValueLength = 1;
					autoPanelWidth = (optionTextMaxLen + 8) * 6;
					autoPanelWidth = autoPanelWidth < opts.width ? opts.width : autoPanelWidth;
					var autoPanelHeight = data.length;
					if (data.length > opts.panelOptionsNumber) {
						autoPanelHeight = opts.panelOptionsNumber;
					}
					else if (autoPanelHeight < 2) {
						autoPanelHeight = 2;
					}
					var itemHeight = 20;
					if (IE && IE_VERSION <= 9) {
						itemHeight = 18;
					}
					$(this).combo('panel').panel('resize',{width:autoPanelWidth ,height: autoPanelHeight * itemHeight + 2});	
				}
				return row[opts.textField];
			},
			loadFilter: function(data) {
				return data;
			},
			onSelect: function(record) {
				for (var item in returnFieldData) {
					if (record[item]) {
						if (item == "text") {
							$('#' + returnFieldData[item]).val($('#' + mlphmcID).val() + record[item]);
						}
						else {
							$('#' + returnFieldData[item]).val(record[item]);
						}
					}
				}
				try {
					if (onSelectAfterMplhXz && typeof(eval(onSelectAfterMplhXz)) == 'function') {
						eval(onSelectAfterMplhXz + "(mlphXzComboID)");
					}
				}
				catch (err) {
					$.messager.alert('页面错误', "执行事件 "+ onSelectAfterMplhXz + " 有错误发生：<br/><br/>错误名称: " + err.name + "<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;错误行号:" + (err.number & 0xFFFF ) + "<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;错误信息:" + err.message, 'error'); 
				}
			},
			onHidePanel: function() {
				var opts = $(this).combobox('options');
				if (!opts.validEnter) {
					var comboText = $('#' + mlphXzComboID).next(".combo").children(".combo-text");
					if (comboText.val() == "") {
						$(this).combobox('clear');
						for (var item in returnFieldData) {
							if (item == "id") {
								$('#' + returnFieldData[item]).val($('#' + mlphID).val());
							}
							else if (item == "text") {
								$('#' + returnFieldData[item]).val($('#' + mlphmcID).val());
							}
						}
					}
					return;
				}
				else {
					var clearData = false;
					var data = $(this).combobox('getData');
					if (data.length == 0) {
						clearData = true;
					}
					else {
						var panel = $(this).combo('panel');
						var itemSelected = panel.find('div.combobox-item-selected');
						if (itemSelected.length == 0) {
							clearData = true;
						}
					}
					var comboText = $('#' + mlphXzComboID).next(".combo").children(".combo-text");
					if (comboText.val() == "") {
						clearData = true;
					}
					if (clearData) {
						$(this).combobox('clear');
						for (var item in returnFieldData) {
							if (item == "id") {
								$('#' + returnFieldData[item]).val($('#' + mlphID).val());
							}
							else if (item == "text") {
								$('#' + returnFieldData[item]).val($('#' + mlphmcID).val());
							}
						}
					}
				}
			}
		});
	}

	$('#' + mlphComboID).combobox({
		delay: 600,
		unValidClear: false,
		url: contextPath + '/dzContextSearch/searchAddressMlph',
		loader: function(param, success, error) {
			var opts = $(this).combobox('options');
			if (!opts.url) return false;
			if ("undefined" == typeof param.q) {
				var data = [];
				success(data);
				return;
			}
			else {
				param.q = param.q.replace(/(^[\s|　]*)|([\s|　]*$)/g, "");
				if (param.q == "") {
					var data = [];
					success(data);
					// 清空
					if (mlphID) {
						$('#' + mlphID).val("");
					}
					if (mlphmcID) {
						$('#' + mlphmcID).val(param.q);
					}
					if (mlphXzComboID) {
						$('#' + mlphXzComboID).combobox('loadData',[]);
						$('#' + mlphXzComboID).combobox('clear');
					}
					if (returnFieldData) {
						for (var item in returnFieldData) {
							if (item == "text") {
								$('#' + returnFieldData[item]).val(param.q);
							}
							else {
								$('#' + returnFieldData[item]).val("");
							}
						}
					}
					return;
				}
			}
			// 清空
			if (mlphID) {
				$('#' + mlphID).val("");
			}
			if (mlphmcID) {
				$('#' + mlphmcID).val(param.q);
			}
			if (mlphXzComboID) {
				$('#' + mlphXzComboID).combobox('loadData',[]);
				$('#' + mlphXzComboID).combobox('clear');
			}
			if (returnFieldData) {
				for (var item in returnFieldData) {
					if (item == "text") {
						$('#' + returnFieldData[item]).val(param.q);
					}
					else {
						$('#' + returnFieldData[item]).val("");
					}
				}
			}
			var submitParam = {};
			for (var item in filterData) {
				submitParam[item] = filterData[item];
			}
			for (var item in submitParam) {
				var submitValue = submitParam[item];
				if (submitValue && submitValue.indexOf("#") == 0) { // 动态值
					if ($(submitValue).val()) {
						submitParam[item] = $(submitValue).val();
					}
					else {
						submitParam[item] = "";
					}
				}
			}
			submitParam['searchKey'] = param.q;
			$.ajax({
				type: opts.method,
				url: opts.url,
				data: submitParam,
				dataType: 'json',
				success: function(data) {
					opts.loaded = true;
					success(data);
				},
				error: function() {
				}
			});
		},
		onLoadSuccess: function() {
		},
		filter: function(q, row) {
			return true;
		},
		formatter: function(row) {
			var opts = $(this).combobox('options');
			if (opts.maxValueLength == 0) { // 自动调整宽度与高度
				var data = $(this).combobox('getData');
				var optionTextMaxLen = 0; // text的最大长度
				for (var i = 0; i < data.length; i++){
					var tempRow = data[i];
					var sLen = getGBLength(tempRow[opts.textField]);
					if (sLen > optionTextMaxLen) {
						optionTextMaxLen = sLen;
					}
				}
				opts.maxValueLength = 1;
				autoPanelWidth = (optionTextMaxLen + 8) * 6;
				autoPanelWidth = autoPanelWidth < opts.width ? opts.width : autoPanelWidth;
				var autoPanelHeight = data.length;
				if (data.length > opts.panelOptionsNumber) {
					autoPanelHeight = opts.panelOptionsNumber;
				}
				else if (autoPanelHeight < 2) {
					autoPanelHeight = 2;
				}
				var itemHeight = 20;
				if (IE && IE_VERSION <= 9) {
					itemHeight = 18;
				}
				$(this).combo('panel').panel('resize',{width:autoPanelWidth ,height: autoPanelHeight * itemHeight + 2});	
			}
			return row[opts.textField];
		},
		loadFilter: function(data) {
			return data;
		},
		onSelect: function(record) {
			var opts = $(this).combobox('options');
			opts.mlphdm = record['mlphdm'];
			if (mlphID) {
				if ($('#' + mlphID).val() != record['mlphdm']) {
					$('#' + mlphID).val(record['id']);
					if (mlphXzComboID) {
						$('#' + mlphXzComboID).combobox('clear');
					}
					if (returnFieldData) {
						for (var item in returnFieldData) {
							$('#' + returnFieldData[item]).val("");
						}
					}
					if (mlphXzComboID) {
						$('#' + mlphXzComboID).combobox('reload');
					}
				}
				for (var item in returnFieldData) {
					if (record[item]) {
						$('#' + returnFieldData[item]).val(record[item]);
					}
				}
			}
			if (mlphmcID) {
				$('#' + mlphmcID).val(record['text']);
			}
			try {
				if (onSelectAfterMplh && typeof(eval(onSelectAfterMplh)) == 'function') {
					eval(onSelectAfterMplh + "(mlphComboID)");
				}
			}
			catch (err) {
				$.messager.alert('页面错误', "执行事件 "+ onSelectAfterMplh + " 有错误发生：<br/><br/>错误名称: " + err.name + "<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;错误行号:" + (err.number & 0xFFFF ) + "<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;错误信息:" + err.message, 'error'); 
			}
		},
		onHidePanel: function() {
		}
	});
	
	if (mlphXzComboID) { // 门楼牌号名称为空但地址详址不为空时，门楼牌号的值为地址详址，门楼牌号详址为空
		$(document).ready(function() {
			var comboText1 = $('#' + mlphComboID).next(".combo").children(".combo-text");
			var comboText2 = $('#' + mlphXzComboID).next(".combo").children(".combo-text");
			comboText1.attr('maxlength', 80); // 设置门楼牌号选择输入框只能输入80个汉字
			comboText2.attr('maxlength', 80); // 设置门门楼牌号详址选择输入框只能输入80个汉字
			if (comboText1.val() == "" && comboText2.val() != "") {
				var tempValue = comboText2.val();
				$('#' + mlphComboID).combobox("setValue", tempValue);
				$('#' + mlphmcID).val(tempValue);
				$('#' + mlphXzComboID).combobox("setValue", "");
			}
		});
	}
	else {
		$(document).ready(function() {
			var comboText1 = $('#' + mlphComboID).next(".combo").children(".combo-text");
			comboText1.attr('maxlength', 80); // 设置门楼牌号选择输入框只能输入80个汉字
		});
	}
}

// 初始化单位选择输入框
// dwComboID  单位下拉框ID
// filterData 单位过滤参数，如：{glpcsid:'XXXX'}，如果单位过滤参数为动态的值，则传入的动态值的id（{glpcsid:'#glpcsid'}，注意id前面加“#”），其中：
//			xt_zxbz =注销标志：0=正常数据;2=未核实数据;1=注销数据（为空查询正常数据）
//			glpcsid =管理派出所代码
//			glfxjid =管理分县局代码
// dwID 单位表ID隐藏框ID
// dwmcID 单位名称隐藏框ID
// returnFieldData 选择地址时回填的字段ID，如：{dwbh:'dwbh'}，其中：
//			glbmid  =管理部门代码
//			glpcsid =管理派出所代码
//			glfxjid =管理分县局代码
//			dwbh    =单位编号
//			dwdz    =单位地址
//			xt_zxbz =注销标志：0=正常数据;2=未核实数据;1=注销数据
// onSelectAfterDw 选择有效单位后执行的方法，方法传入的参数为dwComboID
function initDepartmentSearch(dwComboID, filterData, dwID, dwmcID, returnFieldData, onSelectAfterDw) {
	$('#' + dwComboID).combobox({
		delay: 600,
		unValidClear: false,
		url: contextPath + '/dwContextSearch/searchDepartment',
		loader: function(param, success, error) {
			var opts = $(this).combobox('options');
			if (!opts.url) return false;
			if ("undefined" == typeof param.q) {
				var data = [];
				success(data);
				return;
			}
			else {
				param.q = param.q.replace(/(^[\s|　]*)|([\s|　]*$)/g, "");
				if (param.q == "") {
					var data = [];
					success(data);
					// 清空
					if (dwID) {
						$('#' + dwID).val("");
					}
					if (dwmcID) {
						$('#' + dwmcID).val(param.q);
					}
					if (returnFieldData) {
						for (var item in returnFieldData) {
							$('#' + returnFieldData[item]).val("");
						}
					}
					return;
				}
			}
			// 清空
			if (dwID) {
				$('#' + dwID).val("");
			}
			if (dwmcID) {
				$('#' + dwmcID).val(param.q);
			}
			if (returnFieldData) {
				for (var item in returnFieldData) {
					$('#' + returnFieldData[item]).val("");
				}
			}
			var submitParam = {};
			for (var item in filterData) {
				submitParam[item] = filterData[item];
			}
			for (var item in submitParam) {
				var submitValue = submitParam[item];
				if (submitValue && submitValue.indexOf("#") == 0) { // 动态值
					if ($(submitValue).val()) {
						submitParam[item] = $(submitValue).val();
					}
					else {
						submitParam[item] = "";
					}
				}
			}
			submitParam['searchKey'] = param.q;
			$.ajax({
				type: opts.method,
				url: opts.url,
				data: submitParam,
				dataType: 'json',
				success: function(data) {
					opts.loaded = true;
					success(data);
				},
				error: function() {
				}
			});
		},
		onLoadSuccess: function() {
		},
		filter: function(q, row) {
			return true;
		},
		formatter: function(row) {
			var opts = $(this).combobox('options');
			if (opts.maxValueLength == 0) { // 自动调整宽度与高度
				var data = $(this).combobox('getData');
				var optionTextMaxLen = 0; // text的最大长度
				for (var i = 0; i < data.length; i++){
					var tempRow = data[i];
					var optionText = tempRow[opts.textField];
					if (tempRow['dwdz']) {
						optionText += tempRow['dwdz'];
					}
					var sLen = getGBLength(optionText) + 6;
					if (sLen > optionTextMaxLen) {
						optionTextMaxLen = sLen;
					}
				}
				opts.maxValueLength = 1;
				autoPanelWidth = (optionTextMaxLen + 8) * 6;
				autoPanelWidth = autoPanelWidth < opts.width ? opts.width : autoPanelWidth;
				var autoPanelHeight = data.length;
				if (data.length > opts.panelOptionsNumber) {
					autoPanelHeight = opts.panelOptionsNumber;
				}
				else if (autoPanelHeight < 2) {
					autoPanelHeight = 2;
				}
				var itemHeight = 20;
				if (IE && IE_VERSION <= 9) {
					itemHeight = 18;
				}
				$(this).combo('panel').panel('resize',{width:autoPanelWidth ,height: autoPanelHeight * itemHeight + 2});	
			}
			var optionText = row[opts.textField];
			if (row['dwdz']) {
				optionText += "－【"+ row['dwdz'] +"】";
			}
			return optionText;
		},
		loadFilter: function(data) {
			return data;
		},
		onSelect: function(record) {
			var opts = $(this).combobox('options');
			if (dwID) {
				$('#' + dwID).val(record['id']);
				for (var item in returnFieldData) {
					if (record[item]) {
						$('#' + returnFieldData[item]).val(record[item]);
					}
				}
			}
			if (dwmcID) {
				$('#' + dwmcID).val(record['text']);
			}
			try {
				if (onSelectAfterDw && typeof(eval(onSelectAfterDw)) == 'function') {
					eval(onSelectAfterDw + "(dwComboID)");
				}
			}
			catch (err) {
				$.messager.alert('页面错误', "执行事件 "+ onSelectAfterDw + " 有错误发生：<br/><br/>错误名称: " + err.name + "<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;错误行号:" + (err.number & 0xFFFF ) + "<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;错误信息:" + err.message, 'error'); 
			}
		},
		onHidePanel: function() {
		}
	});
	
	$(document).ready(function() {
		var comboText = $('#' + dwComboID).next(".combo").children(".combo-text");
		comboText.attr('maxlength', 50); // 设置单位选择输入框只能输入50个汉字
	});
}

// 附件管理
// lybm      来源表名
// lyid      来源id
// lyms      来源描述
// fileType  上传类型（只能上传图片=img）
// fileOnly  附件是否唯一（'1'=唯一，'0'=不唯一，默认不唯一）
// isTopOpen 是否在顶层页面打开（'1'=顶层页面打开，'0'=本面页打开，默认在本页面打开）
// winTitle  弹出页面窗口标题
function uploadFileEdit(lybm, lyid, lyms, fileType, fileOnly, isTopOpen, winTitle) {
	if ("undefined" == typeof lybm || lybm == null || lybm == "" || 
		"undefined" == typeof lyid || lyid == null || lyid == "") {
		topMessager.alert('', '管理附件传入参数错误！');
		return;
	}
	if ("undefined" == typeof lyms) {
		lyms = "";
	}
	lyms = encodeURI(lyms);
	if ("undefined" == typeof fileType) {
		fileType = "";
	}
	if ("undefined" == typeof fileOnly || fileOnly == "") {
		fileOnly = "0";
	}
	if ("undefined" == typeof winTitle || winTitle == null || winTitle == "") {
		winTitle = "附件信息";
	}
	var dataOptions = {
   		title: winTitle,
   		url: contextPath + '/zpfjFjxxb/edit?lybm='+ lybm + '&lyid=' + lyid + '&lyms=' + lyms + '&fileType=' + fileType + '&fileOnly=' + fileOnly,
   		width: 850,
   		height: 531
   	};
	if ("undefined" == typeof isTopOpen) {
		isTopOpen = "0";
	}
	if (isTopOpen == "1") {
		window.top.openWindowWithSave(false, "", window, null, dataOptions, "您是否要上传附件？", "");
	}
	else {
		openWindowWithSave(false, "", window, null, dataOptions, "您是否要上传附件？", "");
	}
}

// 附件查看
// lybm 来源表名
// lyid 来源id
// isTopOpen 是否在顶层页面打开（'1'=顶层页面打开，'0'=本面页打开，默认在本页面打开）
// winTitle  弹出页面窗口标题
function uploadFileView(lybm, lyid, isTopOpen, winTitle) {
	if ("undefined" == typeof lybm || lybm == null || lybm == "" || 
		"undefined" == typeof lyid || lyid == null || lyid == "") {
		topMessager.alert('', '查看附件传入参数错误！');
		return;
	}
	if ("undefined" == typeof winTitle || winTitle == null || winTitle == "") {
		winTitle = "附件信息";
	}
	var dataOptions = {
   		title: winTitle,
   		url: contextPath + '/zpfjFjxxb/query?lybm='+ lybm + '&lyid=' + lyid,
   		width: 850,
   		height: 417
   	};
	if ("undefined" == typeof isTopOpen) {
		isTopOpen = "0";
	}
	if (isTopOpen == "1") {
		window.top.openWindowNoSave(false, "", window, null, dataOptions);
	}
	else {
		openWindowNoSave(false, "", window, null, dataOptions);
	}
}

// 附件图片查看（支持多个）
// lybm       来源表名
// lyid       来源id
// divID      显示图片div的ID
// imgWidth   图片宽度
// imgHeight  图片高度
// emptyImage 无图片时显示的照片
function uploadFileImageView(lybm, lyid, divID, imgWidth, imgHeight, emptyImage) {
	if ("undefined" == typeof lybm || lybm == null || lybm == "" || 
		"undefined" == typeof lyid || lyid == null || lyid == "" || 
		"undefined" == typeof divID || divID == null || divID == "") {
		topMessager.alert('', '查看附件图片查传入参数错误！');
		return;
	}
	$.ajax({
		type: "POST",
		url: contextPath + "/zpfjFjxxb/queryZpfjIdList",
		dataType: "json",
		data: "lybm="+lybm+"&lyid="+lyid,
		success: function(data) {
			if (data) {
				var idArray = data.split(",");
				var id = idArray[0];
				var imgUrl = contextPath + "/zpfjFjxxb/queryZpfjById.jpg?id=" + id;
				var divHtml = "<img id='"+ divID +"Img' src='"+ imgUrl +"' width='"+imgWidth+"' height='"+imgHeight+"' border='0' idString='"+ data +"' idIndex='1' style='cursor:pointer'/>";
				divHtml += "<div style='width:100%; padding-top:2px; padding-bottom:2px;'><span id='"+ divID +"ImgIndex'>1</span> / " + idArray.length + " 张</span>&nbsp;&nbsp;";
				divHtml += "<input type='button' id='"+ divID +"ImgPrev' value='上一张' disabled='disabled' style='cursor:pointer'/>&nbsp;";
				if (idArray.length == 1) {
					divHtml += "<input type='button' id='"+ divID +"ImgNext' value='下一张' disabled='disabled' style='cursor:pointer'/>";
				}
				else {
					divHtml += "<input type='button' id='"+ divID +"ImgNext' value='下一张' style='cursor:pointer'/>";
				}
				divHtml += "</div>";
				$('#' + divID).html(divHtml);
				$('#' + divID).bind("dblclick",function() {
					cancelBubble();
				});
				$('#' + divID + 'Img').bind("click",function() {
					var imgUrl = $('#' + divID + 'Img').attr('src');
					window.open(imgUrl);
				});
				$('#' + divID + 'ImgPrev').bind("click",function() {
					$('#' + divID + 'ImgNext').prop('disabled', false);
					var idString = $('#' + divID + 'Img').attr('idString');
					var idArray = idString.split(",");
					var idIndex = $('#' + divID + 'Img').attr('idIndex');
					idIndex = parseInt(idIndex) - 1;
					$('#' + divID + 'Img').attr('idIndex', idIndex);
					var id = idArray[idIndex - 1];
					var imgUrl = contextPath + "/zpfjFjxxb/queryZpfjById.jpg?id=" + id;
					$('#' + divID + 'ImgIndex').html(idIndex);
					$('#' + divID + 'Img').attr('src', imgUrl);
					if (idIndex == 1) {
						$('#' + divID + 'ImgPrev').prop('disabled', true);
					}
				});
				$('#' + divID + 'ImgNext').bind("click",function() {
					$('#' + divID + 'ImgPrev').prop('disabled', false);
					var idString = $('#' + divID + 'Img').attr('idString');
					var idArray = idString.split(",");
					var idIndex = $('#' + divID + 'Img').attr('idIndex');
					idIndex = parseInt(idIndex) + 1;
					$('#' + divID + 'Img').attr('idIndex', idIndex);
					var id = idArray[idIndex - 1];
					var imgUrl = contextPath + "/zpfjFjxxb/queryZpfjById.jpg?id=" + id;
					$('#' + divID + 'ImgIndex').html(idIndex);
					$('#' + divID + 'Img').attr('src', imgUrl);
					if (idIndex == idArray.length) {
						$('#' + divID + 'ImgNext').prop('disabled', true);
					}
				});
			}
			else {
				if ("undefined" == typeof emptyImage || emptyImage == null || emptyImage == "") {
					var divHtml = "<div style='width:100%; padding-top:10px; padding-bottom:10px; color: #666666'>暂无照片</div>";
					$('#' + divID).html(divHtml);
				}
				else {
					var divHtml = "<img id='"+ divID +"Img' src='"+ emptyImage +"' width='100%' height='100%' border='0'/>";
					$('#' + divID).html(divHtml);
				}
			}
		}
	});
}


// 人员照片管理
// ryid            人员ID
// lybm            来源表名
// lyid            来源id
// lyms            来源描述
// isTopOpen       是否在顶层页面打开（'1'=顶层页面打开，'0'=本面页打开，默认在本页面打开）
// onSubmitSuccess 对话中提交成功后执行的方法点击确认后执行原页面中的方法（如：“xzqh_onSubmitSuccess”，该方法的参数会传入弹出页面所有已提交的数据）
// oldPageObject   执行onSubmitSuccess方法时返回原页面的参数（如：原页面的某个动太对象{oldObject:this}）
function ptryzpEdit(ryid, lybm, lyid, lyms, isTopOpen, onSubmitSuccess, oldPageObject) {
	if ("undefined" == typeof ryid || ryid == null || ryid == "" ||
		"undefined" == typeof lybm || lybm == null || lybm == "" || 
		"undefined" == typeof lyid || lyid == null || lyid == "") {
		topMessager.alert('', '管理人员照片传入参数错误！');
		return;
	}
	if ("undefined" == typeof lyms) {
		lyms = "";
	}
	lyms = encodeURI(lyms);
	var dataOptions = {
		title: '人员照片信息',
		url: contextPath + '/zpfjPtryzp/edit?ryid=' + ryid + '&lybm='+ lybm + '&lyid=' + lyid + '&lyms=' + lyms,
		width: 820,
		height: 200
	};
	if ("undefined" == typeof isTopOpen) {
		isTopOpen = "0";
	}
	if (isTopOpen == "1") {
		window.top.openWindowWithSave(false, "", window, null, dataOptions, "您是否要上传人员照片？", onSubmitSuccess, oldPageObject);
	}
	else {
		openWindowWithSave(false, "", window, null, dataOptions, "您是否要上传人员照片？",  onSubmitSuccess, oldPageObject);
	}
}

// 验证联系电话正确性（是否与其他人的联系电话有重复）
// lxdh_id 联系电话输入框ID
// ryid_id 人员ID输入框ID
function checkLxdh(lxdh_id, ryid_id) {
	if (!$("#" + lxdh_id).validatebox("isValid")) {
		return;
	}	
	if ($("#" + lxdh_id).val() == "") {
		return;
	}
	var _lxdh = $("#" + lxdh_id).attr("lxdh");
	if (!_lxdh) {
		_lxdh = "";
	} 
	if ($("#" + lxdh_id).val() != _lxdh) {
		$.ajax({
			type: "POST",
			url: contextPath + "/ryRylxfsxxb/check",
			dataType: "json",
			data: "lxfs="+$("#" + lxdh_id).val()+"&ryid="+$("#" + ryid_id).val(),
			success: function(data){
				if (data && data.xm) {
					if (data.xm != ""||data.xm!=null) {
						topMessagerAlert('数据质量提醒', '电话号码：'+data.lxfs+'<br><br>已于'+data.djsj+'在办理业务中<br><br>登记为【'+data.xm+'】的联系电话，<br><br>与当前录入信息不符');
					}
				}
				
			}
		});
		$("#" + lxdh_id).attr("lxdh", $("#" + lxdh_id).val());
	}
}
