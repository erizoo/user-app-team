<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3>Hello, ${name}</h3>

<h4>Here is your friends:</h4>
<ul>
    <c:forEach items="${friends}" var="friend">
        <li><c:out value="${friend.id}, ${friend.name}"/></li>
    </c:forEach>
</ul>
</body>
</html>
