<<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
    <link rel="stylesheet" href="webjars/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="webjars/js/jquery-3.4.1.slim.min.js"></script>
    <script src="webjars/js/popper.min.js"></script>
    <script src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="../../docs-assets/ico/favicon.png">
    <link href="webjars/bootstrap/3.3.7/css/bootstrap.css" rel="stylesheet">

    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="localization.local" var="loc" />

    <fmt:message bundle="${loc}" key="local.mainpage" var="main"/>
    <fmt:message bundle="${loc}" key="local.registration" var="registr"/>
    <fmt:message bundle="${loc}" key="local.tarif" var="tarif"/>
    <fmt:message bundle="${loc}" key="local.logout" var="logout"/>
    <fmt:message bundle="${loc}" key="local.adminpage" var="admin"/>
    <fmt:message bundle="${loc}" key="local.privpage" var="priv"/>
    <fmt:message bundle="${loc}" key="local.errorpage" var="error"/>
    <fmt:message bundle="${loc}" key="local.message" var="hello"/>
    <fmt:message bundle="${loc}" key="local.message2error" var="mess2"/>
    <fmt:message bundle="${loc}" key="local.gotomain" var="gotomain"/>

    <title>${error}</title>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">

    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a href="main" class="navbar-brand">My Telecom</a>
    </div>
    <div>
        <ul class="nav navbar-nav">
            <li><a href="main">${main}</a></li>
            <li><form action="controller" method="get">
                <input type="hidden" name="command" value="show_tarifs">
                <input type="submit" value="${tarif}" class="btn-link">
                </form></li>
            <li><a href="registration">${registr}</a></li>
            <li class="active"><a href="#">${error} </a></li>
        </ul>
    </div>
</nav>

<div class="jumbotron">
    <div class="container">
        <c:if test="${user!=null}">
            <h2>${hello}, ${user.getName()}!</h2>
        </c:if>
        <p>${mess2}</p>
        <p style="color: red"><c:out value="${sessionScope.errorMessage}"/></p>
        <c:remove var="errorMessage" scope="session"/>
    </div>

    </form>
    <form action="main" method="get">
        <input type="hidden" name="command" value="cancel"/>
        <input type="submit" class="btn btn-success" value="${gotomain} &raquo;" />
    </form>
</div>





<footer class="footer mt-auto py-3 navbar-fixed-bottom">
    <div class="container">
        <hr>
        <span class="text-muted">&copy; EPAM 2020</span>
        <hr>
        <br>
        <br>
    </div>

</footer>
<script src="webjars/jquery-1.10.2.min.js"></script>
<script src="webjars/dist/js/bootstrap.min.js"></script>
</body>
</html>
