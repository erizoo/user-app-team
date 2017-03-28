<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Chat WebSocket</title>
    <c:set var="contextPath" value="${pageContext.request.contextPath}"/>
    <script src="${contextPath}/js/sockjs-0.3.4.js"></script>
    <script src="${contextPath}/js/stomp.js"></script>
    <script src="${contextPath}/js/jquery-3.2.0.min.js"></script>
    <script src="${contextPath}/js/ion.sound.js"></script>
    <link rel="stylesheet" href="${contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${contextPath}/css/bootstrap-social.css"/>
    <script type="text/javascript">
        ion.sound({
            sounds: [
                {
                    name: "beer_can_opening"
                }
            ],
            volume: 0.5,
            path: "${contextPath}/js/sounds",
            preload: true
        });
        var stompClient = null;

        function setConnected(connected) {
            document.getElementById('connect').disabled = connected;
            document.getElementById('disconnect').disabled = !connected;
            document.getElementById('conversationDiv').style.visibility
                = connected ? 'visible' : 'hidden';
            document.getElementById('response').innerHTML = '';
        }

        function connect() {
            var socket = new SockJS('/chat');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function(frame) {
                setConnected(true);
                console.log('Connected: ' + frame);
                stompClient.subscribe('/topic/messages', function(messageOutput) {
                    showMessageOutput(JSON.parse(messageOutput.body));
                });
            });
        }

        function disconnect() {
            if(stompClient != null) {
                stompClient.disconnect();
            }
            setConnected(false);
            console.log("Disconnected");
        }

        function sendMessage() {
            var from = document.getElementById('from').value;
            var text = document.getElementById('text').value;
            stompClient.send("/app/chat", {},
                JSON.stringify({'from':from, 'text':text}));
            $('#text').val('');
        }

        function showMessageOutput(messageOutput) {
            var response = document.getElementById('response');
            var p = document.createElement('p');
            p.style.wordWrap = 'break-word';
            p.appendChild(document.createTextNode(messageOutput.from + ": "
                + messageOutput.text + " (" + messageOutput.time + ")"));
            response.appendChild(p);
            ion.sound.play("beer_can_opening");
        }

    </script>

</head>
<body onload="disconnect()">
<div class="container" style="align-content: center"><br />
    <div class="form-group row">
        <div class="col-sm-3">
        <input class="form-control" type="text" id="from" placeholder="Choose a nickname"/>
        </div>
    </div>
    <div>
        <button class="btn btn-success" id="connect" onclick="connect();">Connect</button>
        <button class="btn btn-warning" id="disconnect" disabled="disabled" onclick="disconnect();">
            Disconnect
        </button>
    </div>
    <br />
    <div class="input-group" id="conversationDiv">
        <input class="form-control" type="text" id="text" placeholder="Write a message..."
               onkeydown="if (event.keyCode == 13){document.getElementById('sendMessage').click(); return false;}"/>
        <span class="input-group-btn">
        <button class="btn btn-info" id="sendMessage" onclick="sendMessage();"><span class="glyphicon glyphicon-search" aria-hidden="true"></span> Send</button>
        </span>
    </div> <br>
        <p class="lead" id="response"></p>

</div>

</body>
</html>


</html>
