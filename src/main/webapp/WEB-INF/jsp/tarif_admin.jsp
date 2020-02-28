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

    <script type="text/javascript">
        function valid(form) {
            var fail = false;
            var name = form.name.value;
            var description = form.description.value;
            var speed = form.speed.value;
            var discount =form.discount.value;
            var price = form.price.value;

            if (name === "" || name === " ") {
                fail = "Name is incorrect!";
            } else if (description === "" || description === " ") {
                fail = "Description is incorrect!";
            } else if (speed ==="" && speed ===" ") {
                fail = "Speed is incorrect!"
            } else if (price ==="" && price ===" ") {
                fail = "Price is incorrect!";
            } else if (discount ==="" && discount ===" ") {
                fail = "Discount is incorrect!";
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
    <title>${admin}</title>
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
                <li class="disabled"><a href="#">${registr}</a></li>
                <li><a href="admin">${admin}</a></li>
                <li><a href="auth_user">${priv}</a></li>
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

        <p>${mess1} </p>

    </div>
</div>
<div>
    <p style="color: red" align="left">${sessionScope.errorMessage}</p>
    <c:remove var="errorMessage" scope="session"/>
    <p style="color: #4cae4c" align="left">${sessionScope.editMessage}</p>
    <c:remove var="editMessage" scope="session"/>
    <p style="color: #4cae4c" align="left">${sessionScope.deleteMessage}</p>
    <c:remove var="deleteMessage" scope="session"/>

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
                        <c:set scope="session" var="pageNum" value="${pageNum+1}"/>
                    </form>
                </c:if>
            </td>
        </tr>
    </table>
</div>
<div class="table-responsive">
    <table class="table table-striped ">
        <tr class="active">
            <th>#</th>
            <th>${name}</th>
            <th>${descr}</th>
            <th>${speed}</th>
            <th>${price}, $</th>
            <th>${discount}</th>
            <th></th>
            <th></th>
        </tr>


        <c:forEach var="tarifList" items="${tarifs}">
            <tr>
                <form action="controller" method="post" id="edit" onsubmit="return valid(document.getElementById('edit'))">
                    <input type="hidden" name="command" value="edit_tarif">
                    <td>${tarifList.id}</td>
                    <td><input type="text"  name="name" value="${tarifList.name}"/></td>
                    <td><input type="text"  name="description" value="${tarifList.description}"/></td>
                    <td><input type="text"  name="speed" value=" ${tarifList.speed}"/></td>
                    <td><input type="text"  name="price" value="${tarifList.price}"/></td>
                    <td><input type="text"  name="discount" value="${tarifList.discount}"/></td>
                    <input type="hidden" name="tarif_id" value="${tarifList.id}">
                    <td><input type="submit" class="btn btn-md btn-info" value="${edit}"></td>
                </form>
                <td>
                    <form action="controller" method="post">
                        <input type="hidden" name="command" value="delete_tarif">
                        <input type="hidden" name="tarif_id" value="${tarifList.id}">
                        <input type="submit" class="btn btn-md btn-danger" value="${delete}">
                    </form>

                </td>

            </tr>

        </c:forEach>
        <tr>
            <form action="controller" method="post" id="add" onsubmit="return valid(document.getElementById('add')) ">
                <input type="hidden" name="command" value="add_tarif">
                <td></td>
                <td><input type="text" id="name"  placeholder="${name}" class="form-control" name="name"/></td>
                <td><input type="text" id="description"  placeholder="${descr}" class="form-control"
                           name="description"/></td>
                <td><input type="number" id="speed"  placeholder="${speed}" class="form-control" name="speed"/></td>
                <td><input type="number" id="price"  placeholder="${price}" class="form-control" name="price"/></td>
                <td><input type="number" id="discount"  placeholder="${discount}" class="form-control" name="discount"/>
                </td>
                <td><input type="submit" class="btn btn-md btn-success" value="${addtariff}"></td>
            </form>
        </tr>
    </table>
</div>

<form action="admin" method="get">
    <input type="hidden" name="command" value="cancel"/>
    <input type="submit" class="btn btn-success" value="${back}"/>
</form>


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
