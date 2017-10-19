<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/resources/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/resources/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="./dashboard"> Application - Computer Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>Add Computer</h1>
					<form action="addComputer" method="POST">
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> 
								<input type="text" class="form-control" id="computerName"
									placeholder="Computer name" name="computerName" value="${out.print(companyId)}">
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date</label> 
								<input type="date" class="form-control" id="introduced"
									placeholder="Introduced date" name="introduced" value="${introduced}">
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date</label> 
								<input type="date" class="form-control" id="discontinued"
									placeholder="Discontinued date" name="discontinued" value="${discontinued}">
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> <select class="form-control" id="companyId" name="companyId">
									<option value="${noCompany}">--</option>
									<c:forEach items="${companies}" var="item">
										<option value="${item.id}" ${item.id == companyId ? selected : "" }>${item.name}</option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<c:if test="${not empty msg}">
							<div class="msg"><p>${msg}</p></div>
						</c:if>
						<div class="msg"><p>${out.print(introduced)}</p></div>
						<div class="actions pull-right">
							<input type="submit" value="Add" class="btn btn-primary"> or <a href="./dashboard"
								class="btn btn-default">Cancel</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
	<script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/dashboard.js"></script>

</body>
</html>