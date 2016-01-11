<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div style="background-color:#00008b; color:#ffffff; height:20px; padding:5px;">
	SPMS(Simple Project Management System)
	<c:choose>
		<c:when test="${member.email != '' }">
			<span style="float:right;">
				${member.name }
				<a style="color:white;" href="${pageContext.request.contextPath }/auth/logout.do">로그아웃</a>
			</span>
		</c:when>
	</c:choose>
</div>