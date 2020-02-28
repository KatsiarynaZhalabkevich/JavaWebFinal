<%@page import="by.epam.web.unit6.bean.User" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" errorPage="/page/error.jsp" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <script type="text/javascript">
        function checkBalance(form) {
            var fail = false;
            var balance = form.balance;

            if (balance < 0) {
                fail = "Your balance < 0. Please, update balance to delete your account!"
            }
            if (fail) {
                alert(fail);
                return false;
            }

        }
    </script>

    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>

    <fmt:message bundle="${loc}" key="local.mainpage" var="main"/>
    <fmt:message bundle="${loc}" key="local.registration" var="registr"/>
    <fmt:message bundle="${loc}" key="local.tarif" var="tarif"/>
    <fmt:message bundle="${loc}" key="local.logout" var="logout"/>
    <fmt:message bundle="${loc}" key="local.message1admin" var="mes1admin"/>
    <fmt:message bundle="${loc}" key="local.message2user" var="mes2user"/>
    <fmt:message bundle="${loc}" key="local.message3user" var="mes3user"/>
    <fmt:message bundle="${loc}" key="local.privpage" var="priv"/>
    <fmt:message bundle="${loc}" key="local.login" var="login"/>
    <fmt:message bundle="${loc}" key="local.name" var="name"/>
    <fmt:message bundle="${loc}" key="local.surname" var="surname"/>
    <fmt:message bundle="${loc}" key="local.phone" var="phone"/>
    <fmt:message bundle="${loc}" key="local.email" var="email"/>
    <fmt:message bundle="${loc}" key="local.accountstat" var="account"/>
    <fmt:message bundle="${loc}" key="local.butteditinfo" var="edit"/>
    <fmt:message bundle="${loc}" key="local.balance" var="balance"/>
    <fmt:message bundle="${loc}" key="local.buttupdbalance" var="buttupdbalance"/>
    <fmt:message bundle="${loc}" key="local.message4user" var="mess4user"/>
    <fmt:message bundle="${loc}" key="local.nametarif" var="nametarif"/>
    <fmt:message bundle="${loc}" key="local.description" var="descrip"/>
    <fmt:message bundle="${loc}" key="local.price" var="price"/>
    <fmt:message bundle="${loc}" key="local.buttactivate" var="buttactiv"/>
    <fmt:message bundle="${loc}" key="local.message5user" var="mess5user"/>
    <fmt:message bundle="${loc}" key="local.date" var="date"/>
    <fmt:message bundle="${loc}" key="local.buttdeactiv" var="buttdeactiv"/>
    <fmt:message bundle="${loc}" key="local.adminpage" var="admin"/>


    <link rel="stylesheet" href="webjars/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="webjars/js/jquery-3.4.1.slim.min.js"></script>
    <script src="webjars/js/popper.min.js"></script>
    <script src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="../../docs-assets/ico/favicon.png">
    <link href="webjars/bootstrap/3.3.7/css/bootstrap.css" rel="stylesheet">

    <title>${priv}</title>
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
                <li class="active"><a href="#">${priv}</a></li>
                <c:if test="${user.role eq 'ADMIN'}">
                    <li><a href="admin">${admin}</a></li>
                </c:if>
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
        <h1>${mes1admin}, ${user.name}!</h1>
        <p>${mes2user} </p>


    </div>
</div>
<div style="color: red" align="center"><c:out value="${sessionScope.errorMessage}"/></div>
<c:remove var="errorMessage" scope="session"/>
<p style="color: blue" align="center">${sessionScope.updateMessage}</p>
<c:remove var="updateMessage" scope="session"/>

<div class="container">

    <div class="row">
        <div class="col-md-6">
            <h2>${mes3user}</h2>

            <form>
                <div class="form-group"><label>${login}: </label>${user.login}</div>
                <div class="form-group"><label> ${name}: </label>
                    ${user.name}</div>
                <div class="form-group"><label>${surname}: </label>
                    ${user.surname}</div>
                <div class="form-group"><label>${phone}: </label>
                    ${user.phone}</div>
                <div class="form-group"><label> ${email}: </label>
                    ${user.email} </div>
                <div class="form-group"><label>${account}: </label> ${user.active}</div>
            </form>
            <form action="controller" method="post">
                <input type="hidden" name="command" value="edit_profile">
                <input class="btn btn-success" type="submit" value="${edit}"/>
            </form>
        </div>
        <div class="col-md-6">

            <form action="controller" method="post">
                <p style="color: blue">${sessionScope.updBalanceMessage}</p>
                <c:remove var="updBalanceMessage" scope="session"/>
                <h2> ${balance}:</h2>
                <input type="hidden" name="command" value="update_balance"/>
                <p>${user.balance}</p>
                <input type="text" name="balance"/>
                <input class="btn btn-success" type="submit" name="send" value="${buttupdbalance}">

            </form>
            <br>

        </div>

    </div>
