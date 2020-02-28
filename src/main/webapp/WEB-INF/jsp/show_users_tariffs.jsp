<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="/page/error.jsp"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
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
    <style>
        body {
            padding: 5px;
        }
    </style>

    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="localization.local" var="loc" />

    <fmt:message bundle="${loc}" key="local.mainpage" var="main"/>
    <fmt:message bundle="${loc}" key="local.registration" var="registr"/>
    <fmt:message bundle="${loc}" key="local.tarif" var="tarif"/>
    <fmt:message bundle="${loc}" key="local.logout" var="logout"/>
    <fmt:message bundle="${loc}" key="local.adminpage" var="admin"/>
    <fmt:message bundle="${loc}" key="local.privpage" var="priv"/>
    <fmt:message bundle="${loc}" key="local.nametarif" var="name"/>
    <fmt:message bundle="${loc}" key="local.description" var="descr"/>
    <fmt:message bundle="${loc}" key="local.price" var="price"/>
    <fmt:message bundle="${loc}" key="local.date" var="date"/>

    <fmt:message bundle="${loc}" key="local.back" var="back"/>

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
                <li>
                    <form action="controller" method="get">
                        <input type="hidden" name="command" value="show_tarifs">
                        <input type="submit" value="${tarif}" class="btn-link">
                    </form>
                </li>
                <li class="disabled"><a href="#">${registr}</a></li>
                <li ><a href="admin">${admin} </a></li>
                <li ><a href="auth_user">${priv} </a></li>
                <li ><a href="statistic" >${statistic}</a></li>
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
<div class="jumbotron">
    <div class="container">
<!-- проверить информацию -->

        <h2>  ${userName.name} ${userName.surname} tariffs list! </h2>

    </div>
</div>
<div class="container">

    <div class="col-md-8">

        <table class="table table-striped">
            <tr class="active">
                <th>${name}</th>
                <th>${descr}</th>
                <th>${price}, $</th>
                <th>${date}</th>


            </tr>

            <c:forEach var="tariffs" items="${userTarifList}">
                <tr>
                    <td> ${tariffs.name}</td>

                    <td>${tariffs.description}</td>

                    <td>${tariffs.price}</td>
                    <td>${tariffs.date}</td>

                </tr>
            </c:forEach>

        </table>
    </div>
    <br>
    <form action="users" method="get">
        <input type="submit" class="btn btn-success" value="${back}"/>
    </form>
</div>

<script src="webjars/jquery-1.10.2.min.js"></script>
<script src="webjars/dist/js/bootstrap.min.js"></script>
</body>
</html>
