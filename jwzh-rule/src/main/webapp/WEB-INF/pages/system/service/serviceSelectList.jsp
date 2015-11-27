<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<select id="serviceid" name="serviceid" class="form-control" >
<c:forEach items="${List}" var="item" varStatus="status">
	<option value="${item.id }">${item.servicename}</option>
</c:forEach>
</select>