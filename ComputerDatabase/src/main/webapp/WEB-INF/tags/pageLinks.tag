<%@ tag body-content="scriptless" language="java" pageEncoding="UTF-8"%>
<%@ attribute name="target" required="true" rtexprvalue="true" type="String"%>
<%@ attribute name="current_page" required="true" rtexprvalue="true" type="Long"%>
<%@ attribute name="pages_limit" required="true" rtexprvalue="true" type="Long"%>
<%@ attribute name="pagination_size" required="true" rtexprvalue="true" type="Long"%>
<%@ attribute name="params" required="false" rtexprvalue="true" type="String"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
	<c:when test="${ current_page > 3 }">
		<c:set var="startDrawIndex" value="${ current_page - 2 }" />			
	</c:when>
	<c:otherwise>
		<c:set var="startDrawIndex" value="1" />
	</c:otherwise>
</c:choose>
<c:set var="endDrawIndex" value="${startDrawIndex + 4}" />

<ul class="pagination">
	<c:if test="${ current_page > 1 }">
		<li><a href="${target}?page=1&pagination=${pagination_size}${params}" aria-label="First"> <span aria-hidden="true">&laquo;</span></a></li>
	</c:if>

	<c:forEach var="i" begin="${startDrawIndex}" end="${endDrawIndex}" step="1">
		<c:if test="${i <= pages_limit}">
		
			<c:choose>
				<c:when test="${ current_page == i }">
					<c:set var="active" value="class=\"active\""/>
				</c:when>
				<c:otherwise>
					<c:set var="active" value=""/>
				</c:otherwise>
			</c:choose>
			
			<li ${ active }>
				<a href="${target}?page=${i}&pagination=${pagination_size}${params}"> <c:out value="${current_page == i ? [i] : i}" /></a>
			</li>
			
		</c:if>
	</c:forEach>
	
	<c:if test="${current_page < pages_limit}">
		<li><a href="${target}?page=${pages_limit}&pagination=${pagination_size}${params}" aria-label="Last"> <span aria-hidden="true">&raquo;</span></a></li>
	</c:if>
</ul>