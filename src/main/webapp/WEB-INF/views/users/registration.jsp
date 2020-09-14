<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <h4 ></h4>
        Login
        <input type="text" placeholder="Login" name="login" required>
        <h4 style="color:red"> ${errorLoginMessage} </h4>
        Password
        <input type="password" required minlength="4" placeholder="Enter Password" name="password" required>
        <h4 ></h4>
        Repeat Password
        <input type="password" required minlength="4" placeholder="Repeat Password" name="repeatPassword" required>
        <h4 style="color:red"> ${errorMessage} </h4>
        <button type="submit" class="registerbtn">Register</button>
    </div>
</form>
</body>
</html>