<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/WEB-INF/pages/header.jsp" />
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<div class="alert alert-danger">
				<spring:message code="error.message.500"/> 
				
			</div>
			<div class="alert alert-danger">
				<c:set var="exception" value="${requestScope['javax.servlet.error.exception']}"/>
				<c:out value="${exception.message}"/>
				<c:forEach items="${exception.stackTrace}" var="element">
				   <c:out value="${element}" />
				</c:forEach>
			</div>
		</div>
	</section>
	<jsp:include page="/WEB-INF/pages/footer.jsp" />
</body>
</html>