// 常用的公共方法
// author : redstorm
// 2014-05-01

// 去掉左右两边的空格（回车不替换）
function stringTrim(str) {
	if (str != null && str != "") {
		return str.replace(/(^[\s|　]*)|([\s|　]*$)/g, "");
	}
	else {
		return "";
	}
}

// 去掉左边的空格（回车不替换）
function stringLeftTrim(str) {
	if (str != null && str != "") {
		return str.replace(/(^[\s|　]*)/g, "");
	}
	else {
		return "";
	}
}

// 去掉右边的空格（回车不替换）
function stringRightTrim(str) {
	if (str != null && str != "") {
		 return str.replace(/([\s|　]*$)/g, "");
	}
	else {
		return "";
	}
}

// 清空所有空格
function stringClearSpace(str) {
	if (str != null && str != "") {
		 return str.replace(/ /g, "");
	}
	else {
		return "";
	}
}

// 取得一个字符串的长度（一个汉字为两个长度）
function getGBLength(str) {
	if (str == null || str == "") {
		return 0;
	}
	var cnstrCount = 0; 
	for (var i = 0; i < str.length; i++) {
		if (str.charCodeAt(i) > 255) {
			cnstrCount = cnstrCount + 1;
		}
	}
	len = str.length + cnstrCount;
	return len;
}

// 取得一个字符串左子串，长度为字节长度（一个汉字为两个长度）
// 不足长度时不补充
function getGBLeft(str, len) {
	if (str == null || str == "" || len == 0) {
		return "";
	}
	var cnstrCount = 0; 
	var curCharLen = 0;
	for (var i = 0; i < str.length; i++) {
		if (str.charCodeAt(i) > 255)
			curCharLen = 2;
		else 
			curCharLen = 1;
		if (cnstrCount + curCharLen == len) {
			cnstrCount = cnstrCount + curCharLen;
			i ++;
			break;
		}
		else {
			if (cnstrCount + curCharLen > len) {
				break;
			}
			else {
				cnstrCount = cnstrCount + curCharLen;
			}
		}
	}
	return str.substr(0,i);
}

// 取得一个字符串等长子串，长度为字节长度（一个汉字为两个长度）
// 不足长度时用 padStr 补充
function getGBRpad(str, len, padStr) {
	if (str == null || str == "") {
		return getReapeatString(padStr, len);
	}
	if (len == 0) {
		return "";
	}
	var cnstrCount = 0; 
	var curCharLen = 0;
	var i = 0;
	for (i = 0; i < str.length; i++) {
		if (str.charCodeAt(i) > 255)
			curCharLen = 2;
		else 
			curCharLen = 1;
		if (cnstrCount + curCharLen == len) {
			cnstrCount = cnstrCount + curCharLen;
			i ++;
			break;
		}
		else {
			if (cnstrCount + curCharLen > len) {
				break;
			}
			else {
				cnstrCount = cnstrCount + curCharLen;
			}
		}
	}
	var rpadStr = str.substr(0, i);
	if (cnstrCount < len) {
		rpadStr = rpadStr + getReapeatString(padStr, len - cnstrCount);
	}
	return rpadStr;
}

// 取得一个字符的重复字符串
function getReapeatString(repChar, repTimes) {
	var reapeatStr = "";
	for (var i = 1; i <= repTimes; i++) {
		reapeatStr = reapeatStr + repChar; 
	}
	return reapeatStr;
}

// 全角转成半角
function charSet_fullToHalf(str) { 
	var result = "";
	if (str != "") {
		for (var i = 0; i < str.length; i++) {
			var charCode = str.charCodeAt(i);
			if (charCode == 12288) {
				result += String.fromCharCode(charCode - 12256);
				continue;
			} 
			if (charCode > 65280 && charCode < 65375) {
				result += String.fromCharCode(charCode - 65248);
			}
			else {
				result += str.substr(i, 1);
			}
		} 
	}
	return result;
} 

// 全角转成半角小写
function charSet_fullToHalfLower(str) {
	var result = "";
	if (str != "") {
		result = charSet_fullToHalf(str);
		result = result.toLowerCase();
	}
	return result;
}

