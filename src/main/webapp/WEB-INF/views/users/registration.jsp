<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        <%@include file="../styles/button.css"%>
        body {
            font-family: Arial, Helvetica, sans-serif;
            background-color: #ffffff;
        }
        * {
            box-sizing: border-box;
        }
        .container {
            padding: 16px;
            background-color: white;
        }
        input[type=text], input[type=password] {
            width: 100%;
            padding: 15px;
            margin: 5px 0 22px 0;
            display: inline-block;
            border: none;
            background: #f1f1f1;
        }
        input[type=text]:focus, input[type=password]:focus {
            background-color: #ddd;
            outline: none;
        }
        hr {
            border: 1px solid #f1f1f1;
            margin-bottom: 25px;
        }
        a {
            color: dodgerblue;
        }
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
