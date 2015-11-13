/**
 * jQuery EasyUI 1.3.6 Copyright (c) 2009-2014 www.jeasyui.com. All rights reserved.
 * 
 * Licensed under the GPL or commercial licenses To use it on other terms please
 * contact author: info@jeasyui.com http://www.gnu.org/licenses/gpl.txt
 * http://www.jeasyui.com/license_commercial.php
 * 
 * jQuery EasyUI 1.3.6 扩展 
 * author : redstorm 
 * create time : 2014-05-01
 * 
 */

var IE = window.navigator.appVersion.toUpperCase().indexOf('MSIE') == -1 ? false : true;
var IE_VERSION = getIEVersion();
var MESSAGER_TITLE = '系统信息';

// 获取 IE 的版本号
function getIEVersion() {
	if (IE) {
		var nAppVersion = window.navigator.appVersion.toUpperCase();
		var fromAt = nAppVersion.indexOf('MSIE');
		if (fromAt != -1) {
			nAppVersion = nAppVersion.substring(fromAt + 4, nAppVersion.indexOf(";", fromAt + 4));
			nAppVersion = nAppVersion.replace(/(^\s*)|(\s*$)/g, "");
			nAppVersion = parseInt(nAppVersion);
			return nAppVersion;
		}
	}
	return 0;
}

(function ($) {

	function setValues(target, values, remainText) {
		var opts = $.data(target, 'combobox').options;
		if (opts.loaded) {
			if (opts.clientLoad) {
				var vv = [], ss = [];
				var clearList = true;
				var panel = $(target).combo('panel');
				panel.find('div.combobox-item-selected').removeClass('combobox-item-selected');
				for (var i = 0; i < values.length; i++) {
					var v = values[i];
					var s = window.top.getDictName(opts.url, v);
					if (s != "") {
						opts.finder.getEl(target, v).addClass('combobox-item-selected');
						var row = opts.finder.getRow(target, v);
						if (row) {
							clearList = false;
						}
						vv.push(v);
						ss.push(s);
						if (!opts.multiple) {
							break;
						}
					}
				}				
				$(target).combo('setValues', vv);
				target.value = vv;
				if (clearList) {
					$(target).combobox('loadData', []);
				}
				var comboText = $(target).next(".combo").children(".combo-text");
				if (ss.length == 0) {
					comboText.css('color', '#C0C0C0');
					comboText.val("请输入搜索内容...");
				}
				else {
					comboText.css('color', '#222222');
					$(target).combo('setText', ss.join(opts.separator));
					opts.validEnter = false;
				}
				return;
			}
			
			if (!opts.unValidClear && values.length == 0) {
				$(target).combo('setValues', []);
				$(target).combo('setText', '');
				target.value = "";
				return;
			}
			var panel = $(target).combo('panel');
			panel.find('div.combobox-item-selected').removeClass('combobox-item-selected');
			var vv = [], ss = [];
			for (var i = 0; i < values.length; i++) {
				var v = values[i];
				var s = v;
				opts.finder.getEl(target, v).addClass('combobox-item-selected');
				var row = opts.finder.getRow(target, v);
				if (row) {
					s = row[opts.textField];
					vv.push(v);
					ss.push(s);
					if (!opts.multiple) {
						break;
					}
				}
			}
			$(target).combo('setValues', vv);
			target.value = vv; 
			if (!remainText) {
				$(target).combo('setText', ss.join(opts.separator));
			}
		}
		else {
			if (opts.clientLoad) {
				var vv = [], ss = [];
				var clearList = true;
				var panel = $(target).combo('panel');
				panel.find('div.combobox-item-selected').removeClass('combobox-item-selected');
				for (var i = 0; i < values.length; i++) {
					var v = values[i];
					var s = window.top.getDictName(opts.url, v);
					if (s != "") {
						opts.finder.getEl(target, v).addClass('combobox-item-selected');
						var row = opts.finder.getRow(target, v);
						if (row) {
							clearList = false;
						}
						vv.push(v);
						ss.push(s);
						if (!opts.multiple) {
							break;
						}
					}
				}				
				$(target).combo('setValues', vv);
				target.value = vv;
				if (clearList) {
					$(target).combobox('loadData', []);
				}
				var comboText = $(target).next(".combo").children(".combo-text");
				if (ss.length == 0) {
					comboText.css('color', '#C0C0C0');
					comboText.val("请输入搜索内容...");
				}
				else {
					comboText.css('color', '#222222');
					$(target).combo('setText', ss.join(opts.separator));
					opts.validEnter = false;
				}
				return;
			}
			var data = $(target).combobox('getData');
			if (!opts.unValidClear && values.length == 0 && data.length == 0) {
				$(target).combo('setValues', []);
				$(target).combo('setText', '');
				target.value = "";
				return;
			}
			if (!opts.unValidClear && values.length > 0 && data.length == 0) {
				target.value = values.join(opts.separator); 
				if (!remainText) {
					$(target).combo('setText', values.join(opts.separator));
				}
				return;
			}
			var valueArray = [];
			for (var i = 0; i < values.length; i++) {
				if (values[i].indexOf(opts.separator) != -1) {
					valueArray = values[i].split(opts.separator);
				}
				else {
					valueArray.push(values[i]);
				}
			}
			$(target).combo('setValues', valueArray);
		}
	}
	
	var defaults = $.extend({}, $.fn.combobox.defaults, {
		
		isTopLoad: true, 
		
		maxValueLength: 0,
		
		panelOptionsNumber: 10,

		loaded: false,

		dataFilter: '',
		
		unValidClear: true,
		
		filter: function(q, row) {
			if (q == "") {
				return true;
			}
			var opts = $(this).combobox('options');
			var str = q.toUpperCase();
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
			return returnValue;
		},
		
		formatter: function(row) {
			var opts = $(this).combobox('options');
			if (opts.maxValueLength == 0) { 
				var data = $(this).combobox('getData');
				var maxValueLen = 0; 
				var optionTextMaxLen = 0;
				for (var i = 0; i < data.length; i++) {
					var tempRow = data[i];
					var vLen = (tempRow[opts.valueField] + '').length;
					var sLen = getGBLength(tempRow[opts.textField]);
					if (vLen > maxValueLen) {
						maxValueLen = vLen;
					}
					if (sLen > optionTextMaxLen) {
						optionTextMaxLen = sLen;
					}
				}
				opts.maxValueLength = maxValueLen;
				autoPanelWidth = (maxValueLen + optionTextMaxLen + 8) * 6;
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
			var emptyString = getReapeatString("\xA0", opts.maxValueLength - row[opts.valueField].length);
			return row[opts.valueField] + emptyString + "\xA0|\xA0" + row[opts.textField];
		},
		
		onBeforeLoad: function(param) { 
			var state = $.data(this, 'combobox'); 
			var opts = state.options;
			opts.maxValueLength = 0;
		},
		
		loader: function(param, success, error) {
			var opts = $(this).combobox('options');
			if (!opts.url) return false;
			if (opts.isTopLoad && window.top && window.top.publicDictArray) {
				data = window.top.getPublicDict(opts.url);
				opts.loaded = true;
				success(data);
			}
			else {
				$.ajax({
					type: opts.method,
					url: opts.url,
					data: param,
					dataType: 'json',
					success: function(data) {
						opts.loaded = true;
						success(data);
					},
					error: function() {
						error.apply(this, arguments);
					}
				});
			}
		},
		
		loadFilter: function(data) {
			var opts = $(this).combobox('options');
			var dataFilter = opts.dataFilter;
			if (dataFilter == "") {
				return data;
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
						resultData.push(row);
					}
				}
				return resultData;
			}
		},
		
		onLoadSuccess: function() { 
			var state = $.data(this, 'combobox'); 
			var opts = state.options;
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
			setValues(this, valueArray);
		},
		
		onHidePanel: function() {
			var opts = $(this).combobox('options');
			var oldValue = $(this).combobox("getValues");
			if (oldValue.length == 1 && oldValue[0] == "") {
				setValues(this, []);
			}
			else {
				if (opts.mode == "remote") {
					setValues(this, oldValue);
				}
				else if (opts.multiple) {
					setValues(this, oldValue);
				}
			}
			this.value = oldValue; 
		}
		
	});

	var methods = $.extend({}, $.fn.combobox.methods, {
		
		setValue: function(jq, value) { 
			if (value == null || value == "") {
				return jq.each(function() {
					setValues(this, []);
				});
			}
			var opts = jq.combo('options');
			var valueArray = [];
			if (value.indexOf(opts.separator) != -1) {
				valueArray = value.split(opts.separator);
			}
			else {
				valueArray.push(value);
			}
			return jq.each(function() {
				setValues(this, valueArray);
			});
		},
		
		setValues: function(jq, values) { 
			if (values == null) {
				return jq.each(function() {
					setValues(this, []);
				});
			}
			var opts = jq.combo('options');
			var valueArray = [];
			for (var i = 0; i < values.length; i++) {
				var value = values[i];
				if (value != null && value != "") {
					if (value.indexOf(opts.separator) != -1) {
						valueArray = valueArray.concat(value.split(opts.separator));
					}
					else {
						valueArray.push(value);
					}
				}
			}
			return jq.each(function() {
				setValues(this, valueArray);
			});
		},
		
		getDataFilter: function(jq) {
			var opts = jq.combobox('options');
			return opts.dataFilter;
		},

		setDataFilter: function(jq, param) {
			var opts = jq.combobox('options');
			if (opts.dataFilter != param) {
				opts.dataFilter = param;
				jq.combobox('reload');
			}
		}
	
	});
	
	$.extend($.fn.combobox.defaults, defaults);
	$.extend($.fn.combobox.methods, methods);

})(jQuery);
 

