<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${ not empty msg }">
	<div class="alert ${ success ? 'alert-success' : 'alert-danger' }">
		<p><c:out value="${ msg }" /></p>
	</div>
</c:if>