// 全角转成半角大写
function charSet_fullToHalfUpper(str) {
	var result = "";
	if (str != "") {
		result = charSet_fullToHalf(str);
		result = result.toUpperCase();
	}
	return result;
}

// 半角转成全角
function charSet_halfToFull(str) { 
	var result = "";
	if (str != "") {
		for (var i = 0; i < str.length; i++) {
			var charCode = str.charCodeAt(i);
			if (charCode == 13 || charCode == 10) {
				result += str.substr(i, 1);
				continue;
			} else if (charCode == 32) {
				result += String.fromCharCode(12288);
				continue;
			} 
			if (charCode < 127) {
				result += String.fromCharCode(charCode + 65248);
			}
			else {
				result += String.fromCharCode(charCode);
			}
		} 
	}
	return result;
} 

// 半角转成全角小写
function charSet_halfToFullLower(str) {
	var result = "";
	if (str != "") {
		result = str.toLowerCase();
		result = charSet_halfToFull(result);
	}
	return result;
}

// 半角转成全角大写
function charSet_halfToFullUpper(str) {
	var result = "";
	if (str != "") {
		result = str.toUpperCase();
		result = charSet_halfToFull(result);
	}
	return result;
}

// 转成小写
function charSet_lower(str) {
	var result = "";
	if (str != "") {
		result = str.toLowerCase();
	}
	return result;
}

// 转成大写
function charSet_upper(str) {
	var result = "";
	if (str != "") {
		result = str.toUpperCase();
	}
	return result;
}

// JS 实现 StringBuffer
function StringBuffer(str) {
	this.append = function(str) {
		if (str) {
			this.stringArray.push(str);
		}
	}

	this.clear = function() {
		this.stringArray.length = 1;
	}

	this.toString = function() {
		return this.stringArray.join("");
	}

	this.stringArray = new Array("");
	this.append(str);
}

// 取得人民币大写金额
// money	金额数字字符串
function getChineseMoney(money) {
	if (money == null || money == "") {
		return "";
	}
	var moneyFloat = parseFloat(money); 
	if (isNaN(moneyFloat)) {
		return "";
	}
	else {
		if (moneyFloat == 0) {
			return "零元整";
		}
		var chinaDigital = new Array("零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖");
		var chinaUnit = new Array("仟", "佰", "拾", "万", "仟", "佰", "拾", "亿", "仟", "佰", "拾", "万", "仟", "佰", "拾", "", "元", "角", "分");
		var moneyStr = "" + moneyFloat;
		var indexPoint = moneyStr.indexOf(".");
		if (indexPoint != -1) {
			moneyStr += "00";
			moneyStr = moneyStr.substring(0, indexPoint + 3);
		}
		else {
			moneyStr += ".00";
		}
		var moneyLength = moneyStr.length;
		if (moneyLength > 18) {  // 数字太大
			return "";
		}
		var tempChar = "";
		var tempInt = 0;
		var tempAtArray = 0;
		var zeroCount = 0; 
		var lastZero = false;
		var returnValue = "";
		for (var i = 0; i < moneyLength; i++) {
			tempChar = moneyStr.substring(i, i + 1);
			if (tempChar == ".") {
				if (moneyFloat >= 1) {
					returnValue += "元";
				}
			} 
			else {
				tempInt = parseInt(tempChar);
				tempAtArray = 19 - moneyLength + i;
				if (tempInt == 0) { // 为零的处理
					lastZero = true;
					zeroCount++;
					if (tempAtArray == 7 || (tempAtArray % 4 == 3 && zeroCount < 4)) { // 亿必需出现
						returnValue += chinaUnit[tempAtArray];
						zeroCount = 0;
					}
				}
				else {
					zeroCount = 0;
					if (lastZero) {
						if (moneyFloat >= 1) {
							returnValue += chinaDigital[0];
						}
					}
					returnValue += chinaDigital[tempInt];
					returnValue += chinaUnit[tempAtArray];
					lastZero = false;
				}
			}
		}
		if (moneyStr.lastIndexOf("0") == moneyLength - 1) {
			returnValue += "整";
		}
		return returnValue;
	}
}


