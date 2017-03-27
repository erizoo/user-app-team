<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>
        Main page
    </title>
</head>
<body>

<h1>Main page</h1>
<div>
    Hello. click link bellow to see full users list:
    <a href="<c:url value='/api/users'/>">Click me</a><br>
</div>
<div>
    To login with Facebook click link below:
    <a href="<c:url value='api/login/facebook'/>">Login</a>
</div>
<div>
    To logout with Facebook click link below:
    <a href="<c:url value='api/logout/facebook'/>">Logout</a>
</div>
<div>
    To login with Instagram click link below:
    <a href="<c:url value='api/login/instagram'/>">Login</a>
</div>
<div>
    To logout with Instagram click link below:
    <a href="<c:url value='api/logout/instagram'/>">Logout</a>
</div>
<br>
<div>
    To check current Instagram user use link below:
    <a href="<c:url value='api/current-user'/>">Checking</a>
</div>
</body>
</html>