<%@ tag body-content="scriptless" language="java" pageEncoding="UTF-8"%>
<%@ attribute name="target" required="true" rtexprvalue="true"%>
<%@ attribute name="page" required="true" rtexprvalue="true" type="Long"%>
<%@ attribute name="limit" required="true" rtexprvalue="true" type="Long"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<ul class="pagination">

	<c:if test="${ page > 1 }">
		<li><a href="${pageScope.target}?page=1" aria-label="Previous"> <span aria-hidden="true">&laquo;</span></a></li>
	</c:if>

	<c:choose>
		<c:when test="${ page > 3 && limit > 5 }">
			<c:set var="startDrawIndex" value="${ page - 2 }" />
		</c:when>
		<c:otherwise>
			<c:set var="startDrawIndex" value="1" />
		</c:otherwise>
	</c:choose>

	<c:forEach var="i" begin="${startDrawIndex}" end="${startDrawIndex + 4}" step="1">
		<c:if test="${i <= limit}">
		
			<c:choose>
				<c:when test="${ page == i }">
					<c:set var="active" value="class=\"active\""/>
				</c:when>
				<c:otherwise>
					<c:set var="active" value=""/>
				</c:otherwise>
			</c:choose>
			
			<li ${ active }>
			<%-- 			<c:out value="${ page == i ? <li class=\"active\"> : <li> }"/> --%>
			<a href="${pageScope.target}?page=${i}"> <c:out value="${page == i ? [i] : i}" /></a>
			</li>
			
		</c:if>
	</c:forEach>

	<c:if test="${page < limit}">
		<li><a href="${pageScope.target}?page=${pageScope.limit}#" aria-label="Next"> <span aria-hidden="true">&raquo;</span></a></li>
	</c:if>
</ul>