(function ($) {

	function getDataFilterData(data, regExp) {
		for (var i = data.length - 1; i >= 0 ; i--) {
			var node = data[i];
			if (!isValidFilterNode(node, regExp)) {
				data.splice(i, 1);
			}
		}
	}

	function isValidFilterNode(node, regExp) {
		var v = node['id'];
		if (node.children) {
			var cNodes = node.children;
			var returnValue = false;
			for (var j = cNodes.length - 1; j >= 0 ; j--) {
				var isValid = isValidFilterNode(cNodes[j], regExp);
				if (isValid) {
					returnValue = true;
				}
				else {
					cNodes.splice(j, 1);
				}
			}
			return returnValue;
		}
		else {
			if (regExp.test(v)) {
				return true;
			}
		}
		return false;
	}

	function searchTreeNode(nodes, onlyLeaf, searchKey, searchResultArray) {
		for (var i = 0; i < nodes.length; i++) {
			var searchResult = false;
			var node = nodes[i];
			if (node.children) {
				if (!onlyLeaf && node['id'] != "ROOT") {
					var py = node['py'];
					if (py) {
						if (node['id'].indexOf(searchKey) == 0 || node['text'].toUpperCase().indexOf(searchKey) >= 0 || node['py'].indexOf(searchKey) >= 0) {
							searchResult = true;
						}
					}
					else {
						if (node['id'].indexOf(searchKey) == 0 || node['text'].toUpperCase().indexOf(searchKey) >= 0) {
							searchResult = true;
						}
					}
				}
				if (searchResult) {
					searchResultArray.push(node);
				}
				searchTreeNode(node.children, onlyLeaf, searchKey, searchResultArray);
			}
			else {
				var py = node['py'];
				if (py) {
					if (node['id'].indexOf(searchKey) == 0 || node['text'].toUpperCase().indexOf(searchKey) >= 0 || node['py'].indexOf(searchKey) >= 0) {
						searchResult = true;
					}
				}
				else {
					if (node['id'].indexOf(searchKey) == 0 || node['text'].toUpperCase().indexOf(searchKey) >= 0) {
						searchResult = true;
					}
				}
				if (searchResult) {
					searchResultArray.push(node);
				}
			}
		}
	}
	
	var defaults = $.extend({}, $.fn.tree.defaults, {

		onlyLeaf: true, 

		loaded: false, 
		
		dataFilter: '',
		
		searchResultKey: '',
		
		searchResultArray: [],
		
		searchResultIndex: 0,
		
		searchServer: false,

		loadFilter: function(data, parent) { 
			var opts = $(this).tree('options');
			if (!opts.searchServer) {
				opts.searchResultKey = "";
				opts.searchResultArray = [];
				opts.searchResultIndex = 0;
			}
			var dataFilter = opts.dataFilter;
			if (!parent) {
				if (dataFilter == "") {
					return data;
				}
				else {
					if (dataFilter.indexOf("^") == -1 && dataFilter.indexOf("*") == -1 && dataFilter.indexOf("|") == -1 && dataFilter.indexOf("+") == -1 
							&& dataFilter.indexOf("?") == -1 && dataFilter.indexOf("$") == -1 && dataFilter.indexOf("(") == -1 && dataFilter.indexOf(")") == -1 
							&& dataFilter.indexOf("{") == -1 && dataFilter.indexOf("}") == -1 && dataFilter.indexOf("[") == -1 && dataFilter.indexOf("]") == -1 
							&& dataFilter.indexOf(".") == -1) {
						dataFilter = "^" + dataFilter;
					}
					var regExp = new RegExp(dataFilter); 
					getDataFilterData(data, regExp);
					return data;
				}
			}
			else { 
				return data;
			}
		}
	
	});

	var methods = $.extend({}, $.fn.tree.methods, {
		
		getDataFilter: function(jq) {
			var opts = jq.tree('options');
			return opts.dataFilter;
		},

		setDataFilter: function(jq, param) {
			var opts = jq.tree('options');
			if (opts.dataFilter != param) {
				opts.dataFilter = param;
				jq.tree('reload');
			}
		},
		
		searchTreeNode: function(jq, param) {
			var searchKey = param.searchKey;
			var onlyLeaf = param.onlyLeaf;
			var opts = jq.tree('options');
			var searchResultKey = opts.searchResultKey;
			var searchResultArray = opts.searchResultArray;
			var searchResultIndex = opts.searchResultIndex;
			var resultNode = null;
			if (searchKey == "") {
				opts.searchResultKey = "";
				opts.searchResultArray = [];
				opts.searchResultIndex = 0;
			}
			else {
				if (searchKey == searchResultKey) {
					if (searchResultArray.length > 0) {
						searchResultIndex = searchResultIndex + 1;
						if (searchResultIndex == searchResultArray.length) {
							searchResultIndex = 0;
						}
						opts.searchResultIndex = searchResultIndex;
						resultNode = searchResultArray[searchResultIndex];
					}
				}
				else {
					var rootNodes = jq.tree('getRoots');
					searchResultArray = [];
					if ("undefined" == typeof onlyLeaf || onlyLeaf == null) {
						onlyLeaf = opts.onlyLeaf;
					}
					searchTreeNode(rootNodes, onlyLeaf, searchKey, searchResultArray);
					opts.searchResultKey = searchKey;
					if (searchResultArray.length > 0) {
						resultNode = searchResultArray[0]; // 新搜索时第一个为返回结果
					}
					opts.searchResultArray = searchResultArray;
					opts.searchResultIndex = 0;
					
				}
			}
			return resultNode;
		},
		
		serverSearchTreeNode: function(jq, param) {
			var searchKey = param.searchKey;
			var url = param.url;
			var opts = jq.tree('options');
			var searchResultKey = opts.searchResultKey;
			var searchResultArray = opts.searchResultArray;
			var searchResultIndex = opts.searchResultIndex;
			var resultNode = null;
			if (searchKey == "") {
				opts.searchResultKey = "";
				opts.searchResultArray = [];
				opts.searchResultIndex = 0;
			}
			else {
				if (searchKey == searchResultKey) {
					if (searchResultArray.length > 0) {
						searchResultIndex = searchResultIndex + 1;
						if (searchResultIndex == searchResultArray.length) {
							searchResultIndex = 0;
						}
						opts.searchResultIndex = searchResultIndex;
						resultNode = searchResultArray[searchResultIndex];
					}
				}
				else {
					searchResultArray = [];
					opts.searchResultKey = searchKey;
					opts.searchResultIndex = 0;
					$.ajax({
						type: "POST",
						async: false,
						url: url,
						dataType: 'json',
						data: "searchKey=" + searchKey,
						success: function(data) {
							if (data) {
								searchResultArray = data;
								if (searchResultArray.length > 0) {
									resultNode = searchResultArray[0]; // 新搜索时第一个为返回结果
								}
							}
							opts.searchResultArray = searchResultArray;
						},
						error: function() {
							error.apply(this, arguments);
						}
					});
				}
			}
			return resultNode;
		}
	
	});
	
	$.extend($.fn.tree.defaults, defaults);
	$.extend($.fn.tree.methods, methods);

})(jQuery);


