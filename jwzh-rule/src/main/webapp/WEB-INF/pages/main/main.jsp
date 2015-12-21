<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.founder.framework.config.SystemConfig"%>
<%
	String contextPath = request.getContextPath();
%>
<html>
<head>
    <title>规则平台</title>
</head>
<body scroll="no" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0px" 
   	  style="BORDER-RIGHT: 0px none; BORDER-TOP: 0px none; MARGIN: 0px; BORDER-LEFT: 0px none; BORDER-BOTTOM: 0px none; overflow: hidden;"
  	  onselectstart="return false;" oncontextmenu="return false;" >
	<iframe id="main_frame" src="<%=contextPath%>/forward/main|main_frame" frameborder="0" scrolling="no" style="width: 100%; height: 100%"></iframe>
</body>
</html>