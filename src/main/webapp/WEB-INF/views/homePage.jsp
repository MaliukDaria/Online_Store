<!DOCTYPE html>
<html lang="en">
<head>
    <title>Home page</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        body {
            font-family: Arial, Helvetica, sans-serif;
            margin: 0;
        }
        .header-img {
            width: 100%;
            height: 200px;
            background:  url('https://yls89h5e1x-flywheel.netdna-ssl.com/wp-content/uploads/2019/08/iStock-image-of-Tatras_Slovakia_1.jpg');
            background-size: cover;
        }
        .header h1 {
            font-size: 40px;
        }
        .navbar {
            overflow: hidden;
            background-color: #34432f;
        }
        .navbar a {
            float: left;
            display: block;
            color: white;
            text-align: center;
            padding: 14px 20px;
            text-decoration: none;
        }
        .navbar a.right {
            float: right;
        }
        .navbar a:hover {
            background-color: #ddd;
            color: #4c4c4c;
        }
    </style>
</head>
<body>
<div class="header-img"></div>
<div class="navbar">
    <a href="${pageContext.request.contextPath}/products/all">Product catalog</a>
    <a href="${pageContext.request.contextPath}/shopping-cart/products">Shopping cart</a>
    <a href="${pageContext.request.contextPath}/orders/user">Orders history</a>
    <a href="${pageContext.request.contextPath}/users/all">All users</a>
    <a href="${pageContext.request.contextPath}/products/manage">Product catalog(Admin)</a>
    <a href="${pageContext.request.contextPath}/orders/all">Orders(Admin)</a>
    <a href="${pageContext.request.contextPath}/users/login" class="right">Login</a>
    <a href="${pageContext.request.contextPath}/users/logout" class="right">Logout</a>
    <a href="${pageContext.request.contextPath}/users/registration" class="right">Register</a>

</div>
<br/>
<a href="${pageContext.request.contextPath}
/users/injectData">Inject test data into the DB</a>
</body>
</html>