(function ($) {

	function setValues(target, values) {
		var opts = $.data(target, "combotree").options;
		var tree = $.data(target, "combotree").tree;
		var onlyLeaf = tree.tree('options').onlyLeaf;
		if (tree.tree('options').loaded) { 
			tree.find("span.tree-checkbox").addClass("tree-checkbox0").removeClass(
					"tree-checkbox1 tree-checkbox2");
			var vv = [], ss = [];
			for (var i = 0; i < values.length; i++) {
				var v = values[i];
				if (v != "ROOT") {
					var s = v;
					var node = tree.tree("find", v);
					if (node) {
						if (!onlyLeaf || !node.children) {
							s = node.text;
							tree.tree("check", node.target);
							tree.tree("select", node.target);
							vv.push(v);
							ss.push(s);
							if (!opts.multiple) {
								break;
							}
						}
					}
				}
			}
			$(target).combo("setValues", vv).combo("setText", ss.join(opts.separator));
			target.value = vv; 
		}
		else {
			tree.find("span.tree-checkbox").addClass("tree-checkbox0").removeClass(
					"tree-checkbox1 tree-checkbox2");
			var vv = [], ss = [];
			for (var i = 0; i < values.length; i++) {
				var v = values[i];
				if (v != "ROOT") {
					var s = v;
					var node = tree.tree("find", v);
					if (node) {
						s = node.text;
						tree.tree("check", node.target);
						tree.tree("select", node.target);
					}
					vv.push(v);
					ss.push(s);
				}
			}
			$(target).combo("setValues", vv);
		}
	}
	
	function searchTreeNode(nodes, onlyLeaf, searchKey) {
		var searchResult = false;
		var node = null;
		for (var i = 0; i < nodes.length; i++) {
			node = nodes[i];
			if (node.children) {
				if (!onlyLeaf && node['id'] != "ROOT") {
					var py = node['py'];
					if (py) {
						if (node['id'].indexOf(searchKey) == 0 || node['text'].toUpperCase().indexOf(searchKey) >= 0 || node['py'].indexOf(searchKey) >= 0) {
							searchResult = true;
						}
					}
					else {
						if (node['id'].indexOf(searchKey) == 0 || node['text'].toUpperCase().indexOf(searchKey) >= 0) {
							searchResult = true;
						}
					}
				}
				if (!searchResult) {
					var tempNode = searchTreeNode(node.children, onlyLeaf, searchKey);
					if (tempNode != null) {
						searchResult = true;
						node = tempNode; 
					}
				}
			}
			else {
				var py = node['py'];
				if (py) {
					if (node['id'].indexOf(searchKey) == 0 || node['text'].toUpperCase().indexOf(searchKey) >= 0 || node['py'].indexOf(searchKey) >= 0) {
						searchResult = true;
					}
				}
				else {
					if (node['id'].indexOf(searchKey) == 0 || node['text'].toUpperCase().indexOf(searchKey) >= 0) {
						searchResult = true;
					}
				}
			}
			if (searchResult) {
				return node;
			}
		}
		return null;		
	}

	function doQuery(target, q) {
		var state = $.data(target, 'combotree');
		var opts = state.options;
		var tree = state.tree;
		var rootNode = tree.tree('getRoot');
		if (q != "") {
			var onlyLeaf = tree.tree('options').onlyLeaf;
			var rootNodes = tree.tree('getRoots');
			var searchNode = searchTreeNode(rootNodes, onlyLeaf, q.toUpperCase());
			if (searchNode != null) {
				var locateNode = tree.tree('find', searchNode['id']);
				tree.tree('expandTo', locateNode.target);
				tree.tree('scrollTo', locateNode.target);
				tree.tree('select', locateNode.target);
			}
			else {
				tree.tree('select', null);
				tree.tree('scrollTo', rootNode.target);
			}
		}
		else {
			tree.tree('select', null);
			tree.tree('scrollTo', rootNode.target);
		}
	}
	
	function nav(target, dir) {
		var state = $.data(target, 'combotree');
		var opts = state.options;
		var tree = state.tree;
		var selectNode = tree.tree('getSelected');
		var onlyLeaf = tree.tree('options').onlyLeaf;
		if (selectNode != null) {
			var selectNodeID = selectNode['id'];
			var parentNode = tree.tree('getParent', selectNode.target);
			var cNodes = parentNode.children;
			var isLocate = false;
			if (cNodes.length > 1) {
				if (dir == "next") {
					var j = -1;
					for (var i = 0; i < cNodes.length; i++) {
						if (selectNodeID == cNodes[i].id) {
							isLocate = true;
						}
						else {
							if (isLocate) {
								if (!onlyLeaf || !cNodes[i].children) {
									j = i;
									break;
								}
							}
						}
					}
					if (j != -1) {
						var locateNode = tree.tree("find", cNodes[i]['id']);
						tree.tree('scrollTo', locateNode.target);
						tree.tree('select', locateNode.target);
					}
				}
				else {
					var j = -1;
					for (var i = cNodes.length - 1; i >= 0 ; i--) {
						if (selectNodeID == cNodes[i].id) {
							isLocate = true;
						}
						else {
							if (isLocate) {
								if (!onlyLeaf || !cNodes[i].children) {
									j = i;
									break;
								}
							}
						}
					}
					if (j != -1) {
						var locateNode = tree.tree("find", cNodes[i]['id']);
						tree.tree('scrollTo', locateNode.target);
						tree.tree('select', locateNode.target);
					}
				}
			}
		}
	}
	
	function doEnter(target, e) {
		var state = $.data(target, 'combotree');
		var opts = state.options;
		var tree = state.tree;
		var selectNode = tree.tree('getSelected');
		var onlyLeaf = tree.tree('options').onlyLeaf;
		if (selectNode == null) {
			$(target).combotree("setValues", []);
			$(target).combo("hidePanel");
		}
		else {
			if (!onlyLeaf || (onlyLeaf && !selectNode.children)) {
				$(target).combotree("setValues", [selectNode['id']]);
				$(target).combo("hidePanel");
			}
			else {
				$(target).combotree("setValues", []);
				tree.tree('select', null);
				var rootNode = tree.tree("getRoot");
				tree.tree('scrollTo', rootNode.target);
				$(target).combo("hidePanel");
			}
		}
	}
	
	function getDataFilterData(data, regExp) {
		for (var i = data.length - 1; i >= 0 ; i--) {
			var node = data[i];
			if (!isValidFilterNode(node, regExp)) {
				data.splice(i, 1);
			}
		}
	}

	function isValidFilterNode(node, regExp) {
		var v = node['id'];
		if (node.children) {
			var cNodes = node.children;
			var returnValue = false;
			for (var j = cNodes.length - 1; j >= 0 ; j--) {
				var isValid = isValidFilterNode(cNodes[j], regExp);
				if (isValid) {
					returnValue = true;
				}
				else {
					cNodes.splice(j, 1);
				}
			}
			return returnValue;
		}
		else {
			if (regExp.test(v)) {
				return true;
			}
		}
		return false;
	}

	var defaults = $.extend({}, $.fn.combotree.defaults, {

		keyHandler: {
			up: function(e) {nav(this,'prev');e.preventDefault()},
			down: function(e) {nav(this,'next');e.preventDefault()},
			enter: function(e) {doEnter(this, e)},
			query: function(q,e) {doQuery(this, q)}
		},

		onBeforeSelect: function(node) {
			if (node != null) {
				if (node['id'] == "ROOT") {
					return false;
				}
				var opts = $(this).tree("options");
				if (opts.onlyLeaf && node.children) {
					return false;
				}
				
			}
			return true;
		},
		
		loadFilter: function(data, parent) { 
			var opts = $(this).tree('options');
			var dataFilter = opts.dataFilter;
			opts.loaded = true;
			if (!parent) {
				if (dataFilter == "") {
					return data;
				}
				else {
					if (dataFilter.indexOf("^") == -1 && dataFilter.indexOf("*") == -1 && dataFilter.indexOf("|") == -1 && dataFilter.indexOf("+") == -1 
							&& dataFilter.indexOf("?") == -1 && dataFilter.indexOf("$") == -1 && dataFilter.indexOf("(") == -1 && dataFilter.indexOf(")") == -1 
							&& dataFilter.indexOf("{") == -1 && dataFilter.indexOf("}") == -1 && dataFilter.indexOf("[") == -1 && dataFilter.indexOf("]") == -1 
							&& dataFilter.indexOf(".") == -1) {
						dataFilter = "^" + dataFilter;
					}
					var regExp = new RegExp(dataFilter); 
					getDataFilterData(data, regExp);
					return data;
				}
			}
			else { 
				return data;
			}
		},
		
		onShowPanel: function() {
			var opts = $(this).combotree('options');
			if (!opts.multiple) { 
				var tree = $(this).combotree('tree');
				var selectNode = tree.tree('getSelected');
				if (selectNode != null) {
					tree.tree('expandTo', selectNode.target);
					tree.tree('scrollTo', selectNode.target);
				}
			}
		},

		onHidePanel: function() { 
			var opts = $(this).combotree('options');
			if (!opts.multiple) { 
				var tree = $(this).combotree('tree');
				var selectNode = tree.tree('getSelected');
				if (selectNode == null) {
					var vv = [];
					var ss = [];
					$(this).combo("setValues", vv).combo("setText", ss.join(opts.separator));
					this.value = ""; 
				}
				else {
					if (selectNode.id != "ROOT") {
						var vv = [selectNode.id];
						var ss = [selectNode.text];
						$(this).combo("setValues", vv).combo("setText", ss.join(opts.separator));
						this.value = selectNode.id; 
					}
					else {
						var vv = [];
						var ss = [];
						$(this).combo("setValues", vv).combo("setText", ss.join(opts.separator));
						this.value = ""; 
					}
				}
			}
			else {
				if ($(this).combo("getText") == "") {
					$(this).combo("setValues", []);
					$(this).combotree('clear');
				}
			}
		}

	});

	var methods = $.extend({}, $.fn.combotree.methods, {
		
		setValue: function(jq, value) { 
			if (value == null || value == "") {
				return jq.each(function() {
					setValues(this, []);
				});
			}
			var opts = jq.combo('options');
			var valueArray = [];
			if (value.indexOf(opts.separator) != -1) {
				valueArray = value.split(opts.separator);
			}
			else {
				valueArray.push(value);
			}
			return jq.each(function() {
				setValues(this, valueArray);
			});
		},
		
		setValues: function(jq, values) { 
			if (values == null) {
				return jq.each(function() {
					setValues(this, []);
				});
			}
			var opts = jq.combo('options');
			var valueArray = [];
			for (var i = 0; i < values.length; i++) {
				var value = values[i];
				if (value != null && value != "") {
					if (value.indexOf(opts.separator) != -1) {
						valueArray = valueArray.concat(value.split(opts.separator));
					}
					else {
						valueArray.push(value);
					}
				}
			}
			return jq.each(function() {
				setValues(this, valueArray);
			});
		}
		
	});
	
	$.extend($.fn.combotree.defaults, defaults);
	$.extend($.fn.combotree.methods, methods);

})(jQuery);


