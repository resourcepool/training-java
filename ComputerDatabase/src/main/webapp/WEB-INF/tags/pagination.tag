<%@ tag body-content="scriptless" language="java" pageEncoding="UTF-8" %>
<%@ attribute name="page_count" required="true" rtexprvalue="true" type="Long" %> <!-- count => nb elem / page -->

<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<div class="btn-group btn-group-sm pull-right" role="group">

<c:forTokens var="value" items="10,20,50,100" delims=",">

	<c:choose>
		<c:when test="${ value == page_count }"> <!-- TODO -->
			<c:set var="active" value=" active"/>
		</c:when>
		<c:otherwise>
			<c:set var="active" value=""/>
		</c:otherwise>
	</c:choose>
	
	<button type="button" class="btn btn-default${active}" onclick="location.href='dashboard?pagination=${value}'">${value}</button>
</c:forTokens>
</div>
