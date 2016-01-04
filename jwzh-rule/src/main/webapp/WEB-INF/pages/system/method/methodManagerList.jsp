<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="panel panel-default">
   <div class="panel-heading">查询结果</div>
	<table class="table">
	<tr><th nowrap="nowrap">方法ID</th><th nowrap="nowrap">方法名称</th><th nowrap="nowrap">返回结果</th><th nowrap="nowrap">方法说明</th><th nowrap="nowrap">操作</th></tr>
	<c:forEach items="${Paginator.list}" var="item" varStatus="status">
		<tr><td><c:out value="${item.id }" /></td>
		<td><c:out value="${item.methodname }" /></td>
		<td><c:out value="${item.methodresponse }" /></td>		
		<td><c:out value="${item.bz }" /></td>
		<td>
			<a href="#" onclick="edit('${item.id}')">编辑</a>			
		</td></tr>
	</c:forEach>
	</table>
</div>
<%@include file="/WEB-INF/pages/Paginator.jsp"%>