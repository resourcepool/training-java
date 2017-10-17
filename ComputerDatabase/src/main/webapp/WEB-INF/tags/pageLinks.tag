<%@ tag body-content="scriptless" language="java" pageEncoding="UTF-8" %>
<%@ attribute name="target" required="true" rtexprvalue="true" %>
<%@ attribute name="page" required="true" rtexprvalue="true" type="Long"%>
<%@ attribute name="limit" required="true" rtexprvalue="true" type="Long"%>

<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<ul class="pagination">
	
	<% if (page > 1) { %>
		<li><a href="${pageScope.target}?page=1" aria-label="Previous"> <span aria-hidden="true">&laquo;</span></a></li>
	<% } %>

	<% Long start = page > 3 && limit > 5 ? page - 2 : 1; %>
	<% for (Long i = start; i < start + 5 && i <= limit; i++) { %>
		<li><a href="${pageScope.target}?page=<%= i %>">
		
		<% out.print((i == page ? "[" + i + "]" : i.toString())); %>
		
		</a></li>
	<% } %>		
	
	<% if (page < limit) { %>
	<li><a href="${pageScope.target}?page=${pageScope.limit}#" aria-label="Next"> <span aria-hidden="true">&raquo;</span></a></li>
	<% } %>
</ul>