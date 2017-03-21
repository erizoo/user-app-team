<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
<!-- Bootstrap core CSS -->
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<link rel="stylesheet" href="${contextPath}/css/bootstrap.min.css"/>
<link rel="stylesheet" href="${contextPath}/css/bootstrap-social.css"/>
<link rel="stylesheet" href="${contextPath}/css/docs.css"/>
<link rel="stylesheet" href="${contextPath}/css/font-awesome.css"/>

<div class="alert alert-danger">
    <strong>Error!</strong> Check your username or password. <a href="/">You can return to the authorization page.</a>
</div>

</body>
</html>
