<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="domain.User"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	HashMap<String, User> userList = (HashMap<String, User>) application.getAttribute("userList");
%>

<c:choose>
	<c:when test="${ empty applicationScope.userList }">
				<div style="text-align:center;">尚无在线用户</div>
	</c:when>
</c:choose>

<c:forEach items="${ applicationScope.userList}" var="userItem"
	begin="0" end="10" step="1" varStatus="userStatus">
	<li>${userItem.value.name}<c:choose>
			<c:when test=" ${userItem.value.gender.equals('男')}">
                ♂
            </c:when>
			<c:when test=" ${userItem.value.gender.equals('女')}">
                ♀
            </c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>

	</li>
</c:forEach>
