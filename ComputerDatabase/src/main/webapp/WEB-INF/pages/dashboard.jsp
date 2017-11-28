<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/pages/header.jsp" />
<script type="text/javascript">
  var lang = new Array();
  lang['toogle.on'] = "<spring:message code='button.hide' javaScriptEscape='true' />";
  lang['toogle.off'] = "<spring:message code='button.edit' javaScriptEscape='true' />";
  lang['confirm.computer'] = "<spring:message code='confirm.computers' javaScriptEscape='true' />";
  lang['confirm.company'] = "<spring:message code='confirm.company' javaScriptEscape='true' />";
</script>
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="/ComputerDatabase/dashboard"> Application - Computer Database </a>
			<mylib:lang/>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<h1 id="homeTitle">
				<spring:message code="dashboard.count" arguments="${page.totalCount}" />
			</h1>
			<mylib:showMsg />
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">
						<input type="search" id="searchbox" name="search" class="form-control" placeholder="<spring:message code="dashboard.search"/>" value="${ page.search }" />
						<input type="hidden" name="sort" value="${ page.formSort }" />
						<input type="hidden" name="order" value="${ page.order }" />
						<input type="submit" id="searchsubmit" value="<spring:message code="button.search"/>" class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="/ComputerDatabase/add-computer">
						<spring:message code="button.add" />
					</a>
					<a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">
						<spring:message code="button.edit" />
					</a>
				</div>
			</div>
		</div>
		<form id="deleteComputerForm" action="/ComputerDatabase/dashboard/delete-computer" method="POST">
			<input type="hidden" name="computer_selection_delete" value="">
			<input type="hidden" name="search" value="${ page.search }" />
			<input type="hidden" name="sort" value="${ page.formSort }" />
			<input type="hidden" name="order" value="${ page.order }" />
			<input type="hidden" name="page" value="${ page.currentPage }" />
			<input type="hidden" name="pagination" value="${ page.pageSize }" />
		</form>
		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th class="editMode" style="width: 60px; height: 22px;">
							<input type="checkbox" id="selectall" />
							<span style="vertical-align: top;"> - <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
									<i class="fa fa-trash-o fa-lg"></i>
								</a>
							</span>
						</th>
						<mylib:sortTableHeader content="computer.name" column_name="computerName" current_order="${ page.order }" current_sort="${ page.formSort }" params="${sortparams}" />
						<mylib:sortTableHeader content="computer.introduced" column_name="introduced" current_order="${ page.order }" current_sort="${ page.formSort }" params="${sortparams}" />
						<mylib:sortTableHeader content="computer.discontinued" column_name="discontinued" current_order="${ page.order }" current_sort="${ page.formSort }" params="${sortparams}" />
						<mylib:sortTableHeader content="computer.company" column_name="company" current_order="${ page.order }" current_sort="${ page.formSort }" params="${sortparams}" />
						<th class="editMode" style="width: 60px; height: 22px;"><spring:message code="dashboard.delete.company"/></th>
					</tr>
				</thead>
				<tbody id="results">
					<c:forEach var="computer" items="${computers}">
						<tr>
							<td class="editMode">
								<input type="checkbox" name="cb" class="cb" value="${computer.getId()}">
							</td>
							<td>
								<a href="edit-computer?id=${computer.getId()}" onclick="">
									<c:out value="${computer.getName()}" />
								</a>
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
									<form id="deleteCompanyForm" action="/ComputerDatabase/dashboard/delete-company" method="POST" onsubmit="return $.fn.confirmDeleteCompany();">
										<input type="hidden" name="company_id_delete" value="${ computer.getCompany().getId() }" />
										<input class="btn btn-default" type="submit" id="btnSubmit" value="<spring:message code="dashboard.delete.company"/>" />
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
			<mylib:pageLinks target="dashboard" current_page="${page.currentPage}" pages_limit="${page.totalPages}" pagination_size="${page.pageSize}" params="${pageparams}" />
			<mylib:pagination page_count="${page.pageSize}" params="${pageparams}" />
		</div>
	</footer>
	<jsp:include page="/WEB-INF/pages/footer.jsp" />
	<script src="<c:url value ="/resources/js/dashboard.js"/>"></script>
</body>
</html>