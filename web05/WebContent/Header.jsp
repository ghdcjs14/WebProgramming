<%@ page import="spms.vo.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:useBean id="member" scope="session" class="spms.vo.Member"/> 
<%
	// jsp 액션태그 사용으로 주석처리
	//Member member = (Member)session.getAttribute("member");
%>

<div style="background-color:#00008b; color:#ffffff; height:20px; padding:5px;">
	SPMS(Simple Project Management System)
	<% if(member.getEmail() != null) { %>
	<span style="float:right;">
		<%=member.getName() %>
		<a style="color:white;" href="<%=request.getContextPath() %>/auth/logout">로그아웃</a>
	</span>
	<% } %>	
</div>