<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
			<h1 id="homeTitle">${page.totalCount} Computers found</h1>
			<c:if test="${ not empty msg }">
				<div class="alert ${ success ? 'alert-success' : 'alert-danger' }">
					<p><c:out value="${ msg }" /></p>
				</div>
			</c:if>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">
						<input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" value="${ page.search }" />
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
							<span style="vertical-align: top;"> - <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();"> <i class="fa fa-trash-o fa-lg"></i>
							</a>
							</span>
						</th>
						<mylib:sortTableHeader content="Computer name" column_name="computerName" current_order="${ page.order }"  current_sort="${ page.columnSort }"/>
						<mylib:sortTableHeader content="Introduced date" column_name="introduced" current_order="${ page.order }"  current_sort="${ page.columnSort }"/>
						<mylib:sortTableHeader content="Discontinued date" column_name="discontinued" current_order="${ page.order }"  current_sort="${ page.columnSort }"/>
						<mylib:sortTableHeader content="Company" column_name="company" current_order="${ page.order }"  current_sort="${ page.columnSort }"/>
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
			<mylib:pageLinks target="dashboard" current_page="${page.currentPage}" pages_limit="${page.totalPages}" pagination_size="${page.pageSize}" params="${params}" />
			<mylib:pagination page_count="${page.pageSize}" params="${params}" />
		</div>
	</footer>
	
	<jsp:include page="/WEB-INF/pages/footer.jsp" />
	<script src="<c:url value ="/resources/js/dashboard.js"/>"></script>
</body>
</html>