// 取得用人民币大写金额表示的整数
// money	金额数字字符串
function getChineseNumberAsMoney(money) {
	if (money == null || money == "") {
		return "";
	}
	var moneyFloat = parseFloat(money); 
	if (isNaN(moneyFloat)) {
		return "";
	}
	else {
		if (moneyFloat == 0) {
			return "";
		}
		var chinaDigital = new Array("零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖");
		var chinaUnit = new Array("仟", "佰", "拾", "万", "仟", "佰", "拾", "亿", "仟", "佰", "拾", "万", "仟", "佰", "拾", "", "", "", "");
		var moneyStr = "" + moneyFloat;
		var indexPoint = moneyStr.indexOf(".");
		if (indexPoint != -1) {
			moneyStr = moneyStr.substring(0, indexPoint) + ".00";
		}
		else {
			moneyStr += ".00";
		}
		var moneyLength = moneyStr.length;
		if (moneyLength > 18) {  // 数字太大
			return "";
		}
		var tempChar = "";
		var tempInt = 0;
		var tempAtArray = 0;
		var zeroCount = 0; 
		var lastZero = false;
		var returnValue = "";
		for (var i = 0; i < moneyLength; i++) {
			tempChar = moneyStr.substring(i, i + 1);
			if (tempChar == ".") {
				if (moneyFloat >= 1) {
					//returnValue += "元";
				}
			} 
			else {
				tempInt = parseInt(tempChar);
				tempAtArray = 19 - moneyLength + i;
				if (tempInt == 0) { // 为零的处理
					lastZero = true;
					zeroCount++;
					if (tempAtArray == 7 || (tempAtArray % 4 == 3 && zeroCount < 4)) { // 亿必需出现
						returnValue += chinaUnit[tempAtArray];
						zeroCount = 0;
					}
				} 
				else {
					zeroCount = 0;
					if (lastZero) {
						if (moneyFloat >= 1) {
							returnValue += chinaDigital[0];
						}
					}
					returnValue += chinaDigital[tempInt];
					returnValue += chinaUnit[tempAtArray];
					lastZero = false;
				}
			}
		}
		return returnValue;
	}
}

// 取得数字中文字符串（如：法律文书条款）
// numberStr	整数字符串
function getChineseNumber(numberStr) {
	if (numberStr == null || numberStr == "") {
		return "";
	}
	var numberLong = parseFloat(numberStr); 
	if (isNaN(numberLong)) {
		return "";
	}
	else {
		numberLong = Math.floor(numberLong);
		if (numberLong == 0) {
			return "零";
		}
		var chinaDigital = new Array("零", "一", "二", "三", "四", "五", "六", "七", "八", "九");
		var chinaUnit = new Array("亿", "千", "百", "十", "万", "千", "百", "十", "");
		var tempStr = "" + numberLong;
		var numberLength = tempStr.length;
		if (numberLength > 9) {  // 数字太大
			return "";
		}
		var tempChar = "";
		var tempInt = 0;
		var tempAtArray = 0;
		var zeroCount = 0; 
		var lastZero = false;
		var returnValue = "";
		for (var i = 0; i < numberLength; i++) {
			tempChar = tempStr.substring(i, i + 1);
			tempInt = parseInt(tempChar);
			tempAtArray = 9 - numberLength + i;
			if (tempInt == 0) { // 为零的处理
				lastZero = true;
				zeroCount++;
				if (tempAtArray == 0 || (tempAtArray % 4 == 0 && zeroCount < 4)) { // 亿必需出现
					returnValue += chinaUnit[tempAtArray];
					zeroCount = 0;
				}
			} 
			else {
				zeroCount = 0;
				if (lastZero) {
					if (numberLong >= 1) {
						returnValue += chinaDigital[0];
					}
				}
				returnValue += chinaDigital[tempInt];
				returnValue += chinaUnit[tempAtArray];
				lastZero = false;
			}
		}
		return returnValue;
	}
}

// 取得日期中文字符串
// dateString	日期字符串格式，如: (2008-05-12 14:28:36)
// resultFormat 返回结果的格式（字符型）
// resultFormat = 1 ：2008年5月12日
// resultFormat = 2 ：2008年5月12日14时
// resultFormat = 3 ：2008年5月12日14时28分
// resultFormat = 4 ：二○○八年五月十二日

