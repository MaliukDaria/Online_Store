<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        body {
            font-family: Arial, Helvetica, sans-serif;
        }


        input[type=text], input[type=password] {
            width: 50%;
            padding: 12px 20px;
            margin: 8px 0;
            display: inline-block;
            border: 1px solid #ccc;
            box-sizing: border-box;

        }

        button {
            background-color: #34432f;
            color: white;
            padding: 14px 20px;
            margin: 8px 0;
            border: none;
            cursor: pointer;
            width: 50%;
        }

        button:hover {
            opacity: 0.8;
        }

        .container {
            padding: 16px;
        }

        span.psw {
            float: right;
            padding-top: 16px;
        }
    </style>
</head>
<body>
<h1>Login</h1>
<form action="${pageContext.request.contextPath}/users/login" method="post">
    <h4 style="color:red"> ${errorMsg} </h4>
    Login </br>
    <input type="text" required placeholder=" Enter login" name="login" required> </br>

    Password </br>
    <input type="password" required placeholder="Enter password" name="password" required> </br>

    <button type="submit">Login</button>
    </br>
    <a href="${pageContext.request.contextPath}/users/registration">Register</a>
    </div>
</form>
</body>
</html>
