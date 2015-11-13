<%@ page import="com.founder.framework.utils.DateUtils" %>
<%@ page import="com.founder.framework.config.SystemConfig"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String contextPath = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + contextPath + "/";
	String systemDate = DateUtils.getSystemDateString();
	String systemDateTime = DateUtils.getSystemDateTimeString();
%>
<!-- 系统基本参数 -->
<script type="text/javascript">
	var basePath = "<%=basePath%>";
	var systemDate = "<%=systemDate%>";
	var contextPath = "<%=contextPath%>";
	var systemDateTime = "<%=systemDateTime%>";
	var MOS_SOCKET_IP 	= "<%=SystemConfig.getString("websocket.ip")%>";
	var MOS_SOCKET_SERVER_PROT = "<%=SystemConfig.getString("websocket.serverprot")%>";
</script>

<!-- EasyUI 1.3.6 扩展 -->
<script type="text/javascript" src="<%=contextPath%>/common/jeasyui/json2.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jeasyui/tools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jeasyui/jquery1.11.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jeasyui/jquery.form.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jeasyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jeasyui/plugins/jquery.panel.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jeasyui/plugins/jquery.combo.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jeasyui/plugins/jquery.combobox.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jeasyui/plugins/jquery.combotree.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jeasyui/plugins/jquery.window.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jeasyui/plugins/jquery.datagrid.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jeasyui/plugins/jquery.validatebox.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jeasyui/plugins/jquery.tooltip.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jeasyui/plugins/jquery.messager.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jeasyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jeasyui/jquery.easyui.extend.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jeasyui/jquery.easyui.extend.validatebox.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jeasyui/mainFrame.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jeasyui/business.tools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jeasyui/workflow.tools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jeasyui/plugins/datagrid-detailview.js"></script>

<script type="text/javascript" src="<%=contextPath%>/js/messenger.js"></script>

<!-- 样式部分 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/jeasyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/jeasyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/jeasyui/themes/custom.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/css/syrk.css"></link>

