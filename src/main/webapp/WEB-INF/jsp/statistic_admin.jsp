<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 24.02.20
  Time: 22:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="/page/error.jsp" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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


    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>

    <fmt:message bundle="${loc}" key="local.mainpage" var="main"/>
    <fmt:message bundle="${loc}" key="local.registration" var="registr"/>
    <fmt:message bundle="${loc}" key="local.tarif" var="tarif"/>
    <fmt:message bundle="${loc}" key="local.logout" var="logout"/>
    <fmt:message bundle="${loc}" key="local.adminpage" var="admin"/>
    <fmt:message bundle="${loc}" key="local.privpage" var="priv"/>
    <fmt:message bundle="${loc}" key="local.nametarif" var="name"/>
    <fmt:message bundle="${loc}" key="local.statistic" var="statistic"/>
    <fmt:message bundle="${loc}" key="local.learnstat" var="learnstat"/>
    <fmt:message bundle="${loc}" key="local.amount" var="amount"/>
    <fmt:message bundle="${loc}" key="local.registerusers" var="users"/>
    <fmt:message bundle="${loc}" key="local.connections" var="connections"/>
    <fmt:message bundle="${loc}" key="local.ratio" var="ratio"/>
    <fmt:message bundle="${loc}" key="local.conn" var="conn"/>
    <fmt:message bundle="${loc}" key="local.cover" var="cover"/>

    <fmt:message bundle="${loc}" key="local.back" var="back"/>

    <title>${statistic}</title>
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
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
                <li ><a href="tarif_admin.jsp">${tarif}</a></li>
                <li class="disabled"><a href="#">${registr}</a></li>
                <li><a href="admin">${admin}</a></li>
                <li><a href="auth_user">${priv}</a></li>
                <li class="active"><a href="#" >${statistic}</a></li>
            </ul>
        </div>

        <div class="navbar-right">
            <form action="controller" method="get">
                <input type="hidden" name="command" value="logout"/>
                <input class="btn btn-success" type="submit" value="${logout}"/>
            </form>
        </div>

    </div>
</nav>
<br>
<br>
<div class="jumbotron">
    <div class="container">

        <p>${learnstat} </p>

    </div>
</div>
<div>


</div>
<div class="container">
    <div class="row">
        <div class="col-md-6">
            <h2>${amount}</h2>
            <div class="table-responsive">

                <table class="table table-striped ">
                    <tr class="active">

                        <th>${users}</th>
                        <th>${tarif}</th>
                        <th>${connections}</th>
                    </tr>
                    <tr>

                        <td>${user_number}</td>
                        <td>${tariff_number}</td>
                        <td>${connectionsCount}</td>

                    </tr>

                </table>
            </div>
        </div>
        <div class="col-md-6">
            <h2>${ratio}</h2>
            <div class="table-responsive">
                <table class="table table-striped ">
                    <tr class="active">

                        <th>${name}</th>
                        <th>${conn}</th>
                        <th>${cover}, %</th>
                    </tr>
                    <c:forEach var="tariffs" items="${tarifs}">
                        <tr>

                            <td>${tariffs.name}</td>
                            <td>${tariff_count[tariffs.id]}</td>
                            <td>${(tariff_count[tariffs.id]/connectionsCount)*100} %</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
    <form action="admin" method="get">
        <input type="hidden" name="command" value="cancel"/>
        <input type="submit" class="btn btn-success" value="${back}"/>
    </form>
</div>
<footer class="footer mt-auto py-3">
    <div class="container">
        <hr>
        <span class="text-muted">&copy; EPAM 2020</span>
        <hr>

    </div>

</footer>
<script src="webjars/jquery-1.10.2.min.js"></script>
<script src="webjars/dist/js/bootstrap.min.js"></script>
</body>

</html>
