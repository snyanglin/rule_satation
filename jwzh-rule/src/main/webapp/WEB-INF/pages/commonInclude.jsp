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

<script type="text/javascript" src="<%=contextPath%>/js/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/js/messenger.js"></script>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/css/syrk.css"></link>



