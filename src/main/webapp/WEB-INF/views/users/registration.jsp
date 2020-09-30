<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        <%@include file="../styles/button.css"%>
        <%@include file="../styles/registration.css"%>

    </style>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/users/registration">
    <div class="container">
        <h1>Register</h1>
        <h4 ></h4>
        Login
        <input type="text"  required placeholder="Login" name="login" value=${login}>
        <h4 style="color:red"> ${errorLoginMessage} </h4>
        Password
        <input type="password" required minlength="4"  required placeholder="Enter Password" name="password">
        <h4 ></h4>
        Repeat Password
        <input type="password" required minlength="4" placeholder="Repeat Password" name="repeatPassword" required>
        <h4 style="color:red"> ${errorMessage} </h4>
        <button type="submit" class="button">Register</button>
    </div>
</form>
</body>
</html>
