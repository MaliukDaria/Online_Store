<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Product catalog</title>
</head>
<body>
<h1>Product catalog</h1>
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
                /products/buy?id=${product.id}"> buy </a>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}
                /products/delete?id=${product.id}"> delete </a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
