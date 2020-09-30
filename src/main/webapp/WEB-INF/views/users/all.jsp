<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>All users</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        <%@include file="../styles/table.css"%>
    </style>
</head>
<body>
<jsp:include page="../header/header.jsp"></jsp:include>
<h2>All users</h2>
<table>
    <tr>
        <th>ID</th>
        <th>Login</th>
        <th>   </th>
    </tr>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>
                <c:out value="${user.id}"/>
            </td>
            <td>
                <c:out value="${user.login}"/>
            </td>
            <td>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}
                /users/delete?id=${user.id}"><button>delete</button></a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
