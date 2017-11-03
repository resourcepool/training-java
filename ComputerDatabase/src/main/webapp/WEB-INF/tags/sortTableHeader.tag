<%@ tag body-content="scriptless" language="java" pageEncoding="UTF-8" %>
<%@ attribute name="content" required="true" rtexprvalue="true" type="String" %> 
<%@ attribute name="column_name" required="true" rtexprvalue="true" type="String" %> 
<%@ attribute name="current_sort" required="true" rtexprvalue="true" type="String" %> 
<%@ attribute name="current_order" required="true" rtexprvalue="true" type="String" %>
<%@ attribute name="params" required="false" rtexprvalue="true" type="String" %>

<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<c:set var="sort_name" value="${ current_order eq 'ASC' && current_sort eq column_name ? 'DESC' : 'ASC' }" />
						
<th ${column_name eq current_sort ? 'class="active"' : '' }>
	<a href="dashboard?sort=${column_name}&order=${sort_name}${params}">
	<c:choose>
	<c:when test="${column_name eq current_sort}">
		<span class="${current_order eq 'ASC' ? 'glyphicon glyphicon-arrow-up' : 'glyphicon glyphicon-arrow-down'} "></span>
	</c:when>
	<c:otherwise>
		<span class="glyphicon glyphicon-sort"></span>
	</c:otherwise>
	</c:choose>
	
	</a> ${ content }
</th>