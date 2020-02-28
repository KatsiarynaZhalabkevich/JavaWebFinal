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
    <link rel="shortcut icon" href="webjars/docs-assets/ico/favicon.png">
    <link href="webjars/bootstrap/3.3.7/css/bootstrap.css" rel="stylesheet">
    <style>
        body {
            padding: 5px;
        }
    </style>


    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>

    <fmt:message bundle="${loc}" key="local.mainpage" var="main"/>
    <fmt:message bundle="${loc}" key="local.registration" var="registr"/>
    <fmt:message bundle="${loc}" key="local.tarif" var="tarif"/>
    <fmt:message bundle="${loc}" key="local.logout" var="logout"/>
    <fmt:message bundle="${loc}" key="local.adminpage" var="admin"/>
    <fmt:message bundle="${loc}" key="local.privpage" var="priv"/>
    <fmt:message bundle="${loc}" key="local.showuser" var="title"/>
    <fmt:message bundle="${loc}" key="local.message1admin" var="hello"/>
    <fmt:message bundle="${loc}" key="local.message1showusers" var="mess1"/>
    <fmt:message bundle="${loc}" key="local.message3admin" var="mess2"/>
    <fmt:message bundle="${loc}" key="local.message4admin" var="mes4admin"/>
    <fmt:message bundle="${loc}" key="local.payment" var="payment"/>
    <fmt:message bundle="${loc}" key="local.block" var="block"/>
    <fmt:message bundle="${loc}" key="local.name" var="name"/>
    <fmt:message bundle="${loc}" key="local.surname" var="surname"/>
    <fmt:message bundle="${loc}" key="local.phone" var="phone"/>
    <fmt:message bundle="${loc}" key="local.email" var="email"/>
    <fmt:message bundle="${loc}" key="local.balancename" var="balance"/>
    <fmt:message bundle="${loc}" key="local.activ" var="active"/>
    <fmt:message bundle="${loc}" key="local.blockstatus" var="blockstat"/>
    <fmt:message bundle="${loc}" key="local.status" var="status"/>
    <fmt:message bundle="${loc}" key="local.updstatus" var="updstatus"/>
    <fmt:message bundle="${loc}" key="local.buttupdbalance" var="updbalance"/>
    <fmt:message bundle="${loc}" key="local.userstariffs" var="usertariff"/>
    <fmt:message bundle="${loc}" key="local.deleteuser" var="deleteuser"/>
    <fmt:message bundle="${loc}" key="local.prevpage" var="prev"/>
    <fmt:message bundle="${loc}" key="local.nextpage" var="next"/>
    <fmt:message bundle="${loc}" key="local.back" var="back"/>

    <title>${title}</title>
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
                        <input type="hidden" name="command" value="show_tarifs"/>
                        <input type="submit" value="${tarif}" class="btn-link"/>
                    </form>
                </li>
                <li class="disabled"><a href="#">${registr}</a></li>
                <li class="active"><a href="admin">${admin} </a></li>
                <li class="active"><a href="auth_user">${priv} </a></li>
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

<div class="jumbotron">
    <div class="container">
        <h1>${hello}, ${user.getName()}!</h1>
        <p>${mess1} </p>

    </div>
</div>

<!-- сделать красивое размещение кнопок -->