function getChineseDate(dateString, resultFormat) {
	if (dateString == null || dateString == "") {
		return "";
	}
	if (resultFormat == null || resultFormat == "") {
		resultFormat = "1";
	}
	var chineseDate = "";
	try {
		var tempYear = dateString.substring(0, 4);
		var tempMonth = dateString.substring(5, 7);
		var tempDay = dateString.substring(8, 10);
		var tempHour = dateString.substring(11, 13);
		var tempMinute = dateString.substring(14, 16);
		var tempSecond = dateString.substring(17);
		switch (resultFormat) {
			case "1" :
				chineseDate = tempYear + "年" + tempMonth.replace(/(^0*)/g, "") + "月" + tempDay.replace(/(^0*)/g, "") + "日";
				break;
			case "2" : 
				var hourString = tempHour.replace(/(^0*)/g, "");
				if (hourString == "") {
					hourString = "0";
				}
				chineseDate = tempYear + "年" + tempMonth.replace(/(^0*)/g, "") + "月" + tempDay.replace(/(^0*)/g, "") + "日" + hourString + "时";
				break;
			case "3" :  
				var hourString = tempHour.replace(/(^0*)/g, "");
				if (hourString == "") {
					hourString = "0";
				}
				var minuteString = tempMinute.replace(/(^0*)/g, "")
				if (minuteString == "") {
					minuteString = "0";
				}
				chineseDate = tempYear + "年" + tempMonth.replace(/(^0*)/g, "") + "月" + tempDay.replace(/(^0*)/g, "") + "日" + hourString + "时" + minuteString + "分";
				break;
			case "4" :  
				var chinaNumber = new Array(10);
				chinaNumber[0] = "○"; // 0
				chinaNumber[1] = "一";
				chinaNumber[2] = "二";
				chinaNumber[3] = "三";
				chinaNumber[4] = "四";
				chinaNumber[5] = "五";
				chinaNumber[6] = "六";
				chinaNumber[7] = "七";
				chinaNumber[8] = "八";
				chinaNumber[9] = "九";

				for (var i = 0; i < tempYear.length; i++) {
					var cn = parseInt(tempYear.substring(i, i + 1));
					chineseDate += chinaNumber[cn];
				}
				chineseDate += "年";
				for (var i = 0; i < tempMonth.length; i++) {
					var cn = parseInt(tempMonth.substring(i, i + 1));
					if (i == 0) {
						if (cn == 1) {
							chineseDate += "十";
						}
					} 
					else {
						if (cn > 0) {
							chineseDate += chinaNumber[cn];
						}
					}
				}
				chineseDate += "月";
				for (var i = 0; i < tempDay.length; i++) {
					var cn = parseInt(tempDay.substring(i, i + 1));
					if (i == 0) {
						switch (cn) {
							case 0:
								break;
							case 1:
								chineseDate += "十";
								break;
							default:
								chineseDate += chinaNumber[cn] + "十";
								break;
						}
					} 
					else {
						if (cn > 0) {
							chineseDate += chinaNumber[cn];
						}
					}
				}
				chineseDate += "日";
				break;
			}
	}
	catch (err) {
		chineseDate = "";
	}
	return chineseDate;
}

// 15位身份证号码转成18位
function getSfzh18(sfzh15) {
	var tempSfzh = sfzh15;
	if (("undefined" != typeof sfzh15) && (sfzh15 != "")) {
		if (sfzh15.length == 15) {
			var tempStr = sfzh15.substring(0, 6) + "19" + sfzh15.substring(6);
			var lastAt = 0;
			for (var i = 0; i < 17; i++) {
				var bitInt = parseInt(tempStr.substring(i, i + 1));
				var bitIntTemp = 1;
				for (var j = 0; j < 17 - i; j++) {
					bitIntTemp = (bitIntTemp * 2) % 11;
				}
				lastAt += bitInt * bitIntTemp;
			}
			lastAt %= 11;
			tempSfzh = tempStr + "10X98765432".substring(lastAt, lastAt + 1);
		}
	}
	return tempSfzh;
}