(function ($) {

	var defaults = $.extend({}, $.fn.datagrid.defaults, {
		
		delayCountUrl: null, // 延时统计记录数URL
		
		onLoadSuccess: function(data) {
			var opts = $(this).datagrid('options');
			if (opts.delayCountUrl != null && opts.delayCountUrl != "") {
				if (data.begin == 0) {
					if (data.rows.length > 0) {
						if (data.total == opts.pageSize) { // 满一页延时统计
							var pager = $(this).datagrid('getPager');
							$.ajax({
								url: opts.delayCountUrl,
								type: 'POST',
								data: opts.queryParams
							}).done(function(result) {
								if (result) {
									result = parseReturn(result);
									data.total = result;
									pager.pagination('refresh', {total: result});
								}
							});							
						}
					}
					else {
						data.total = 0;
						var pager = $(this).datagrid('getPager');
						pager.pagination('refresh');
					}
				}
			}
		}

	});

	$.extend($.fn.datagrid.defaults, defaults);

})(jQuery);


// ****************************************************************************************************
// 全局初始化
// ****************************************************************************************************
(function() {
	$.ajaxSetup({
		cache: false, // 禁用cache
		data: {}
	})
	
	// 初始化 datagrid 默认属性
	$.extend($.fn.datagrid.defaults, {
		striped: true,
		pageSize: 20,
		pageList: [10,20,40,80],
		pagination: true,
		fitColumns: true,
		singleSelect: true,
		selectOnCheck: false,
		checkOnSelect: false,
		fit: true,
		resizeHandle: 'right'
	});

	if (!window.console) { // 防止浏览器不支持console报错
		window.console = {};
		var funs = ["profiles", "memory", "_commandLineAPI", "debug", "error", "info", "log", "warn", "dir", "dirxml", "trace", "assert", "count", "markTimeline", "profile", "profileEnd", "time", "timeEnd", "timeStamp", "group", "groupCollapsed", "groupEnd"];
		for (var i = 0; i < funs.length; i++) {
			console[funs[i]] = function() {};
		}
	}
	
	// 全局ajax处理
	$(document).ajaxError(function(event, request, settings, thrownError) { // 请求失败处理
		if (request.status == 418) {
			topMessager.alert(MESSAGER_TITLE, '用户操作超时，请重新登录！', 'error', function() {
				window.top.location.href = contextPath + "/index.jsp";
			});
		} else if (request.status == 308) {
			topMessager.alert(MESSAGER_TITLE, '数据处理中或已保存，请勿重复提交！', 'error');
		} else {
			var result = parseReturn(request.responseText);
			if (!result) {
				// topMessager.alert(MESSAGER_TITLE, request.status+ ' ' + (thrownError ? thrownError : '操作失败'), 'error');
				// google浏览器下弹出0错误
			} else {
				topMessager.alert(MESSAGER_TITLE, request.status+ ' ' + (result.message ? result.message : '操作失败'), 'error');
			}
		}
	});
	
})();

// 顶层页面的 message 封装
var topMessager = {
	show : function(options) {
		window.top.$.messager.show(options);
	},
	alert : function(title, msg, icon, fn) {
		if (!title) title = MESSAGER_TITLE;
		if (!icon) icon = 'error';
		window.top.$.messager.alert(title, msg, icon, fn);
	},
	confirm : function(title, msg, fn) {
		if(!title) title = MESSAGER_TITLE;
		window.top.$.messager.confirm(title, msg, fn);
	},
	prompt : function(title, msg, fn) {
		window.top.$.messager.prompt(title, msg, fn);
	},
	progress : function(optionsOrMethod) {
		window.top.$.messager.progress(optionsOrMethod);
	}
}

// 顶层页面弹出消息窗口
function topMessagerShow(title, msg, timeout) {
	title = title ? title : MESSAGER_TITLE;
	timeout = timeout ? timeout : 1500;
	window.top.$.messager.show({
		title: title,
		msg: msg,
		timeout: timeout
	});
}

// 顶层页面弹出警告窗口
function topMessagerAlert(title, msg, type) {
	title = title ? title : MESSAGER_TITLE;
	type = type ? type : 'error';
	window.top.$.messager.alert(title, msg, type);
}

function getEvent() {
	if (window.event) {
		return window.event;
	}
	func = getEvent.caller;
	while (func != null) {
		var arg0 = func.arguments[0];
		if (arg0) {
			if ((arg0.constructor==Event || arg0.constructor == MouseEvent
                 || arg0.constructor==KeyboardEvent)
                 ||(typeof(arg0)=="object" && arg0.preventDefault
                 && arg0.stopPropagation)) {
				return arg0;
			}
		}
		func=func.caller;
	}
	return null;
 }

 function cancelBubble() {
	var e = getEvent();
	if (window.event) {
		e.cancelBubble=true;
	}
	else if (e.preventDefault) {
		e.stopPropagation();
	}
}
 
