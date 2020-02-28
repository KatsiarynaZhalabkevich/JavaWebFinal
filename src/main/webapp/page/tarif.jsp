<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/tags.tld" prefix='mytag' %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
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
    <fmt:message bundle="${loc}" key="local.description" var="descr"/>
    <fmt:message bundle="${loc}" key="local.price" var="price"/>
    <fmt:message bundle="${loc}" key="local.speed" var="speed"/>
    <fmt:message bundle="${loc}" key="local.discount" var="discount"/>
    <fmt:message bundle="${loc}" key="local.edit" var="edit"/>
    <fmt:message bundle="${loc}" key="local.delete" var="delete"/>
    <fmt:message bundle="${loc}" key="local.addtariff" var="addtariff"/>
    <fmt:message bundle="${loc}" key="local.message1tariffadmin" var="mess1"/>
    <fmt:message bundle="${loc}" key="local.prevpage" var="prev"/>
    <fmt:message bundle="${loc}" key="local.nextpage" var="next"/>
    <fmt:message bundle="${loc}" key="local.back" var="back"/>
    <title>${tarif}</title>
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
                <li class="active"><a href="#">${tarif}</a></li>
                <li class=""><a href="registration">${registr}</a></li>
                <li class="disabled"><a href="#">${priv} </a></li>
            </ul>
        </div>
        <c:if test="${user!=null}">
            <div class="navbar-right">
                <form action="controller" method="get">
                    <input type="hidden" name="command" value="logout"/>
                    <input class="btn btn-success" type="submit" value="${logout}"/>
                </form>
            </div>
        </c:if>
    </div>
</nav>
<div class="jumbotron">
    <div class="container">
        <br>

        <p>${mess1} </p>

    </div>
</div>
<div class="table-responsive">
    <table>
        <tr>
            <td>
                <c:if test="${sessionScope.pageNum>1}">
                    <form action="controller" method="get">
                        <input type="hidden" name="command" value="show_tarifs"/>
                        <input type="hidden" name="pageNum" value="${sessionScope.pageNum-1}" >
                        <input type="submit" class="btn-link" style="color: black" value="${prev}"/>
                        <c:set scope="session" var="pageNum" value="${sessionScope.pageNum-1}"/>
                    </form>
                </c:if>
            </td>
            <td>
                <form action="#">
                    <input type="submit" class="btn-link" style="color: black" value="${sessionScope.pageNum}">
                </form>
            </td>
            <td>
                <c:if test="${!sessionScope.isLastPage}">
                    <form action="controller" method="get">
                        <input type="hidden" name="command" value="show_tarifs"/>
                        <input type="hidden" name="pageNum" value="${sessionScope.pageNum+1}" >
                        <input type="submit" class="btn-link" style="color: black" value="${next}"/>
                        <c:set scope="session" var="pageNum" value="${sessionScope.pageNum+1}"/>
                    </form>
                </c:if>
            </td>
        </tr>
    </table>
</div>
<div class="table-responsive">
    <table class="table table-striped">
        <tr class="active">
            <th>№</th>
            <th> ${name} </th>
            <th>${descr}</th>
            <th>${speed}</th>
            <th>${price}, $</th>
            <th>${discount}</th>
            <th></th>
        </tr>

        <jsp:useBean id="userbean" class="by.epam.web.unit6.tag.JSPListBean" scope="session"/>
        <mytag:tariff list="${sessionScope.userbean}" num="${sessionScope.userbean.size}"/>
    </table>
</div>




<footer class="footer mt-auto py-3">
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
