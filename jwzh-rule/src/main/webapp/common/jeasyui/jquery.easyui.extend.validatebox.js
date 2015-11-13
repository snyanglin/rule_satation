/**
* jQuery EasyUI 1.3.6
* Copyright (c) 2009-2014 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL or commercial licenses
* To use it on other terms please contact author: info@jeasyui.com
* http://www.gnu.org/licenses/gpl.txt
* http://www.jeasyui.com/license_commercial.php
* 
* jQuery EasyUI 1.3.6 validatebox 组件扩展 
* author : redstorm 
* 2014-05-01
* 
* 依赖文件：
*   1、tools.js
* 
*/

(function ($) {

	var rules = {

		noMark: {
			validator: function (value) {
				return /^[^"^;^']*$/.test(value);
			},
			message: '不能包含有单引号、双引号、分号'
		},

		naturalNumber: {
			validator: function (value) {
				return /^[+]?[1-9]+\d*$/i.test(value);
			},
			message: "请输入正确的自然数格式"
		},

		naturalNumberRange: {
			validator: function (value, param) {
				var returnValue = false;
				if (/^[+]?[1-9]+\d*$/i.test(value)) {
					returnValue = true;
					value = parseInt(value);
					if (param[0] != undefined) {
						returnValue = (value >= param[0]);
					}
					if (param[1] != undefined) {
						returnValue = returnValue && (value <= param[1]);
					}
				}
				return returnValue;
			},
			message: "请输入正确的自然数格式且值在 {0} 与 {1} 之间"
		},

		numeric: {
			validator: function (value, param) {
				if (param) {
					var returnValue = false;
					switch (param[0]) {
						case "+": // 正数
							returnValue = /(^\+?|^\d?)\d*\.?\d+$/.test(value);
							break;
						case "-": // 负数
							returnValue = /^-\d*\.?\d+$/.test(value);
							break;
						case "i": // 整数
							returnValue = /(^-?|^\+?|\d)\d+$/.test(value);
							break;
						case "+i": // 正整数
							returnValue = /(^\d+$)|(^\+?\d+$)/.test(value);
							break;
						case "-i": // 负整数
							returnValue = /^[-]\d+$/.test(value);
							break;
						case "f": // 浮点数
							returnValue = /(^-?|^\+?|^\d?)\d*\.?\d+$/.test(value);
							if (param[1] != undefined) {
								var dotPos = value.indexOf(".");
								if (dotPos != -1 && value.length - dotPos - 1 > param[1]) {
									returnValue = false;
								}
							}
							break;
						case "+f": // 正浮点数
							returnValue = /(^\+?|^\d?)\d*\.?\d+$/.test(value);
							if (param[1] != undefined) {
								var dotPos = value.indexOf(".");
								if (dotPos != -1 && value.length - dotPos - 1 > param[1]) {
									returnValue = false;
								}
							}
							break;
						case "-f": // 负浮点数
							returnValue = /^[-]\d*\.?\d$/.test(value);
							if (param[1] != undefined) {
								var dotPos = value.indexOf(".");
								if (dotPos != -1 && value.length - dotPos - 1 > param[1]) {
									returnValue = false;
								}
							}
							break;
						default: // 缺省为整数
							returnValue = /(^-?|^\+?|\d)\d+$/.test(value);
							break;
					}
					return returnValue;
				}
				else {
					return /(^-?|^\+?|\d)\d+$/.test(value);
				}
			},
			message: "请输入指定类型的数字格式"
		},

		numericRange: {
			validator: function (value, param) {
				if (param) {
					var returnValue = false;
					switch (param[0]) {
						case "+": // 正数
							returnValue = /(^\+?|^\d?)\d*\.?\d+$/.test(value);
							break;
						case "-": // 负数
							returnValue = /^-\d*\.?\d+$/.test(value);
							break;
						case "i": // 整数
							returnValue = /(^-?|^\+?|\d)\d+$/.test(value);
							break;
						case "+i": // 正整数
							returnValue = /(^\d+$)|(^\+?\d+$)/.test(value);
							break;
						case "-i": // 负整数
							returnValue = /^[-]\d+$/.test(value);
							break;
						case "f": // 浮点数
							returnValue = /(^-?|^\+?|^\d?)\d*\.?\d+$/.test(value);
							if (returnValue) {
								if (param[1] != undefined) {
									var dotPos = value.indexOf(".");
									if (dotPos != -1 && value.length - dotPos - 1 > param[1]) {
										returnValue = false;
									}
								}
							}
							break;
						case "+f": // 正浮点数
							returnValue = /(^\+?|^\d?)\d*\.?\d+$/.test(value);
							if (returnValue) {
								if (param[1] != undefined) {
									var dotPos = value.indexOf(".");
									if (dotPos != -1 && value.length - dotPos - 1 > param[1]) {
										returnValue = false;
									}
								}
							}
							break;
						case "-f": // 负浮点数
							returnValue = /^[-]\d*\.?\d$/.test(value);
							if (returnValue) {
								if (param[1] != undefined) {
									var dotPos = value.indexOf(".");
									if (dotPos != -1 && value.length - dotPos - 1 > param[1]) {
										returnValue = false;
									}
								}
							}
							break;
						default: // 缺省为整数
							returnValue = /(^-?|^\+?|\d)\d+$/.test(value);
							break;
					}
					if (returnValue) {
						value = Number(value);
						if (param[2] != undefined) {
							returnValue = (value >= param[2]);
						}
						if (param[3] != undefined) {
							returnValue = returnValue && (value <= param[3]);
						}
					}
					return returnValue;
				}
				else {
					return /(^-?|^\+?|\d)\d+$/.test(value);
				}
			},
			message: "请输入指定类型的数字格式且值在 {2} 与 {3} 之间"
		},

		money: {
			validator: function (value) { 
				var returnValue = /(^-?|^\+?|\d)\d+$/.test(value) || /(^-?|^\+?|^\d?)\d*\.\d+$/.test(value);
				if (returnValue) {
					var dotPos = value.indexOf(".");
					if (dotPos != -1 && value.length - dotPos - 1 > 2) {
						returnValue = false;
					}
				}
				if(value<0){
					returnValue=false;
				}
				return returnValue; 
			},
			message: "请输入正确的货币金额（小数两位）"
		},

		letter: {
			validator: function (value) { 
				return /^[a-zA-Z]*$/.test(value);
			},
			message: "请输入英文字母"
		},

		letterOrNum: {
			validator: function (value) {
				return /^[0-9a-zA-Z]*$/.test(value);
			},
			message: '请输入英文字母或数字'
		},

		lowerLetterOrNum: {
			validator: function (value) { 
				return /^[0-9a-z]*$/.test(value);
			},
			message: "请输入小写字母或数字"
		},

		upperLetterOrNum: {
			validator: function (value) { 
				return /^[0-9A-Z]*$/.test(value);
			},
			message: "请输入大写字母或数字"
		},

		letterOrSpace: {
			validator: function (value) {
				return /^[a-zA-Z ]*$/.test(value);
			},
			message: '请输入英文字母或空格'
		},

		chinese: {
			validator: function (value) { 
				return /^[\u4E00-\u9FA5]+$/i.test(value); // [\u4E00-\u9FA5]为汉字﹐[\uFE30-\uFFA0]为全角符号
			},
			message: "请输入纯中文汉字"
		},

		chsOrNumOrLetter: {
			validator: function (value, param) {
				return /^([\u4e00-\u9fa5]|[a-zA-Z0-9])*$/.test(value);
			},
			message: '请输入汉字、英文字母或数字'
		},

		variable: {
			validator: function (value) { 
				return /^[0-9a-zA-Z_]*$/.test(value);
			},
			message: "请输入正确的变量（由字母、数字、下划线组成）"
		},

		jndi: {
			validator: function (value) { 
				return /^[0-9A-Za-z_\/]*$/.test(value);
			},
			message: "请输入正确的数据源jndi（字母、数字、下划线、反斜杠组成）"
		},

		javaClass: {
			validator: function (value) { 
				if (/^[0-9A-Za-z_\.]*$/.test(value)) {
					if (!/(.?)\.$/.test(value) && !/^\.(.?)/.test(value) && /^(?!.*?\.\.).*$/.test(value)) {
						return true;
					}
				}
				return false;
			},
			message: "请输入正确的 java 类名"
		},

		ip: {
			validator: function (value) { 
				var re=/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/; //匹配IP地址的正则表达式
				if (re.test(value)) {
					if (RegExp.$1.length > 3 || RegExp.$2.length > 3 || RegExp.$3.length > 3 || RegExp.$4.length > 3) {
						return false;
					}
					if (RegExp.$1 > 0 && RegExp.$1 < 256 && RegExp.$2 < 256 && RegExp.$3 < 256 && RegExp.$4 < 255) {
						return true;
					}
				}
				return false;
			},
			message: "请输入正确的IP地址"
		},

		port: {
			validator: function (value) { 
				if (/^[+]?[1-9]+\d*$/i.test(value)) {
					var valueInt = parseInt(value);
					if (value > 0 && value < 65535) {
						return true;
					}
				}
				return false;
			},
			message: "请输入正确的端口号"
		},

		folder: {
			validator: function (value) { 
				var regu =/(^[^\.])/;
				var re = new RegExp(regu);
				if (!re.test(value)) {
					return false;
				}
				regu=/(^[^\\\/\?\*\"\<\>\:\|]*$)/;
				var re = new RegExp(regu);
				if (re.test(value)) {
					return true;
				}
				return false;
			},
			message: "请输入正确的文件夹"
		},

		fileName: {
			validator: function (value) {
				return !/[\\\/\*\?\|:<>]/g.test(value); 
			},
			message: "请输入正确的文件名（不能包含字符 \\/:*?\"<>|）"
		},

		imgFile: {
			validator: function (value) {
				var returnValue = false;
				var atI = value.lastIndexOf(".");
				if (atI != -1) {
					var fileExt = value.substr(atI + 1);
					fileExt = "," + fileExt.toUpperCase() + ",";
					if (",JPG,JPEG,GIF,PNG,BMP,".indexOf(fileExt) != -1) {
						returnValue = true;
					}
				}
				return returnValue; 
			},
			message: "请选择一个图片格式的文件（JPG,JPEG,GIF,PNG,BMP）"
		},

		email: {
			validator : function (value) {
				return /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i.test(value);
			},
			message : "请输入正确的 email 地址"
		},

		url: {
			validator : function(value) {
				return /^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i.test(value);
			},
			message : "请输入正确的 url 地址"
		},

		phone: {
			validator : function(value) {
				return /(^([0][1-9]{1}[0-9]{1,2}[-]?)?\d{3,8}(-\d{1,6})?$)|(^[1][3|4|5|7|8]{1}\d{9,}$)|(^\d{3,8}$)/.test(value);
			},
			message : "请输入正确的电话号码（固定电话或移动电话）"
		},

		manyPhones:{
			validator : function(value) {
				var pattern=/(^([0][1-9]{1}[0-9]{1,2}[-]?)?\d{3,8}(-\d{1,6})?$)|(^[1][3|4|5|7|8]{1}\d{9,}$)|(^\d{3,8}$)/;
				var phoneArray=value.split('/');
				var bSuccess=true;
				for(var i=0;i<phoneArray.length;i++){
					if(!pattern.test(phoneArray[i])){
						bSuccess=false;
						break;
					}
				}
				return bSuccess;
			},
			message : "请输入正确的电话号码（多个号码之间用'/'分隔）"			
			
		},
		mobile: {
			validator : function(value) {
				return /(^[1][3|4|5|7|8]{1}\d{9,}$)/.test(value);
			},
			message : "请输入正确的移动电话"
		},
		manyMobils:{
			validator : function(value) {
				var pattern=/(^[1][3|4|5|7|8]{1}\d{9,}$)/;
				var phoneArray=value.split('/'); //
				var bSuccess=true;
				for(var i=0;i<phoneArray.length;i++){
					if(!pattern.test(phoneArray[i])){
						bSuccess=false;
						break;
					}
				}
				return bSuccess;
			},
			message : "请输入正确的移动电话（多个号码之间用'/'分隔）"					
			
		},
		zipCode: {
			validator : function(value) {
				return /^[\d]{6}$/.test(value);
			},
			message : "请输入正确的邮政编码"
		},

		contains: {
			validator: function (value, param) { 
				if (param) {
					if (param[0]) {
						if (value.indexOf(param[0]) == -1) {
							return false;
						}
					}
				}
				return true; 
			},
			message: "输入的内容包含“{0}”"
		},

		startsWith: {
			validator: function (value, param) { 
				if (param) {
					if (param[0]) {
						if (value.indexOf(param[0]) != 0) {
							return false;
						}
					}
				}
				return true; 
			},
			message: "输入的内容以“{0}”作为起始字符"
		},

		endsWith: {
			validator: function (value, param) { 
				if (param) {
					if (param[0]) {
						if (value.substr(value.length - param[0].length) != param[0]) {
							return false;
						}
					}
				}
				return true; 
			},
			message: "输入的内容以“{0}”作为结束字符"
		},

		length: {
			validator : function(value, param) {
				var len = $.trim(value).length;
				return len >= param[0] && len <= param[1];
			},
			message: "输入的长度在 {0} 与 {1} 之间"
		},

		minLength: {
			validator: function (value, param) {
				if (param) {
					if (param[0] != undefined) {
						if (value.length < param[0]) {
							return false;
						}
					}
				}
				return true;
			},
			message: "输入最少输入 {0} 个字符"
		},

		maxLength: {
			validator: function (value, param) {
				if (param) {
					if (param[0] != undefined) {
						if (value.length > param[0]) {
							return false;
						}
					}
				}
				return true;
			},
			message: "输入最多输入 {0} 个字符"
		},

		date: {
			validator: function (value, param) {
				if (param) {
					if (param[0]) {
						switch (param[0]) {
							case "yyyy":
								return isValidDate(value + "0101", 0);
							case "yyyyMM":
								return isValidDate(value + "01", 0);
							case "yyyy-MM":
								return isValidDate(value + "-01", 1);
							case "yyyyMMdd":
								return isValidDate(value, 0);
							case "yyyy-MM-dd":
								return isValidDate(value, 1);
							case "yyyyMMddHH":
								return isValidDateTime(value + "0000", 0);
							case "yyyy-MM-dd HH":
								return isValidDateTime(value + ":00:00", 1);
							case "yyyyMMddHHmm":
								return isValidDateTime(value + "00", 0);
							case "yyyy-MM-dd HH:mm":
								return isValidDateTime(value + ":00", 1);
							case "yyyyMMddHHmmss":
								return isValidDateTime(value, 0);
							case "yyyy-MM-dd HH:mm:ss":
								return isValidDateTime(value, 1);
						}
					}
				}
				return true;
			},
			message: "请输入正确的日期，日期格式为 {0}"
		},

		sfzh: {
			validator: function (value) {
				return isCorrectSfzh(value);
			},
			message: "请输入正确的身份证号码"
		},

		equals: {
			validator: function (value, param) {
				var type = param[0];
				var val = param[1]; 
				if (type) {
					switch (String(type).toLowerCase()) {
						case "jquery":
						case "dom":
							val = $(val).val();
							break;
						case "id":
							val = $("#" + val).val();
							break;
						case "string":
						default:
							break;
					}
				}
				return value === val;
			},
			message: "输入的内容不匹配"
		},
		
		notEmpty: {
			validator: function (value, param) {
				if (param) {
					if (param[0]) {
						val = $("#" + param[0]).val();
						return val != "";
					}
				}
				return true;
			},
			message: "请输入该数据"
		}

	};

	$.extend($.fn.validatebox.defaults.rules, rules);

})(jQuery);