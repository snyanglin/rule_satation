// 主框架页面调用方法


// 打开Tab标签字页面
// menuName   打开标签名称
// openUrl    打开的url地址
// matchLeft  URL是否只匹配?前面的（默认为false，匹配整个URL）
function menu_open(menuName, openURL, matchLeft) {
	if (!openURL || openURL == "") {
		return;
	}
	try {
		var openURLUpper = openURL.toUpperCase();
		if (openURLUpper.indexOf("HTTP://") == 0) {
			var sessionUserID = window.top.sessionUserID;
			var sessionPassword = window.top.sessionPassword;
			var sessionUserOrgCode = window.top.sessionUserOrgCode;
			if (!sessionUserID) {
				sessionUserID = "";
			}
			if (!sessionPassword) {
				sessionPassword = "";
			}
			if (!sessionUserOrgCode) {
				sessionUserOrgCode = "";
			}
			openURL = openURL.replace(/#userID#/g, sessionUserID);
			openURL = openURL.replace(/#password#/g, sessionPassword);
			openURL = openURL.replace(/#userOrgCode#/g, sessionUserOrgCode);
		}
		var menuNameLink = "";
		if (openURL.indexOf("?") == -1) {
			menuNameLink = "?";
		}
		else {
			menuNameLink = "&";
		}
		if (IE) {
			window.top.document.frames["main_frame"].main_center.main_tabTitle.tabTitle_add(menuName, openURL + menuNameLink + "menuName=" + menuName, matchLeft);
		}
		else {
			var main_center =  window.top.document.getElementById("main_frame").contentWindow.frames.document.getElementById("main_center");
			var main_tabTitle = main_center.contentWindow.frames.document.getElementById("main_tabTitle");
			main_tabTitle.contentWindow.tabTitle_add(menuName, openURL + menuNameLink + "menuName=" + menuName, matchLeft);
		}
	}
	catch (err) {}
}

// 关闭当前的标签页
function closeSelf() {
	try {
		if (IE) {
			window.top.document.frames["main_frame"].main_center.main_tabTitle.tabTitle_closeSelf();
		}
		else {
			var main_center = window.top.document.getElementById("main_frame").contentWindow.frames.document.getElementById("main_center");
			var main_tabTitle = main_center.contentWindow.frames.document.getElementById("main_tabTitle");
			main_tabTitle.contentWindow.tabTitle_closeSelf();
		}
	}
	catch (err) {}   
}

// 切换到标签首页
function firstPage() {
	try {
		if (IE) {
			window.top.document.frames["main_frame"].main_center.main_tabTitle.tabTitle_onClick("0");
		}
		else {
			var main_center = window.top.document.getElementById("main_frame").contentWindow.frames.document.getElementById("main_center");
			var main_tabTitle = main_center.contentWindow.frames.document.getElementById("main_tabTitle");
			main_tabTitle.contentWindow.tabTitle_onClick("0");
		}
	}
	catch (err) {}     
}

// 切换到第几个标签页
function locateMainPage(tabNumber) {
	try {
		if (IE) {
			window.top.document.frames["main_frame"].main_center.main_tabTitle.tabTitle_onClick("" + tabNumber);
		}
		else {
			var main_center = window.top.document.getElementById("main_frame").contentWindow.frames.document.getElementById("main_center");
			var main_tabTitle = main_center.contentWindow.frames.document.getElementById("main_tabTitle");
			main_tabTitle.contentWindow.tabTitle_onClick("" + tabNumber);
		}
	}
	catch (err) {}     
}

// 取得当前标签的ID
function getMainTabID() {
	try {
		if (IE) {
			return window.top.document.frames["main_frame"].main_center.main_tabTitle.ETID;
		}
		else {
			var main_center = window.top.document.getElementById("main_frame").contentWindow.frames.document.getElementById("main_center");
			var main_tabTitle = main_center.contentWindow.frames.document.getElementById("main_tabTitle");
			return main_tabTitle.contentWindow.ETID;
		}
	}
	catch (err) {}
	return "";
}

// 执行指定标签页的页面中的方法
// mainTabID 标签页ID
// method    页面中的方法名
function executeTabPageMethod(mainTabID, method) {
	if (!mainTabID || mainTabID == "" || !method || method == "") {
		return;
	}
	try {
		var openFrameID = "main_tab" + mainTabID;
		if (IE) {
			var openFrame = window.top.document.frames["main_frame"].main_center.frames(openFrameID);
			eval("openFrame." + method + "()");
		}
		else {
			var main_center = window.top.document.getElementById("main_frame").contentWindow.frames.document.getElementById("main_center");
			var openFrame = main_center.contentWindow.frames.document.getElementById(openFrameID).contentWindow;
			eval("openFrame." + method + "()");
		}
	}
	catch (err) {}
}

// 关闭指定名称的标签页
function closeMainPageByName(tabName) {
	if ("undefined" == typeof tabName || tabName == null || tabName == "") {
		return;
	}
	try {
		if (IE) {
			window.top.document.frames["main_frame"].main_center.main_tabTitle.tabTitle_closeByName(tabName);
		}
		else {
			var main_center = window.top.document.getElementById("main_frame").contentWindow.frames.document.getElementById("main_center");
			var main_tabTitle = main_center.contentWindow.frames.document.getElementById("main_tabTitle");
			main_tabTitle.contentWindow.tabTitle_closeByName(tabName);
		}
	}
	catch (err) {}     
}

