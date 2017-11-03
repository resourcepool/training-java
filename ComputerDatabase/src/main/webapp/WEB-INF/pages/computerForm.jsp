<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${empty edit}">
	<c:set var="edit" value="false"/>
</c:if>

<c:if test="${empty name}">
	<c:set var="name" value="false"/>
</c:if>

<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/WEB-INF/pages/header.jsp" />
	<link href="<c:url value ="/resources/css/cdbForm.css"/>" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<c:if test="${ not empty msg }">
				<div class="alert ${ success ? 'alert-success' : 'alert-danger' }">
					<p><c:out value="${ msg }" /></p>
				</div>
			</c:if>
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>${ edit ? 'Edit Computer' : 'Add Computer' }</h1>
					<form action="${ edit ? editComputer : addComputer }" method="POST" name="${edit ? editComputer : addComputer}">
						<c:if test="${ edit }">
							<input type="hidden" value="${ id }" id="id" />
						</c:if>
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label>
								<input type="text" class="form-control" id="computerName" placeholder="Computer name" name="computerName" value="${ computerName }">
							</div>
							<div class="form-group">
								<label for="introduced"> Introduced date <small>(dd-MM-yyyy)</small>
								</label>
								<input type="date" class="form-control" id="introduced" placeholder="Introduced date" name="introduced" value="${ introduced }">
							</div>
							<div class="form-group">
								<label for="discontinued"> Discontinued date <small>(dd-MM-yyyy)</small>
								</label>
								<input type="date" class="form-control" id="discontinued" placeholder="Discontinued date" name="discontinued" value="${ discontinued }">
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> <select class="form-control" id="companyId" name="companyId">
									<option value="--">--</option>
									<c:forEach items="${ companies }" var="item">
										<option value="${ item.id }" ${item.id eq companyId ? 'selected' : '' }> ${ item.name } </option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input id="btnSubmit" type="submit" value="${ edit ? 'Edit' : 'Add' }" class="btn btn-primary">
							or <a href="./dashboard" class="btn btn-default">Cancel</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
	
	<jsp:include page="/WEB-INF/pages/footer.jsp" />
	<script src="<c:url value ="/resources/js/addComputerForm.js"/>"></script>
</body>
</html>