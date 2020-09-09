<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<h1>Register here:</h1>
<form method="post" action="${pageContext.request.contextPath}/users/registration">
    Login: <input type="text" name="login"> <br/> <br/>
    Password: <input type="password" placeholder="password" name="password"> <br/> <br/>
    Repeat password: <input type="password" placeholder="password" name="repeatPassword">
    <h4 style="color:red"> ${errorMessage} </h4>
    <button type="submit">Register</button>
</form>
</body>
</html>
