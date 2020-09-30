<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Product catalog</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        <%@include file="../styles/table.css"%>
        <%@include file="../styles/button.css"%>
    </style>
</head>
<body>
<jsp:include page="../header/header.jsp"></jsp:include>
<h1>All products</h1>
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
                /products/delete?id=${product.id}"> delete </a>
            </td>
        </tr>
    </c:forEach>
</table>
<br/> <br/>
<a href="${pageContext.request.contextPath}/products/add">
    <button class="button">Add product</button>
</a>
<br/> <br/>
</body>
</html>