// 页面键盘事件
// 禁止后退键（Backspace）页面回退
// input 框中的回车自动换成tab键（IE支持）
$(document).ready(function() {
	$("body").bind("keydown", function(e) { 
		var ev = e || window.event; // 获取event对象
		if (ev.keyCode == 8) {
			var obj = ev.target || ev.srcElement; // 获取事件源
			var t = obj.type || obj.getAttribute('type'); // 获取事件源类型
			if (t == "password" || t == "text" || t == "textarea" || t == "file") {
				var vReadOnly = obj.readOnly; 
				var vDisabled = obj.disabled; 
				vReadOnly = (vReadOnly == undefined) ? false : vReadOnly; 
				vDisabled = (vDisabled == undefined) ? true : vDisabled; 
				if (vReadOnly == true || vDisabled == true) {
					cancelBubble();
					if (window.event) {
						event.returnValue = false;
					}
					return false;
				}
			}
			else {
				cancelBubble();
				if (window.event) {
					event.returnValue = false;
				}
				return false;
			}
		}
	});
	
	$("input").bind("keydown", function(e) {
		try {
			var ev = e || window.event; // 获取event对象
			if (ev.keyCode == 27) { // ESC键
				cancelBubble();
				if (window.event) {
					event.returnValue = false;
				}
				return false;
			}
			if (ev.keyCode == 13) { // 回车键
				var obj = ev.target || ev.srcElement; // 获取事件源
				var t = obj.type || obj.getAttribute('type'); // 获取事件源类型
				if (t == "password" || t == "text" || t == "file") {
					cancelBubble();
					event.keyCode = 9;
				}
			}
		}
		catch (err) {};
	});
	
});