</div>
<div class="container">

    <div class="row">
        <div class="col-md-6">
            <h2>${mess4user}</h2>
            <c:if test="${tarifs!=null}">
                <div class="table-responsive">
                    <table>
                        <tr>
                            <td>
                                <c:if test="${sessionScope.pageNum>1}">
                                    <form action="controller" method="get">
                                        <input type="hidden" name="command" value="tariff_pagination"/>
                                        <input type="submit" class="btn-link" style="color: black" value="prev"/>
                                        <c:set scope="session" var="pageNum" value="${sessionScope.pageNum-1}"/>
                                        <c:set scope="session" var="goToPage" value="auth_user"/>
                                    </form>
                                </c:if>
                            </td>
                            <td>
                                <a href="#" style="color: black"><c:out value="${sessionScope.pageNum}"/> </a>
                            </td>
                            <td>
                                <c:if test="${!sessionScope.isLastPage}">
                                    <form action="controller" method="get">
                                        <input type="hidden" name="command" value="tariff_pagination"/>
                                        <input type="submit" class="btn-link" style="color: black" value="next"/>
                                        <c:set scope="session" var="pageNum" value="${pageNum+1}"/>
                                        <c:set scope="session" var="goToPage" value="auth_user"/>
                                    </form>
                                </c:if>
                            </td>
                        </tr>
                    </table>
                </div>
                <table class="table table-striped table-responsive">
                    <tr class="active">
                        <div class="form-group">
                            <th><label>№</label></th>
                        </div>
                        <div class="form-group">
                            <th>${nametarif}</th>
                        </div>
                        <div class="form-group">
                            <th>${descrip}</th>
                        </div>
                        <div class="form-group">
                            <th>${price}</th>
                        </div>
                        <div class="form-group">
                            <th></th>
                        </div>
                    </tr>

                    <c:forEach var="tarifList" items="${tarifs}" varStatus="counter">
                        <tr>
                            <td>${counter.count}</td>
                            <td> ${tarifList.name}</td>

                            <td>${tarifList.description}</td>

                            <td>${tarifList.price}</td>
                            <td>

                                <form action="controller" method="post">
                                    <input type="hidden" name="command" value="add_note">
                                    <input type="hidden" name="tarif_id" value="${tarifList.id}">
                                    <c:if test="${user.active!='false'}">
                                        <input type="submit" class="btn btn-info" value="${buttactiv}">
                                    </c:if>
                                    <c:if test="${user.active=='false'}">
                                        <input type="submit" class="btn btn-info disabled" data-toggle="tooltip"
                                               title="Your account is blocked! Check your balance or contact admin"
                                               value="${buttactiv}">
                                    </c:if>
                                </form>
                            </td>

                        </tr>
                    </c:forEach>


                </table>
            </c:if>
        </div>
        <div class="col-md-6">

            <h2> ${mess5user}:</h2>
            <c:if test="${userTarifs!=null}">

                <table class="table table-responsive table-striped">
                    <tr class="active">
                        <div class="form-group">
                            <th><label>№</label></th>
                        </div>
                        <div class="form-group">
                            <th><label>${nametarif}</label></th>
                        </div>
                        <div class="form-group">
                            <th><label>${price}</label></th>
                        </div>
                        <div class="form-group">
                            <th><label>${date}</label></th>
                        </div>
                        <th></th>
                    </tr>

                    <c:forEach var="tariffs" items="${userTarifs}" varStatus="counter">

                        <tr>

                            <div class="form-group">
                                <td>${counter.count} </td>
                            </div>
                            <div class="form-group">
                                <td>${tariffs.name}</td>
                            </div>
                            <div class="form-group">
                                <td>${tariffs.price}</td>
                            </div>
                            <div class="form-group">
                                <td>${tariffs.date}</td>
                            </div>

                            <td>
                                <form action="controller" method="post">
                                    <input type="hidden" name="command" value="delete_note">
                                    <input type="hidden" name="note_id" value="${tariffs.noteId}">
                                    <input type="submit" class="btn btn-info" value="${buttdeactiv}">
                                </form>
                            </td>
                        </tr>

                    </c:forEach>

                </table>
            </c:if>

            <br>

        </div>

    </div>

    <br><br>
    <c:if test="${user.id>0}">

        <form action="controller" method="get" id="delete"
              onsubmit="return checkBalance(document.getElementById('delete'))">
            <input type="hidden" name="command" value="delete_user">
            <input type="hidden" name="user_id" value="${user.id}">
            <input type="hidden" name="balance" value="${user.balance}">
            <input type="submit" class="btn-link" value="You can delete your account">
        </form>
    </c:if>

</div>
<br>

<footer class="footer mt-auto py-3 ">
    <div class="container">
        <hr>
        <span class="text-muted">&copy; EPAM 2020</span>
        <hr>

    </div>

</footer>


</body>
</html>
