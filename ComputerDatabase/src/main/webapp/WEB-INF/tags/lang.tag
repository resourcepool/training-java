<%@ tag body-content="scriptless" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<ul class="nav navbar-nav navbar-right">
	<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
			<spring:message code="language" />
			<img class="flag" src="https://lipis.github.io/flag-icon-css/flags/4x3/fr.svg" alt="France Flag">
			<img class="flag" src="https://lipis.github.io/flag-icon-css/flags/4x3/gb.svg" alt="United Kingdom Flag">
			<span class="caret"></span>
		</a>
		<ul class="dropdown-menu">
			<li><a href="?lang=en">English</a></li>
			<li><a href="?lang=fr">Français</a></li>
		</ul></li>
</ul>