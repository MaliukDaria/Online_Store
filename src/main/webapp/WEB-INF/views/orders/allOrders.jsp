<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Orders</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        <%@include file="../styles/table.css"%>
    </style>
</head>
<body>
<jsp:include page="../header/header.jsp"></jsp:include>
<h2>All orders</h2>
<table border="1">
    <tr>
        <th>ID</th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>
                <c:out value="${order.id}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}
                /orders/details?id=${order.id}">order details</a>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}
                /orders/delete?id=${order.id}">Delete order</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
