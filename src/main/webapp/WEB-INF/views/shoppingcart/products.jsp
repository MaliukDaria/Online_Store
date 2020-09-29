<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Shopping cart</title>
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
        table {
            border-collapse: collapse;
            border-spacing: 0;
            width: 60%;
            border: 1px solid #ddd;
        }
        th, td {
            text-align: left;
            padding: 16px;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2
        }
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
<h2>Shopping Cart</h2>
<table border="1">
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Price</th>
    </tr>
    <c:forEach var="product" items="${products}">
        <tr>
            <td>
                <c:out value="${product.id}"/>
            </td>
            <td>
                <c:out value="${product.name}"/>
            </td>
            <td>
                <c:out value="${product.price}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}
                /shopping-cart/products/delete?id=${product.id}"> delete </a>
            </td>
        </tr>
    </c:forEach>
    <table >
        <tr>
            <th></th>
            <th></th>
            <th>Total sum:</th>
            <th>${totalSum}</th>
        </tr>
</table>
<form method="post" action="${pageContext.request.contextPath}/order/add">
    <br/> <br/>
    <button type="submit" class="registerbtn">Checkout</button>
</form>
</body>
</html>
