<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Shopping cart</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        <%@include file="../styles/table.css"%>
        <%@include file="../styles/button.css"%>
    </style>
</head>
<body>
<jsp:include page="../header/header.jsp"></jsp:include>
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
    <button type="submit" class="button">Checkout</button>
</form>
</body>
</html>
