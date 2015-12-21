<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="Refresh" content="2;url=LoginFirst.jsp">
	
  </head>
  
  <body>
     <br>
     <hr/>
     <%out.print(path); %>
     <hr/>
     <%out.print(basePath); %>
  </body>
  

</html>
