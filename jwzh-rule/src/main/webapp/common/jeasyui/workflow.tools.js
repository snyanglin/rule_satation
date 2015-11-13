/**
 * 流程引擎公共JS方法 
 * author : redstorm 
 * create time : 2014-11-18
 */

// 流程事件资源公共选择
// isCache         是否缓存页面（默认为false不缓存）
// windowID        窗口的ID（isCache=true，windowID确保在同一个页面中唯一；isCache=false，windowID可以不指定；）
// parentWindow    调用页面的window对象
// eventclassID    事件执行类名存放的ID
// eventmethodID   事件执行方法名存放的ID
// eventtype       事件类型过滤条件（1=流程整体事件（启动前）；2=流程整体事件（完成后）；3=流程环节流向事件）
// onOkMethod      对话中点击确认后执行原页面中的方法（如：“event_onOk”）
function wf_wfDeEventresourceSelect(isCache, windowID, parentWindow, eventclassID, eventmethodID, eventtype, onOkMethod) {
	if (isCache) {
		if (windowID == null || windowID == "") {
			$.messager.alert('页面错误','流程事件资源选择wf_wfDeEventresourceSelect()方法：<br><br>参数 windowID 不能为空！','error');
			return;
		}
	}
	if (!windowID) {
		var myTime = (new Date()).getTime();
		windowID = "win_" + myTime;
	}
	if ("undefined" == typeof eventtype || eventtype == null) {
		eventtype = "";
	}
	var openURL = contextPath + "/wfDeEventresource/select?eventtype=" + eventtype;
	if ("undefined" == typeof onOkMethod || onOkMethod == null) {
		onOkMethod = "";
	}
	var paramArray = [];
	paramArray['parentWindow'] = parentWindow;
	paramArray['eventclassID'] = eventclassID;
	paramArray['eventmethodID'] = eventmethodID;
	paramArray['onOkMethod'] = onOkMethod;
	var dataOptions = {
		title: '&nbsp;流程事件资源选择',
		width: 850,
		height: 420,
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
				if (!iframeObject.ok_execute()) {
					return;
				}
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

// 流转到下一个流转环节
// windowID       业务打开页面的窗口ID
// datagrid_ID    列表页面datagrid的ID
// processid      流程实例ID
// taskid         当前任务ID
// tacheid        当前环节ID
// transtionid    环节流向ID
// nexttacheid    目标环节ID
function transitionTache_click(windowID, datagrid_ID, processid, taskid, tacheid, transtionid, nexttacheid) {
	var iframeObject = window.frames[windowID + '_iframe'];
	if (iframeObject.contentWindow) {
		iframeObject = iframeObject.contentWindow;
	}
	var formObject = iframeObject.$('form').first();
	if (formObject) {
		checkComboIsClosed(iframeObject, formObject); // 提交之前强行校验下拉框的是否关闭
		if (formObject.form('validate')) { // 业务表单的验证
			var isCache = false;
			var urlParameter = "processid=" + processid + "&taskid=" + taskid + "&tacheid=" + tacheid + "&nexttacheid=" + nexttacheid;
			var openURL = contextPath + "/workFlowEngine/transitionDriveTache?" + urlParameter;
			var paramArray = [];
			var dataOptions = {
				title: '&nbsp;流程驱动－流转信息设置',
				width: 850,
				height: 420,
				collapsible: false, 
				minimizable: false, 
				maximizable: false,
				closable: true,
			    closed: false,    
			    cache: false,
			    inline: false,
			    modal: true
			};
			var myTime = (new Date()).getTime();
			var wfWin = "win_" + myTime;
			dataOptions.buttons = [
				{
					text: '确定',
					iconCls: 'icon-ok',
					handler: function() {
						var bottonObject = this;
						if (buttonDisabled(bottonObject) == false) {
							return false;
						}
						var wfIframeObject = window.frames[wfWin + '_iframe'];
						if (wfIframeObject.contentWindow) {
							wfIframeObject = wfIframeObject.contentWindow;
						}
						var returnObject = wfIframeObject.ok_execute();					
						if (returnObject == null) {
							buttonEnabled(bottonObject);
							return;
						}
						iframeObject.$("input[name='wfTacheDrive.transitionid']").val(transtionid);
						iframeObject.$("input[name='wfTacheDrive.nexttacheid']").val(nexttacheid);
						iframeObject.$("input[name='wfTacheDrive.nexttacheuserid']").val(returnObject['nexttacheuserid']);
						iframeObject.$("input[name='wfTacheDrive.sendshortmessage']").val(returnObject['sendshortmessage']);
						iframeObject.$("input[name='wfTacheDrive.writeidea']").val(returnObject['writeidea']);
						
						formObject.form('submit',{ // 业务表单的提交
							dataType : 'json',
							onSubmit: function() {
							},
							success: function(result) {
								result = parseReturn(result);
								if (result.status == 'success') { // 返回成功后执行的方法
									buttonEnabled(bottonObject);
									$('#' + wfWin).dialog('close');
									doSubmitResult(result, windowID, datagrid_ID);
								}
								else {
									buttonEnabled(bottonObject);
									topMessagerAlert("", "流程驱动，流转信息保存错误！");
								}
							}
						});
					}
				},
				{
					text: '关闭',
					iconCls: 'icon-cancel',
					handler: function() {
						$('#' + wfWin).dialog('close');
					}
				}
			];
			openWindow(isCache, wfWin, openURL, paramArray, dataOptions);			
		}
	}
	return;
}

// 流转到结束环节
// windowID       业务打开页面的窗口ID
// datagrid_ID    列表页面datagrid的ID
// processid      流程实例ID
// taskid         当前任务ID
// tacheid        当前环节ID
// transtionid    环节流向ID
// nexttacheid    目标环节ID
function transitionEnd_click(windowID, datagrid_ID, processid, taskid, tacheid, transtionid, nexttacheid) {
	var iframeObject = window.frames[windowID + '_iframe'];
	if (iframeObject.contentWindow) {
		iframeObject = iframeObject.contentWindow;
	}
	var formObject = iframeObject.$('form').first();
	if (formObject) {
		checkComboIsClosed(iframeObject, formObject); // 提交之前强行校验下拉框的是否关闭
		if (formObject.form('validate')) { // 业务表单的验证
			var isCache = false;
			var urlParameter = "processid=" + processid + "&taskid=" + taskid + "&tacheid=" + tacheid + "&nexttacheid=" + nexttacheid;
			var openURL = contextPath + "/workFlowEngine/transitionDriveEnd?" + urlParameter;
			var paramArray = [];
			var dataOptions = {
				title: '&nbsp;流程驱动－流转信息设置',
				width: 850,
				height: 420,
				collapsible: false, 
				minimizable: false, 
				maximizable: false,
				closable: true,
			    closed: false,    
			    cache: false,
			    inline: false,
			    modal: true
			};
			var myTime = (new Date()).getTime();
			var wfWin = "win_" + myTime;
			dataOptions.buttons = [
				{
					text: '确定',
					iconCls: 'icon-ok',
					handler: function() {
						var bottonObject = this;
						if (buttonDisabled(bottonObject) == false) {
							return false;
						}
						var wfIframeObject = window.frames[wfWin + '_iframe'];
						if (wfIframeObject.contentWindow) {
							wfIframeObject = wfIframeObject.contentWindow;
						}
						var returnObject = wfIframeObject.ok_execute();					
						if (returnObject == null) {
							buttonEnabled(bottonObject);
							return;
						}
						iframeObject.$("input[name='wfTacheDrive.transitionid']").val(transtionid);
						iframeObject.$("input[name='wfTacheDrive.nexttacheid']").val(nexttacheid);
						iframeObject.$("input[name='wfTacheDrive.writeidea']").val(returnObject['writeidea']);
						
						formObject.form('submit',{ // 业务表单的提交
							dataType : 'json',
							onSubmit: function() {
							},
							success: function(result) {
								result = parseReturn(result);
								if (result.status == 'success') { // 返回成功后执行的方法
									buttonEnabled(bottonObject);
									$('#' + wfWin).dialog('close');
									doSubmitResult(result, windowID, datagrid_ID);
								}
								else {
									buttonEnabled(bottonObject);
									topMessagerAlert("", "流程驱动，流转信息保存错误！");
								}
							}
						});
					}
				},
				{
					text: '关闭',
					iconCls: 'icon-cancel',
					handler: function() {
						$('#' + wfWin).dialog('close');
					}
				}
			];
			openWindow(isCache, wfWin, openURL, paramArray, dataOptions);			
		}
	}
	return;
}

// 填写意见
// windowID       业务打开页面的窗口ID
// datagrid_ID    列表页面datagrid的ID
// processid      流程实例ID
// taskid         当前任务ID
// tacheid        当前环节ID
function transition_wirteIdea(windowID, datagrid_ID, processid, taskid, tacheid) {
	var iframeObject = window.frames[windowID + '_iframe'];
	if (iframeObject.contentWindow) {
		iframeObject = iframeObject.contentWindow;
	}
	var formObject = iframeObject.$('form').first();
	if (formObject) {
		checkComboIsClosed(iframeObject, formObject); // 提交之前强行校验下拉框的是否关闭
		if (formObject.form('validate')) { // 业务表单的验证
			var isCache = false;
			var urlParameter = "processid=" + processid + "&taskid=" + taskid + "&tacheid=" + tacheid;
			var openURL = contextPath + "/workFlowEngine/transitionWriteIdea?" + urlParameter;
			var paramArray = [];
			var dataOptions = {
				title: '&nbsp;流程驱动－流转信息设置',
				width: 850,
				height: 420,
				collapsible: false, 
				minimizable: false, 
				maximizable: false,
				closable: true,
			    closed: false,    
			    cache: false,
			    inline: false,
			    modal: true
			};
			var myTime = (new Date()).getTime();
			var wfWin = "win_" + myTime;
			dataOptions.buttons = [
				{
					text: '确定',
					iconCls: 'icon-ok',
					handler: function() {
						var bottonObject = this;
						if (buttonDisabled(bottonObject) == false) {
							return false;
						}
						var wfIframeObject = window.frames[wfWin + '_iframe'];
						if (wfIframeObject.contentWindow) {
							wfIframeObject = wfIframeObject.contentWindow;
						}
						var returnObject = wfIframeObject.ok_execute();					
						if (returnObject == null) {
							buttonEnabled(bottonObject);
							return;
						}
						iframeObject.$("input[name='wfTacheDrive.writeidea']").val(returnObject['writeidea']);
						iframeObject.$("input[name='wfTacheDrive.iswriteidea']").val('1');
						
						formObject.form('submit',{ // 业务表单的提交
							dataType : 'json',
							onSubmit: function() {
							},
							success: function(result) {
								result = parseReturn(result);
								if (result.status == 'success') { // 返回成功后执行的方法
									buttonEnabled(bottonObject);
									$('#' + wfWin).dialog('close');
									doSubmitResult(result, windowID, datagrid_ID);
								}
								else {
									buttonEnabled(bottonObject);
									topMessagerAlert("", "流程驱动，流转信息保存错误！");
								}
							}
						});
					}
				},
				{
					text: '关闭',
					iconCls: 'icon-cancel',
					handler: function() {
						$('#' + wfWin).dialog('close');
					}
				}
			];
			openWindow(isCache, wfWin, openURL, paramArray, dataOptions);			
		}
	}
	return;
}

// 退回上一步
// windowID       业务打开页面的窗口ID
// datagrid_ID    列表页面datagrid的ID
// processid      流程实例ID
// taskid         当前任务ID
// tacheid        当前环节ID
// nexttacheid    目标环节ID
// backtaskid     退回上一步任务ID
function transition_back(windowID, datagrid_ID, processid, taskid, tacheid, nexttacheid, backtaskid) {
	var iframeObject = window.frames[windowID + '_iframe'];
	if (iframeObject.contentWindow) {
		iframeObject = iframeObject.contentWindow;
	}
	var formObject = iframeObject.$('form').first();
	if (formObject) {
		checkComboIsClosed(iframeObject, formObject); // 提交之前强行校验下拉框的是否关闭
		if (formObject.form('validate')) { // 业务表单的验证
			var isCache = false;
			var urlParameter = "processid=" + processid + "&taskid=" + taskid + "&tacheid=" + tacheid + "&nexttacheid=" + nexttacheid + "&backtaskid=" + backtaskid + "&isback=1";
			var openURL = contextPath + "/workFlowEngine/transitionDriveTache?" + urlParameter;
			var paramArray = [];
			var dataOptions = {
				title: '&nbsp;流程驱动－流转信息设置',
				width: 850,
				height: 420,
				collapsible: false, 
				minimizable: false, 
				maximizable: false,
				closable: true,
			    closed: false,    
			    cache: false,
			    inline: false,
			    modal: true
			};
			var myTime = (new Date()).getTime();
			var wfWin = "win_" + myTime;
			dataOptions.buttons = [
				{
					text: '确定',
					iconCls: 'icon-ok',
					handler: function() {
						var bottonObject = this;
						if (buttonDisabled(bottonObject) == false) {
							return false;
						}
						var wfIframeObject = window.frames[wfWin + '_iframe'];
						if (wfIframeObject.contentWindow) {
							wfIframeObject = wfIframeObject.contentWindow;
						}
						var returnObject = wfIframeObject.ok_execute();					
						if (returnObject == null) {
							buttonEnabled(bottonObject);
							return;
						}
						iframeObject.$("input[name='wfTacheDrive.nexttacheid']").val(nexttacheid);
						iframeObject.$("input[name='wfTacheDrive.nexttacheuserid']").val(returnObject['nexttacheuserid']);
						iframeObject.$("input[name='wfTacheDrive.sendshortmessage']").val(returnObject['sendshortmessage']);
						iframeObject.$("input[name='wfTacheDrive.writeidea']").val(returnObject['writeidea']);
						iframeObject.$("input[name='wfTacheDrive.isback']").val('1');
						iframeObject.$("input[name='wfTacheDrive.backtaskid']").val(backtaskid);
						
						formObject.form('submit',{ // 业务表单的提交
							dataType : 'json',
							onSubmit: function() {
							},
							success: function(result) {
								result = parseReturn(result);
								if (result.status == 'success') { // 返回成功后执行的方法
									buttonEnabled(bottonObject);
									$('#' + wfWin).dialog('close');
									doSubmitResult(result, windowID, datagrid_ID);
								}
								else {
									buttonEnabled(bottonObject);
									topMessagerAlert("", "流程驱动，流转信息保存错误！");
								}
							}
						});
					}
				},
				{
					text: '关闭',
					iconCls: 'icon-cancel',
					handler: function() {
						$('#' + wfWin).dialog('close');
					}
				}
			];
			openWindow(isCache, wfWin, openURL, paramArray, dataOptions);			
		}
	}
	return;
}
