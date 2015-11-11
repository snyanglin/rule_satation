<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.founder.framework.config.SystemConfig"%>
<%
	String contextPath = request.getContextPath();
%>
<html>
<head>
 	<title>规则平台</title>
</head>
<frameset id="main_system" rows="80,*,0" border="0" frameborder="0">
   <frame id="main_top" src="<%=contextPath%>/forward/main|main_top" noresize scrolling="no">
     <frameset id="kernel" cols="240,6,*">
     <frame id="main_left" src="<%=contextPath%>/forward/main|main_Left" noresize scrolling="no">
     <frame id="leftScroll" src="<%=contextPath%>/forward/main|main_leftScroll" noresize scrolling="no">
     <frame id="main_center" name="main_center" src="<%=contextPath%>/forward/main|main_center" noresize scrolling="yes">
  </frameset>
  <noframes>
    <body>
     <p>此网页使用了框架，但您的浏览器不支持框架。</p>
    </body>
  </noframes>
</frameset>