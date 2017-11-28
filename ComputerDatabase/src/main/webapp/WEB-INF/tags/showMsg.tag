<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${ not empty msg || not empty errors }">
<div class="alert ${ not success || not empty errors ? 'alert-danger' : 'alert-success' }">
	<c:if test="${ not empty msg }">
		<p><c:out value="${ msg }" /></p>
	</c:if>
	<c:if test="${ not empty errors }">
		<ul>
			<c:forEach items="${ errors }" var="err">
				<li><c:out value="[ ${ err.getKey() } ]" /> <c:out value="${ err.getValue() }" /></li>
			</c:forEach>
		</ul>
	</c:if>
</div>
</c:if>