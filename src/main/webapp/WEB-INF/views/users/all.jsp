<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>All users</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        table {
            border-collapse: collapse;
            border-spacing: 0;
            width: auto;
            border: 1px solid #ddd;
        }
        th, td {
            text-align: left;
            padding: 16px;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2
        }
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
