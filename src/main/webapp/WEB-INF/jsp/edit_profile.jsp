<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" errorPage="/page/error.jsp" %>
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
    <style>
        body {
            padding: 5%;
        }
    </style>
    <script type="text/javascript">
        function valid(form) {
            var fail = false;
            var name = form.name.value;
            var surname = form.surname.value;
            var phone = form.phone.value;
            var email = form.email.value;
            var password1 = form.password1.value;
            var password2 = form.password2.value;
            var email_pattern = /^[A-Z0-9._%+-]+@[A-Z0-9-]+.+.[A-Z]{2,4}$/i;
            var phone_pattern =/^\+?(\d{1,3})?[- .]?\(?(?:\d{2,3})\)?[- .]?\d\d\d[- .]?\d\d\d\d$/;
            var password_pattern =/[0-9a-zA-Z!@#$%^&*]{6,}/;
            var login_pattern = /[0-9a-zA-Z]{4,10}/;
            if (name === "" || name === " ") {
                fail = "Name is incorrect";
            } else if (surname === "" || surname === " ") {

                fail = "Surname is incorrect";
            } else if (phone_pattern.test(phone) === false) {

                fail = "Incorrect phone format";
            } else if (email_pattern.test(email) === false) {
                fail="Incorrect email";

            }

            else if(password1!==password2){
                fail="Passwords not equals";
            }
            if(fail){
                alert(fail);
                return false;
            }

        }
    </script>

    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.mainpage" var="main"/>
    <fmt:message bundle="${loc}" key="local.registration" var="registr"/>
    <fmt:message bundle="${loc}" key="local.privpage" var="priv"/>
    <fmt:message bundle="${loc}" key="local.adminpage" var="admin"/>
    <fmt:message bundle="${loc}" key="local.editpage" var="edit"/>
    <fmt:message bundle="${loc}" key="local.cancel" var="cancel"/>
    <fmt:message bundle="${loc}" key="local.logout" var="logout"/>
    <fmt:message bundle="${loc}" key="local.updateinfo" var="update"/>
    <fmt:message bundle="${loc}" key="local.login" var="login"/>
    <fmt:message bundle="${loc}" key="local.name" var="name"/>
    <fmt:message bundle="${loc}" key="local.surname" var="surname"/>
    <fmt:message bundle="${loc}" key="local.phone" var="phone"/>
    <fmt:message bundle="${loc}" key="local.email" var="email"/>
    <fmt:message bundle="${loc}" key="local.password1" var="password1"/>
    <fmt:message bundle="${loc}" key="local.password2" var="password2"/>
    <fmt:message bundle="${loc}" key="local.message1edit" var="editmess1"/>
    <fmt:message bundle="${loc}" key="local.message2edit" var="editmess2"/>
    <fmt:message bundle="${loc}" key="local.message3user" var="userinfo"/>

    <title>${edit}</title>
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
            <li>
                <form action="controller" method="get">
                    <input type="hidden" name="command" value="show_tarifs">
                    <input type="submit" value="Tariffs" class="btn-link">
                </form>
            </li>
            <li class="disabled"><a href="#">${registr}</a></li>
            <li class="active"><a href="#">${edit} </a></li>
            <li><a href="auth_user">${priv}</a></li>
            <c:if test="${user.role eq 'ADMIN'}">
                <li><a href="admin">${admin}</a></li>
            </c:if>
        </ul>
    </div>
    <div class="container">
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
        <h1>${editmess1}</h1>
        <p>${editmess2}</p>
    </div>
</div>


<div class="container">

    <div class="row">
        <div class="col-md-6">
            <h2>${userinfo}</h2>
            <p style="color: red">${sessionScope.updateMessage}</p>
            <c:remove var="updateMessage" scope="session"/>
            <p style="color: red">${sessionScope.errorPasswordMessage}</p>
            <c:remove var="errorPasswordMessage" scope="session"/>


            <form action="controller" method="post" id="update"  onsubmit="return valid(document.getElementById('update'))">
                <input type="hidden" name="command" value="update_user"/>
                <div class="form-group"><label data-toggle="tooltip" title="You can't change your login"> ${login}: </label>
                    ${user.getLogin()}<br></div>
                <div class="form-group"><label data-toggle="tooltip" title="${name}">  ${name}:</label>
                    <input type="text" data-toggle="tooltip" title="${surname}" id="name" name="name" value="${user.getName()}"></div>
                <div class="form-group"><label>  ${surname}:</label>
                    <input type="text" data-toggle="tooltip" title="${surname}" id="surname" name="surname" value="${user.getSurname()}"></div>
                <div class="form-group"><label> ${phone}:</label>
                    <input type="text" data-toggle="tooltip" title="${phone}" id="phone" name="phone" value="${user.getPhone()}"></div>
                <div class="form-group"><label>    ${email}:</label>
                    <input type="text" data-toggle="tooltip" title="${email}" id="email" name="email" value="${user.getEmail()}"></div>

                <div class="form-group"><label>   ${password1}:</label>
                    <input type="password" data-toggle="tooltip" title="${password1}>6" id="password1" name="password1" value=""></div>
                <p style="color: red">${passwordError}</p>
                <div class="form-group"><label>  ${password2}:</label>
                    <input type="password" data-toggle="tooltip" title="${password1}>6" id="password2" name="password2" value=""></div>

                <div class="form-group form-inline"><input class="btn btn-success" type="submit" value="${update}"/></div>
                <div class="form-group form-inline" >
                    <form action="auth_user" method="get">
                    <input type="hidden" name="command" value="cancel"/>
                    <input type="submit" class="btn btn-danger" value="${cancel}"/>
                </form>
                </div>
            </form>


        </div>

    </div>
</div>
<br>
<br>
<footer class="footer mt-auto py-3 ">
    <div class="container">
        <hr>
        <span class="text-muted">&copy; EPAM 2020</span>
        <hr>
        <br>
        <br></div>

</footer>
<script src="webjars/jquery-1.10.2.min.js"></script>
<script src="webjars/dist/js/bootstrap.min.js"></script>
</body>
</html>