<div class="container">

    <div class="row">
        <div class="col-md-12">
            <h2>${mess2}</h2>
            <p>${mes4admin}</p>
            <br>
            <div>
                <p style="color: #4cae4c">${sessionScope.paymentMessage}</p>
                <c:remove var="paymentMessage" scope="session"/>
                <p style="color: #4cae4c">${sessionScope.blockMessage}</p>
                <c:remove var="blockMessage" scope="session"/>
                <p style="color: #4cae4c">${sessionScope.updBalanceMessage}</p>
                <c:remove var="updBalanceMessage" scope="session"/>
                <p style="color: #4cae4c">${sessionScope.updStatusMessage}</p>
                <c:remove var="updStatusMessage" scope="session"/>
                <p style="color: #4cae4c">${sessionScope.deleteUserMessage}</p>
                <c:remove var="deleteUserMessage" scope="session"/>
                <p style="color: #4cae4c">${sessionScope.errorMessage}</p>
                <c:remove var="errorMessage" scope="session"/>
            </div>
            <div>
                <form action="controller" method="post">
                    <input type="hidden" name="command" value="payment">
                    <input type="submit" class=" btn btn-danger" value="${payment}">
                </form>
            </div>
            <div>
                <form action="controller" method="post">
                    <input type="hidden" name="command" value="block">
                    <input type="submit" class="btn btn-danger" value="${block}">
                </form>
            </div>
            <div>

                <c:if test="${usersList!= null}">
                    <div class="table-responsive">
                        <table>
                            <tr>
                                <td> <!-- переделать кнопки -->

                                    <c:if test="${sessionScope.pageNumU>1}">
                                        <form action="controller" method="get"  >
                                            <input type="hidden" name="command" value="user_pagination"/>
                                            <input type="hidden" name="pageNumU" value="${sessionScope.pageNumU-1}">
                                            <input type="submit" class="btn-link" style="color: black" value="${prev}"/>
                                        </form>
                                    </c:if>
                                </td>
                                <td>
                                    <form action="#">
                                        <input type="submit" class="btn-link" style="color: black" value="${sessionScope.pageNumU}">
                                    </form>
                                </td>
                                <td>
                                    <c:if test="${!sessionScope.isLastPageU}">
                                        <form action="controller" method="get" >
                                            <input type="hidden" name="command" value="user_pagination"/>
                                            <input type="hidden" name="pageNumU" value="${sessionScope.pageNumU+1}" >
                                            <input type="submit" class="btn-link" style="color: black" value="${next}"/>
                                        </form>
                                    </c:if>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <table class="table-responsive table-striped table-bordered">
                        <tr class="active">
                            <th>№</th>
                            <th>${name}</th>
                            <th>${surname}</th>
                            <th>${phone}</th>
                            <th>${email}</th>
                            <th>${balance}, $</th>
                            <th>${status}</th>
                            <th>${tarif}</th>
                            <th></th>
                        </tr>
                        <c:forEach var="users" items="${usersList}" varStatus="counter">
                            <tr>
                                <td> ${counter.count}</td>
                                <td>${users.name}</td>
                                <td>${users.surname}</td>
                                <td>${users.phone}</td>
                                <td>${users.email}</td>
                                <td>
                                    <form action="controller" method="post">
                                            ${users.balance}<br>
                                        <input type="hidden" name="command" value="change_balance">
                                        <input type="hidden" name="user_id" value="${users.id}">
                                        <input type="hidden" name="old_balance" value="${users.balance}">
                                        <input type="text" name="balance" value=""><br>
                                        <input type="submit" class="btn btn-info btn-md" name="upd"
                                               value="${updbalance}">
                                    </form>
                                </td>
                                <td>

                                    <form action="controller" method="post">
                                        <input type="hidden" name="command" value="change_status">
                                        <input type="hidden" name="user_id" value="${users.id}">
                                        <c:choose>
                                            <c:when test="${users.active=='true'}">
                                                <input type="radio" class="radio-button" name="active" value="active"
                                                       checked>${active}</input>
                                                <input type="radio" сlass="radio-button active" name="active"
                                                       value="blocked">${blockstat}</input>
                                            </c:when>
                                            <c:otherwise>
                                                <input type="radio" class="radio-button" name="active"
                                                       value="active">${active}</input>
                                                <input type="radio" сlass="radio-button active" name="active"
                                                       value="blocked"
                                                       checked>${blockstat}</input>
                                            </c:otherwise>
                                        </c:choose>

                                        <input type="submit" class="btn btn-info btn-md" name="" value="${updstatus}">
                                    </form>
                                </td>

                                <td>

                                    <form action="controller" method="get">
                                        <input type="hidden" name="command" value="show_user_tarif">
                                        <input type="hidden" name="user_id" value="${users.id}">
                                        <input type="submit" class="btn btn-info btn-md" value="${usertariff}">
                                    </form>

                                </td>

                                <td>
                                    <form action="controller" method="post">
                                        <input type="hidden" name="command" value="delete_user">
                                        <input type="hidden" name="user_id" value="${users.id}">
                                        <input type="submit" class="btn btn-danger btn-xs" name=""
                                               value="${deleteuser}">
                                    </form>

                                </td>

                            </tr>
                        </c:forEach>
                    </table>

                </c:if>

            </div>


        </div>


        <form action="admin" method="get">
            <input type="hidden" name="command" value="cancel"/>
            <input type="submit" class="btn btn-success" value="${back}"/>
        </form>
    </div>
</div>

<br>
<br><br>


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