// 判断是否为正确的身份证号码
// 验证通过返回true,否则返回false
function isCorrectSfzh(sfzh18) {
	if (sfzh18 != "" && sfzh18.length == 18) {
		for (var i = 0; i < 17; i++) {
			var bitChar = sfzh18.substring(i, i + 1);
			if (bitChar < "0" || bitChar > "9") {
				return false;
			}
		}
		var birthday = sfzh18.substring(6, 14);
		return (isValidDate(birthday, 0) && checkSfzh18Bit(sfzh18));
	}
	return false;
}

// 18位身份证号码校验位检查
// 验证通过返回true,否则返回false
function checkSfzh18Bit(sfzh18) {
	try {
		var tempStr = sfzh18;
		var lastAt = 0;
		for (var i = 0; i < 17; i++) {
			var bitInt = parseInt(tempStr.substring(i, i + 1));
			var bitIntTemp = 1;
			for (var j = 0; j < 17 - i; j++) {
				bitIntTemp = (bitIntTemp * 2) % 11;
			}
			lastAt += bitInt * bitIntTemp;
		}
		lastAt %= 11;
		if ("10X98765432".substring(lastAt, lastAt + 1) == sfzh18.substring(17)) { 
			return true;
		}
		else {
			return false;
		}
	}
	catch (err) {
		return false;
	}
}

// 判断是否为一个有效的日期
// curDate 日期字符串，格式如：2008-05-12，也可以校验紧凑型日期格式：20080512
// splitLength 分隔符（日期时间分隔符长度，只能为 1）
// 验证通过返回true,否则返回false
function isValidDate(curDate, splitLength) {
	var valid = false;
	if (("undefined" != typeof splitLength) && (splitLength == 1)) {
		if (curDate.length == 10) {
			valid = checkDate(curDate.substring(0, 4), curDate.substring(5, 7), curDate.substring(8));
		}
	}
	else {
		if (curDate.length == 8) {
			valid = checkDate(curDate.substring(0, 4), curDate.substring(4, 6), curDate.substring(6));
		}
	}
	return valid;
}

// 日期检查
// year 年字符串，month 月字符串，day 日字符串
// 年月日可以有0的前缀
function checkDate(year, month, day) {
	try {
		year = year.replace(/(^0*)/g, "");
		month = month.replace(/(^0*)/g, "");
		day = day.replace(/(^0*)/g, "");
		var regExp = new RegExp("^\\d+$");
		if (!regExp.test(year) || !regExp.test(month) || !regExp.test(day)) {
			return false;
		}
		var yearInt = parseInt(year);
		var monthInt = parseInt(month);
		var dayInt = parseInt(day);
		if ((yearInt < 1900 || yearInt > 3000) || (monthInt < 1 || monthInt > 12) || (dayInt < 1 || dayInt > 31)) {
			return false;
		}
		if (dayInt == 31 && (monthInt==4 || monthInt==6 || monthInt==9 || monthInt==11)) {
			return false;
		}
		if (monthInt == 2) {
			if ((yearInt % 4 == 0 && yearInt % 100 != 0) || yearInt % 400 == 0) {
				if (dayInt > 29) {
					return false;
				}
			} 
			else {
				if (dayInt > 28) {
					return false;
				}
			} 
		} 
	}
	catch (err) {
		return false;
	}
	return true;
}

// 判断是否为一个有效的日期时间
// curDate 日期时间字符串，格式如：2008-05-12 14:28:20 ，也可以校验紧凑型日期时间格式：20080512142820
// splitLength 分隔符（日期时间分隔符长度，只能为 1）
// 验证通过返回true,否则返回false
function isValidDateTime(curDate, splitLength) {
	var valid = false;
	if (("undefined" != typeof splitLength) && (splitLength == 1)) {
		if (curDate.length == 19) {
			valid = checkDateTime(curDate.substring(0, 4), curDate.substring(5, 7), curDate.substring(8, 10), curDate.substring(11, 13), curDate.substring(14, 16), curDate.substring(17));
		}
	}
	else {
		if (curDate.length == 14) {
			valid = checkDateTime(curDate.substring(0, 4), curDate.substring(4, 6), curDate.substring(6, 8), curDate.substring(8, 10), curDate.substring(10, 12), curDate.substring(12));
		}
	}
	return valid;
}

