<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Java Team</title>

    <!-- Bootstrap core CSS -->
    <c:set var="contextPath" value="${pageContext.request.contextPath}"/>
    <link rel="stylesheet" href="${contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${contextPath}/css/bootstrap-social.css"/>
    <link rel="stylesheet" href="${contextPath}/css/docs.css"/>
    <link rel="stylesheet" href="${contextPath}/css/font-awesome.css"/>

</head>

<body>

<div class="container" style="width: 300px;">
    <c:url value="/j_spring_security_check" var="loginUrl"/>
    <c:url value="/api/login/facebook" var="loginFacebook"/>
    <c:url value="/api/login/instagram" var="loginInstagram"/>
    <form action="${loginUrl}" method="post">
        <center><h2 class="form-signin-heading">Java Team</h2></center>
        <input type="text" class="form-control" name="j_username" placeholder="Логин"><br>
        <input type="password" class="form-control" name="j_password" placeholder="Пароль"><br>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Go!</button>
    </form>
    <form action="${loginFacebook}" method="get"><br>
        <button class="btn btn-social btn-facebook" type="submit">Sign in with Facebook</button>
    </form>
    <form action="${loginInstagram}" method="get"><br>
        <button class="btn btn-social btn-instagram" type="submit">Sign in with Instagram</button>
    </form>

</div>

</body>
</html>