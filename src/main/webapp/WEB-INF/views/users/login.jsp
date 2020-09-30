<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        <%@include file="../styles/login.css"%>
    </style>
</head>
<body>
<h1>Login</h1>
<form action="${pageContext.request.contextPath}/users/login" method="post">
    <h4 style="color:red"> ${errorMsg} </h4>
    Login </br>
    <input type="text" required placeholder=" Enter login" name="login" required value=${login}> </br>
    Password </br>
    <input type="password" required placeholder="Enter password" name="password" required> </br>
    <button type="submit">Login</button>
    </br>
    <a href="${pageContext.request.contextPath}/users/registration">Register</a>
    </div>
</form>
</body>
</html>
