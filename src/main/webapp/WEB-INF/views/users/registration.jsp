<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<h1>Register here:</h1>
<form method="post" action="${pageContext.request.contextPath}/users/registration">
    Login: <input type="text" required autofocus placeholder="login" name="login"> <br/> <br/>
    Password: <input type="password" required autofocus minlength="4" placeholder="password" name="password"> <br/> <br/>
    Repeat password: <input type="password" autofocus required minlength="4" placeholder="password" name="repeatPassword">
    <h4 style="color:red"> ${errorMessage} </h4>
    <button type="submit">Register</button>
</form>
</body>
</html>--%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        body {
            font-family: Arial, Helvetica, sans-serif;
            background-color: #ffffff;
        }

        * {
            box-sizing: border-box;
        }

        /* Add padding to containers */
        .container {
            padding: 16px;
            background-color: white;
        }

        /* Full-width input fields */
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

        /* Overwrite default styles of hr */
        hr {
            border: 1px solid #f1f1f1;
            margin-bottom: 25px;
        }

        /* Set a style for the submit button */
        .registerbtn {
            background-color: #34432f;
            color: white;
            padding: 16px 20px;
            margin: 8px 0;
            border: none;
            cursor: pointer;
            width: 50%;
            opacity: 0.9;
            float: left;
        }

        .registerbtn:hover {
            opacity: 1;
        }

        /* Add a blue text color to links */
        a {
            color: dodgerblue;
        }

        /* Set a grey background color and center the text of the "sign in" section */
        .signin {
            background-color: #f1f1f1;
            text-align: center;
        }
    </style>
</head>
<body>

<form method="post" action="${pageContext.request.contextPath}/users/registration">
    <div class="container">
        <h1>Register</h1>
        Login
        <input type="text" placeholder="Login" name="login" required>

        Password
        <input type="password" required minlength="4" placeholder="Enter Password" name="password" required>

        Repeat Password
        <input type="password" required minlength="4" placeholder="Repeat Password" name="repeatPassword" required>
        <h4 style="color:red"> ${errorMessage} </h4>
        <button type="submit" class="registerbtn">Register</button>
    </div>

</form>

</body>

<!-- Mirrored from www.w3schools.com/howto/tryit.asp?filename=tryhow_css_register_form by HTTrack Website Copier/3.x [XR&CO'2014], Sun, 22 Sep 2019 08:50:28 GMT -->
</html>