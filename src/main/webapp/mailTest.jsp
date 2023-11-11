<%--
  Created by IntelliJ IDEA.
  User: robin
  Date: 29/10/2023
  Time: 18:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Mail test</title>
    <script>
        function sendTestMail() {
            let mailTestParams = {
                "to": "jeewebproject@gmail.com",
                "subject": "Sujet de test",
                "body": "Ceci est un corps de texte"
            }
            let statusTextDiv = document.getElementById("statusText");

            fetch("send-mail", {
                mode: "cors", // no-cors, *cors, same-origin
                cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
                credentials: "same-origin", // include, *same-origin, omit
                headers: {
                    "Content-Type": "application/json",
                    // 'Content-Type': 'application/x-www-form-urlencoded',
                },
                redirect: "follow", // manual, *follow, error
                referrerPolicy: "no-referrer", // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
                body: JSON.stringify(mailTestParams), // body data type must match "Content-Type" header
                method: "POST", // *GET, POST, PUT, DELETE, etc.
            }).catch((err) => {
                statusTextDiv.innerText = "ERROR: " + err;
            });
        }
    </script>
</head>
<body>
<div id="statusText"></div>
<button onclick="sendTestMail();">SEND MAIL</button>

</body>
</html>
