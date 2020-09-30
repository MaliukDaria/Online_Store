<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Catalog</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        <%@include file="../styles/table.css"%>
        <%@include file="../styles/button.css"%>
        input[type=submit] {
            width: 100%;
            background-color: #34432f;
            color: white;
            padding: 14px 20px;
            margin: 8px 0;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
    </style>
</head>
<body>
<jsp:include page="../header/header.jsp"></jsp:include>
<h2>Product catalog</h2>
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
                /shopping-cart/products/add?id=${product.id}"> buy </a>
            </td>
        </tr>
    </c:forEach>
</table>
<br/> <br/>
<a href="${pageContext.request.contextPath}/shopping-cart/products"><button class="button">Go to shopping cart</button></a>
<br/> <br/>
</body>
</html>