// 动态弹出一个窗口
// isCache     是否缓存页面（默认为false不缓存）
// windowID    窗口的ID（isCache=true，windowID确保在同一个页面中唯一；isCache=false，windowID可以不指定；）
// openURL     打开的URL地址
// paramArray  传入的参数数组（作为打开页面的doInit()方法的参数）
// dataOptions jquery.window中的data-options定义参数
var publicWindowArray = new Array(); // 窗口管理数组
function openWindow(isCache, windowID, openURL, paramArray, dataOptions) {
	if ("undefined" == typeof isCache || isCache == null || isCache == "") {
		isCache = false;
	}
	if ("undefined" == typeof paramArray || paramArray == null) {
		paramArray = [];
	}
	var windowObject;
	if (isCache) { 
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
				onBeforeClose: function() { 
					var curWindowObject = this;
					if (openWindowButtonHasDisabled(curWindowObject)) {
						return false;
					}
					return true;
				},
				onBeforeDestroy: function() { 
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
					var _this = this; 
					var iframeObject = iframe.get(0).contentWindow;
					if (iframeObject.doInit && typeof(iframeObject.doInit) == 'function') { 
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
			onBeforeClose: function() { 
				var curWindowObject = this;
				if (openWindowButtonHasDisabled(curWindowObject)) {
					return false;
				}
				return true;
			},
			onClose: function() {
				$(this).dialog('destroy');
			},
			onBeforeDestroy: function() { 
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
			var _this = this; 
			var iframeObject = iframe.get(0).contentWindow;
			if (iframeObject.doInit && typeof(iframeObject.doInit) == 'function') { 
				iframeObject.doInit(paramArray);
			}	
		}
		iframe.bind('load', frameOnLoad);
		openURL = openURL + (openURL.indexOf('?') != -1 ? '&' : '?') + "time="+(new Date()).getTime();
		iframe.attr('src', openURL);
		windowObject.dialog('open');
	}
}

// 是否存在被屏蔽的按钮，如正在保存中不能关闭窗口
function openWindowButtonHasDisabled(curWindowObject) {
	var obj = $(curWindowObject);
	var divButton = $(obj).find('div.dialog-button');
	if (divButton) {
		var btnArray = divButton.find('a.l-btn');
		if (btnArray) {
			for (i = 0; i < btnArray.length; i++) {
				if ($(btnArray[i]).data('disabled')) {
					return true;
				}
			}
		}
	}
	return false;
}

// 动态弹出一个窗口（带有保存与退出按钮）
// isCache         是否缓存页面（默认为false不缓存）
// windowID        窗口的ID（isCache=true，windowID确保在同一个页面中唯一；isCache=false，windowID可以不指定；）
// parentWindow    调用页面的window对象
// paramArray      传入打开页面的参数（如：{document:document, param1: 'test1', param2: 2}）
// dataOptions     jquery.window中的data-options定义参数
// submitConfirm   提交确认提示信息（为空则不出现确认框）
// onSubmitSuccess 对话中提交成功后执行的方法点击确认后执行原页面中的方法（如：“xzqh_onSubmitSuccess”，该方法的参数会传入弹出页面所有已提交的数据）
// oldPageObject   执行onSubmitSuccess方法时返回原页面的参数（如：原页面的某个动态对象{oldObject:this}）
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
	dataOptions.maximizable = dataOptions.maximizable ? dataOptions.maximizable : false; // 是否最大化图标
	dataOptions.closable = true;
	dataOptions.closed = false;   
	dataOptions.cache = false;
	dataOptions.inline = false;
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
						if (iframeObject.beforeSubmit && typeof(iframeObject.beforeSubmit) == 'function') { // 执行自定义方法beforeSubmit
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

// 设置一个form内所有的对象是否只读（只读时取消所有的验证）
// formID     表单ID
// isReadonly 是否只读
function formReadonly(formID, isReadonly) {
	if (isReadonly) {
		$('#'+ formID).form('disableValidation');
		$('#'+ formID +' input:not(:button,:hidden)').prop('readonly', isReadonly).addClass("inputReadonly");
		$('#'+ formID +' input').next(".combo").addClass("inputReadonly");
		$('#'+ formID +' input:button').prop('disabled', isReadonly);
		$('#'+ formID +' input:reset').prop('disabled', isReadonly);
		$('#'+ formID +' input:submit').prop('disabled', isReadonly);
		$('#'+ formID +' input:checkbox').prop('disabled', isReadonly).addClass("inputReadonly");
		$('#'+ formID +' input:radio').prop('disabled', isReadonly).addClass("inputReadonly");
		$('#'+ formID +' input:file').prop('disabled', isReadonly).addClass("inputReadonly");
		$('#'+ formID +' textarea').prop('readonly', isReadonly).addClass("inputReadonly");
	}
	else {
		$('#'+ formID +' input:not(:button,:hidden)').prop('readonly', isReadonly).removeClass("inputReadonly");
		$('#'+ formID +' input').next(".combo").removeClass("inputReadonly");
		$('#'+ formID +' input:button').prop('disabled', isReadonly);
		$('#'+ formID +' input:reset').prop('disabled', isReadonly);
		$('#'+ formID +' input:submit').prop('disabled', isReadonly);
		$('#'+ formID +' input:checkbox').prop('disabled', isReadonly).removeClass("inputReadonly");
		$('#'+ formID +' input:radio').prop('disabled', isReadonly).removeClass("inputReadonly");
		$('#'+ formID +' input:file').prop('disabled', isReadonly).removeClass("inputReadonly");
		$('#'+ formID +' textarea').prop('readonly', isReadonly).removeClass("inputReadonly");
		$('#'+ formID).form('enableValidation');
		$('#'+ formID).form('validate');
	}
}

// 设置一个输入框是否只读（只读时取消验证）
// inputID    输入框对象ID
// isReadonly 是否只读
function setInputReadonly(inputID, isReadonly) {
	if (isReadonly) {
		$('#'+ inputID).validatebox({novalidate:true});
		$('#'+ inputID).prop('readonly', isReadonly).addClass("inputReadonly");
		$('#'+ inputID).next(".combo").addClass("inputReadonly");
		var comboText = $('#'+ inputID).next(".combo").children(".combo-text");
		comboText.validatebox({novalidate:true});
		comboText.prop('readonly', isReadonly).addClass("inputReadonly");
	}
	else {
		$('#'+ inputID).prop('readonly', isReadonly).removeClass("inputReadonly");
		$('#'+ inputID).validatebox({novalidate:false});
		$('#'+ inputID).next(".combo").removeClass("inputReadonly");
		var comboText = $('#'+ inputID).next(".combo").children(".combo-text");
		comboText.validatebox({novalidate:false});
		comboText.prop('readonly', isReadonly).removeClass("inputReadonly");
		comboText.validatebox("validate");
	}
}

// 设置一个输入框是否 disabled（disabled时取消验证）
// inputID    输入框对象ID
// isDisabled 是否 disable
function setInputDisabled(inputID, isDisabled) {
	var obj = $('#'+ inputID)[0];
	var objType = obj.type || obj.getAttribute('type');
	if (objType == "button") {
		$('#'+ inputID).prop('disabled', isDisabled);
		var className = $('#'+ inputID).attr('class');
		if (className == 'butSelect2' || className == 'butSelect2Disabled') {
			if (isDisabled) {
				$('#'+ inputID).attr('class', 'butSelect2Disabled');
			}
			else {
				$('#'+ inputID).attr('class', 'butSelect2');
			}
		}
	}
	else {
		if (isDisabled) {
			$('#'+ inputID).validatebox({novalidate:true});
			$('#'+ inputID).prop('disabled', isDisabled).addClass("inputReadonly");
			$('#'+ inputID).next(".combo").addClass("inputReadonly");
			var comboText = $('#'+ inputID).next(".combo").children(".combo-text");
			comboText.validatebox({novalidate:true});
			comboText.prop('disabled', isDisabled).addClass("inputReadonly");
		}
		else {
			$('#'+ inputID).prop('disabled', isDisabled).removeClass("inputReadonly");
			$('#'+ inputID).validatebox({novalidate:false});
			$('#'+ inputID).next(".combo").removeClass("inputReadonly");
			var comboText = $('#'+ inputID).next(".combo").children(".combo-text");
			comboText.validatebox({novalidate:false});
			comboText.prop('disabled', isDisabled).removeClass("inputReadonly");
			comboText.validatebox("validate");
		}
	}
}

// 设置一个combo是否 必填
// inputID    combo对象ID
// isRequired 是否 disable
function setComboRequired(comboID, isRequired) {
	var comboText = $("#" + comboID).next(".combo").children(".combo-text");
	if (comboText.length) {
		comboText.validatebox({required:isRequired});
	}
}


// 取得 form 对象中的所有可提交的数据
// formObject form的DOM对象
function getFormData(formObject) {
	var formData = {};
	var elements = formObject.elements;
	for (var i = 0; i < elements.length; i++) {
		var element = elements[i];
		var tagName = element.tagName;
		if (tagName == "INPUT") {
			var _type = element.type;
			var _name = element.name;
			if (_name) {
				if (_type == "text" || _type == "hidden" || _type == "file" || _type == "password") {
					if (formData[_name] == null) { // 多个name时值用逗号分隔（多选字典）
						formData[_name] = element.value;
					}
					else {
						formData[_name] += "," + element.value;
					}					
				}
				else if (_type == "radio") {
					if (formData[_name] == null) {
						formData[_name] = "";
					}
					if (element.checked) {
						formData[_name] = element.value;
					}
				}
				else if (_type == "checkbox") {
					if (formData[_name] == null) {
						formData[_name] = "";
					}
					if (element.checked) {
						if (formData[_name] == "") {
							formData[_name] = element.value;
						}
						else {
							formData[_name] += "," + element.value;
						}
					}
				} 
			}
		}
		else if (tagName == "TEXTAREA") {
			var _name = element.name;
			if (_name) {
				formData[_name] = element.value;
			}
		}
	}
	return formData;
}

// 取得 form 对象中的所有可提交的数据
// formID form的ID
function getFormDataByID(formID) {
	var formObject = $('#' + formID)[0];
	return getFormData(formObject);
}

// 在提交按钮与页面分离的对话框中，提交之前强行校验下拉框的是否关闭
// iframeObject  iframe的对象
// formObject    form的对象
function checkComboIsClosed(iframeObject, formObject) {
	try {
		var comboSet = formObject.find('input.combo-f');
		if (comboSet && comboSet.length) {
			for (var i = 0; i < comboSet.length; i ++) {
				var comboId = comboSet[i].id;
				if (comboId) {
					var panelObject = iframeObject.$("#" + comboId).combo("panel");
					if (panelObject) {
						var opts = panelObject.panel("options");
						if (!opts.closed) {
							panelObject.panel("close");
							break;
						}
					}
				}
			}
		}
	}
	catch (err) {}
}

// datagrid中的字典翻译，用法（formatter:dictFormatter,dictName:contextPath+'/common/dict/D_DZ_JLXLX.js'）
var dictFormatter = function(value, row, index) {
	var opts = $(this);
	if (opts[0].dictName) {
		try {
			value = window.top.getDictName(opts[0].dictName, value);
		}
		catch (err) {}
	}
	return value;
}

// datagrid中的编辑与删除操作生成，用法（formatter: datagridProcessFormater）
var datagridProcessFormater = function(val, row, index) {
	return '&nbsp;<a class="link" href="javascript:javascript:void(0)" onclick="doEdit(this, '+index+')">编辑</a>&nbsp;'+
		   '&nbsp;<a class="link" href="javascript:javascript:void(0)" onclick="doDelete(this, '+index+')">删除</a>&nbsp;';
}

// datagrid中的每页记录数，自动适应屏幕分辨率
// offsetHeight 表格数据内容显示区以外的高度
function getAutoPageSize(offsetHeight) {
	var pageSize = 0;
	if ("undefined" == typeof offsetHeight || offsetHeight == null || offsetHeight == "") {
		offsetHeight = 130;
	}
	var dataRowHeight = document.body.clientHeight - offsetHeight;
	if (dataRowHeight > 0) {
		pageSize = Math.floor(dataRowHeight / 25);
	}  	
	if (pageSize < 5) {
		pageSize = 5;
	}
	return pageSize;
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

// 按钮屏蔽并且改变显示
function buttonDisabled(buttonObject) {
	var obj = $(buttonObject);
	if (obj.hasClass('l-btn')) {
		if (obj.data('disabled')) {
			return false;
		}
		obj.data('disabled', true);
		obj.find('span.l-btn-text').html(obj.find('span.l-btn-text').html() + '中...');
		obj.css('cursor', 'wait');
	}
	return true;
}

// 按钮解除屏蔽
function buttonEnabled(buttonObject) {
	var obj = $(buttonObject);
	obj.data('disabled', false);
	obj.find('span.l-btn-text').html(obj.find('span.l-btn-text').html().slice(0, -4));
	obj.css('cursor', 'Pointer');
}

// 是否存在被屏蔽的按钮，如正在提交中不能关闭窗口
function buttonHasDisabled(buttonObject) {
	var obj = $(buttonObject);
	var btnArray = obj.parent().find('a.l-btn');
	if (btnArray) {
		for (i = 0; i < btnArray.length; i++) {
			if ($(btnArray[i]).data('disabled')) {
				return true;
			}
		}
	}
	return false;
}

// datagrid 查询条件（默认在本页面中弹出，而且缓存页面）
// paramArray  传入打开页面的参数（如：{document:document, param1: 'test1', param2: 2}）
// dataOptions jquery.window中的data-options定义参数
function datagridQuery(toolbarButton, windowID, paramArray, dataOptions) {
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
	dataOptions.maximizable = dataOptions.maximizable ? dataOptions.maximizable : false; // 是否最大化图标
	dataOptions.closable = true;
	dataOptions.closed = false;   
	dataOptions.cache = false;
	dataOptions.inline = false;
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
						if (iframeObject.beforeSubmit && typeof(iframeObject.beforeSubmit) == 'function') { // 执行自定义方法beforeSubmit
							if (iframeObject.beforeSubmit() == false) {
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

// datagrid 新增（默认在本页面中弹出，不缓存页面）
// paramArray    传入打开页面的参数（如：{document:document, param1: 'test1', param2: 2}）
// dataOptions   jquery.window中的data-options定义参数
// submitConfirm 提交确认提示信息（为空则不出现确认框）
function datagridAdd(toolbarButton, windowID, paramArray, dataOptions, submitConfirm) {
	if (!dataOptions.url) {
		topMessagerAlert('', '弹出层缺少 url 参数！');
		return;
	}
	var datagrid_ID = getDatagrid_ID(1, toolbarButton);
	dataOptions.datagrid_ID = datagrid_ID;
	if (!dataOptions.title) {
		dataOptions.title = '新增';
	}
	dataOptions.title = '&nbsp;' + dataOptions.title;
	if (!dataOptions.width) {
		dataOptions.width = 850;
	}
	if (!dataOptions.height) {
		dataOptions.height = 420;
	}
	if (!windowID) {
		windowID = "addWindow";
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
											result = parseReturn(result);
											if (result.status == 'success') { // 返回成功后执行的方法
												if (iframeObject.afterSubmit && typeof(iframeObject.afterSubmit) == 'function') { // 执行自定义方法afterSubmit
													iframeObject.afterSubmit(result);
												}
											}
											if (iframeObject.successCloseWindow && typeof(iframeObject.successCloseWindow) == 'function') { // 执行自定义方法successCloseWindow，返回true关闭窗口
												if (!iframeObject.successCloseWindow(result)) {
													doSubmitResult(result, null, datagrid_ID);
													return;
												}
											}
											doSubmitResult(result, windowID, datagrid_ID);
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
									result = parseReturn(result);
									if (result.status == 'success') { // 返回成功后执行的方法
										if (iframeObject.afterSubmit && typeof(iframeObject.afterSubmit) == 'function') { // 执行自定义方法afterSubmit
											iframeObject.afterSubmit(result);
										}
									}
									if (iframeObject.successCloseWindow && typeof(iframeObject.successCloseWindow) == 'function') { // 执行自定义方法successCloseWindow，返回true关闭窗口
										if (!iframeObject.successCloseWindow(result)) {
											doSubmitResult(result, null, datagrid_ID);
											return;
										}
									}
									doSubmitResult(result, windowID, datagrid_ID);
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
	openWindow(false, windowID, dataOptions.url, paramArray, dataOptions);
}

// datagrid 批量删除
// submitFields  除主键之外需要提交的字段（多个用逗号分隔）
// dataOptions   jquery.window中的data-options定义参数
// submitConfirm 提交确认提示信息（为空则不出现确认框）
function datagridDeletePatch(toolbarButton, windowID, submitFields, dataOptions, submitConfirm) {
	if (!dataOptions.url) {
		topMessagerAlert('', '弹出层缺少 url 参数！');
		return;
	}
	var datagrid_ID = getDatagrid_ID(1, toolbarButton);
	dataOptions.datagrid_ID = datagrid_ID;
	var selections = $('#' + datagrid_ID).datagrid('getSelections');
	if (selections.length == 0) {
		topMessagerAlert('', '请先选择需要处理的数据！');
		return;
	}
	if (!submitConfirm) {
		submitConfirm = '您确认要删除数据吗？';
	}
	topMessager.confirm('', submitConfirm, function(r) {    
		if (r) {	
			var opts = $('#' + datagrid_ID).datagrid("options");
			var postFieldArray = [];
			postFieldArray.push(opts.idField);
			if ("undefined" == typeof submitFields || submitFields == null) {
				submitFields = "";
			}
			if (submitFields != "") {
				postFieldArray = postFieldArray.concat(submitFields.split(","));
			}
			var postData = [];
			for (var i = 0; i < selections.length; i++) {
				var dataArray = {};
				for (var j = 0; j < postFieldArray.length; j++) {
					var postField = postFieldArray[j];
					if (selections[i][postField]) {
						dataArray[postField] = selections[i][postField];
					}
				}
				postData.push(dataArray);
			}
			$.ajax({
				url: dataOptions.url,
				type: 'POST',
				dataType: "json",
				contentType: "application/json",
				data: JSON.stringify(postData)
			}).done(function(result) {
				result = parseReturn(result);
				doSubmitResult(result, null, datagrid_ID);
			});
		}
	});
}

// datagrid 修改（默认在本页面中弹出，不缓存页面）
// paramArray    可以传入参数（如：{document:document, param1: 'test1', param2: 2}）
// dataOptions   jquery.window中的data-options定义参数
// submitConfirm 提交确认提示信息（为空则不出现确认框）
function datagridEdit(datagrid_ID, windowID, paramArray, dataOptions, submitConfirm) {
	if (!dataOptions.url) {
		topMessagerAlert('', '弹出层缺少 url 参数！');
		return;
	}
	dataOptions.datagrid_ID = datagrid_ID;
	if (!dataOptions.title) {
		dataOptions.title = '修改';
	}
	dataOptions.title = '&nbsp;' + dataOptions.title;
	if (!dataOptions.width) {
		dataOptions.width = 850;
	}
	if (!dataOptions.height) {
		dataOptions.height = 420;
	}
	if (!windowID) {
		windowID = "editWindow";
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
						if (iframeObject.beforeSubmit && typeof(iframeObject.beforeSubmit) == 'function') { // 执行自定义方法beforeSubmit
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
											result = parseReturn(result);
											if (result.status == 'success') { // 返回成功后执行的方法
												if (iframeObject.afterSubmit && typeof(iframeObject.afterSubmit) == 'function') { // 执行自定义方法afterSubmit
													iframeObject.afterSubmit(result);
												}
											}
											if (iframeObject.successCloseWindow && typeof(iframeObject.successCloseWindow) == 'function') { // 执行自定义方法successCloseWindow，返回true关闭窗口
												if (!iframeObject.successCloseWindow(result)) {
													doSubmitResult(result, null, datagrid_ID);
													return;
												}
											}
											doSubmitResult(result, windowID, datagrid_ID);
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
									result = parseReturn(result);
									if (result.status == 'success') { // 返回成功后执行的方法
										if (iframeObject.afterSubmit && typeof(iframeObject.afterSubmit) == 'function') { // 执行自定义方法afterSubmit
											iframeObject.afterSubmit(result);
										}
									}
									if (iframeObject.successCloseWindow && typeof(iframeObject.successCloseWindow) == 'function') { // 执行自定义方法successCloseWindow，返回true关闭窗口
										if (!iframeObject.successCloseWindow(result)) {
											doSubmitResult(result, null, datagrid_ID);
											return;
										}
									}
									doSubmitResult(result, windowID, datagrid_ID);
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
	openWindow(false, windowID, dataOptions.url, paramArray, dataOptions);
}

// datagrid 查看（默认在本页面中弹出，不缓存页面）
// paramArray    可以传入参数（如：{document:document, param1: 'test1', param2: 2}）
// dataOptions   jquery.window中的data-options定义参数
function datagridView(datagrid_ID, windowID, paramArray, dataOptions) {
	if (!dataOptions.url) {
		topMessagerAlert('', '弹出层缺少 url 参数！');
		return;
	}
	dataOptions.datagrid_ID = datagrid_ID;
	if (!dataOptions.title) {
		dataOptions.title = '查看';
	}
	dataOptions.title = '&nbsp;' + dataOptions.title;
	if (!dataOptions.width) {
		dataOptions.width = 850;
	}
	if (!dataOptions.height) {
		dataOptions.height = 420;
	}
	if (!windowID) {
		windowID = "editWindow";
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
	openWindow(false, windowID, dataOptions.url, paramArray, dataOptions);
}

// 字符串解析为json对象
function parseReturn(result) {
	if (typeof result == 'string') {
		try {
			result = $.parseJSON(result);
		} catch(err) {
			return false;
		}
	}
	return result;
}

// 处理弹出层form表单提交后，服务端返回的数据
function doSubmitResult(result, windowID, datagrid_ID) {
	if ("string" == typeof result) {
		result = parseReturn(result);
	}
	if (!result) {
		return false;
	}
	if (result.callback) {
		eval(result.callback+'()');
	}
	if (result.status == 'success') {
		topMessager.show({
			title: MESSAGER_TITLE,
			msg: result.message,
			timeout:1500
		});
		if (windowID) {
			$('#' + windowID).dialog('close'); // 关闭弹出窗口
		}
		if (datagrid_ID) {
			$('#' + datagrid_ID).datagrid('reload'); // 表格
		}
	} 
	else {
		topMessager.alert(MESSAGER_TITLE, result.message, 'error');
	}
}

// 打印出一个对象的属性与方法，在页面上输出
function printObject(object) { 
	$("body").append("<br><br><br><br><br><hr>");
	for (el in object) {
		$("body").append(el + " : " + object[el] + "<br><br>");
	}
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
function openWindowWithSaveNext(isCache, windowID, parentWindow, paramArray, dataOptions, submitConfirm, onSubmitSuccess, oldPageObject) {
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
			text: '保存/新增',
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
						if (iframeObject.beforeSubmit && typeof(iframeObject.beforeSubmit) == 'function') { // 执行自定义方法beforeSubmit
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
														doSubmitResult(result, null, null);
														isDoSubmitResult = false;
														var parentWinObject = parentWindow;
														if (parentWinObject.contentWindow) {
															parentWinObject = parentWinObject.contentWindow;
														}
														eval("parentWinObject." + onSubmitSuccess + "(oldPageObject, submitData)");
														iframeObject.resetForm();
													}
													catch (err) {
														$.messager.alert('页面错误', "执行事件 "+ onSubmitSuccess + " 有错误发生：<br/><br/>错误名称: " + err.name + "<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;错误行号:" + (err.number & 0xFFFF ) + "<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;错误信息:" + err.message, 'error'); 
													}
												}
											}
											if (isDoSubmitResult) {
												doSubmitResult(result, null, null);
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
												doSubmitResult(result, null, null);
												isDoSubmitResult = false;
												var parentWinObject = parentWindow;
												if (parentWinObject.contentWindow) {
													parentWinObject = parentWinObject.contentWindow;
												}
												eval("parentWinObject." + onSubmitSuccess + "(oldPageObject, submitData)");
												iframeObject.resetForm();
											}
											catch (err) {
												$.messager.alert('页面错误', "执行事件 "+ onSubmitSuccess + " 有错误发生：<br/><br/>错误名称: " + err.name + "<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;错误行号:" + (err.number & 0xFFFF ) + "<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;错误信息:" + err.message, 'error'); 
											}
										}
									}
									if (isDoSubmitResult) {
										doSubmitResult(result, null, null);
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

// 隐藏对话框窗口中的保存按钮
function dialogSaveButtonHide(windowID) {
	var dialogButtonDiv = window.parent.$('#' + windowID).find('div.dialog-button');
	if (dialogButtonDiv.length > 0) {
		var saveSpan = dialogButtonDiv.find('span.icon-save');
		if (saveSpan.length > 0) {
			var saveButton = saveSpan.parents("a");
			saveButton.hide();
		}
	}
}

// 隐藏对话框窗口中的重置按钮
function dialogResetButtonHide(windowID) {
	var dialogButtonDiv = window.parent.$('#' + windowID).find('div.dialog-button');
	if (dialogButtonDiv.length > 0) {
		var resetSpan = dialogButtonDiv.find('span.icon-reset');
		if (resetSpan.length > 0) {
			var resetButton = resetSpan.parents("a");
			resetButton.hide();
		}
	}
}

// 设置对话框按钮已以加载完成（避免刷新页面又增加一次按钮）
function dialogButtonIsLoad(windowID) {
	var dialogButtonDiv = window.parent.$('#' + windowID).find('div.dialog-button');
	if (dialogButtonDiv.length > 0) {
		dialogButtonDiv.attr('isLoad', '1');
	}
}

// 增加对话框窗口中按钮
function dialogButtonAdd(windowID, buttomHTML, datagrid_ID) {
	var dialogButtonDiv = window.parent.$('#' + windowID).find('div.dialog-button');
	if (dialogButtonDiv.length > 0) {
		var isLoad = dialogButtonDiv.attr('isLoad');
		if (isLoad == "1") {
			return;
		}
		if ("undefined" == typeof windowID || windowID == null) {
			windowID = "";
		}
		if ("undefined" == typeof datagrid_ID || datagrid_ID == null) {
			datagrid_ID = "";
		}
		buttomHTML = buttomHTML.replace(/(windowID)/g, "'" + windowID + "'");
		buttomHTML = buttomHTML.replace(/(datagrid_ID)/g, "'" + datagrid_ID + "'");
		dialogButtonDiv.prepend(buttomHTML);
	}
}

// 增加对话框窗口中删除按钮
function dialogRemoveButtonAdd(windowID, submitURL) {
	var dialogButtonDiv = window.parent.$('#' + windowID).find('div.dialog-button');
	if (dialogButtonDiv.length > 0) {
		var isLoad = dialogButtonDiv.attr('isLoad');
		if (isLoad == "1") {
			return;
		}
		var sb = new StringBuffer();
		sb.append('<a id="removeButton" class="l-btn l-btn-small" href="javascript:void(0)" group="" onclick="removeButtonExecute(\''+ submitURL + '\',\''+ windowID +'\',\''+ datagrid_ID +'\')">');
		sb.append('<span class="l-btn-left l-btn-icon-left">');
		sb.append('<span class="l-btn-text" nowrap="nowrap">删除</span>');
		sb.append('<span class="l-btn-icon icon-remove"></span>');
		sb.append('</span>');
		sb.append('</a>');
		dialogButtonDiv.prepend(sb.toString());
	}
}

// 执行对话框窗口中删除方法
function removeButtonExecute(submitURL, windowID, datagrid_ID) {
	var iframeObject = window.frames[windowID + '_iframe'];
	if (iframeObject.contentWindow) {
		iframeObject = iframeObject.contentWindow;
	}
	var formObject = iframeObject.$('form').first();
	if (formObject) {
		topMessager.confirm('', '您确认要删除数据吗？<br><br>提示：当前的流程也将被中止！', function(r) {
			if (r) {
				formObject.form('submit',{
					url: submitURL,
					dataType : 'json',
					onSubmit: function() {
					},
					success: function(result) {
						result = parseReturn(result);
						if (result.status == 'success') { // 返回成功后执行的方法
							doSubmitResult(result, windowID, datagrid_ID);
						}
					}
				});
			}
		});
	}
}

// 增加对话框窗口中查看流程按钮
function dialogViewProcessButtonAdd(windowID, processid) {
	var dialogButtonDiv = window.parent.$('#' + windowID).find('div.dialog-button');
	if (dialogButtonDiv.length > 0) {
		var isLoad = dialogButtonDiv.attr('isLoad');
		if (isLoad == "1") {
			return;
		}
		var sb = new StringBuffer();
		sb.append('<a id="viewProcessButton" class="l-btn l-btn-small" href="javascript:void(0)" group="" onclick="viewProcessExecute('+ processid +')">');
		sb.append('<span class="l-btn-left l-btn-icon-left">');
		sb.append('<span class="l-btn-text" nowrap="nowrap">查看流程</span>');
		sb.append('<span class="l-btn-icon icon-query"></span>');
		sb.append('</span>');
		sb.append('</a>');
		dialogButtonDiv.prepend(sb.toString());
	}
}

// 执行对话框窗口中查看流程方法
function viewProcessExecute(processid) {
	var dataOptions = {};
	dataOptions.url = contextPath + '/workFlowEngine/processView?processid=' + processid;
	dataOptions.title = '';
	var documentSize = getDocumentSize(); // 全屏显示
	dataOptions.width = documentSize.width;
	dataOptions.height = documentSize.height;
	var windowID = "processViewWindow";
	dataOptions.collapsible = dataOptions.collapsible ? dataOptions.collapsible : false;
	dataOptions.minimizable = dataOptions.minimizable ? dataOptions.minimizable : false;
	dataOptions.maximizable = dataOptions.maximizable ? dataOptions.maximizable : false; // 是否最大化图标
	dataOptions.closable = false;
	dataOptions.closed = false;   
	dataOptions.cache = false;
	dataOptions.inline = false;
	dataOptions.modal = true;
	dataOptions.noheader = true;
	dataOptions.border = false;
	dataOptions.style = {borderWidth: 0};
	dataOptions.buttons = [
		{
			text: '关闭',
			iconCls: 'icon-cancel',
			handler: function() {
				$('#' + windowID).dialog('close');
			}
		}
	];
	openWindow(false, windowID, dataOptions.url, null, dataOptions);
	
	$(window).resize(function() {
		var winObject = $('#' + windowID);
		if (winObject.length > 0) {
			var documentSize = getDocumentSize(); // 全屏显示
			winObject.panel('resize',{
				width: documentSize.width,
				height: documentSize.height
			});
		}
	});
	
}

/*
 * 子项信息保存后 重置表单.readonly除外
 */
function resetForm() {
	var table = $("#dataForm").find("table");
	$(table).find("input[readonly!='readonly'][type!='button']").each(function(i) {
		$(this).val("");
	});
	$(table).find("textarea[readonly!='readonly']").each(function(i) {
		$(this).val("");
	});
}

/**
 * 这个方法是在载入列表的时候，延迟加载统计。
 * @param data
 * @param tableId
 */
function beforeTableLoad(data,tableId){
	var opts = $('#'+tableId).datagrid('options');
	if (opts.delayCountUrl != null && opts.delayCountUrl != "") {
		if (data.begin == 0) {
			if (data.rows.length > 0) {
				if (data.total == opts.pageSize) { // 满一页延时统计
					var pager = $('#'+tableId).datagrid('getPager');
					$.ajax({
						url: opts.delayCountUrl,
						type: 'POST',
						data: opts.queryParams
					}).done(function(result) {
						if (result) {
							result = parseReturn(result);
							data.total = result;
							pager.pagination('refresh', {total: result});
						}
					});							
				}
			}
			else {
				data.total = 0;
				var pager = $('#'+tableId).datagrid('getPager');
				pager.pagination('refresh');
			}
		}
	}
}
