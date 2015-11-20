<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<div class="panel panel-default">
   <div class="panel-heading">查询结果</div>
   
   <table class="table">
	<tr><th>服务名</th><th>规则分组</th><th>规则文件名称</th><th>操作</th></tr>
	<c:forEach items="${Paginator.list}" var="item" varStatus="status">
		<tr>
		<td><c:out value="${item.servicename }" /></td>
		<td><c:out value="${item.groupname }" /></td>
		<td><c:out value="${item.rulefilename }" /></td>	
		<td><a href="#" onclick="detail('${item.ruleid}')">查看</a></td>
		</tr>
	</c:forEach>
	</table>
   
</div>

<%@include file="/WEB-INF/pages/Paginator.jsp"%>