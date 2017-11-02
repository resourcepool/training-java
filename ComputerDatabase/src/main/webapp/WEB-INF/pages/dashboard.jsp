<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<link href="<c:url value ="/resources/css/bootstrap.min.css"/>" rel="stylesheet" media="screen">
<link href="<c:url value ="/resources/css/font-awesome.css"/>" rel="stylesheet" media="screen">
<link href="<c:url value ="/resources/css/main.css"/>" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<h1 id="homeTitle">${page.totalCount} Computers found</h1>
			<c:if test="${ not empty msg }">
				<div class="alert ${ success ? 'alert-success' : 'alert-danger' }">
					<p><c:out value="${ msg }" /></p>
				</div>
			</c:if>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">
						<input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" value="${ page.search }"/>
						<input type="submit" id="searchsubmit" value="Filter by name" class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addComputer">Add Computer</a> <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
			</div>
		</div>
		<form id="deleteComputerForm" action="#" method="POST">
			<input type="hidden" name="computer_selection_delete" value="">
		</form>
		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th class="editMode" style="width: 60px; height: 22px;">
							<input type="checkbox" id="selectall" />
							<span style="vertical-align: top;">
								- <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();"> <i class="fa fa-trash-o fa-lg"></i>
								</a>
							</span>
						</th>
						<c:set var="sort_name" value="${ page.order eq 'ASC' && page.columnSort eq 'computerName' ? 'DESC' : 'ASC' }"/>
						<c:set var="sort_introduced" value="${ page.order eq 'ASC' && page.columnSort eq 'introduced' ? 'DESC' : 'ASC' }"/>
						<c:set var="sort_discontinued" value="${ page.order eq 'ASC' && page.columnSort eq 'discontinued' ? 'DESC' : 'ASC' }"/>
						<c:set var="sort_company" value="${ page.order eq 'ASC' && page.columnSort eq 'company' ? 'DESC' : 'ASC' }"/>
						
						<th><a href="dashboard?sort=computerName&order=${sort_name}">${sort_name}</a> Computer name</th>
						<th><a href="dashboard?sort=introduced&order=${ sort_introduced }">${ sort_introduced }</a> Introduced date</th>
						<th><a href="dashboard?sort=discontinued&order=${ sort_discontinued }">${ sort_discontinued }</a> Discontinued date</th>
						<th><a href="dashboard?sort=company&order=${ sort_company }">${ sort_company }</a> Company</th>
						<th class="editMode" style="width: 60px; height: 22px;">Delete company ?</th>
					</tr>
				</thead>
				<tbody id="results">
					<c:forEach var="computer" items="${computers}">
						<tr>
							<td class="editMode">
								<input type="checkbox" name="cb" class="cb" value="${computer.getId()}">
							</td>
							<td>
								<a href="editComputer?id=${computer.getId()}" onclick=""><c:out value="${computer.getName()}" /></a>
							</td>
							<td>
								<c:out value="${computer.getIntroduced()}" />
							</td>
							<td>
								<c:out value="${computer.getDiscontinued()}" />
							</td>
							<td>
								<c:out value="${computer.getCompany().getName()}" />
							</td>
							<td class="editMode">
								<c:if test="${ not empty computer.company.id }">
									<%-- 									<a onclick="$.fn.confirm();" href="deleteCompany?id=${ computer.getCompany().getId() }" class="btn btn-default"> Delete Company </a> --%>
									<form id="deleteCompanyForm" action="dashboard" method="POST" onsubmit="return $.fn.confirmDeleteCompany();">
										<input type="hidden" name="company_id_delete" value="${ computer.getCompany().getId() }" />
										<input class="btn btn-default" type="submit" id="btnSubmit" value="Delete Company" />
									</form>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>
	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<mylib:pageLinks target="dashboard" current_page="${page.currentPage}" pages_limit="${page.totalPages}" pagination_size="${page.pageSize}" params="${params}"/>
			<mylib:pagination page_count="${page.pageSize}" params="${params}"/>
		</div>
	</footer>
	<script src="<c:url value ="/resources/js/jquery.min.js"/>"></script>
	<script src="<c:url value ="/resources/js/bootstrap.min.js"/>"></script>
	<script src="<c:url value ="/resources/js/dashboard.js"/>"></script>
</body>
</html>