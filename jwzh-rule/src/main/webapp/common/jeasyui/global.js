// 设置是否为IE的常量，以及IE的版本判断
var IE = window.navigator.appVersion.toUpperCase().indexOf('MSIE') == -1 ? false : true;
var IE_VERSION = getIEVersion();

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
