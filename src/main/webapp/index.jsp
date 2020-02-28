<%@ page contentType="text/html;charset=UTF-8" language="java"
         errorPage="page/error.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/tags.tld" prefix="m" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.locbutton.name.ru"
                 var="ru_button"/>
    <fmt:message bundle="${loc}" key="local.locbutton.name.en"
                 var="en_button"/>
    <fmt:message bundle="${loc}" key="local.mainpage" var="main"/>
    <fmt:message bundle="${loc}" key="local.registration" var="registr"/>
    <fmt:message bundle="${loc}" key="local.privpage" var="priv"/>
    <fmt:message bundle="${loc}" key="local.adminpage" var="admin"/>
    <fmt:message bundle="${loc}" key="local.logout" var="logout"/>
    <fmt:message bundle="${loc}" key="local.placeholderlog" var="login"/>
    <fmt:message bundle="${loc}" key="local.placeholderpas" var="pass"/>
    <fmt:message bundle="${loc}" key="local.buttonsignin" var="signin"/>
    <fmt:message bundle="${loc}" key="local.message1index" var="mes1index"/>
    <fmt:message bundle="${loc}" key="local.message2index" var="mes2index"/>
    <fmt:message bundle="${loc}" key="local.buttontarif" var="buttarif"/>

    <link rel="stylesheet" href="webjars/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="webjars/js/jquery-3.4.1.slim.min.js"></script>
    <script src="webjars/js/popper.min.js"></script>
    <script src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="../../docs-assets/ico/favicon.png">
    <link href="webjars/bootstrap/3.3.7/css/bootstrap.css" rel="stylesheet">

    <script type="text/javascript">
        function valid(form) {
            var fail = false;
            var login = form.login.value;
            var password = form.password.value;
            if (login === "" || login === " ") {
                fail = "Please, insert your login";

            } else if (password === "" || password === " ") {
                fail = "Please, insert your password";

            }
            if (fail) {
                alert(fail);
                return false;
            }


        }


    </script>

    <title>My Telecom </title>
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
            <a href="#" class="navbar-brand">My Telecom</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">${main}</a></li>
                <c:if test="${user==null}">
                    <li><a href="registration">${registr}</a></li>
                </c:if>
                <c:if test="${user!=null}">
                    <li><a href="auth_user">${priv}</a></li>
                </c:if>
                <c:if test="${user.role eq 'ADMIN'}">
                    <li><a href="admin">${admin}</a></li>
                </c:if>
            </ul>
            <c:if test="${user!=null}">
                <div class="navbar-right">
                    <form action="controller" method="get">
                        <input type="hidden" name="command" value="logout"/>
                        <input class="btn btn-success" type="submit" value="${logout}"/>
                    </form>
                </div>
            </c:if>

            <c:if test="${user==null}">
                <form class="navbar-form navbar-right" role="form" action="controller" method="post"
                      id="form" onsubmit="return valid(document.getElementById('form'))">
                    <input type="hidden" name="command" value="authorization"/>
                    <div class="form-group">
                        <input type="text" placeholder="${login}" class="form-control" name="login" id="login"/>
                    </div>
                    <div class="form-group">
                        <input type="password" placeholder="${pass}" class="form-control" name="password"
                               id="password"/>
                    </div>

                    <div class="form-group">
                        <input type="submit" class="btn btn-success" value="${signin}"/>
                    </div>
                </form>
            </c:if>
            <div class="row navbar-right">
                <form action="controller" method="post">
                    <input type="hidden" name="local" value="ru"/>

                    <input type="submit" class="btn btn-xs btn-default " value="${ru_button}"/>

                </form>

                <form action="controller" method="post">
                    <input type="hidden" name="local" value="en"/>
                    <input type="submit" class="btn-default btn btn-xs" value="${en_button}"/>
                </form>
            </div>

        </div>
    </div>

</nav>
<br>
<br>
<br>
<div style="color: red" align="right"><c:out value="${sessionScope.loginErrorMessage}"/></div>
<c:remove var="loginErrorMessage" scope="session"/>
<div style="color: orangered" align="center"><c:out value="${sessionScope.errorMessage}"/>
    <c:remove var="errorMessage" scope="session"/></div>
<br>
<div class="container"><h3 align="right"><m:date/></h3></div>

<div class="jumbotron">

    <div class="container">
        <h1>${mes1index}</h1>
        <p>${mes2index}</p>
        <form action="controller" method="get">
            <input type="hidden" name="command" value="show_tarifs"/>
            <input class="btn btn-info btn-lg" role="button" type="submit" name="show tarifs"
                   value="${buttarif} &raquo;"/>
        </form>

    </div>

</div>

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
