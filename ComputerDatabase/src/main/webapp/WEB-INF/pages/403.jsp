<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
				Error 403: Access denied! <br />
				<!-- stacktrace -->
			</div>
		</div>
	</section>
	
	<jsp:include page="/WEB-INF/pages/footer.jsp" />
</body>
</html>