<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:if test="${empty edit}">
	<c:set var="edit" value="false" />
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
			<mylib:showMsg />
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>
						<spring:message code="${ edit ? 'dashboard.edit.title' : 'dashboard.add.title' }" />
					</h1>
					<form action="${ edit ? 'edit-computer' : 'add-computer' }" method="POST" name="${ edit ? 'edit-computer' : 'add-computer'}">
						<fieldset>
							<c:if test="${ edit }">
								<input type="hidden" id="id" name="id" value="${ id }" />
							</c:if>
							<div class="form-group">
								<label for="introduced">
									<spring:message code="computer.introduced" />
								</label>
								<input type="date" class="form-control" id="introduced" placeholder="Introduced date" name="introduced" value="${ introduced }">
							</div>
							<div class="form-group">
								<label for="discontinued">
									<spring:message code="computer.discontinued" />
								</label>
								<input type="date" class="form-control" id="discontinued" placeholder="Discontinued date" name="discontinued" value="${ discontinued }">
							</div>
							<div class="form-group">
								<label for="companyId">
									<spring:message code="computer.company" />
								</label>
								<select class="form-control" id="companyId" name="companyId">
									<option value="--">--</option>
									<c:forEach items="${ companies }" var="item">
										<option value="${ item.id }" ${ item.id eq companyId ? 'selected' : '' }>${ item.name }</option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input id="btnSubmit" type="submit" value="<spring:message code="${ edit ? 'button.edit' : 'button.add' }"/>" class="btn btn-primary">
							or
							<a href="dashboard" class="btn btn-default">
								<spring:message code="button.cancel" />
							</a>
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