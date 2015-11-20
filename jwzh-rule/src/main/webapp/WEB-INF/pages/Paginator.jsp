<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String contextPath = request.getContextPath();
%>

<script type="text/javascript" src="<%=contextPath%>/js/jqPaginator.js"></script>

<!-- 分页处理 -->
<script type="text/javascript">
var totalCount=${Paginator.totalCount };//分页的总条目数
var pageIndex=${Paginator.pageIndex };
var pageSize=${Paginator.pageSize };//每一页的条目数
var visiblePages=${Paginator.visiblePages };//设置最多显示的页码数

if(totalCount=="" || totalCount == undefined)	totalCount=0;
if(pageIndex == "" || pageIndex == undefined) pageIndex=1;
if(pageSize == "" || pageSize == undefined) pageSize=10;
if(visiblePages == "" || visiblePages == undefined) visiblePages=5;

	$("#PaginatorDiv").jqPaginator({
		totalCounts : totalCount,
	    currentPage : pageIndex,
	    pageSize : pageSize,
	    visiblePages : visiblePages,
	    
	    
	    first: '<li class="first"><a href="javascript:void(0);">首页</a></li>',
	    prev: '<li class="prev"><a href="javascript:void(0);">上一页</a></li>',
	    next: '<li class="next"><a href="javascript:void(0);">下一页</a></li>',
	    last: '<li class="last"><a href="javascript:void(0);">尾页</a></li>',
	    page: '<li class="page"><a href="javascript:void(0);">{{page}}</a></li>',
	    onPageChange : onPageChange
	});

</script>

<div align="center" >
	<ul class="pagination" id="PaginatorDiv">
	</ul>
</div>