// 日期时间检查
// year 年字符串，month 月字符串，day 日字符串，hour 小时字符串，minute 分字符串，second 秒字符串
// 年月日时分秒可以有0的前缀
function checkDateTime(year, month, day, hour, minute, second) {
	try {
		year = year.replace(/(^0*)/g, "");
		month = month.replace(/(^0*)/g, "");
		day = day.replace(/(^0*)/g, "");
		var regExp = new RegExp("^\\d+$");
		if (!regExp.test(year) || !regExp.test(month) || !regExp.test(day)) {
			return false;
		}
		var yearInt = parseInt(year);
		var monthInt = parseInt(month);
		var dayInt = parseInt(day);
		if ((yearInt < 1900 || yearInt > 3000) || (monthInt < 1 || monthInt > 12) || (dayInt < 1 || dayInt > 31)) {
			return false;
		}
		if (dayInt == 31 && (monthInt==4 || monthInt==6 || monthInt==9 || monthInt==11)) {
			return false;
		}
		if (monthInt == 2) {
			if ((yearInt % 4 == 0 && yearInt % 100 != 0) || yearInt % 400 == 0) {
				if (dayInt > 29) {
					return false;
				}
			} 
			else {
				if (dayInt > 28) {
					return false;
				}
			} 
		}
		hour = hour.replace(/(^0{1})/g, "");
		minute = minute.replace(/(^0{1})/g, "");
		second = second.replace(/(^0{1})/g, "");
		if (!regExp.test(hour) || !regExp.test(minute) || !regExp.test(second)) {
			return false;
		}
		var hourInt = parseInt(hour);
		var minuteInt = parseInt(minute);
		var secondInt = parseInt(second);
		if ((hourInt < 0 || hourInt > 23) || (minuteInt < 0 || minuteInt > 59) || (secondInt < 0 || secondInt > 59)) {
			return false;
		}
	}
	catch (err) {
		return false;
	}
	return true;
}

// 兼容模式下取浏览器页面有效高度（去除上下菜单、边框等）
// 返回值中：
//   width：页面可视宽度;
//   height：页面可视高度;
//   scrollWidth：页面内容宽度;
//   scrollHeight：页面内容高度;
function getDocumentSize() { 
	var _pageWidth = window.innerWidth;
	var _pageHeight = window.innerHeight;
	var _scrollWidth = 0;
	var _scrollHeight = 0;
	if (typeof _pageWidth != "number"){ 
		if(document.compatMode == "number"){ 
			_pageWidth = document.documentElement.clientWidth; 
			_pageHeight = document.documentElement.clientHeight; 
		}
		else{ 
			_pageWidth = document.body.clientWidth; 
			_pageHeight = document.body.clientHeight; 
		} 
	}
	if (document.documentElement && document.documentElement.scrollHeight) {
		_scrollWidth = document.documentElement.scrollWidth;
		_scrollHeight = document.documentElement.scrollHeight;
	}
	else {
		_scrollWidth = document.body.scrollWidth;
		_scrollHeight = document.body.scrollHeight;
	}
	return {width: _pageWidth, height: _pageHeight, scrollWidth: _scrollWidth, scrollHeight: _scrollHeight};
}

// iframe 重置大小，铺满当前页面
function iframeResize(iframeID) {
	var iframeObject = document.getElementById(iframeID);
	if (iframeObject != null) {
		var documentSize = getDocumentSize();
		document.getElementById(iframeID).style.width = documentSize.width;
		document.getElementById(iframeID).style.height = documentSize.height;
	}
}

// iframe 显示
function iframeShow(iframeID, iframeURL) {
	var iframeObject = document.getElementById(iframeID);
	if (iframeObject != null) {
		document.getElementById(iframeID).style.display = "";
		if (iframeURL) {
			document.getElementById(iframeID).src = iframeURL;
		}
	}
}

// iframe 隐藏
function iframeHide(iframeID) {
	var iframeObject = document.getElementById(iframeID);
	if (iframeObject != null) {
		document.getElementById(iframeID).style.display = "none";
		document.getElementById(iframeID).src = "";
	}
}
