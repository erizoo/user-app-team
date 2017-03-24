<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<script src="${contextPath}/js/script.js"></script>
    <head>
        <title>Chat</title>
    </head>
    <body>

        <table>
            <tr>
                <td colspan="2">
                    <input type="text" id="username" placeholder="Username"/>
                    <button type="button" onclick="connect();" >Connect</button>
                </td>
            </tr>
            <tr>
                <td>
                    <textarea readonly="true" rows="10" cols="80" id="log"></textarea>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="text" size="15" id="to" placeholder="To"/>
                    <input type="text" size="51" id="msg" placeholder="Message"/>
                    <button type="button" onclick="send();" >Send</button>
                </td>
            </tr>
        </table>
    </body>



</html>
